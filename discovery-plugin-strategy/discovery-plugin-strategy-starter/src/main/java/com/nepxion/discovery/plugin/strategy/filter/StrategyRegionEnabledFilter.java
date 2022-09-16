package com.nepxion.discovery.plugin.strategy.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.netflix.loadbalancer.Server;

public class StrategyRegionEnabledFilter extends AbstractStrategyEnabledFilter {
    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_REGION_TRANSFER_ENABLED + ":false}")
    protected Boolean regionTransferEnabled;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_REGION_FAILOVER_ENABLED + ":false}")
    protected Boolean regionFailoverEnabled;

    @Override
    public boolean apply(List<? extends Server> servers, Server server) {
        String serviceId = pluginAdapter.getServerServiceId(server);

        String region = pluginAdapter.getServerRegion(server);

        String regions = JsonUtil.fromJsonMap(pluginContextHolder.getContextRouteRegion(), serviceId);
        if (StringUtils.isEmpty(regions)) {
            // 流量路由到指定的区域下。当未对服务指定访问区域的时候，路由到事先指定的区域
            // 使用场景示例：
            // 开发环境（个人电脑环境）在测试环境（线上环境）进行联调
            // 访问路径为A服务 -> B服务 -> C服务，A服务和B服务在开发环境上，C服务在测试环境上
            // 调用时候，在最前端传入的Header（n-d-region）指定为B的开发环境区域（用来保证A服务和B服务只在开发环境调用），而B服务会自动路由调用到测试环境上的C服务实例，但不会路由到其它个人电脑的C服务实例
            // 该功能的意义，个人电脑环境可以接入到测试环境联调，当多套个人环境接入时候，可以保护不同的个人环境间不会彼此调用
            if (regionTransferEnabled) {
                String regionTransfers = JsonUtil.fromJsonMap(pluginContextHolder.getContextRouteRegionTransfer(), serviceId);
                if (StringUtils.isEmpty(regionTransfers)) {
                    throw new DiscoveryException("The Region Transfer value is missing");
                }

                return discoveryMatcher.match(regionTransfers, region, true);
            } else {
                return true;
            }
        }

        if (regionFailoverEnabled) {
            boolean matched = matchByRegion(servers, regions);
            if (!matched) {
                // 判断提供端服务的元数据多活标记
                boolean isServerActive = pluginAdapter.isServerActive(server);
                // 如果提供端为多活服务，消费端不执行故障转移
                if (isServerActive) {
                    return false;
                }

                String regionFailovers = JsonUtil.fromJsonMap(pluginContextHolder.getContextRouteRegionFailover(), serviceId);
                if (StringUtils.isEmpty(regionFailovers)) {
                    return true;
                } else {
                    return discoveryMatcher.match(regionFailovers, region, true);
                }
            }
        }

        return discoveryMatcher.match(regions, region, true);
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 6;
    }
}
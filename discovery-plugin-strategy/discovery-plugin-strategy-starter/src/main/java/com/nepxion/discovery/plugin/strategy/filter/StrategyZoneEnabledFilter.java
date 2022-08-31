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

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.netflix.loadbalancer.Server;

public class StrategyZoneEnabledFilter extends AbstractStrategyEnabledFilter {
    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_ZONE_AFFINITY_ENABLED + ":false}")
    protected Boolean zoneAffinityEnabled;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_ZONE_ROUTE_ENABLED + ":true}")
    protected Boolean zoneRouteEnabled;

    @Override
    public boolean apply(List<? extends Server> servers, Server server) {
        if (!zoneAffinityEnabled) {
            return true;
        }

        String zone = pluginAdapter.getZone();
        // 当服务未接入本框架或者版本号未设置（表现出来的值为DiscoveryConstant.DEFAULT），则不过滤，返回
        if (StringUtils.equals(zone, DiscoveryConstant.DEFAULT)) {
            return true;
        }

        String serverZone = pluginAdapter.getServerZone(server);

        boolean found = findByZone(servers, zone);
        if (found) {
            // 可用区存在：执行可用区亲和性，即调用端实例和提供端实例的元数据Metadata的zone配置值相等才能调用
            return StringUtils.equals(serverZone, zone);
        } else {
            // 可用区不存在：路由开关打开，可路由到其它可用区；路由开关关闭，不可路由到其它可用区或者不归属任何可用区
            if (zoneRouteEnabled) {
                return true;
            } else {
                return StringUtils.equals(serverZone, zone);
            }
        }
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 4;
    }
}
package com.nepxion.discovery.plugin.example.gateway.extension;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nepxion.discovery.plugin.strategy.extension.gateway.context.GatewayStrategyContext;
import com.nepxion.discovery.plugin.strategy.extension.gateway.impl.VersionDiscoveryEnabledAdapter;
import com.netflix.loadbalancer.Server;

// 实现了组合策略，版本路由策略+自定义策略
// 如果不想要版本路由策略，请直接implements DiscoveryEnabledAdapter，实现自定义策略 
public class MyDiscoveryEnabledAdapter extends VersionDiscoveryEnabledAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(MyDiscoveryEnabledAdapter.class);

    @Override
    public boolean apply(Server server, Map<String, String> metadata) {
        // 1.对Rest调用传来的Header的路由Version做策略。注意这个Version不是灰度发布的Version
        boolean enabled = super.apply(server, metadata);
        if (!enabled) {
            return false;
        }

        // 2.对Rest调用传来的Header参数（例如Token）做策略
        return applyFromHeader(server, metadata);
    }

    // 根据Rest调用传来的Header参数（例如Token），选取执行调用请求的服务实例
    private boolean applyFromHeader(Server server, Map<String, String> metadata) {
        GatewayStrategyContext context = GatewayStrategyContext.getCurrentContext();
        String token = context.getExchange().getRequest().getHeaders().getFirst("token");
        // String value = context.getExchange().getRequest().getQueryParams().getFirst("value");

        String serviceId = server.getMetaInfo().getAppName().toLowerCase();

        LOG.info("Gateway端负载均衡用户定制触发：serviceId={}, host={}, metadata={}, context={}", serviceId, server.toString(), metadata, context);

        String filterToken = "abc";
        if (StringUtils.isNotEmpty(token) && token.contains(filterToken)) {
            LOG.info("过滤条件：当Token含有'{}'的时候，不能被Ribbon负载均衡到", filterToken);

            return false;
        }

        return true;
    }
}
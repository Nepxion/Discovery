package com.nepxion.discovery.plugin.example.gateway.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ServerWebExchange;

import com.nepxion.discovery.plugin.strategy.adapter.DiscoveryEnabledStrategy;
import com.nepxion.discovery.plugin.strategy.gateway.context.GatewayStrategyContextHolder;
import com.netflix.loadbalancer.Server;

// 实现了组合策略，版本路由策略+区域路由策略+自定义策略
public class MyDiscoveryEnabledStrategy implements DiscoveryEnabledStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(MyDiscoveryEnabledStrategy.class);

    @Autowired
    private GatewayStrategyContextHolder gatewayStrategyContextHolder;

    @Override
    public boolean apply(Server server, Map<String, String> metadata) {
        // 对Rest调用传来的Header参数（例如Token）做策略
        return applyFromHeader(server, metadata);
    }

    // 根据Rest调用传来的Header参数（例如Token），选取执行调用请求的服务实例
    private boolean applyFromHeader(Server server, Map<String, String> metadata) {
        ServerWebExchange exchange = gatewayStrategyContextHolder.getExchange();
        if (exchange == null) {
            return true;
        }

        String token = exchange.getRequest().getHeaders().getFirst("token");
        // String value = exchange.getRequest().getQueryParams().getFirst("value");

        String serviceId = server.getMetaInfo().getAppName().toLowerCase();

        LOG.info("Gateway端负载均衡用户定制触发：serviceId={}, host={}, metadata={}", serviceId, server.toString(), metadata);

        String filterToken = "abc";
        if (StringUtils.isNotEmpty(token) && token.contains(filterToken)) {
            LOG.info("过滤条件：当Token含有'{}'的时候，不能被Ribbon负载均衡到", filterToken);

            return false;
        }

        return true;
    }
}
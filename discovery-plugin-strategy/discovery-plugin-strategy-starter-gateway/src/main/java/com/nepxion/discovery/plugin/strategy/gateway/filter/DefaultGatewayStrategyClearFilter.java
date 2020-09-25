package com.nepxion.discovery.plugin.strategy.gateway.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;

import com.nepxion.discovery.plugin.strategy.gateway.context.GatewayStrategyContext;
import com.nepxion.discovery.plugin.strategy.gateway.monitor.GatewayStrategyMonitor;

public class DefaultGatewayStrategyClearFilter implements GatewayStrategyClearFilter {
    @Autowired(required = false)
    protected GatewayStrategyMonitor gatewayStrategyMonitor;

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        GatewayStrategyContext.clearCurrentContext();

        // 调用链释放
        if (gatewayStrategyMonitor != null) {
            gatewayStrategyMonitor.release(exchange);
        }

        return chain.filter(exchange);
    }
}
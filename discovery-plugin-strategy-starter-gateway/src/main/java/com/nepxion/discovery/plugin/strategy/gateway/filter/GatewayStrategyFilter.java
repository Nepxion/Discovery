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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;

import com.nepxion.discovery.plugin.strategy.gateway.constant.GatewayStrategyConstant;
import com.nepxion.discovery.plugin.strategy.gateway.context.GatewayStrategyContext;

public class GatewayStrategyFilter implements GlobalFilter, Ordered {
    private static final Logger LOG = LoggerFactory.getLogger(GatewayStrategyFilter.class);

    @Value("${" + GatewayStrategyConstant.SPRING_APPLICATION_STRATEGY_GATEWAY_ROUTE_FILTER_ORDER + ":" + GatewayStrategyConstant.SPRING_APPLICATION_STRATEGY_GATEWAY_ROUTE_FILTER_ORDER_VALUE + "}")
    protected Integer filterOrder;

    @Override
    public int getOrder() {
        return filterOrder - 1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        LOG.debug("Gateway strategy context is set with {}", exchange);

        GatewayStrategyContext.getCurrentContext().setExchange(exchange);

        return chain.filter(exchange);
    }
}
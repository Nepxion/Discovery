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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;
import com.nepxion.discovery.plugin.strategy.gateway.constant.GatewayStrategyConstant;

public abstract class AbstractGatewayStrategyRouteFilter implements GlobalFilter, Ordered, GatewayStrategyRouteFilter {
    @Autowired
    private ConfigurableEnvironment environment;

    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected StrategyContextHolder strategyContextHolder;

    @Override
    public int getOrder() {
        return environment.getProperty(GatewayStrategyConstant.SPRING_APPLICATION_STRATEGY_GATEWAY_ROUTE_FILTER_ORDER, Integer.class, GatewayStrategyConstant.SPRING_APPLICATION_STRATEGY_GATEWAY_ROUTE_FILTER_ORDER_VALUE);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String routeVersion = getRouteVersion();
        String routeRegion = getRouteRegion();
        String routeAddress = getRouteAddress();
        String routeVersionWeight = getRouteVersionWeight();
        String routeRegionWeight = getRouteRegionWeight();

        // 通过过滤器设置路由Header头部信息，来取代界面（Postman）上的设置，并全链路传递到服务端
        ServerHttpRequest.Builder requestBuilder = exchange.getRequest().mutate();
        if (StringUtils.isNotEmpty(routeVersion)) {
            requestBuilder.header(DiscoveryConstant.N_D_VERSION, routeVersion);
        }
        if (StringUtils.isNotEmpty(routeRegion)) {
            requestBuilder.header(DiscoveryConstant.N_D_REGION, routeRegion);
        }
        if (StringUtils.isNotEmpty(routeAddress)) {
            requestBuilder.header(DiscoveryConstant.N_D_ADDRESS, routeAddress);
        }
        if (StringUtils.isNotEmpty(routeVersionWeight)) {
            requestBuilder.header(DiscoveryConstant.N_D_VERSION_WEIGHT, routeVersionWeight);
        }
        if (StringUtils.isNotEmpty(routeRegionWeight)) {
            requestBuilder.header(DiscoveryConstant.N_D_REGION_WEIGHT, routeRegionWeight);
        }

        // 开启此项，将启动提供端的服务隔离机制，需要在网关上传递Group Header
        if (environment.getProperty(StrategyConstant.SPRING_APPLICATION_STRATEGY_PROVIDER_ISOLATION_ENABLED, Boolean.class, Boolean.FALSE)) {
            requestBuilder.header(DiscoveryConstant.N_D_GROUP, pluginAdapter.getGroup());
        }

        ServerHttpRequest newRequest = requestBuilder.build();
        ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();

        return chain.filter(newExchange);
    }

    public PluginAdapter getPluginAdapter() {
        return pluginAdapter;
    }

    public StrategyContextHolder getStrategyContextHolder() {
        return strategyContextHolder;
    }
}
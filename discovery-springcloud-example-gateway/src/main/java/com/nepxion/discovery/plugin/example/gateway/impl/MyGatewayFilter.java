package com.nepxion.discovery.plugin.example.gateway.impl;

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
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.StrategyEntity;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.gateway.constant.GatewayStrategyConstant;

public class MyGatewayFilter implements GlobalFilter, Ordered {
    @Autowired
    private PluginAdapter pluginAdapter;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String routeVersion = getRouteVersionFromConfig();
        // String routeVersion = getRouteVersionFromCustomer();

        String routeRegion = getRouteRegionFromConfig();
        // String routeRegion = getRouteRegionFromCustomer();

        System.out.println("Route Version=" + routeVersion);
        System.out.println("Route Region=" + routeRegion);

        // 通过过滤器设置路由Header头部信息，来取代界面（Postman）上的设置，并全链路传递到服务端
        ServerHttpRequest newRequest = exchange.getRequest().mutate().header(DiscoveryConstant.N_D_VERSION, routeVersion).header(DiscoveryConstant.N_D_REGION, routeRegion).build();
        ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();

        return chain.filter(newExchange);
    }

    @Override
    public int getOrder() {
        // Order必须小于8888
        return GatewayStrategyConstant.SPRING_APPLICATION_STRATEGY_GATEWAY_FILTER_ORDER_VALUE - 1;
    }

    // 从远程配置中心或者本地配置文件获取版本路由配置。如果是远程配置中心，则值会动态改变
    protected String getRouteVersionFromConfig() {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity != null) {
            StrategyEntity strategyEntity = ruleEntity.getStrategyEntity();
            if (strategyEntity != null) {
                return strategyEntity.getVersionValue();
            }
        }

        return null;
    }

    // 从远程配置中心或者本地配置文件获取区域路由配置。如果是远程配置中心，则值会动态改变
    protected String getRouteRegionFromConfig() {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity != null) {
            StrategyEntity strategyEntity = ruleEntity.getStrategyEntity();
            if (strategyEntity != null) {
                return strategyEntity.getRegionValue();
            }
        }

        return null;
    }

    // 自定义版本路由配置
    protected String getRouteVersionFromCustomer() {
        return "{\"discovery-springcloud-example-a\":\"1.0\", \"discovery-springcloud-example-b\":\"1.0\", \"discovery-springcloud-example-c\":\"1.0;1.2\"}";
    }

    // 自定义区域路由配置
    protected String getRouteRegionFromCustomer() {
        return "{\"discovery-springcloud-example-a\":\"qa;dev\", \"discovery-springcloud-example-b\":\"dev\", \"discovery-springcloud-example-c\":\"qa\"}";
    }
}
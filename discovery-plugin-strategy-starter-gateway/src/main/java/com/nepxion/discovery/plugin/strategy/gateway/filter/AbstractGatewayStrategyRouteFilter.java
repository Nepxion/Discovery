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
import com.nepxion.discovery.plugin.strategy.tracer.StrategyTracer;

public abstract class AbstractGatewayStrategyRouteFilter implements GlobalFilter, Ordered, GatewayStrategyRouteFilter {
    @Autowired
    private ConfigurableEnvironment environment;

    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected StrategyContextHolder strategyContextHolder;

    @Autowired(required = false)
    private StrategyTracer strategyTracer;

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

        // 通过过滤器设置路由Header头部信息，并全链路传递到服务端
        // 如果外界也传了相同的Header，例如，从Postman传递过来的Header，当下面的变量为true，以网关设置为优先，否则以外界传值为优先
        Boolean gatewayHeaderPriority = environment.getProperty(GatewayStrategyConstant.SPRING_APPLICATION_STRATEGY_GATEWAY_HEADER_PRIORITY, Boolean.class, Boolean.TRUE);

        // 通过过滤器设置路由Header头部信息，并全链路传递到服务端
        ServerHttpRequest.Builder requestBuilder = exchange.getRequest().mutate();
        if (StringUtils.isNotEmpty(routeVersion)) {
            GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_VERSION, routeVersion, gatewayHeaderPriority);
        }
        if (StringUtils.isNotEmpty(routeRegion)) {
            GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_REGION, routeRegion, gatewayHeaderPriority);
        }
        if (StringUtils.isNotEmpty(routeAddress)) {
            GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_ADDRESS, routeAddress, gatewayHeaderPriority);
        }
        if (StringUtils.isNotEmpty(routeVersionWeight)) {
            GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_VERSION_WEIGHT, routeVersionWeight, gatewayHeaderPriority);
        }
        if (StringUtils.isNotEmpty(routeRegionWeight)) {
            GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_REGION_WEIGHT, routeRegionWeight, gatewayHeaderPriority);
        }

        // 开启此项，将启动提供端的服务隔离机制，则传递到服务的是网关自身ServiceType, ServiceId, ServiceHost, Group
        // 如果不隔离，为了调用链完整，最好外界自身传递ServiceType, ServiceId, ServiceHost, Group
        Boolean providerIsolationEnabled = environment.getProperty(StrategyConstant.SPRING_APPLICATION_STRATEGY_PROVIDER_ISOLATION_ENABLED, Boolean.class, Boolean.FALSE);

        GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_SERVICE_TYPE, pluginAdapter.getServiceType(), providerIsolationEnabled ? providerIsolationEnabled : gatewayHeaderPriority);
        GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_SERVICE_ID, pluginAdapter.getServiceId(), providerIsolationEnabled ? providerIsolationEnabled : gatewayHeaderPriority);
        GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_SERVICE_ADDRESS, pluginAdapter.getHost() + ":" + pluginAdapter.getPort(), providerIsolationEnabled ? providerIsolationEnabled : gatewayHeaderPriority);
        GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_SERVICE_GROUP, pluginAdapter.getGroup(), providerIsolationEnabled ? providerIsolationEnabled : gatewayHeaderPriority);
        GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_SERVICE_VERSION, pluginAdapter.getVersion(), providerIsolationEnabled ? providerIsolationEnabled : gatewayHeaderPriority);
        GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_SERVICE_REGION, pluginAdapter.getRegion(), providerIsolationEnabled ? providerIsolationEnabled : gatewayHeaderPriority);

        // 调用链追踪
        if (strategyTracer != null) {
            strategyTracer.traceHeader();
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
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

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;
import com.nepxion.discovery.plugin.strategy.gateway.constant.GatewayStrategyConstant;
import com.nepxion.discovery.plugin.strategy.gateway.context.GatewayStrategyContext;
import com.nepxion.discovery.plugin.strategy.gateway.tracer.GatewayStrategyTracer;

public abstract class AbstractGatewayStrategyRouteFilter implements GatewayStrategyRouteFilter {
    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected StrategyContextHolder strategyContextHolder;

    @Autowired(required = false)
    private List<GatewayStrategyTracer> gatewayStrategyTracerList;

    // 如果外界也传了相同的Header，例如，从Postman传递过来的Header，当下面的变量为true，以网关设置为优先，否则以外界传值为优先
    @Value("${" + GatewayStrategyConstant.SPRING_APPLICATION_STRATEGY_GATEWAY_HEADER_PRIORITY + ":true}")
    protected Boolean gatewayHeaderPriority;

    // 当以网关设置为优先的时候，网关未配置Header，而外界配置了Header，仍旧忽略外界的Header
    @Value("${" + GatewayStrategyConstant.SPRING_APPLICATION_STRATEGY_GATEWAY_ORIGINAL_HEADER_IGNORED + ":true}")
    protected Boolean gatewayOriginalHeaderIgnored;

    @Value("${" + GatewayStrategyConstant.SPRING_APPLICATION_STRATEGY_GATEWAY_ROUTE_FILTER_ORDER + ":" + GatewayStrategyConstant.SPRING_APPLICATION_STRATEGY_GATEWAY_ROUTE_FILTER_ORDER_VALUE + "}")
    protected Integer filterOrder;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACE_ENABLED + ":false}")
    protected Boolean strategyTraceEnabled;

    @Override
    public int getOrder() {
        return filterOrder;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 把ServerWebExchange放入ThreadLocal中
        GatewayStrategyContext.getCurrentContext().setExchange(exchange);

        String routeVersion = getRouteVersion();
        String routeRegion = getRouteRegion();
        String routeAddress = getRouteAddress();
        String routeVersionWeight = getRouteVersionWeight();
        String routeRegionWeight = getRouteRegionWeight();

        // 通过过滤器设置路由Header头部信息，并全链路传递到服务端
        ServerHttpRequest.Builder requestBuilder = exchange.getRequest().mutate();
        if (StringUtils.isNotEmpty(routeVersion)) {
            GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_VERSION, routeVersion, gatewayHeaderPriority);
        } else {
            GatewayStrategyFilterResolver.ignoreHeader(requestBuilder, DiscoveryConstant.N_D_VERSION, gatewayHeaderPriority, gatewayOriginalHeaderIgnored);
        }
        if (StringUtils.isNotEmpty(routeRegion)) {
            GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_REGION, routeRegion, gatewayHeaderPriority);
        } else {
            GatewayStrategyFilterResolver.ignoreHeader(requestBuilder, DiscoveryConstant.N_D_REGION, gatewayHeaderPriority, gatewayOriginalHeaderIgnored);
        }
        if (StringUtils.isNotEmpty(routeAddress)) {
            GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_ADDRESS, routeAddress, gatewayHeaderPriority);
        } else {
            GatewayStrategyFilterResolver.ignoreHeader(requestBuilder, DiscoveryConstant.N_D_ADDRESS, gatewayHeaderPriority, gatewayOriginalHeaderIgnored);
        }
        if (StringUtils.isNotEmpty(routeVersionWeight)) {
            GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_VERSION_WEIGHT, routeVersionWeight, gatewayHeaderPriority);
        } else {
            GatewayStrategyFilterResolver.ignoreHeader(requestBuilder, DiscoveryConstant.N_D_VERSION_WEIGHT, gatewayHeaderPriority, gatewayOriginalHeaderIgnored);
        }
        if (StringUtils.isNotEmpty(routeRegionWeight)) {
            GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_REGION_WEIGHT, routeRegionWeight, gatewayHeaderPriority);
        } else {
            GatewayStrategyFilterResolver.ignoreHeader(requestBuilder, DiscoveryConstant.N_D_REGION_WEIGHT, gatewayHeaderPriority, gatewayOriginalHeaderIgnored);
        }

        GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_SERVICE_GROUP, pluginAdapter.getGroup(), gatewayHeaderPriority);
        if (strategyTraceEnabled) {
            GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_SERVICE_TYPE, pluginAdapter.getServiceType(), gatewayHeaderPriority);
            String serviceAppId = pluginAdapter.getServiceAppId();
            if (StringUtils.isNotEmpty(serviceAppId)) {
                GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_SERVICE_APP_ID, serviceAppId, gatewayHeaderPriority);
            }
            GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_SERVICE_ID, pluginAdapter.getServiceId(), gatewayHeaderPriority);
            GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_SERVICE_ADDRESS, pluginAdapter.getHost() + ":" + pluginAdapter.getPort(), gatewayHeaderPriority);
            GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_SERVICE_VERSION, pluginAdapter.getVersion(), gatewayHeaderPriority);
            GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_SERVICE_REGION, pluginAdapter.getRegion(), gatewayHeaderPriority);
            GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_SERVICE_ENVIRONMENT, pluginAdapter.getEnvironment(), gatewayHeaderPriority);
        }

        ServerHttpRequest newRequest = requestBuilder.build();
        ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();

        ServerWebExchange extensionExchange = extendFilter(newExchange, chain);

        ServerWebExchange finalExchange = extensionExchange != null ? extensionExchange : newExchange;

        // 把新的ServerWebExchange放入ThreadLocal中
        GatewayStrategyContext.getCurrentContext().setExchange(newExchange);

        // 调用链追踪
        if (CollectionUtils.isNotEmpty(gatewayStrategyTracerList)) {
            for (GatewayStrategyTracer gatewayStrategyTracer : gatewayStrategyTracerList) {
                gatewayStrategyTracer.trace(finalExchange);
            }
        }

        return chain.filter(finalExchange);
    }

    protected ServerWebExchange extendFilter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return null;
    }

    public PluginAdapter getPluginAdapter() {
        return pluginAdapter;
    }

    public StrategyContextHolder getStrategyContextHolder() {
        return strategyContextHolder;
    }
}
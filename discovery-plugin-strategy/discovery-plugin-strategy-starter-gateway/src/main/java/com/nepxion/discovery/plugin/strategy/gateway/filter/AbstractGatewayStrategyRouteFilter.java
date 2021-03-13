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

import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.gateway.constant.GatewayStrategyConstant;
import com.nepxion.discovery.plugin.strategy.gateway.context.GatewayStrategyContext;
import com.nepxion.discovery.plugin.strategy.gateway.monitor.GatewayStrategyMonitor;
import com.nepxion.discovery.plugin.strategy.wrapper.StrategyWrapper;

public abstract class AbstractGatewayStrategyRouteFilter implements GatewayStrategyRouteFilter {
    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected StrategyWrapper strategyWrapper;

    @Autowired(required = false)
    protected GatewayStrategyMonitor gatewayStrategyMonitor;

    // 如果外界也传了相同的Header，例如，从Postman传递过来的Header，当下面的变量为true，以网关设置为优先，否则以外界传值为优先
    @Value("${" + GatewayStrategyConstant.SPRING_APPLICATION_STRATEGY_GATEWAY_HEADER_PRIORITY + ":true}")
    protected Boolean gatewayHeaderPriority;

    // 当以网关设置为优先的时候，网关未配置Header，而外界配置了Header，仍旧忽略外界的Header
    @Value("${" + GatewayStrategyConstant.SPRING_APPLICATION_STRATEGY_GATEWAY_ORIGINAL_HEADER_IGNORED + ":true}")
    protected Boolean gatewayOriginalHeaderIgnored;

    // Gateway上核心策略Header是否传递。当全局订阅启动时，可以关闭核心策略Header传递，这样可以节省传递数据的大小，一定程度上可以提升性能。核心策略Header，包含如下
    // 1. n-d-version
    // 2. n-d-region
    // 3. n-d-address
    // 4. n-d-version-weight
    // 5. n-d-region-weight
    // 6. n-d-id-blacklist
    // 7. n-d-address-blacklist
    // 8. n-d-env (不属于蓝绿灰度范畴的Header，只要外部传入就会全程传递)
    @Value("${" + GatewayStrategyConstant.SPRING_APPLICATION_STRATEGY_GATEWAY_CORE_HEADER_TRANSMISSION_ENABLED + ":true}")
    protected Boolean gatewayCoreHeaderTransmissionEnabled;

    @Value("${" + GatewayStrategyConstant.SPRING_APPLICATION_STRATEGY_GATEWAY_ROUTE_FILTER_ORDER + ":" + GatewayStrategyConstant.SPRING_APPLICATION_STRATEGY_GATEWAY_ROUTE_FILTER_ORDER_VALUE + "}")
    protected Integer filterOrder;

    @Override
    public int getOrder() {
        return filterOrder;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 把ServerWebExchange放入ThreadLocal中
        GatewayStrategyContext.getCurrentContext().setExchange(exchange);

        // 通过过滤器设置路由Header头部信息，并全链路传递到服务端
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder requestBuilder = request.mutate();

        String routeEnvironment = getRouteEnvironment();
        // 通过过滤器设置路由Header头部信息，并全链路传递到服务端
        if (StringUtils.isNotEmpty(routeEnvironment)) {
            GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_ENVIRONMENT, routeEnvironment, false);
        }

        if (gatewayCoreHeaderTransmissionEnabled) {
            // 内置Header预先塞入
            Map<String, String> headerMap = strategyWrapper.getHeaderMap();
            if (MapUtils.isNotEmpty(headerMap)) {
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    GatewayStrategyFilterResolver.setHeader(requestBuilder, key, value, gatewayHeaderPriority);
                }
            }

            String routeVersion = getRouteVersion();
            String routeRegion = getRouteRegion();
            String routeAddress = getRouteAddress();
            String routeVersionWeight = getRouteVersionWeight();
            String routeRegionWeight = getRouteRegionWeight();
            String routeIdBlacklist = getRouteIdBlacklist();
            String routeAddressBlacklist = getRouteAddressBlacklist();
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
            if (StringUtils.isNotEmpty(routeIdBlacklist)) {
                GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_ID_BLACKLIST, routeIdBlacklist, gatewayHeaderPriority);
            } else {
                GatewayStrategyFilterResolver.ignoreHeader(requestBuilder, DiscoveryConstant.N_D_ID_BLACKLIST, gatewayHeaderPriority, gatewayOriginalHeaderIgnored);
            }
            if (StringUtils.isNotEmpty(routeAddressBlacklist)) {
                GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_ADDRESS_BLACKLIST, routeAddressBlacklist, gatewayHeaderPriority);
            } else {
                GatewayStrategyFilterResolver.ignoreHeader(requestBuilder, DiscoveryConstant.N_D_ADDRESS_BLACKLIST, gatewayHeaderPriority, gatewayOriginalHeaderIgnored);
            }
        } else {
            GatewayStrategyFilterResolver.ignoreHeader(requestBuilder, DiscoveryConstant.N_D_VERSION);
            GatewayStrategyFilterResolver.ignoreHeader(requestBuilder, DiscoveryConstant.N_D_REGION);
            GatewayStrategyFilterResolver.ignoreHeader(requestBuilder, DiscoveryConstant.N_D_ADDRESS);
            GatewayStrategyFilterResolver.ignoreHeader(requestBuilder, DiscoveryConstant.N_D_VERSION_WEIGHT);
            GatewayStrategyFilterResolver.ignoreHeader(requestBuilder, DiscoveryConstant.N_D_REGION_WEIGHT);
            GatewayStrategyFilterResolver.ignoreHeader(requestBuilder, DiscoveryConstant.N_D_ID_BLACKLIST);
            GatewayStrategyFilterResolver.ignoreHeader(requestBuilder, DiscoveryConstant.N_D_ADDRESS_BLACKLIST);
        }

        // 对于服务A -> 网关 -> 服务B调用链
        // 域网关下(gatewayHeaderPriority=true)，只传递网关自身的group，不传递服务A的group，起到基于组的网关端服务调用隔离
        // 非域网关下(gatewayHeaderPriority=false)，优先传递服务A的group，基于组的网关端服务调用隔离不生效，但可以实现基于相关参数的熔断限流等功能        
        GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_SERVICE_GROUP, pluginAdapter.getGroup(), gatewayHeaderPriority);
        // 网关只负责传递服务A的相关参数（例如：serviceId），不传递自身的参数，实现基于相关参数的熔断限流等功能
        GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_SERVICE_TYPE, pluginAdapter.getServiceType(), false);
        String serviceAppId = pluginAdapter.getServiceAppId();
        if (StringUtils.isNotEmpty(serviceAppId)) {
            GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_SERVICE_APP_ID, serviceAppId, false);
        }
        GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_SERVICE_ID, pluginAdapter.getServiceId(), false);
        GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_SERVICE_ADDRESS, pluginAdapter.getHost() + ":" + pluginAdapter.getPort(), false);
        GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_SERVICE_VERSION, pluginAdapter.getVersion(), false);
        GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_SERVICE_REGION, pluginAdapter.getRegion(), false);
        GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_SERVICE_ENVIRONMENT, pluginAdapter.getEnvironment(), false);
        GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.N_D_SERVICE_ZONE, pluginAdapter.getZone(), false);

        // 调用链监控
        if (gatewayStrategyMonitor != null) {
            gatewayStrategyMonitor.monitor(exchange);
        }

        // 拦截侦测请求
        String path = request.getPath().toString();
        if (path.contains(DiscoveryConstant.INSPECTOR_ENDPOINT_URL)) {
            GatewayStrategyFilterResolver.setHeader(requestBuilder, DiscoveryConstant.INSPECTOR_ENDPOINT_HEADER, pluginAdapter.getPluginInfo(null), true);
        }

        ServerHttpRequest newRequest = requestBuilder.build();
        ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();

        // 把新的ServerWebExchange放入ThreadLocal中
        GatewayStrategyContext.getCurrentContext().setExchange(newExchange);

        return chain.filter(newExchange);
    }

    public PluginAdapter getPluginAdapter() {
        return pluginAdapter;
    }
}
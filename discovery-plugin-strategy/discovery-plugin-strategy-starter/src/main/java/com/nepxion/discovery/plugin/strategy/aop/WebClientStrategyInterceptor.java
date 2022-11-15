package com.nepxion.discovery.plugin.strategy.aop;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author Fengfeng Li
 * @version 1.0
 */

import reactor.core.publisher.Mono;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.InterceptorType;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;
import com.nepxion.discovery.plugin.strategy.util.StrategyUtil;

public class WebClientStrategyInterceptor extends AbstractStrategyInterceptor implements ExchangeFilterFunction {
    @Autowired
    protected StrategyContextHolder strategyContextHolder;

    // WebClient核心策略Header是否传递。当全局订阅启动时，可以关闭核心策略Header传递，这样可以节省传递数据的大小，一定程度上可以提升性能
    // 核心策略Header指n-d-开头的Header（不包括n-d-env，因为环境路由隔离，必须传递该Header），不包括n-d-service开头的Header
    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_WEB_CLIENT_CORE_HEADER_TRANSMISSION_ENABLED + ":true}")
    protected Boolean webClientCoreHeaderTransmissionEnabled;

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        ClientRequest.Builder requestBuilder = ClientRequest.from(request);

        // 拦截打印输入的Header
        interceptInputHeader();

        // 处理内部Header的转发
        applyInnerHeader(requestBuilder);

        // 处理外部Header的转发
        applyOuterHeader(requestBuilder);

        ClientRequest newRequest = requestBuilder.build();

        // 拦截打印输出的Header
        interceptOutputHeader(newRequest);

        return next.exchange(newRequest);
    }

    // 处理内部Header的转发，即把本地服务的相关属性封装成Header转发到下游服务去
    private void applyInnerHeader(ClientRequest.Builder requestBuilder) {
        requestBuilder.header(DiscoveryConstant.N_D_SERVICE_GROUP, pluginAdapter.getGroup());
        requestBuilder.header(DiscoveryConstant.N_D_SERVICE_TYPE, pluginAdapter.getServiceType());
        String serviceAppId = pluginAdapter.getServiceAppId();
        if (StringUtils.isNotEmpty(serviceAppId)) {
            requestBuilder.header(DiscoveryConstant.N_D_SERVICE_APP_ID, serviceAppId);
        }
        requestBuilder.header(DiscoveryConstant.N_D_SERVICE_ID, pluginAdapter.getServiceId());
        requestBuilder.header(DiscoveryConstant.N_D_SERVICE_ADDRESS, pluginAdapter.getHost() + ":" + pluginAdapter.getPort());
        String version = pluginAdapter.getVersion();
        if (StringUtils.isNotEmpty(version) && !StringUtils.equals(version, DiscoveryConstant.DEFAULT)) {
            requestBuilder.header(DiscoveryConstant.N_D_SERVICE_VERSION, version);
        }
        String region = pluginAdapter.getRegion();
        if (StringUtils.isNotEmpty(region) && !StringUtils.equals(region, DiscoveryConstant.DEFAULT)) {
            requestBuilder.header(DiscoveryConstant.N_D_SERVICE_REGION, region);
        }
        String environment = pluginAdapter.getEnvironment();
        if (StringUtils.isNotEmpty(environment) && !StringUtils.equals(environment, DiscoveryConstant.DEFAULT)) {
            requestBuilder.header(DiscoveryConstant.N_D_SERVICE_ENVIRONMENT, environment);
        }
        String zone = pluginAdapter.getZone();
        if (StringUtils.isNotEmpty(zone) && !StringUtils.equals(zone, DiscoveryConstant.DEFAULT)) {
            requestBuilder.header(DiscoveryConstant.N_D_SERVICE_ZONE, zone);
        }
    }

    // 处理外部Header的转发，即外部服务传递过来的Header，中继转发到下游服务去
    private void applyOuterHeader(ClientRequest.Builder requestBuilder) {
        Enumeration<String> headerNames = strategyContextHolder.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = strategyContextHolder.getHeader(headerName);
                boolean isHeaderContains = isHeaderContainsExcludeInner(headerName.toLowerCase());
                if (isHeaderContains) {
                    if (webClientCoreHeaderTransmissionEnabled) {
                        requestBuilder.header(headerName, headerValue);
                    } else {
                        boolean isCoreHeaderContains = StrategyUtil.isCoreHeaderContains(headerName);
                        if (!isCoreHeaderContains) {
                            requestBuilder.header(headerName, headerValue);
                        }
                    }
                }
            }
        }

        if (webClientCoreHeaderTransmissionEnabled) {
            ClientRequest request = requestBuilder.build();
            HttpHeaders headers = request.headers();
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_VERSION))) {
                String routeVersion = strategyContextHolder.getRouteVersion();
                if (StringUtils.isNotEmpty(routeVersion)) {
                    requestBuilder.header(DiscoveryConstant.N_D_VERSION, routeVersion);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_REGION))) {
                String routeRegion = strategyContextHolder.getRouteRegion();
                if (StringUtils.isNotEmpty(routeRegion)) {
                    requestBuilder.header(DiscoveryConstant.N_D_REGION, routeRegion);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_ENVIRONMENT))) {
                String routeEnvironment = strategyContextHolder.getRouteEnvironment();
                if (StringUtils.isNotEmpty(routeEnvironment)) {
                    requestBuilder.header(DiscoveryConstant.N_D_ENVIRONMENT, routeEnvironment);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_ADDRESS))) {
                String routeAddress = strategyContextHolder.getRouteAddress();
                if (StringUtils.isNotEmpty(routeAddress)) {
                    requestBuilder.header(DiscoveryConstant.N_D_ADDRESS, routeAddress);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_VERSION_WEIGHT))) {
                String routeVersionWeight = strategyContextHolder.getRouteVersionWeight();
                if (StringUtils.isNotEmpty(routeVersionWeight)) {
                    requestBuilder.header(DiscoveryConstant.N_D_VERSION_WEIGHT, routeVersionWeight);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_REGION_WEIGHT))) {
                String routeRegionWeight = strategyContextHolder.getRouteRegionWeight();
                if (StringUtils.isNotEmpty(routeRegionWeight)) {
                    requestBuilder.header(DiscoveryConstant.N_D_REGION_WEIGHT, routeRegionWeight);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_VERSION_PREFER))) {
                String routeVersionPrefer = strategyContextHolder.getRouteVersionPrefer();
                if (StringUtils.isNotEmpty(routeVersionPrefer)) {
                    requestBuilder.header(DiscoveryConstant.N_D_VERSION_PREFER, routeVersionPrefer);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_VERSION_FAILOVER))) {
                String routeVersionFailover = strategyContextHolder.getRouteVersionFailover();
                if (StringUtils.isNotEmpty(routeVersionFailover)) {
                    requestBuilder.header(DiscoveryConstant.N_D_VERSION_FAILOVER, routeVersionFailover);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_REGION_TRANSFER))) {
                String routeRegionTransfer = strategyContextHolder.getRouteRegionTransfer();
                if (StringUtils.isNotEmpty(routeRegionTransfer)) {
                    requestBuilder.header(DiscoveryConstant.N_D_REGION_TRANSFER, routeRegionTransfer);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_REGION_FAILOVER))) {
                String routeRegionFailover = strategyContextHolder.getRouteRegionFailover();
                if (StringUtils.isNotEmpty(routeRegionFailover)) {
                    requestBuilder.header(DiscoveryConstant.N_D_REGION_FAILOVER, routeRegionFailover);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_ENVIRONMENT_FAILOVER))) {
                String routeEnvironmentFailover = strategyContextHolder.getRouteEnvironmentFailover();
                if (StringUtils.isNotEmpty(routeEnvironmentFailover)) {
                    requestBuilder.header(DiscoveryConstant.N_D_ENVIRONMENT_FAILOVER, routeEnvironmentFailover);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_ZONE_FAILOVER))) {
                String routeZoneFailover = strategyContextHolder.getRouteZoneFailover();
                if (StringUtils.isNotEmpty(routeZoneFailover)) {
                    requestBuilder.header(DiscoveryConstant.N_D_ZONE_FAILOVER, routeZoneFailover);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_ADDRESS_FAILOVER))) {
                String routeAddressFailover = strategyContextHolder.getRouteAddressFailover();
                if (StringUtils.isNotEmpty(routeAddressFailover)) {
                    requestBuilder.header(DiscoveryConstant.N_D_ADDRESS_FAILOVER, routeAddressFailover);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_ID_BLACKLIST))) {
                String routeIdBlacklist = strategyContextHolder.getRouteIdBlacklist();
                if (StringUtils.isNotEmpty(routeIdBlacklist)) {
                    requestBuilder.header(DiscoveryConstant.N_D_ID_BLACKLIST, routeIdBlacklist);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_ADDRESS_BLACKLIST))) {
                String routeAddressBlacklist = strategyContextHolder.getRouteAddressBlacklist();
                if (StringUtils.isNotEmpty(routeAddressBlacklist)) {
                    requestBuilder.header(DiscoveryConstant.N_D_ADDRESS_BLACKLIST, routeAddressBlacklist);
                }
            }
        }
    }

    private void interceptOutputHeader(ClientRequest request) {
        if (!interceptDebugEnabled) {
            return;
        }

        System.out.println("------ WebClient Intercept Output Header Information -------");
        HttpHeaders headers = request.headers();
        for (Iterator<Entry<String, List<String>>> iterator = headers.entrySet().iterator(); iterator.hasNext();) {
            Entry<String, List<String>> header = iterator.next();
            String headerName = header.getKey();
            boolean isHeaderContains = isHeaderContains(headerName.toLowerCase());
            if (isHeaderContains) {
                List<String> headerValue = header.getValue();

                System.out.println(headerName + "=" + headerValue);
            }
        }
        System.out.println("------------------------------------------------------------");
    }

    @Override
    protected InterceptorType getInterceptorType() {
        return InterceptorType.WEB_CLIENT;
    }
}
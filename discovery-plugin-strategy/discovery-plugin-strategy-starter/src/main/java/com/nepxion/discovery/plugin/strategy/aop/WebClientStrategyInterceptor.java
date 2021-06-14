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

    // WebClient核心策略Header是否传递。当全局订阅启动时，可以关闭核心策略Header传递，这样可以节省传递数据的大小，一定程度上可以提升性能。核心策略Header，包含如下
    // 1. n-d-version
    // 2. n-d-region
    // 3. n-d-address
    // 4. n-d-version-weight
    // 5. n-d-region-weight
    // 6. n-d-id-blacklist
    // 7. n-d-address-blacklist
    // 8. n-d-env (不属于蓝绿灰度范畴的Header，只要外部传入就会全程传递)
    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_WEB_CLIENT_CORE_HEADER_TRANSMISSION_ENABLED + ":true}")
    protected Boolean webClientCoreHeaderTransmissionEnabled;

    public WebClientStrategyInterceptor(String contextRequestHeaders, String businessRequestHeaders) {
        super(contextRequestHeaders, businessRequestHeaders);
    }

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        ClientRequest.Builder requestBuilder = ClientRequest.from(request);

        // 拦截打印输入的Header
        interceptInputHeader();

        // 处理内部Header的转发
        applyInnerHeader(requestBuilder);

        // 处理外部Header的转发
        applyOuterHeader(request, requestBuilder);

        ClientRequest newRequest = requestBuilder.build();

        // 拦截打印输出的Header
        interceptOutputHeader(newRequest);

        return next.exchange(newRequest);
    }

    // 处理内部Header的转发，即把本地服务的相关属性封装成Header转发到下游服务去
    private void applyInnerHeader(ClientRequest.Builder requestBuilder) {
        // 设置本地组名到Header中，并全链路传递
        requestBuilder.header(DiscoveryConstant.N_D_SERVICE_GROUP, pluginAdapter.getGroup());

        // 设置本地服务类型到Header中，并全链路传递
        requestBuilder.header(DiscoveryConstant.N_D_SERVICE_TYPE, pluginAdapter.getServiceType());

        // 设置本地服务APPID到Header中，并全链路传递
        String serviceAppId = pluginAdapter.getServiceAppId();
        if (StringUtils.isNotEmpty(serviceAppId)) {
            requestBuilder.header(DiscoveryConstant.N_D_SERVICE_APP_ID, serviceAppId);
        }

        // 设置本地服务名到Header中，并全链路传递
        requestBuilder.header(DiscoveryConstant.N_D_SERVICE_ID, pluginAdapter.getServiceId());

        // 设置本地服务IP地址和端口到Header中，并全链路传递
        requestBuilder.header(DiscoveryConstant.N_D_SERVICE_ADDRESS, pluginAdapter.getHost() + ":" + pluginAdapter.getPort());

        // 设置本地服务版本号到Header中，并全链路传递
        requestBuilder.header(DiscoveryConstant.N_D_SERVICE_VERSION, pluginAdapter.getVersion());

        // 设置本地服务区域值到Header中，并全链路传递
        requestBuilder.header(DiscoveryConstant.N_D_SERVICE_REGION, pluginAdapter.getRegion());

        // 设置本地服务环境值到Header中，并全链路传递
        requestBuilder.header(DiscoveryConstant.N_D_SERVICE_ENVIRONMENT, pluginAdapter.getEnvironment());

        // 设置本地服务可用区到Header中，并全链路传递
        requestBuilder.header(DiscoveryConstant.N_D_SERVICE_ZONE, pluginAdapter.getZone());
    }

    // 处理外部Header的转发，即外部服务传递过来的Header，中继转发到下游服务去
    private void applyOuterHeader(ClientRequest request, ClientRequest.Builder requestBuilder) {
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
            HttpHeaders headers = request.headers();

            // 设置版本匹配路由的配置到Header中
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_VERSION))) {
                String routeVersion = strategyContextHolder.getRouteVersion();
                if (StringUtils.isNotEmpty(routeVersion)) {
                    requestBuilder.header(DiscoveryConstant.N_D_VERSION, routeVersion);
                }
            }

            // 设置区域匹配路由的配置到Header中
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_REGION))) {
                String routeRegion = strategyContextHolder.getRouteRegion();
                if (StringUtils.isNotEmpty(routeRegion)) {
                    requestBuilder.header(DiscoveryConstant.N_D_REGION, routeRegion);
                }
            }

            // 设置环境匹配路由的配置到Header中
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_ENVIRONMENT))) {
                String routeEnvironment = strategyContextHolder.getRouteEnvironment();
                if (StringUtils.isNotEmpty(routeEnvironment)) {
                    requestBuilder.header(DiscoveryConstant.N_D_ENVIRONMENT, routeEnvironment);
                }
            }

            // 设置IP地址和端口匹配路由的配置到Header中
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_ADDRESS))) {
                String routeAddress = strategyContextHolder.getRouteAddress();
                if (StringUtils.isNotEmpty(routeAddress)) {
                    requestBuilder.header(DiscoveryConstant.N_D_ADDRESS, routeAddress);
                }
            }

            // 设置版本权重路由的配置到Header中
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_VERSION_WEIGHT))) {
                String routeVersionWeight = strategyContextHolder.getRouteVersionWeight();
                if (StringUtils.isNotEmpty(routeVersionWeight)) {
                    requestBuilder.header(DiscoveryConstant.N_D_VERSION_WEIGHT, routeVersionWeight);
                }
            }

            // 设置区域权重路由的配置到Header中
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_REGION_WEIGHT))) {
                String routeRegionWeight = strategyContextHolder.getRouteRegionWeight();
                if (StringUtils.isNotEmpty(routeRegionWeight)) {
                    requestBuilder.header(DiscoveryConstant.N_D_REGION_WEIGHT, routeRegionWeight);
                }
            }

            // 设置全局唯一ID黑名单屏蔽的配置到Header中
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_ID_BLACKLIST))) {
                String routeIdBlacklist = strategyContextHolder.getRouteIdBlacklist();
                if (StringUtils.isNotEmpty(routeIdBlacklist)) {
                    requestBuilder.header(DiscoveryConstant.N_D_ID_BLACKLIST, routeIdBlacklist);
                }
            }

            // 设置IP地址和端口黑名单屏蔽的配置到Header中
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
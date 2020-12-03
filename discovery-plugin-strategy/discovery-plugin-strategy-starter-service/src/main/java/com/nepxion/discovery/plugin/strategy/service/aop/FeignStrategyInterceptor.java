package com.nepxion.discovery.plugin.strategy.service.aop;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author Fengfeng Li
 * @version 1.0
 */

import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.service.constant.ServiceStrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.context.ServiceStrategyContextHolder;
import com.nepxion.discovery.plugin.strategy.util.StrategyUtil;

public class FeignStrategyInterceptor extends AbstractStrategyInterceptor implements RequestInterceptor {
    @Autowired
    protected ServiceStrategyContextHolder serviceStrategyContextHolder;

    // Feign上核心策略Header是否传递。当全局订阅启动时，可以关闭核心策略Header传递，这样可以节省传递数据的大小，一定程度上可以提升性能。核心策略Header，包含如下
    // 1. n-d-version
    // 2. n-d-region
    // 3. n-d-address
    // 4. n-d-version-weight
    // 5. n-d-region-weight
    // 6. n-d-id-blacklist
    // 7. n-d-address-blacklist
    // 8. n-d-env (不属于灰度蓝绿范畴的Header，只要外部传入就会全程传递)
    @Value("${" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_FEIGN_CORE_HEADER_TRANSMISSION_ENABLED + ":true}")
    protected Boolean feignCoreHeaderTransmissionEnabled;

    public FeignStrategyInterceptor(String contextRequestHeaders, String businessRequestHeaders) {
        super(contextRequestHeaders, businessRequestHeaders);
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        interceptInputHeader();

        applyInnerHeader(requestTemplate);
        applyOuterHeader(requestTemplate);

        interceptOutputHeader(requestTemplate);
    }

    private void applyInnerHeader(RequestTemplate requestTemplate) {
        requestTemplate.header(DiscoveryConstant.N_D_SERVICE_GROUP, pluginAdapter.getGroup());
        requestTemplate.header(DiscoveryConstant.N_D_SERVICE_TYPE, pluginAdapter.getServiceType());
        String serviceAppId = pluginAdapter.getServiceAppId();
        if (StringUtils.isNotEmpty(serviceAppId)) {
            requestTemplate.header(DiscoveryConstant.N_D_SERVICE_APP_ID, serviceAppId);
        }
        requestTemplate.header(DiscoveryConstant.N_D_SERVICE_ID, pluginAdapter.getServiceId());
        requestTemplate.header(DiscoveryConstant.N_D_SERVICE_ADDRESS, pluginAdapter.getHost() + ":" + pluginAdapter.getPort());
        requestTemplate.header(DiscoveryConstant.N_D_SERVICE_VERSION, pluginAdapter.getVersion());
        requestTemplate.header(DiscoveryConstant.N_D_SERVICE_REGION, pluginAdapter.getRegion());
        requestTemplate.header(DiscoveryConstant.N_D_SERVICE_ENVIRONMENT, pluginAdapter.getEnvironment());
        requestTemplate.header(DiscoveryConstant.N_D_SERVICE_ZONE, pluginAdapter.getZone());
    }

    private void applyOuterHeader(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = serviceStrategyContextHolder.getRestAttributes();
        if (attributes != null) {
            HttpServletRequest previousRequest = attributes.getRequest();
            Enumeration<String> headerNames = previousRequest.getHeaderNames();
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String headerName = headerNames.nextElement();
                    String headerValue = previousRequest.getHeader(headerName);
                    boolean isHeaderContains = isHeaderContainsExcludeInner(headerName.toLowerCase());
                    if (isHeaderContains) {
                        if (feignCoreHeaderTransmissionEnabled) {
                            requestTemplate.header(headerName, headerValue);
                        } else {
                            boolean isCoreHeaderContains = StrategyUtil.isCoreHeaderContains(headerName);
                            if (!isCoreHeaderContains) {
                                requestTemplate.header(headerName, headerValue);
                            }
                        }
                    }
                }
            }
        }

        if (feignCoreHeaderTransmissionEnabled) {
            Map<String, Collection<String>> headers = requestTemplate.headers();
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_VERSION))) {
                String routeVersion = serviceStrategyContextHolder.getRouteVersion();
                if (StringUtils.isNotEmpty(routeVersion)) {
                    requestTemplate.header(DiscoveryConstant.N_D_VERSION, routeVersion);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_REGION))) {
                String routeRegion = serviceStrategyContextHolder.getRouteRegion();
                if (StringUtils.isNotEmpty(routeRegion)) {
                    requestTemplate.header(DiscoveryConstant.N_D_REGION, routeRegion);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_ENVIRONMENT))) {
                String routeEnvironment = serviceStrategyContextHolder.getRouteEnvironment();
                if (StringUtils.isNotEmpty(routeEnvironment)) {
                    requestTemplate.header(DiscoveryConstant.N_D_ENVIRONMENT, routeEnvironment);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_ADDRESS))) {
                String routeAddress = serviceStrategyContextHolder.getRouteAddress();
                if (StringUtils.isNotEmpty(routeAddress)) {
                    requestTemplate.header(DiscoveryConstant.N_D_ADDRESS, routeAddress);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_VERSION_WEIGHT))) {
                String routeVersionWeight = serviceStrategyContextHolder.getRouteVersionWeight();
                if (StringUtils.isNotEmpty(routeVersionWeight)) {
                    requestTemplate.header(DiscoveryConstant.N_D_VERSION_WEIGHT, routeVersionWeight);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_REGION_WEIGHT))) {
                String routeRegionWeight = serviceStrategyContextHolder.getRouteRegionWeight();
                if (StringUtils.isNotEmpty(routeRegionWeight)) {
                    requestTemplate.header(DiscoveryConstant.N_D_REGION_WEIGHT, routeRegionWeight);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_ID_BLACKLIST))) {
                String routeIdBlacklist = serviceStrategyContextHolder.getRouteIdBlacklist();
                if (StringUtils.isNotEmpty(routeIdBlacklist)) {
                    requestTemplate.header(DiscoveryConstant.N_D_ID_BLACKLIST, routeIdBlacklist);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_ADDRESS_BLACKLIST))) {
                String routeAddressBlacklist = serviceStrategyContextHolder.getRouteAddressBlacklist();
                if (StringUtils.isNotEmpty(routeAddressBlacklist)) {
                    requestTemplate.header(DiscoveryConstant.N_D_ADDRESS_BLACKLIST, routeAddressBlacklist);
                }
            }
        }
    }

    private void interceptOutputHeader(RequestTemplate requestTemplate) {
        if (!interceptDebugEnabled) {
            return;
        }

        System.out.println("------- " + getInterceptorName() + " Intercept Output Header Information ------");
        Map<String, Collection<String>> headers = requestTemplate.headers();
        for (Map.Entry<String, Collection<String>> entry : headers.entrySet()) {
            String headerName = entry.getKey();
            boolean isHeaderContains = isHeaderContains(headerName.toLowerCase());
            if (isHeaderContains) {
                Collection<String> headerValue = entry.getValue();

                System.out.println(headerName + "=" + headerValue);
            }
        }
        System.out.println("--------------------------------------------------");
    }

    @Override
    protected String getInterceptorName() {
        return "Feign";
    }
}
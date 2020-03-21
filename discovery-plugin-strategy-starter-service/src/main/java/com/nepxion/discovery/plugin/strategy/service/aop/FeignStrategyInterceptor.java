package com.nepxion.discovery.plugin.strategy.service.aop;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.service.filter.ServiceStrategyRouteFilter;

public class FeignStrategyInterceptor extends AbstractStrategyInterceptor implements RequestInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(FeignStrategyInterceptor.class);

    @Autowired
    private ServiceStrategyRouteFilter serviceStrategyRouteFilter;

    public FeignStrategyInterceptor(String contextRequestHeaders, String businessRequestHeaders) {
        super(contextRequestHeaders, businessRequestHeaders);

        LOG.info("----------- Feign Intercept Information ----------");
        LOG.info("Feign desires to intercept customer headers are {}", requestHeaderList);
        LOG.info("--------------------------------------------------");
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
    }

    private void applyOuterHeader(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = serviceStrategyContextHolder.getRestAttributes();
        if (attributes == null) {
            return;
        }

        HttpServletRequest previousRequest = attributes.getRequest();
        Enumeration<String> headerNames = previousRequest.getHeaderNames();
        if (headerNames == null) {
            return;
        }

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = previousRequest.getHeader(headerName);
            boolean isHeaderContains = isHeaderContainsExcludeInner(headerName.toLowerCase());
            if (isHeaderContains) {
                requestTemplate.header(headerName, headerValue);
            }
        }

        Map<String, Collection<String>> headers = requestTemplate.headers();
        if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_VERSION))) {
            String routeVersion = serviceStrategyRouteFilter.getRouteVersion();
            if (StringUtils.isNotEmpty(routeVersion)) {
                requestTemplate.header(DiscoveryConstant.N_D_VERSION, routeVersion);
            }
        }
        if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_REGION))) {
            String routeRegion = serviceStrategyRouteFilter.getRouteRegion();
            if (StringUtils.isNotEmpty(routeRegion)) {
                requestTemplate.header(DiscoveryConstant.N_D_REGION, routeRegion);
            }
        }
        if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_ADDRESS))) {
            String routeAddress = serviceStrategyRouteFilter.getRouteAddress();
            if (StringUtils.isNotEmpty(routeAddress)) {
                requestTemplate.header(DiscoveryConstant.N_D_ADDRESS, routeAddress);
            }
        }
        if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_VERSION_WEIGHT))) {
            String routeVersionWeight = serviceStrategyRouteFilter.getRouteVersionWeight();
            if (StringUtils.isNotEmpty(routeVersionWeight)) {
                requestTemplate.header(DiscoveryConstant.N_D_VERSION_WEIGHT, routeVersionWeight);
            }
        }
        if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_REGION_WEIGHT))) {
            String routeRegionWeight = serviceStrategyRouteFilter.getRouteRegionWeight();
            if (StringUtils.isNotEmpty(routeRegionWeight)) {
                requestTemplate.header(DiscoveryConstant.N_D_REGION_WEIGHT, routeRegionWeight);
            }
        }
    }

    private void interceptOutputHeader(RequestTemplate requestTemplate) {
        if (!interceptDebugEnabled) {
            return;
        }

        System.out.println("------- Intercept Output Header Information ------");
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
}
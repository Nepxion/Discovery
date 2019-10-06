package com.nepxion.discovery.plugin.strategy.service.aop;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.adapter.RestTemplateStrategyInterceptorAdapter;
import com.nepxion.discovery.plugin.strategy.service.route.ServiceStrategyRouteFilter;

public class RestTemplateStrategyInterceptor extends AbstractStrategyInterceptor implements ClientHttpRequestInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(RestTemplateStrategyInterceptor.class);

    @Autowired(required = false)
    private List<RestTemplateStrategyInterceptorAdapter> restTemplateStrategyInterceptorAdapterList;

    @Autowired
    private ServiceStrategyRouteFilter serviceStrategyRouteFilter;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACE_ENABLED + ":false}")
    protected Boolean strategyTraceEnabled;

    public RestTemplateStrategyInterceptor(String contextRequestHeaders, String businessRequestHeaders) {
        super(contextRequestHeaders, businessRequestHeaders);

        LOG.info("------- RestTemplate Intercept Information -------");
        LOG.info("RestTemplate desires to intercept customer headers are {}", requestHeaderList);
        LOG.info("--------------------------------------------------");
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        interceptInputHeader();

        applyInnerHeader(request);
        applyOuterHeader(request);

        if (CollectionUtils.isNotEmpty(restTemplateStrategyInterceptorAdapterList)) {
            for (RestTemplateStrategyInterceptorAdapter restTemplateStrategyInterceptorAdapter : restTemplateStrategyInterceptorAdapterList) {
                restTemplateStrategyInterceptorAdapter.intercept(request, body, execution);
            }
        }

        interceptOutputHeader(request);

        return execution.execute(request, body);
    }

    private void applyInnerHeader(HttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        headers.add(DiscoveryConstant.N_D_SERVICE_GROUP, pluginAdapter.getGroup());
        if (strategyTraceEnabled) {
            headers.add(DiscoveryConstant.N_D_SERVICE_TYPE, pluginAdapter.getServiceType());
            headers.add(DiscoveryConstant.N_D_SERVICE_ID, pluginAdapter.getServiceId());
            headers.add(DiscoveryConstant.N_D_SERVICE_ADDRESS, pluginAdapter.getHost() + ":" + pluginAdapter.getPort());
            headers.add(DiscoveryConstant.N_D_SERVICE_VERSION, pluginAdapter.getVersion());
            headers.add(DiscoveryConstant.N_D_SERVICE_REGION, pluginAdapter.getRegion());
        }
    }

    private void applyOuterHeader(HttpRequest request) {
        ServletRequestAttributes attributes = serviceStrategyContextHolder.getRestAttributes();
        if (attributes == null) {
            return;
        }

        HttpServletRequest previousRequest = attributes.getRequest();
        Enumeration<String> headerNames = previousRequest.getHeaderNames();
        if (headerNames == null) {
            return;
        }

        HttpHeaders headers = request.getHeaders();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = previousRequest.getHeader(headerName);
            boolean isHeaderContains = isHeaderContainsExcludeInner(headerName.toLowerCase());
            if (isHeaderContains) {
                headers.add(headerName, headerValue);
            }
        }

        if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_VERSION))) {
            String routeVersion = serviceStrategyRouteFilter.getRouteVersion();
            if (StringUtils.isNotEmpty(routeVersion)) {
                headers.add(DiscoveryConstant.N_D_VERSION, routeVersion);
            }
        }
        if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_REGION))) {
            String routeRegion = serviceStrategyRouteFilter.getRouteRegion();
            if (StringUtils.isNotEmpty(routeRegion)) {
                headers.add(DiscoveryConstant.N_D_REGION, routeRegion);
            }
        }
        if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_ADDRESS))) {
            String routeAddress = serviceStrategyRouteFilter.getRouteAddress();
            if (StringUtils.isNotEmpty(routeAddress)) {
                headers.add(DiscoveryConstant.N_D_ADDRESS, routeAddress);
            }
        }
        if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_VERSION_WEIGHT))) {
            String routeVersionWeight = serviceStrategyRouteFilter.getRouteVersionWeight();
            if (StringUtils.isNotEmpty(routeVersionWeight)) {
                headers.add(DiscoveryConstant.N_D_VERSION_WEIGHT, routeVersionWeight);
            }
        }
        if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_REGION_WEIGHT))) {
            String routeRegionWeight = serviceStrategyRouteFilter.getRouteRegionWeight();
            if (StringUtils.isNotEmpty(routeRegionWeight)) {
                headers.add(DiscoveryConstant.N_D_REGION_WEIGHT, routeRegionWeight);
            }
        }
    }

    private void interceptOutputHeader(HttpRequest request) {
        if (!interceptDebugEnabled) {
            return;
        }

        System.out.println("------- Intercept Output Header Information ------");
        HttpHeaders headers = request.getHeaders();
        for (Iterator<Entry<String, List<String>>> iterator = headers.entrySet().iterator(); iterator.hasNext();) {
            Entry<String, List<String>> header = iterator.next();
            String headerName = header.getKey();
            boolean isHeaderContains = isHeaderContains(headerName.toLowerCase());
            if (isHeaderContains) {
                List<String> headerValue = header.getValue();

                System.out.println(headerName + "=" + headerValue);
            }
        }
        System.out.println("--------------------------------------------------");
    }
}
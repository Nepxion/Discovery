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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;

public class RestTemplateStrategyInterceptor extends AbstractStrategyInterceptor implements ClientHttpRequestInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(RestTemplateStrategyInterceptor.class);

    public RestTemplateStrategyInterceptor(String requestHeaders) {
        super(requestHeaders);

        LOG.info("------- RestTemplate Intercept Information -------");
        LOG.info("RestTemplate desires to intercept customer headers are {}", requestHeaderList);
        LOG.info("--------------------------------------------------");
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        interceptInputHeader();

        applyInnerHeader(request);
        applyOuterHeader(request);

        interceptOutputHeader(request);

        return execution.execute(request, body);
    }

    private void applyInnerHeader(HttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        headers.add(DiscoveryConstant.N_D_SERVICE_TYPE, pluginAdapter.getServiceType());
        headers.add(DiscoveryConstant.N_D_SERVICE_ID, pluginAdapter.getServiceId());
        headers.add(DiscoveryConstant.N_D_SERVICE_ADDRESS, pluginAdapter.getHost() + ":" + pluginAdapter.getPort());
        headers.add(DiscoveryConstant.N_D_SERVICE_GROUP, pluginAdapter.getGroup());
        headers.add(DiscoveryConstant.N_D_SERVICE_VERSION, pluginAdapter.getVersion());
        headers.add(DiscoveryConstant.N_D_SERVICE_REGION, pluginAdapter.getRegion());
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
    }

    private void interceptOutputHeader(HttpRequest request) {
        Boolean interceptLogPrint = environment.getProperty(StrategyConstant.SPRING_APPLICATION_STRATEGY_INTERCEPT_LOG_PRINT, Boolean.class, Boolean.FALSE);
        if (!interceptLogPrint) {
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
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

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nepxion.discovery.plugin.strategy.service.context.ServiceStrategyContextHolder;

public class FeignStrategyInterceptor implements RequestInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(FeignStrategyInterceptor.class);

    private String requestHeaders;

    @Autowired
    private ServiceStrategyContextHolder serviceStrategyContextHolder;

    public FeignStrategyInterceptor(String requestHeaders) {
        this.requestHeaders = requestHeaders.toLowerCase();

        LOG.info("------------- Feign Proxy Information -----------");
        LOG.info("Feign interceptor headers are '{}'", requestHeaders);
        LOG.info("-------------------------------------------------");
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = serviceStrategyContextHolder.getRequestAttributes();
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
            String header = previousRequest.getHeader(headerName);

            if (requestHeaders.contains(headerName.toLowerCase())) {
                requestTemplate.header(headerName, header);
            }
        }
    }
}
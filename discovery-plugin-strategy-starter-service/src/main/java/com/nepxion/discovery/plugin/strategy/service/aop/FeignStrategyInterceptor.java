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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nepxion.discovery.plugin.strategy.service.constant.ServiceStrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.context.ServiceStrategyContextHolder;

public class FeignStrategyInterceptor implements RequestInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(FeignStrategyInterceptor.class);

    private String requestHeaders;

    @Autowired
    private ConfigurableEnvironment environment;

    @Autowired
    private ServiceStrategyContextHolder serviceStrategyContextHolder;

    public FeignStrategyInterceptor(String requestHeaders) {
        LOG.info("------------- Feign Intercept Information -----------");
        if (StringUtils.isNotEmpty(requestHeaders)) {
            this.requestHeaders = requestHeaders.toLowerCase();
        }
        LOG.info("Feign intercepted headers are {}", StringUtils.isNotEmpty(requestHeaders) ? requestHeaders : "empty");
        LOG.info("-------------------------------------------------");
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (StringUtils.isEmpty(requestHeaders)) {
            return;
        }

        ServletRequestAttributes attributes = serviceStrategyContextHolder.getRestAttributes();
        if (attributes == null) {
            return;
        }

        HttpServletRequest previousRequest = attributes.getRequest();
        Enumeration<String> headerNames = previousRequest.getHeaderNames();
        if (headerNames == null) {
            return;
        }

        Boolean interceptLogPrint = environment.getProperty(ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_INTERCEPT_LOG_PRINT, Boolean.class, Boolean.FALSE);
        if (interceptLogPrint) {
            LOG.info("------------- Feign Route Information -----------");
        }
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String header = previousRequest.getHeader(headerName);

            if (requestHeaders.contains(headerName.toLowerCase())) {
                if (interceptLogPrint) {
                    LOG.info("{}={}", headerName, header);
                }
                requestTemplate.header(headerName, header);
            }
        }
        if (interceptLogPrint) {
            LOG.info("-------------------------------------------------");
        }
    }
}
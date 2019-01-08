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

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nepxion.discovery.plugin.strategy.service.constant.ServiceStrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.context.ServiceStrategyContextHolder;

public class RestTemplateStrategyInterceptor implements ClientHttpRequestInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(RestTemplateStrategyInterceptor.class);

    private String requestHeaders;

    @Autowired
    private ConfigurableEnvironment environment;

    @Autowired
    private ServiceStrategyContextHolder serviceStrategyContextHolder;

    public RestTemplateStrategyInterceptor(String requestHeaders) {
        LOG.info("------------- RestTemplate Intercept Information -----------");
        if (StringUtils.isNotEmpty(requestHeaders)) {
            this.requestHeaders = requestHeaders.toLowerCase();
        }
        LOG.info("RestTemplate intercepted headers are {}", StringUtils.isNotEmpty(requestHeaders) ? requestHeaders : "empty");
        LOG.info("-------------------------------------------------");
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        if (StringUtils.isEmpty(requestHeaders)) {
            return execution.execute(request, body);
        }

        ServletRequestAttributes attributes = serviceStrategyContextHolder.getRestAttributes();
        if (attributes == null) {
            return execution.execute(request, body);
        }

        HttpServletRequest previousRequest = attributes.getRequest();
        Enumeration<String> headerNames = previousRequest.getHeaderNames();
        if (headerNames == null) {
            return execution.execute(request, body);
        }

        Boolean interceptLogPrint = environment.getProperty(ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_INTERCEPT_LOG_PRINT, Boolean.class, Boolean.FALSE);
        if (interceptLogPrint) {
            LOG.info("------------- RestTemplate Route Information -----------");
        }
        HttpHeaders headers = request.getHeaders();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String header = previousRequest.getHeader(headerName);

            if (requestHeaders.contains(headerName.toLowerCase())) {
                if (interceptLogPrint) {
                    LOG.info("{}={}", headerName, header);
                }
                headers.add(headerName, header);
            }
        }
        if (interceptLogPrint) {
            LOG.info("-------------------------------------------------");
        }

        return execution.execute(request, body);
    }
}
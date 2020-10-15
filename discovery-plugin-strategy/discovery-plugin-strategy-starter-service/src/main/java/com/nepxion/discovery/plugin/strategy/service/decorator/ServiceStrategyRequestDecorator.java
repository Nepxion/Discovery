package com.nepxion.discovery.plugin.strategy.service.decorator;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author liquanjin
 * @version 1.0
 */

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.collections4.CollectionUtils;

public class ServiceStrategyRequestDecorator extends HttpServletRequestWrapper {
    private Map<String, List<String>> headers;
    private StringBuffer requestURL;
    private String requestURI;

    public ServiceStrategyRequestDecorator(HttpServletRequest request) {
        super(request);

        headers = initializeHeaders(request);
        requestURL = request.getRequestURL();
        requestURI = request.getRequestURI();
    }

    private Map<String, List<String>> initializeHeaders(HttpServletRequest request) {
        Map<String, List<String>> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (headerName != null) {
                headers.put(headerName, Collections.list(request.getHeaders(headerName)));
            }
        }

        return headers;
    }

    @Override
    public String getHeader(String name) {
        List<String> headerValues = headers.get(name);

        return CollectionUtils.isEmpty(headerValues) ? null : headerValues.get(0);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        List<String> headerValues = headers.get(name);

        return Collections.enumeration(headerValues != null ? headerValues : Collections.emptySet());
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return Collections.enumeration(headers.keySet());
    }

    @Override
    public StringBuffer getRequestURL() {
        return requestURL;
    }

    @Override
    public String getRequestURI() {
        return requestURI;
    }
}
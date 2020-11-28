package com.nepxion.discovery.plugin.strategy.service.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.collections4.MapUtils;

public class ServiceStrategyRouteFilterRequest extends HttpServletRequestWrapper {
    private HttpServletRequest originalRequest;

    private Map<String, String> headers;

    public ServiceStrategyRouteFilterRequest(HttpServletRequest request) {
        super(request);

        this.originalRequest = request;

        headers = new HashMap<String, String>();
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        if (headers.containsKey(name)) {
            return headers.get(name);
        }

        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        if (MapUtils.isNotEmpty(headers)) {
            List<String> names = Collections.list(super.getHeaderNames());
            for (String name : headers.keySet()) {
                if (!names.contains(name)) {
                    names.add(name);
                }
            }

            return Collections.enumeration(names);
        }

        return super.getHeaderNames();
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        if (headers.containsKey(name)) {
            List<String> values = Arrays.asList(headers.get(name));

            return Collections.enumeration(values);
        }

        return super.getHeaders(name);
    }

    public HttpServletRequest getOriginalRequest() {
        return originalRequest;
    }
}
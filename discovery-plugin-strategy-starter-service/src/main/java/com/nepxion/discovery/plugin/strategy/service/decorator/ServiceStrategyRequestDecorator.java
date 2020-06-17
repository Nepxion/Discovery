package com.nepxion.discovery.plugin.strategy.service.decorator;

import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 *
 * @author liquanjin
 * @version 1.0
 */
public class ServiceStrategyRequestDecorator extends HttpServletRequestWrapper {

    private Map<String, List<String>> headers;

    public ServiceStrategyRequestDecorator(HttpServletRequest request) {
        super(request);
        headers = initHeaders(request);
    }

    private Map<String, List<String>> initHeaders(HttpServletRequest request) {
        Map<String, List<String>> headers = new HashMap<>();
        Enumeration<String> originalHeaders = request.getHeaderNames();
        while (originalHeaders.hasMoreElements()) {
            String headerName = originalHeaders.nextElement();
            if (headerName != null) {
                headers.put(headerName, Collections.list(request.getHeaders(headerName)));
            }
        }

        return headers;
    }

    @Override
    public String getHeader(String name) {
        List<String> headerValues = this.headers.get(name);
        return CollectionUtils.isEmpty(headerValues) ? null : headerValues.get(0);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        List<String> headerValues = this.headers.get(name);
        return Collections.enumeration(headerValues != null ? headerValues : Collections.emptySet());
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return Collections.enumeration(this.headers.keySet());
    }

}


package com.nepxion.discovery.plugin.strategy.zuul.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;

import com.netflix.zuul.context.RequestContext;

public class ZuulStrategyFilterResolver {
    public static void setHeader(String headerName, String headerValue) {
        RequestContext context = RequestContext.getCurrentContext();

        // 来自于外界的Header，例如，从Postman传递过来的Header
        String header = context.getRequest().getHeader(headerName);
        if (StringUtils.isEmpty(header)) {
            context.addZuulRequestHeader(headerName, headerValue);
        }
    }
}
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
    public static void setHeader(String headerName, String headerValue, Boolean zuulHeaderPriority) {
        RequestContext context = RequestContext.getCurrentContext();

        if (zuulHeaderPriority) {
            context.addZuulRequestHeader(headerName, headerValue);
        } else {
            String header = context.getRequest().getHeader(headerName);
            if (StringUtils.isEmpty(header)) {
                context.addZuulRequestHeader(headerName, headerValue);
            }
        }
    }
}
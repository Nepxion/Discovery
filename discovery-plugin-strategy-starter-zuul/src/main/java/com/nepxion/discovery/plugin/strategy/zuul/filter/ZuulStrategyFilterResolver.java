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
            // 通过Zuul Filter的Header直接把外界的Header替换掉，并传递
            context.addZuulRequestHeader(headerName, headerValue);
        } else {
            // 在外界的Header不存在的前提下，通过Zuul Filter的Header传递
            String header = context.getRequest().getHeader(headerName);
            if (StringUtils.isEmpty(header)) {
                context.addZuulRequestHeader(headerName, headerValue);
            }
        }
    }
}
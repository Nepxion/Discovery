package com.nepxion.discovery.plugin.strategy.zuul.filter;

import com.nepxion.discovery.plugin.hystrix.context.HystrixContextHolder;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

public class SetHystrixContextFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 100;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        HystrixContextHolder.getCurrentContext().setRequest(RequestContext.getCurrentContext().getRequest());
        return null;
    }
}

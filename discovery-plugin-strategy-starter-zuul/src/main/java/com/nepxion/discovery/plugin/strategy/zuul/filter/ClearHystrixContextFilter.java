package com.nepxion.discovery.plugin.strategy.zuul.filter;

import com.nepxion.discovery.plugin.hystrix.context.HystrixContextHolder;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

public class ClearHystrixContextFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        HystrixContextHolder.clearCurrentContext();
        return null;
    }
}

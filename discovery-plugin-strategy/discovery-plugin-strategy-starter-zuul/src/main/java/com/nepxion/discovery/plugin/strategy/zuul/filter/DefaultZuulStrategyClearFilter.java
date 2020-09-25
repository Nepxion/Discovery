package com.nepxion.discovery.plugin.strategy.zuul.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.nepxion.discovery.plugin.strategy.zuul.monitor.ZuulStrategyMonitor;
import com.netflix.zuul.context.RequestContext;

public class DefaultZuulStrategyClearFilter extends ZuulStrategyClearFilter {
    @Autowired(required = false)
    protected ZuulStrategyMonitor zuulStrategyMonitor;

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        // 调用链释放
        RequestContext context = RequestContext.getCurrentContext();
        if (zuulStrategyMonitor != null) {
            zuulStrategyMonitor.release(context);
        }

        return null;
    }
}
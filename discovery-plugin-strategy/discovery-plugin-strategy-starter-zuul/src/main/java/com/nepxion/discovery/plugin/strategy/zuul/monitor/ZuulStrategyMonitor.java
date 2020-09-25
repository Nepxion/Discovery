package com.nepxion.discovery.plugin.strategy.zuul.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.netflix.zuul.context.RequestContext;

public interface ZuulStrategyMonitor {
    void monitor(RequestContext context);

    void release(RequestContext context);
}
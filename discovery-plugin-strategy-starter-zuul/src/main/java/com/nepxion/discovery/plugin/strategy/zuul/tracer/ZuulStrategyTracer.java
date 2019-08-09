package com.nepxion.discovery.plugin.strategy.zuul.tracer;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.netflix.zuul.context.RequestContext;

public interface ZuulStrategyTracer {
    void trace(RequestContext context);

    void release();
}
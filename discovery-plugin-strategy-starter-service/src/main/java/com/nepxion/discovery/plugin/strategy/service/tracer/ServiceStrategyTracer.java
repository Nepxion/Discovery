package com.nepxion.discovery.plugin.strategy.service.tracer;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.aopalliance.intercept.MethodInvocation;

public interface ServiceStrategyTracer {
    void trace(ServiceStrategyTracerInterceptor interceptor, MethodInvocation invocation);

    void release(ServiceStrategyTracerInterceptor interceptor, MethodInvocation invocation);
}
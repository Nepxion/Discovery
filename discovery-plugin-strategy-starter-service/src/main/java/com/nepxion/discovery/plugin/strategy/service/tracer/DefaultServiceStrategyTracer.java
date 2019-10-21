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

import com.nepxion.discovery.plugin.strategy.tracer.StrategyTracer;

public class DefaultServiceStrategyTracer extends StrategyTracer implements ServiceStrategyTracer {
    @Override
    public void trace(ServiceStrategyTracerInterceptor interceptor, MethodInvocation invocation) {
        debugTraceLocal();
    }

    @Override
    public void error(ServiceStrategyTracerInterceptor interceptor, MethodInvocation invocation, Throwable e) {

    }

    @Override
    public void release(ServiceStrategyTracerInterceptor interceptor, MethodInvocation invocation) {

    }
}
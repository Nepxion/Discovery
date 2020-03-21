package com.nepxion.discovery.plugin.service.opentracing.tracer;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.opentracing.operation.StrategyOpentracingOperation;
import com.nepxion.discovery.plugin.strategy.service.tracer.DefaultServiceStrategyTracer;
import com.nepxion.discovery.plugin.strategy.service.tracer.ServiceStrategyTracerInterceptor;

public class DefaultServiceStrategyOpentracingTracer extends DefaultServiceStrategyTracer {
    @Autowired
    private StrategyOpentracingOperation strategyOpentracingOperation;

    @Override
    public void trace(ServiceStrategyTracerInterceptor interceptor, MethodInvocation invocation) {
        strategyOpentracingOperation.opentracingInitialize();

        super.trace(interceptor, invocation);

        Map<String, String> contextMap = new HashMap<String, String>();
        contextMap.put(DiscoveryConstant.CLASS, interceptor.getMethod(invocation).getDeclaringClass().getName());
        contextMap.put(DiscoveryConstant.METHOD, interceptor.getMethodName(invocation));

        strategyOpentracingOperation.opentracingPut(contextMap, getCustomizationMap());
    }

    @Override
    public void error(ServiceStrategyTracerInterceptor interceptor, MethodInvocation invocation, Throwable e) {
        super.error(interceptor, invocation, e);

        strategyOpentracingOperation.opentracingError(interceptor.getMethod(invocation).getDeclaringClass().getName(), interceptor.getMethodName(invocation), e);
    }

    @Override
    public void release(ServiceStrategyTracerInterceptor interceptor, MethodInvocation invocation) {
        super.release(interceptor, invocation);

        strategyOpentracingOperation.opentracingClear();
    }

    @Override
    public String getTraceId() {
        return strategyOpentracingOperation.getTraceId();
    }

    @Override
    public String getSpanId() {
        return strategyOpentracingOperation.getSpanId();
    }
}
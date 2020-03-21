package com.nepxion.discovery.plugin.zuul.opentracing.tracer;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.opentracing.operation.StrategyOpentracingOperation;
import com.nepxion.discovery.plugin.strategy.zuul.tracer.DefaultZuulStrategyTracer;
import com.netflix.zuul.context.RequestContext;

public class DefaultZuulStrategyOpentracingTracer extends DefaultZuulStrategyTracer {
    @Autowired
    private StrategyOpentracingOperation strategyOpentracingOperation;

    @Override
    public void trace(RequestContext context) {
        strategyOpentracingOperation.opentracingInitialize();

        super.trace(context);

        strategyOpentracingOperation.opentracingPut(null, getCustomizationMap());
    }

    @Override
    public void release(RequestContext context) {
        super.release(context);

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
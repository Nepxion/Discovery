package com.nepxion.discovery.plugin.opentracing.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import io.opentracing.Span;

import com.nepxion.discovery.plugin.strategy.tracer.StrategyTracerContextManager;

public class StrategyOpentracingContextManager implements StrategyTracerContextManager {
    @Override
    public Object getContext() {
        return StrategyOpentracingContext.getCurrentContext().getSpan();
    }

    @Override
    public void setContext(Object context) {
        StrategyOpentracingContext.getCurrentContext().setSpan((Span) context);
    }

    @Override
    public void clearContext() {
        StrategyOpentracingContext.clearCurrentContext();
    }
}
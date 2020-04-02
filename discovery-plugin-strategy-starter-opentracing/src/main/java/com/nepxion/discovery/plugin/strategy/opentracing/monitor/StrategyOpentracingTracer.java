package com.nepxion.discovery.plugin.strategy.opentracing.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.tag.Tags;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableMap;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.monitor.AbstractStrategyTracer;

public class StrategyOpentracingTracer extends AbstractStrategyTracer<Span> {
    @Autowired
    private Tracer tracer;

    @Override
    protected Span buildSpan() {
        return tracer.buildSpan(tracerSpanValue).start();
    }

    @Override
    protected void outputSpan(Span span, String key, String value) {
        span.setTag(key, value);
    }

    @Override
    protected void errorSpan(Span span, String className, String methodName, Throwable e) {
        span.log(new ImmutableMap.Builder<String, Object>()
                .put(DiscoveryConstant.CLASS, className)
                .put(DiscoveryConstant.METHOD, methodName)
                .put(DiscoveryConstant.EVENT, Tags.ERROR.getKey())
                .put(DiscoveryConstant.ERROR_OBJECT, e)
                .build());
    }

    @Override
    protected void finishSpan(Span span) {
        span.finish();
    }

    @Override
    protected Span getActiveSpan() {
        return tracer.activeSpan();
    }

    @Override
    protected String toTraceId(Span span) {
        return span.context().toTraceId();
    }

    @Override
    protected String toSpanId(Span span) {
        return span.context().toSpanId();
    }
}
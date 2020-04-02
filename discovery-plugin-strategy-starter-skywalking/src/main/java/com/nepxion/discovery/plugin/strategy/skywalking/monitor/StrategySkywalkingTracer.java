package com.nepxion.discovery.plugin.strategy.skywalking.monitor;

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

import java.lang.reflect.Field;

import org.apache.skywalking.apm.toolkit.opentracing.SkywalkingTracer;

import com.google.common.collect.ImmutableMap;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.monitor.AbstractStrategyTracer;
import com.nepxion.discovery.plugin.strategy.monitor.StrategyTracerContext;

public class StrategySkywalkingTracer extends AbstractStrategyTracer<Span> {
    private Tracer tracer = new SkywalkingTracer();

    @Override
    protected Span buildSpan() {
        Span span = tracer.buildSpan(tracerSpanValue).startManual();

        StrategyTracerContext.getCurrentContext().setTraceId(createTraceId());
        StrategyTracerContext.getCurrentContext().setSpanId(createSpanId());

        return span;
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

    //  该方法永远不会被用到
    @Override
    protected Span getActiveSpan() {
        return null;
    }

    @Override
    protected String toTraceId(Span span) {
        return StrategyTracerContext.getCurrentContext().getTraceId();
    }

    @Override
    protected String toSpanId(Span span) {
        return StrategyTracerContext.getCurrentContext().getSpanId();
    }

    private String createTraceId() {
        if (System.getProperties().get("skywalking.agent.service_name") == null) {
            return null;
        }

        try {
            Object traceId = ReflectionUtils.invokeStaticMethod("org.apache.skywalking.apm.agent.core.context.ContextManager", "getGlobalTraceId");
            if (traceId != null) {
                return traceId.toString();
            }
        } catch (Exception e) {
        }

        return null;
    }

    private String createSpanId() {
        if (System.getProperties().get("skywalking.agent.service_name") == null) {
            return null;
        }

        try {
            Object traceContext = ReflectionUtils.invokeStaticMethod("org.apache.skywalking.apm.agent.core.context.ContextManager", "get");
            if (traceContext != null) {
                if (traceContext.getClass().getName().equals("org.apache.skywalking.apm.agent.core.context.TracingContext")) {
                    Field fieldSegment = ReflectionUtils.findField(traceContext.getClass(), "segment");
                    Object segment = ReflectionUtils.getField(fieldSegment, traceContext);
                    Field fieldSegmentId = ReflectionUtils.findField(segment.getClass(), "traceSegmentId");
                    String segmentId = ReflectionUtils.getField(fieldSegmentId, segment).toString();

                    return segmentId;
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
        }

        return null;
    }
}
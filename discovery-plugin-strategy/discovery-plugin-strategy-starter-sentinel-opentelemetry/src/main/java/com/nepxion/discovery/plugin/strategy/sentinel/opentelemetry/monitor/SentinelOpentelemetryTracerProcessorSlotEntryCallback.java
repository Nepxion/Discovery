package com.nepxion.discovery.plugin.strategy.sentinel.opentelemetry.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import io.opentelemetry.OpenTelemetry;
import io.opentelemetry.trace.Span;

import com.nepxion.discovery.plugin.strategy.sentinel.monitor.callback.SentinelTracerProcessorSlotEntryCallback;
import com.nepxion.discovery.plugin.strategy.sentinel.monitor.constant.SentinelStrategyMonitorConstant;

public class SentinelOpentelemetryTracerProcessorSlotEntryCallback extends SentinelTracerProcessorSlotEntryCallback<Span> {
    public static final String INSTRUMENTATION_NAME = "opentelemetry.trace.tracer.name";
    private String instrumentationName = System.getProperty(INSTRUMENTATION_NAME);

    @Override
    protected Span buildSpan() {
        return OpenTelemetry.getTracer(instrumentationName).spanBuilder(SentinelStrategyMonitorConstant.SPAN_NAME).startSpan();
    }

    @Override
    protected void outputSpan(Span span, String key, String value) {
        span.setAttribute(key, value);
    }

    @Override
    protected void finishSpan(Span span) {
        span.end();
    }
}
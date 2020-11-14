package com.nepxion.discovery.plugin.strategy.sentinel.opentelemetry.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;

import com.nepxion.discovery.plugin.strategy.sentinel.monitor.callback.SentinelTracingProcessorSlotEntryCallback;
import com.nepxion.discovery.plugin.strategy.sentinel.monitor.constant.SentinelStrategyMonitorConstant;

public class SentinelOpenTelemetryProcessorSlotEntryCallback extends SentinelTracingProcessorSlotEntryCallback<Span> {
    public static final String INSTRUMENTATION_NAME = "opentelemetry.trace.tracer.name";

    private String instrumentationName = System.getProperty(INSTRUMENTATION_NAME);

    @Override
    protected Span buildSpan() {
        return OpenTelemetry.getGlobalTracer(instrumentationName).spanBuilder(SentinelStrategyMonitorConstant.SPAN_NAME).startSpan();
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
package com.nepxion.discovery.plugin.strategy.sentinel.opentelemetry.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Weihua Wang
 * @author Haojun Ren
 * @version 1.0
 */

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;

import org.springframework.core.env.Environment;

import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.strategy.sentinel.monitor.callback.SentinelTracingProcessorSlotEntryCallback;
import com.nepxion.discovery.plugin.strategy.sentinel.monitor.constant.SentinelStrategyMonitorConstant;

public class SentinelOpenTelemetryProcessorSlotEntryCallback extends SentinelTracingProcessorSlotEntryCallback<Span> {
    public static final String INSTRUMENTATION_NAME = "opentelemetry.trace.tracer.name";

    @Override
    protected Span buildSpan() {
        Environment environment = PluginContextAware.getStaticEnvironment();
        String instrumentationName = environment.getProperty(INSTRUMENTATION_NAME, String.class, SentinelStrategyMonitorConstant.TRACER_NAME);

        return GlobalOpenTelemetry.getTracer(instrumentationName).spanBuilder(SentinelStrategyMonitorConstant.SPAN_NAME).startSpan();
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
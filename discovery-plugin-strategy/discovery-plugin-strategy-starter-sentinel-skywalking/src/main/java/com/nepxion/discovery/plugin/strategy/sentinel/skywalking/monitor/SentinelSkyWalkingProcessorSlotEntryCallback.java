package com.nepxion.discovery.plugin.strategy.sentinel.skywalking.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zhang shun
 * @version 1.0
 */

import io.opentracing.Span;
import io.opentracing.Tracer;

import org.apache.skywalking.apm.toolkit.opentracing.SkywalkingTracer;

import com.nepxion.discovery.plugin.strategy.sentinel.monitor.callback.SentinelTracerProcessorSlotEntryCallback;
import com.nepxion.discovery.plugin.strategy.sentinel.monitor.constant.SentinelStrategyMonitorConstant;

public class SentinelSkyWalkingProcessorSlotEntryCallback extends SentinelTracerProcessorSlotEntryCallback<Span> {
    private Tracer tracer = new SkywalkingTracer();
    
    @Override
    protected Span buildSpan() {
        return tracer.buildSpan(SentinelStrategyMonitorConstant.SPAN_NAME).startManual();
    }

    @Override
    protected void outputSpan(Span span, String key, String value) {
        span.setTag(key, value);
    }

    @Override
    protected void finishSpan(Span span) {
        span.finish();
    }
}
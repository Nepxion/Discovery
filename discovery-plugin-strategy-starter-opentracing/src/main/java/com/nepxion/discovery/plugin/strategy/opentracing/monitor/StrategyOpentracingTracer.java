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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;

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
    protected void errorSpan(Span span, Map<String, String> contextMap, Throwable e) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (MapUtils.isNotEmpty(contextMap)) {
            map.putAll(contextMap);
        }
        map.put(DiscoveryConstant.EVENT, Tags.ERROR.getKey());
        map.put(DiscoveryConstant.ERROR_OBJECT, e);

        span.log(map);
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
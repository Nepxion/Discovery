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

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.monitor.AbstractStrategyTracer;

public class OpenTracingStrategyTracer extends AbstractStrategyTracer<Span> {
    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACER_EXCEPTION_DETAIL_OUTPUT_ENABLED + ":false}")
    protected Boolean tracerExceptionDetailOutputEnabled;

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
    protected void errorSpan(Span span, Throwable e) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put(DiscoveryConstant.EVENT, DiscoveryConstant.ERROR);
        if (tracerExceptionDetailOutputEnabled) {
            map.put(DiscoveryConstant.ERROR_OBJECT, ExceptionUtils.getStackTrace(e));
        } else {
            map.put(DiscoveryConstant.ERROR_OBJECT, e);
        }
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
package com.nepxion.discovery.plugin.strategy.skywalking.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import io.opentracing.Tracer;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.skywalking.apm.toolkit.opentracing.SkywalkingTracer;
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.monitor.AbstractStrategyTracer;

public class SkyWalkingStrategyTracer extends AbstractStrategyTracer<SkyWalkingStrategySpan> {
    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACER_EXCEPTION_DETAIL_OUTPUT_ENABLED + ":false}")
    protected Boolean tracerExceptionDetailOutputEnabled;

    private Tracer tracer = new SkywalkingTracer();

    @Override
    protected SkyWalkingStrategySpan buildSpan() {
        return new SkyWalkingStrategySpan(tracer.buildSpan(tracerSpanValue).startManual());
    }

    @Override
    protected void outputSpan(SkyWalkingStrategySpan span, String key, String value) {
        span.setTag(key, value);
    }

    @Override
    protected void errorSpan(SkyWalkingStrategySpan span, Throwable e) {
        if (tracerExceptionDetailOutputEnabled) {
            span.setTag(DiscoveryConstant.ERROR_OBJECT, ExceptionUtils.getStackTrace(e));
        } else {
            span.setTag(DiscoveryConstant.ERROR_OBJECT, e.getMessage());
        }
    }

    @Override
    protected void finishSpan(SkyWalkingStrategySpan span) {
        span.finish();
    }

    //  Never used probably
    @Override
    protected SkyWalkingStrategySpan getActiveSpan() {
        return null;
    }

    @Override
    protected String toTraceId(SkyWalkingStrategySpan span) {
        return span.toTraceId();
    }

    @Override
    protected String toSpanId(SkyWalkingStrategySpan span) {
        return span.toSpanId();
    }
}
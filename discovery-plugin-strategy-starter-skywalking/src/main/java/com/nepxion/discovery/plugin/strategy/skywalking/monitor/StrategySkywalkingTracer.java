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
import io.opentracing.tag.Tags;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.skywalking.apm.toolkit.opentracing.SkywalkingTracer;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.monitor.AbstractStrategyTracer;

public class StrategySkywalkingTracer extends AbstractStrategyTracer<StrategySkywalkingSpan> {
    private Tracer tracer = new SkywalkingTracer();

    @Override
    protected StrategySkywalkingSpan buildSpan() {
        return new StrategySkywalkingSpan(tracer.buildSpan(tracerSpanValue).startManual());
    }

    @Override
    protected void outputSpan(StrategySkywalkingSpan span, String key, String value) {
        span.setTag(key, value);
    }

    @Override
    protected void errorSpan(StrategySkywalkingSpan span, Map<String, String> contextMap, Throwable e) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (MapUtils.isNotEmpty(contextMap)) {
            map.putAll(contextMap);
        }
        map.put(DiscoveryConstant.EVENT, Tags.ERROR.getKey());
        map.put(DiscoveryConstant.ERROR_OBJECT, e);

        span.log(map);
    }

    @Override
    protected void finishSpan(StrategySkywalkingSpan span) {
        span.finish();
    }

    //  该方法永远不会被用到
    @Override
    protected StrategySkywalkingSpan getActiveSpan() {
        return null;
    }

    @Override
    protected String toTraceId(StrategySkywalkingSpan span) {
        return span.toTraceId();
    }

    @Override
    protected String toSpanId(StrategySkywalkingSpan span) {
        return span.toSpanId();
    }
}
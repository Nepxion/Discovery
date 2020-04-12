package com.nepxion.discovery.plugin.strategy.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.strategy.adapter.StrategyTracerAdapter;

public class StrategyMonitorContext {
    @Autowired(required = false)
    protected StrategyTracer strategyTracer;

    @Autowired(required = false)
    protected StrategyTracerAdapter strategyTracerAdapter;

    public String getTraceId() {
        if (strategyTracer != null) {
            return strategyTracer.getTraceId();
        }

        if (strategyTracerAdapter != null) {
            return strategyTracerAdapter.getTraceId();
        }

        return null;
    }


    public String getSpanId() {
        if (strategyTracer != null) {
            return strategyTracer.getSpanId();
        }

        if (strategyTracerAdapter != null) {
            return strategyTracerAdapter.getSpanId();
        }

        return null;
    }

    public Map<String, String> getCustomizationMap() {
        if (strategyTracerAdapter != null) {
            return strategyTracerAdapter.getCustomizationMap();
        }

        return null;
    }
}
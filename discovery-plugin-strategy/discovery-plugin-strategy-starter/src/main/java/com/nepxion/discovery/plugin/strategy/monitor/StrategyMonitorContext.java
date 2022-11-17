package com.nepxion.discovery.plugin.strategy.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.entity.HeadersInjectorType;
import com.nepxion.discovery.plugin.strategy.adapter.StrategyTracerAdapter;
import com.nepxion.discovery.plugin.strategy.injector.StrategyHeadersResolver;
import com.nepxion.discovery.plugin.strategy.injector.StrategyHeadersInjector;

public class StrategyMonitorContext {
    @Autowired(required = false)
    protected StrategyTracer strategyTracer;

    @Autowired(required = false)
    protected StrategyTracerAdapter strategyTracerAdapter;

    @Autowired(required = false)
    protected List<StrategyHeadersInjector> strategyHeadersInjectorList;

    protected List<String> tracerInjectorHeaderNameList;

    @PostConstruct
    public void initialize() {
        tracerInjectorHeaderNameList = StrategyHeadersResolver.getInjectedHeaders(strategyHeadersInjectorList, HeadersInjectorType.TRACER);
    }

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

    public List<String> getTracerInjectorHeaderNameList() {
        return tracerInjectorHeaderNameList;
    }

    public Map<String, String> getTracerCustomizationMap() {
        if (strategyTracerAdapter != null) {
            return strategyTracerAdapter.getCustomizationMap();
        }

        return null;
    }
}
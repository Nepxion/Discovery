package com.nepxion.discovery.plugin.strategy.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.strategy.adapter.StrategyTracerAdapter;
import com.nepxion.discovery.plugin.strategy.injector.StrategyTracerHeadersInjector;

public class StrategyMonitorContext {
    @Autowired(required = false)
    protected StrategyTracer strategyTracer;

    @Autowired(required = false)
    protected StrategyTracerAdapter strategyTracerAdapter;

    @Autowired(required = false)
    protected List<StrategyTracerHeadersInjector> strategyTracerHeadersInjectorList;

    protected List<String> tracerHeaderNameList;

    @PostConstruct
    public void initialize() {
        if (CollectionUtils.isNotEmpty(strategyTracerHeadersInjectorList)) {
            tracerHeaderNameList = new ArrayList<String>();
            for (StrategyTracerHeadersInjector strategyTracerHeadersInjector : strategyTracerHeadersInjectorList) {
                List<String> tracerHeaderNames = strategyTracerHeadersInjector.getHeaderNames();
                if (CollectionUtils.isNotEmpty(tracerHeaderNames)) {
                    for (String tracerHeaderName : tracerHeaderNames) {
                        if (!tracerHeaderNameList.contains(tracerHeaderName)) {
                            tracerHeaderNameList.add(tracerHeaderName);
                        }
                    }
                }
            }
        }
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

    public List<String> getTracerHeaderNameList() {
        return tracerHeaderNameList;
    }

    public Map<String, String> getCustomizationMap() {
        if (strategyTracerAdapter != null) {
            return strategyTracerAdapter.getCustomizationMap();
        }

        return null;
    }
}
package com.nepxion.discovery.plugin.strategy.starter.agent.plugin.discover;

import com.nepxion.discovery.plugin.strategy.monitor.StrategyTracerContext;
import com.nepxion.discovery.plugin.strategy.starter.agent.plugin.thread.ThreadLocalHook;

public class StrategyTracerContextHook implements ThreadLocalHook {
    @Override
    public Object create() {
        return StrategyTracerContext.getCurrentContext().getSpan();
    }

    @Override
    public void before(Object object) {
        StrategyTracerContext.getCurrentContext().setSpan(object);
    }

    @Override
    public void after() {
        StrategyTracerContext.clearCurrentContext();
    }
}

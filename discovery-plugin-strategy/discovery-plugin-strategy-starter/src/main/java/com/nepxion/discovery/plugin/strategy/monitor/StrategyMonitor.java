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

public class StrategyMonitor {
    @Autowired(required = false)
    protected StrategyLogger strategyLogger;

    @Autowired(required = false)
    protected StrategyTracer strategyTracer;

    @Autowired(required = false)
    protected StrategyAlarm strategyAlarm;

    public void loggerOutput() {
        if (strategyLogger != null) {
            strategyLogger.loggerOutput();
        }
    }

    public void loggerClear() {
        if (strategyLogger != null) {
            strategyLogger.loggerClear();
        }
    }

    public void loggerDebug() {
        if (strategyLogger != null) {
            strategyLogger.loggerDebug();
        }
    }

    public void spanBuild() {
        if (strategyTracer != null) {
            strategyTracer.spanBuild();
        }
    }

    public void spanOutput(Map<String, String> contextMap) {
        if (strategyTracer != null) {
            strategyTracer.spanOutput(contextMap);
        }
    }

    public void spanError(Throwable e) {
        if (strategyTracer != null) {
            strategyTracer.spanError(e);
        }
    }

    public void spanFinish() {
        if (strategyTracer != null) {
            strategyTracer.spanFinish();
        }
    }

    public void alarm() {
        if (strategyAlarm != null) {
            strategyAlarm.alarm();
        }
    }

    public StrategyLogger getStrategyLogger() {
        return strategyLogger;
    }

    public StrategyTracer getStrategyTracer() {
        return strategyTracer;
    }

    public StrategyAlarm getStrategyAlarm() {
        return strategyAlarm;
    }
}
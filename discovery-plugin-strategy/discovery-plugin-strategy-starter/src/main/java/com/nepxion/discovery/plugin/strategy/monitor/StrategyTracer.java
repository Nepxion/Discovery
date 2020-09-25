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

public interface StrategyTracer {
    void spanBuild();

    void spanOutput(Map<String, String> contextMap);

    void spanError(Throwable e);

    void spanFinish();

    String getTraceId();

    String getSpanId();
}
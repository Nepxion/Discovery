package com.nepxion.discovery.plugin.strategy.sentinel.opentelemetry.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.statistic.StatisticSlotCallbackRegistry;

public class SentinelOpenTelemetryInitFunc implements InitFunc {
    @Override
    public void init() throws Exception {
        StatisticSlotCallbackRegistry.addEntryCallback(SentinelOpenTelemetryProcessorSlotEntryCallback.class.getName(), new SentinelOpenTelemetryProcessorSlotEntryCallback());
    }
}
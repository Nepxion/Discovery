package com.nepxion.discovery.plugin.strategy.sentinel.opentracing.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zhang shun
 * @version 1.0
 */

import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.statistic.StatisticSlotCallbackRegistry;

public class SentinelOpenTracingInitFunc implements InitFunc {
    @Override
    public void init() throws Exception {
        StatisticSlotCallbackRegistry.addEntryCallback(SentinelOpenTracingProcessorSlotEntryCallback.class.getName(), new SentinelOpenTracingProcessorSlotEntryCallback());
    }
}
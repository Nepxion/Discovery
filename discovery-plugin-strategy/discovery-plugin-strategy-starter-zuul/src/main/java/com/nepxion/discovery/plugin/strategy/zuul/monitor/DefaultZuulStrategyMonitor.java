package com.nepxion.discovery.plugin.strategy.zuul.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.plugin.strategy.monitor.StrategyMonitor;
import com.netflix.zuul.context.RequestContext;

public class DefaultZuulStrategyMonitor extends StrategyMonitor implements ZuulStrategyMonitor {
    @Override
    public void monitor(RequestContext context) {
        spanBuild();

        loggerOutput();
        loggerDebug();

        alarm();

        spanOutput(null);
    }

    @Override
    public void release(RequestContext context) {
        loggerClear();

        spanFinish();
    }
}
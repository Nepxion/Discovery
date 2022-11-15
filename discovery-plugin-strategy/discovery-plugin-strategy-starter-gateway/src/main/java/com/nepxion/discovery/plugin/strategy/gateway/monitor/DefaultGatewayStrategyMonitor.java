package com.nepxion.discovery.plugin.strategy.gateway.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.web.server.ServerWebExchange;

import com.nepxion.discovery.plugin.strategy.monitor.StrategyMonitor;

public class DefaultGatewayStrategyMonitor extends StrategyMonitor implements GatewayStrategyMonitor {
    @Override
    public void monitor(ServerWebExchange exchange) {
        spanBuild();

        loggerOutput();
        loggerDebug();

        alarm(null);

        spanOutput(null);
    }

    @Override
    public void release(ServerWebExchange exchange) {
        loggerClear();

        spanFinish();
    }
}
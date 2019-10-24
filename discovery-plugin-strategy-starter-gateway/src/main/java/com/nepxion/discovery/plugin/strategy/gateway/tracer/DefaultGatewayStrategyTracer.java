package com.nepxion.discovery.plugin.strategy.gateway.tracer;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.web.server.ServerWebExchange;

import com.nepxion.discovery.plugin.strategy.tracer.StrategyTracer;

public class DefaultGatewayStrategyTracer extends StrategyTracer implements GatewayStrategyTracer {
    @Override
    public void trace(ServerWebExchange exchange) {
        mdcTraceHeader();
        debugTraceHeader();
    }

    @Override
    public void release(ServerWebExchange exchange) {
        mdcClear();
    }
}
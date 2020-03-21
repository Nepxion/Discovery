package com.nepxion.discovery.plugin.gateway.opentracing.tracer;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ServerWebExchange;

import com.nepxion.discovery.plugin.opentracing.operation.StrategyOpentracingOperation;
import com.nepxion.discovery.plugin.strategy.gateway.tracer.DefaultGatewayStrategyTracer;

public class DefaultGatewayStrategyOpentracingTracer extends DefaultGatewayStrategyTracer {
    @Autowired
    private StrategyOpentracingOperation strategyOpentracingOperation;

    @Override
    public void trace(ServerWebExchange exchange) {
        strategyOpentracingOperation.opentracingInitialize();

        super.trace(exchange);

        strategyOpentracingOperation.opentracingPut(null, getCustomizationMap());
    }

    @Override
    public void release(ServerWebExchange exchange) {
        super.release(exchange);

        strategyOpentracingOperation.opentracingClear();
    }

    @Override
    public String getTraceId() {
        return strategyOpentracingOperation.getTraceId();
    }

    @Override
    public String getSpanId() {
        return strategyOpentracingOperation.getSpanId();
    }
}
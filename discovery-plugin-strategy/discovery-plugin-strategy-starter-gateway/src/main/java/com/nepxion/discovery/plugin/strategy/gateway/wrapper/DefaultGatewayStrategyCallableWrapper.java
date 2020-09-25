package com.nepxion.discovery.plugin.strategy.gateway.wrapper;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author Hao Huang
 * @version 1.0
 */

import java.util.concurrent.Callable;

import org.springframework.web.server.ServerWebExchange;

import com.nepxion.discovery.plugin.strategy.gateway.context.GatewayStrategyContext;
import com.nepxion.discovery.plugin.strategy.monitor.StrategyTracerContext;

public class DefaultGatewayStrategyCallableWrapper implements GatewayStrategyCallableWrapper {
    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        ServerWebExchange exchange = GatewayStrategyContext.getCurrentContext().getExchange();

        Object span = StrategyTracerContext.getCurrentContext().getSpan();

        return new Callable<T>() {
            @Override
            public T call() throws Exception {
                try {
                    GatewayStrategyContext.getCurrentContext().setExchange(exchange);

                    StrategyTracerContext.getCurrentContext().setSpan(span);

                    return callable.call();
                } finally {
                    GatewayStrategyContext.clearCurrentContext();

                    StrategyTracerContext.clearCurrentContext();
                }
            }
        };
    }
}
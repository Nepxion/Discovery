package com.nepxion.discovery.plugin.strategy.service.wrapper;

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

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.nepxion.discovery.plugin.strategy.monitor.StrategyTracerContext;
import com.nepxion.discovery.plugin.strategy.service.context.RestStrategyContext;
import com.nepxion.discovery.plugin.strategy.wrapper.CallableWrapper;

public class DefaultCallableWrapper implements CallableWrapper {
    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        Object span = StrategyTracerContext.getCurrentContext().getSpan();
        String traceId = StrategyTracerContext.getCurrentContext().getTraceId();
        String spanId = StrategyTracerContext.getCurrentContext().getSpanId();

        return new Callable<T>() {
            @Override
            public T call() throws Exception {
                try {
                    RestStrategyContext.getCurrentContext().setRequestAttributes(requestAttributes);

                    StrategyTracerContext.getCurrentContext().setSpan(span);
                    if (traceId != null) {
                        StrategyTracerContext.getCurrentContext().setTraceId(traceId);
                    }
                    if (spanId != null) {
                        StrategyTracerContext.getCurrentContext().setSpanId(spanId);
                    }

                    return callable.call();
                } finally {
                    RestStrategyContext.clearCurrentContext();

                    StrategyTracerContext.clearCurrentContext();
                }
            }
        };
    }
}
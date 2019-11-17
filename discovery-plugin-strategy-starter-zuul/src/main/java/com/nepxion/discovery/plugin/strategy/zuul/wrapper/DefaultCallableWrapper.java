package com.nepxion.discovery.plugin.strategy.zuul.wrapper;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author Hao Huang
 * @version 1.0
 */

import java.util.Map;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.strategy.tracer.StrategyTracerContextManager;
import com.nepxion.discovery.plugin.strategy.wrapper.CallableWrapper;
import com.nepxion.discovery.plugin.strategy.zuul.context.ZuulStrategyContext;
import com.netflix.zuul.context.RequestContext;

public class DefaultCallableWrapper implements CallableWrapper {
    @Autowired(required = false)
    private StrategyTracerContextManager strategyTracerContextManager;

    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        Map<String, String> headers = RequestContext.getCurrentContext().getZuulRequestHeaders();

        Object tracerContext = getTracerContext();

        return new Callable<T>() {
            @Override
            public T call() throws Exception {
                try {
                    ZuulStrategyContext.getCurrentContext().setRequest(request);
                    ZuulStrategyContext.getCurrentContext().setHeaders(headers);

                    setTracerContext(tracerContext);

                    return callable.call();
                } finally {
                    ZuulStrategyContext.clearCurrentContext();

                    clearTracerContext();
                }
            }
        };
    }

    private Object getTracerContext() {
        if (strategyTracerContextManager == null) {
            return null;
        }

        return strategyTracerContextManager.getContext();
    }

    private void setTracerContext(Object context) {
        if (strategyTracerContextManager == null) {
            return;
        }

        strategyTracerContextManager.setContext(context);
    }

    private void clearTracerContext() {
        if (strategyTracerContextManager == null) {
            return;
        }

        strategyTracerContextManager.clearContext();
    }
}
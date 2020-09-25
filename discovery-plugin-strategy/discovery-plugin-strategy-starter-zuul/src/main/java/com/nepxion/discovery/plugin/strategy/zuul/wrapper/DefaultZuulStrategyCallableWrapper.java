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

import com.nepxion.discovery.plugin.strategy.monitor.StrategyTracerContext;
import com.nepxion.discovery.plugin.strategy.zuul.context.ZuulStrategyContext;
import com.netflix.zuul.context.RequestContext;

public class DefaultZuulStrategyCallableWrapper implements ZuulStrategyCallableWrapper {
    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        Map<String, String> headers = RequestContext.getCurrentContext().getZuulRequestHeaders();

        Object span = StrategyTracerContext.getCurrentContext().getSpan();

        return new Callable<T>() {
            @Override
            public T call() throws Exception {
                try {
                    ZuulStrategyContext.getCurrentContext().setRequest(request);
                    ZuulStrategyContext.getCurrentContext().setHeaders(headers);

                    StrategyTracerContext.getCurrentContext().setSpan(span);

                    return callable.call();
                } finally {
                    ZuulStrategyContext.clearCurrentContext();

                    StrategyTracerContext.clearCurrentContext();
                }
            }
        };
    }
}
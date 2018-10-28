package com.nepxion.discovery.plugin.strategy.zuul.wrapper;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author Hao Wang
 * @version 1.0
 */

import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;

import com.nepxion.discovery.plugin.strategy.wrapper.CallableWrapper;
import com.nepxion.discovery.plugin.strategy.zuul.context.ZuulStrategyContext;
import com.netflix.zuul.context.RequestContext;

public class DefaultCallableWrapper implements CallableWrapper {
    @Override
    public <T> Callable<T> wrapCallable(Callable<T> delegate) {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();

        return new Callable<T>() {
            @Override
            public T call() throws Exception {
                try {
                    ZuulStrategyContext.getCurrentContext().setRequest(request);

                    return delegate.call();
                } finally {
                    ZuulStrategyContext.clearCurrentContext();
                }
            }
        };
    }
}
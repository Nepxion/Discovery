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

import com.nepxion.discovery.plugin.strategy.service.context.RestStrategyContext;
import com.nepxion.discovery.plugin.strategy.wrapper.CallableWrapper;

public class DefaultCallableWrapper implements CallableWrapper {
    @Override
    public <T> Callable<T> wrapCallable(Callable<T> delegate) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        return new Callable<T>() {
            @Override
            public T call() throws Exception {
                try {
                    RestStrategyContext.getCurrentContext().setRequestAttributes(requestAttributes);

                    return delegate.call();
                } finally {
                    RestStrategyContext.clearCurrentContext();
                }
            }
        };
    }
}
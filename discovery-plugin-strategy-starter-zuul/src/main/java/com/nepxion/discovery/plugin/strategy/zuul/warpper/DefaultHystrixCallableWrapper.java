package com.nepxion.discovery.plugin.strategy.zuul.warpper;

import com.nepxion.discovery.plugin.hystrix.context.HystrixCallableWrapper;
import com.nepxion.discovery.plugin.hystrix.context.HystrixContextHolder;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Callable;

public class DefaultHystrixCallableWrapper implements HystrixCallableWrapper {
    @Override
    public <T> Callable<T> wrapCallable(Callable<T> delegate) {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        return new Callable<T>(){
            @Override
            public T call() throws Exception {
                try {
                    HystrixContextHolder.getCurrentContext().setRequest(request);
                    return delegate.call();
                } finally {
                    HystrixContextHolder.clearCurrentContext();
                }
            }
        };
    }
}

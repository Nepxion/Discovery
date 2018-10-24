package com.nepxion.discovery.plugin.strategy.zuul.configuration;

import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableLifecycle;
import com.netflix.hystrix.strategy.properties.HystrixProperty;
import com.netflix.zuul.context.RequestContext;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RequestContextHystrixConcurrencyStrategy extends HystrixConcurrencyStrategy {
    private HystrixConcurrencyStrategy delegate;

    public RequestContextHystrixConcurrencyStrategy(HystrixConcurrencyStrategy delegate) {
        this.delegate=delegate;
    }

    @Override
    public BlockingQueue<Runnable> getBlockingQueue(int maxQueueSize) {
        return delegate != null
                ? delegate.getBlockingQueue(maxQueueSize)
                : super.getBlockingQueue(maxQueueSize);
    }

    @Override
    public <T> HystrixRequestVariable<T> getRequestVariable(
            HystrixRequestVariableLifecycle<T> rv) {
        return delegate != null
                ? delegate.getRequestVariable(rv)
                : super.getRequestVariable(rv);
    }

    @Override
    public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey,
                                            HystrixProperty<Integer> corePoolSize,
                                            HystrixProperty<Integer> maximumPoolSize,
                                            HystrixProperty<Integer> keepAliveTime, TimeUnit unit,
                                            BlockingQueue<Runnable> workQueue) {
        return delegate != null
                ? delegate.getThreadPool(threadPoolKey, corePoolSize,
                maximumPoolSize, keepAliveTime, unit, workQueue)
                : super.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize,
                keepAliveTime, unit, workQueue);
    }

    @Override
    public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey, HystrixThreadPoolProperties threadPoolProperties) {
        return delegate != null
                ? delegate.getThreadPool(threadPoolKey, threadPoolProperties)
                : super.getThreadPool(threadPoolKey, threadPoolProperties);
    }
    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        RequestContext before = RequestContext.getCurrentContext();
        return new Callable<T>() {
            @Override
            public T call() throws Exception {
                ContextHolder current = ContextHolder.getCurrentContext();
                Set<Map.Entry<String, String>> entries = before.getZuulRequestHeaders().entrySet();
                for (Map.Entry<String, String> entry : entries) {
                    current.addZuulRequestHeader(entry.getKey(),entry.getValue());
                }
                current.setRequest(before.getRequest());
                current.setRequestQueryParams(before.getRequestQueryParams());
                try {
                    return callable.call();
                } finally {
                    current.clear();
                }
            }
        };

    }
}

package com.nepxion.discovery.plugin.strategy.agent.plugin.spring.async;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import java.util.concurrent.Callable;

import com.nepxion.discovery.plugin.strategy.agent.async.AsyncContext;
import com.nepxion.discovery.plugin.strategy.agent.async.AsyncContextAccessor;
import com.nepxion.discovery.plugin.strategy.agent.threadlocal.ThreadLocalCopier;

public class WrapCallable<T> implements AsyncContextAccessor, Callable<T> {
    private Callable<T> callable;
    private AsyncContext asyncContext;

    public WrapCallable(Callable<T> callable) {
        this.callable = callable;
        Object[] objects = ThreadLocalCopier.create();
        setAsyncContext(new AsyncContext(objects));
    }

    @Override
    public T call() throws Exception {
        Object[] objects = asyncContext.getObjects();
        ThreadLocalCopier.before(objects);
        try {
            return callable.call();
        } finally {
            ThreadLocalCopier.after();
        }
    }

    @Override
    public void setAsyncContext(AsyncContext asyncContext) {
        this.asyncContext = asyncContext;
    }

    @Override
    public AsyncContext getAsyncContext() {
        return asyncContext;
    }
}
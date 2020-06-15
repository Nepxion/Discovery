package com.nepxion.discovery.plugin.strategy.starter.agent.plugin.thread.interceptor;

import com.nepxion.discovery.plugin.strategy.starter.agent.async.AsyncContext;
import com.nepxion.discovery.plugin.strategy.starter.agent.async.AsyncContextAccessor;
import com.nepxion.discovery.plugin.strategy.starter.agent.threadLocal.ThreadLocalCopier;

public class ThreadConstructorInterceptor {
    public static void before(Object object) {
        if (object instanceof AsyncContextAccessor) {
            AsyncContextAccessor asyncContextAccessor = (AsyncContextAccessor) object;
            Object[] objects = ThreadLocalCopier.create();
            asyncContextAccessor.setAsyncContext(new AsyncContext(objects));
        }
    }
}

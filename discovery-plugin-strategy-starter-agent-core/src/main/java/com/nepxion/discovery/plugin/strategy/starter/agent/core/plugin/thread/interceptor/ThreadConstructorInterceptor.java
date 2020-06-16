package com.nepxion.discovery.plugin.strategy.starter.agent.core.plugin.thread.interceptor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import com.nepxion.discovery.plugin.strategy.starter.agent.core.async.AsyncContext;
import com.nepxion.discovery.plugin.strategy.starter.agent.core.async.AsyncContextAccessor;
import com.nepxion.discovery.plugin.strategy.starter.agent.core.threadlocal.ThreadLocalCopier;

public class ThreadConstructorInterceptor {
    public static void before(Object object) {
        if (object instanceof AsyncContextAccessor) {
            AsyncContextAccessor asyncContextAccessor = (AsyncContextAccessor) object;
            Object[] objects = ThreadLocalCopier.create();
            asyncContextAccessor.setAsyncContext(new AsyncContext(objects));
        }
    }
}
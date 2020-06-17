package com.nepxion.discovery.plugin.strategy.agent.plugin.thread.interceptor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import com.nepxion.discovery.plugin.strategy.agent.async.AsyncContext;
import com.nepxion.discovery.plugin.strategy.agent.async.AsyncContextAccessor;
import com.nepxion.discovery.plugin.strategy.agent.threadlocal.ThreadLocalCopier;

public class ThreadCallInterceptor {
    public static void before(Object object) {
        if (object instanceof AsyncContextAccessor) {
            AsyncContextAccessor asyncContextAccessor = (AsyncContextAccessor) object;
            AsyncContext asyncContext = asyncContextAccessor.getAsyncContext();
            if (null == asyncContext) {
                return;
            }
            Object[] objects = asyncContext.getObjects();
            ThreadLocalCopier.before(objects);
        }
    }

    public static void after(Object object) {
        if (object instanceof AsyncContextAccessor) {
            ThreadLocalCopier.after();
        }
    }
}
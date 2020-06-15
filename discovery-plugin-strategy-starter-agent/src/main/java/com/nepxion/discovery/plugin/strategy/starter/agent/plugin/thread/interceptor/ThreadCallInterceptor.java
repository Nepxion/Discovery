package com.nepxion.discovery.plugin.strategy.starter.agent.plugin.thread.interceptor;


import com.nepxion.discovery.plugin.strategy.starter.agent.async.AsyncContext;
import com.nepxion.discovery.plugin.strategy.starter.agent.async.AsyncContextAccessor;
import com.nepxion.discovery.plugin.strategy.starter.agent.threadLocal.ThreadLocalCopier;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 *
 * @author zifeihan
 * @version 1.0
 */
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

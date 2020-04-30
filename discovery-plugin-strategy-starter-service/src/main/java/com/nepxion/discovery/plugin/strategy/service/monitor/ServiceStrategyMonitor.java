package com.nepxion.discovery.plugin.strategy.service.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.aopalliance.intercept.MethodInvocation;

public interface ServiceStrategyMonitor {
    void monitor(ServiceStrategyMonitorInterceptor interceptor, MethodInvocation invocation);

    void monitor(ServiceStrategyMonitorInterceptor interceptor, MethodInvocation invocation, Object returnValue);

    void error(ServiceStrategyMonitorInterceptor interceptor, MethodInvocation invocation, Throwable e);

    void release(ServiceStrategyMonitorInterceptor interceptor, MethodInvocation invocation);
}
package com.nepxion.discovery.plugin.strategy.service.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;

public interface ServiceStrategyMonitorAdapter {
    Map<String, String> getCustomizationMap(ServiceStrategyMonitorInterceptor interceptor, MethodInvocation invocation, Map<String, Object> parameterMap, Object returnValue);
}
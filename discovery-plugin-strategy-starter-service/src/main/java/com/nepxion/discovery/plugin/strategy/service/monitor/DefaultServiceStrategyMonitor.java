package com.nepxion.discovery.plugin.strategy.service.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.monitor.StrategyMonitor;

public class DefaultServiceStrategyMonitor extends StrategyMonitor implements ServiceStrategyMonitor {
    @Override
    public void monitor(ServiceStrategyMonitorInterceptor interceptor, MethodInvocation invocation) {
        spanBuild();

        loggerOutput();
        loggerDebug();

        Map<String, String> contextMap = new HashMap<String, String>();
        contextMap.put(DiscoveryConstant.CLASS, interceptor.getMethod(invocation).getDeclaringClass().getName());
        contextMap.put(DiscoveryConstant.METHOD, interceptor.getMethodName(invocation));

        spanOutput(contextMap);
    }

    @Override
    public void error(ServiceStrategyMonitorInterceptor interceptor, MethodInvocation invocation, Throwable e) {
        // 一般来说，日志方式对异常不需要做特殊处理，但必须也要把上下文参数放在MDC里，否则链路中异常环节会串不起来
        loggerOutput();

        spanError(interceptor.getMethod(invocation).getDeclaringClass().getName(), interceptor.getMethodName(invocation), e);
    }

    @Override
    public void release(ServiceStrategyMonitorInterceptor interceptor, MethodInvocation invocation) {
        loggerClear();

        spanFinish();
    }
}
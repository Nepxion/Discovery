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
import java.util.List;
import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.util.ClassUtil;
import com.nepxion.discovery.plugin.strategy.monitor.StrategyMonitor;

public class DefaultServiceStrategyMonitor extends StrategyMonitor implements ServiceStrategyMonitor {
    @Autowired(required = false)
    private List<ServiceStrategyMonitorAdapter> serviceStrategyMonitorAdapterList;

    @Override
    public void monitor(ServiceStrategyMonitorInterceptor interceptor, MethodInvocation invocation) {
        spanBuild();

        loggerOutput();
        loggerDebug();

        String className = interceptor.getMethod(invocation).getDeclaringClass().getName();
        String methodName = interceptor.getMethodName(invocation);
        String[] methodParameterNames = interceptor.getMethodParameterNames(invocation);
        Object[] arguments = interceptor.getArguments(invocation);
        Map<String, Object> parameterMap = ClassUtil.getParameterMap(methodParameterNames, arguments);

        Map<String, String> contextMap = new HashMap<String, String>();
        contextMap.put(DiscoveryConstant.CLASS, className);
        contextMap.put(DiscoveryConstant.METHOD, methodName);
        if (CollectionUtils.isNotEmpty(serviceStrategyMonitorAdapterList)) {
            for (ServiceStrategyMonitorAdapter serviceStrategyMonitorAdapter : serviceStrategyMonitorAdapterList) {
                contextMap.putAll(serviceStrategyMonitorAdapter.getCustomizationMap(interceptor, invocation, parameterMap));
            }
        }

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
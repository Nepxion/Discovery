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
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.util.ClassUtil;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.monitor.StrategyMonitor;

public class DefaultServiceStrategyMonitor extends StrategyMonitor implements ServiceStrategyMonitor {
    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACER_ENABLED + ":false}")
    protected Boolean tracerEnabled;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACER_METHOD_CONTEXT_OUTPUT_ENABLED + ":false}")
    protected Boolean tracerMethodContextOutputEnabled;

    @Autowired(required = false)
    private List<ServiceStrategyMonitorAdapter> serviceStrategyMonitorAdapterList;

    @Override
    public void monitor(ServiceStrategyMonitorInterceptor interceptor, MethodInvocation invocation) {
        spanBuild();

        loggerOutput();
        loggerDebug();
    }

    @Override
    public void monitor(ServiceStrategyMonitorInterceptor interceptor, MethodInvocation invocation, Object returnValue) {
        spanOutput(createContextMap(interceptor, invocation, returnValue));
    }

    @Override
    public void error(ServiceStrategyMonitorInterceptor interceptor, MethodInvocation invocation, Throwable e) {
        spanError(e);
    }

    @Override
    public void release(ServiceStrategyMonitorInterceptor interceptor, MethodInvocation invocation) {
        loggerClear();

        spanFinish();
    }

    private Map<String, String> createContextMap(ServiceStrategyMonitorInterceptor interceptor, MethodInvocation invocation, Object returnValue) {
        if (!tracerEnabled) {
            return null;
        }

        Map<String, String> contextMap = new HashMap<String, String>();

        String className = interceptor.getMethod(invocation).getDeclaringClass().getName();
        String methodName = interceptor.getMethodName(invocation);
        contextMap.put("* " + DiscoveryConstant.CLASS, className);
        contextMap.put("* " + DiscoveryConstant.METHOD, methodName);

        if (tracerMethodContextOutputEnabled) {
            String[] methodParameterNames = interceptor.getMethodParameterNames(invocation);
            Object[] arguments = interceptor.getArguments(invocation);
            Map<String, Object> parameterMap = ClassUtil.getParameterMap(methodParameterNames, arguments);
            if (CollectionUtils.isNotEmpty(serviceStrategyMonitorAdapterList)) {
                for (ServiceStrategyMonitorAdapter serviceStrategyMonitorAdapter : serviceStrategyMonitorAdapterList) {
                    Map<String, String> customizationMap = serviceStrategyMonitorAdapter.getCustomizationMap(interceptor, invocation, parameterMap, returnValue);
                    for (Map.Entry<String, String> entry : customizationMap.entrySet()) {
                        contextMap.put("* " + entry.getKey(), entry.getValue());
                    }
                }
            }
        }

        return contextMap;
    }
}
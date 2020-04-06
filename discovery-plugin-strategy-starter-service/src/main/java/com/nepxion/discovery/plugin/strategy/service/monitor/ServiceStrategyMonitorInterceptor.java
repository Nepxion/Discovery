package com.nepxion.discovery.plugin.strategy.service.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.constant.ServiceStrategyConstant;
import com.nepxion.matrix.proxy.aop.AbstractInterceptor;

public class ServiceStrategyMonitorInterceptor extends AbstractInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceStrategyMonitorInterceptor.class);

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACER_METHOD_CONTEXT_OUTPUT_ENABLED + ":false}")
    protected Boolean tracerMethodContextOutputEnabled;

    @Autowired(required = false)
    private List<ServiceStrategyMonitor> serviceStrategyMonitorList;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            String className = getMethod(invocation).getDeclaringClass().getName();
            String methodName = getMethodName(invocation);
            if (StringUtils.equals(className, ServiceStrategyConstant.INSPECTOR_ENDPOINT_CLASS_NAME) && StringUtils.equals(methodName, ServiceStrategyConstant.INSPECTOR_ENDPOINT_METHOD_NAME)) {
                // 调用链监控
                if (CollectionUtils.isNotEmpty(serviceStrategyMonitorList)) {
                    for (ServiceStrategyMonitor serviceStrategyMonitor : serviceStrategyMonitorList) {
                        serviceStrategyMonitor.monitor(this, invocation, null);
                    }
                }

                return invocation.proceed();
            } else {
                Object returnValue = null;

                if (tracerMethodContextOutputEnabled) {
                    returnValue = invocation.proceed();
                }

                // 调用链监控
                if (CollectionUtils.isNotEmpty(serviceStrategyMonitorList)) {
                    for (ServiceStrategyMonitor serviceStrategyMonitor : serviceStrategyMonitorList) {
                        serviceStrategyMonitor.monitor(this, invocation, returnValue);
                    }
                }

                if (!tracerMethodContextOutputEnabled) {
                    returnValue = invocation.proceed();
                }

                return returnValue;
            }
        } catch (Throwable e) {
            if (CollectionUtils.isNotEmpty(serviceStrategyMonitorList)) {
                for (ServiceStrategyMonitor serviceStrategyMonitor : serviceStrategyMonitorList) {
                    serviceStrategyMonitor.error(this, invocation, e);
                }
            }

            LOG.error("Method={} of class={} threw following exception with root cause", getMethodName(invocation), getMethod(invocation).getDeclaringClass().getName(), e);

            throw e;
        } finally {
            // 调用链释放
            if (CollectionUtils.isNotEmpty(serviceStrategyMonitorList)) {
                for (ServiceStrategyMonitor serviceStrategyMonitor : serviceStrategyMonitorList) {
                    serviceStrategyMonitor.release(this, invocation);
                }
            }
        }
    }
}
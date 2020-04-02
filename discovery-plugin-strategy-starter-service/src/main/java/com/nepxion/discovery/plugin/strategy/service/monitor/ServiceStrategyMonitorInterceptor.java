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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.matrix.proxy.aop.AbstractInterceptor;

public class ServiceStrategyMonitorInterceptor extends AbstractInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceStrategyMonitorInterceptor.class);

    @Autowired(required = false)
    private List<ServiceStrategyMonitor> serviceStrategyMonitorList;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            Object returnValue = invocation.proceed();

            // 调用链监控
            if (CollectionUtils.isNotEmpty(serviceStrategyMonitorList)) {
                for (ServiceStrategyMonitor serviceStrategyMonitor : serviceStrategyMonitorList) {
                    serviceStrategyMonitor.monitor(this, invocation, returnValue);
                }
            }

            return returnValue;
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
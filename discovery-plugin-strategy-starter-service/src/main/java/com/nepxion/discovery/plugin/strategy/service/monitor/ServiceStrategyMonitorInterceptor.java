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
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.matrix.proxy.aop.AbstractInterceptor;

public class ServiceStrategyMonitorInterceptor extends AbstractInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceStrategyMonitorInterceptor.class);

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACER_METHOD_CONTEXT_OUTPUT_ENABLED + ":false}")
    protected Boolean tracerMethodContextOutputEnabled;

    @Autowired(required = false)
    private ServiceStrategyMonitor serviceStrategyMonitor;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String className = getMethod(invocation).getDeclaringClass().getName();
        String methodName = getMethodName(invocation);
        boolean isMonitored = false;
        try {
            // 拦截侦测请求  
            if (StringUtils.equals(className, DiscoveryConstant.INSPECTOR_ENDPOINT_CLASS_NAME) && StringUtils.equals(methodName, DiscoveryConstant.INSPECTOR_ENDPOINT_METHOD_NAME)) {
                // 调用链监控
                if (serviceStrategyMonitor != null) {
                    serviceStrategyMonitor.monitor(this, invocation, null);
                    isMonitored = true;
                }

                return invocation.proceed();
            } else {
                Object returnValue = null;

                if (tracerMethodContextOutputEnabled) {
                    returnValue = invocation.proceed();
                }

                // 调用链监控
                if (serviceStrategyMonitor != null) {
                    serviceStrategyMonitor.monitor(this, invocation, returnValue);
                    isMonitored = true;
                }

                if (!tracerMethodContextOutputEnabled) {
                    returnValue = invocation.proceed();
                }

                return returnValue;
            }
        } catch (Throwable e) {
            if (serviceStrategyMonitor != null) {
                if (!isMonitored) {
                    serviceStrategyMonitor.monitor(this, invocation, null);
                }
                serviceStrategyMonitor.error(this, invocation, e);
            }

            LOG.error("Method={} of class={} threw following exception with root cause", methodName, className, e);

            throw e;
        } finally {
            // 调用链释放
            if (serviceStrategyMonitor != null) {
                serviceStrategyMonitor.release(this, invocation);
            }
        }
    }
}
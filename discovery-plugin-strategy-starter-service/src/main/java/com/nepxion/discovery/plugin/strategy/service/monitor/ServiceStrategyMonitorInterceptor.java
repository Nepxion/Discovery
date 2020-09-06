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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.matrix.proxy.aop.AbstractInterceptor;

public class ServiceStrategyMonitorInterceptor extends AbstractInterceptor {
    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACER_METHOD_CONTEXT_OUTPUT_ENABLED + ":false}")
    protected Boolean tracerMethodContextOutputEnabled;

    @Autowired(required = false)
    protected ServiceStrategyMonitor serviceStrategyMonitor;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String className = getMethod(invocation).getDeclaringClass().getName();
        String methodName = getMethodName(invocation);
        boolean isMonitored = false;
        boolean isMethodContextMonitored = false;
        try {
            // 拦截侦测请求  
            if (StringUtils.equals(className, DiscoveryConstant.INSPECTOR_ENDPOINT_CLASS_NAME) && StringUtils.equals(methodName, DiscoveryConstant.INSPECTOR_ENDPOINT_METHOD_NAME)) {
                // 调用链监控
                if (serviceStrategyMonitor != null) {
                    // 通用输出
                    serviceStrategyMonitor.monitor(this, invocation);
                    isMonitored = true;

                    // 方法上下文输出
                    serviceStrategyMonitor.monitor(this, invocation, "* " + DiscoveryConstant.IGNORED);
                    isMethodContextMonitored = true;
                }

                return invocation.proceed();
            } else {
                // 调用链监控
                if (serviceStrategyMonitor != null) {
                    // 通用输出
                    serviceStrategyMonitor.monitor(this, invocation);
                    isMonitored = true;
                }

                Object returnValue = null;
                if (tracerMethodContextOutputEnabled) {
                    // 先执行调用，根据调用结果再输出监控结果
                    returnValue = invocation.proceed();

                    // 调用链监控
                    if (serviceStrategyMonitor != null) {
                        // 方法上下文输出
                        serviceStrategyMonitor.monitor(this, invocation, returnValue);
                        isMethodContextMonitored = true;
                    }
                } else {
                    // 调用链监控
                    if (serviceStrategyMonitor != null) {
                        // 方法上下文输出
                        serviceStrategyMonitor.monitor(this, invocation, returnValue);
                        isMethodContextMonitored = true;
                    }

                    // 后执行调用
                    returnValue = invocation.proceed();
                }

                return returnValue;
            }
        } catch (Throwable e) {
            // 调用链异常监控
            if (serviceStrategyMonitor != null) {
                if (!isMonitored) {
                    // 通用输出
                    serviceStrategyMonitor.monitor(this, invocation);
                }
                if (!isMethodContextMonitored) {
                    // 方法上下文输出
                    serviceStrategyMonitor.monitor(this, invocation, null);
                }
                serviceStrategyMonitor.error(this, invocation, e);
            }

            throw e;
        } finally {
            // 调用链释放
            if (serviceStrategyMonitor != null) {
                serviceStrategyMonitor.release(this, invocation);
            }
        }
    }
}
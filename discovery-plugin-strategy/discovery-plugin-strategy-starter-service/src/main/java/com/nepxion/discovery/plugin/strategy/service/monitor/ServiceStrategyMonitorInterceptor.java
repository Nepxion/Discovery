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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.InitBinder;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.annotation.ServiceMonitorIgnore;
import com.nepxion.matrix.proxy.aop.AbstractInterceptor;

public class ServiceStrategyMonitorInterceptor extends AbstractInterceptor {
    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACER_METHOD_CONTEXT_OUTPUT_ENABLED + ":false}")
    protected Boolean tracerMethodContextOutputEnabled;

    @Autowired
    protected ServiceStrategyMonitor serviceStrategyMonitor;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        boolean hasInitBinderAnnotation = getMethod(invocation).isAnnotationPresent(InitBinder.class);
        if (hasInitBinderAnnotation) {
            return invocation.proceed();
        }

        String className = getMethod(invocation).getDeclaringClass().getName();
        String methodName = getMethodName(invocation);
        boolean isMonitorIgnored = false;
        boolean isMonitored = false;
        boolean isMethodContextMonitored = false;
        try {
            // 拦截侦测请求  
            if (StringUtils.equals(className, DiscoveryConstant.INSPECTOR_ENDPOINT_CLASS_NAME) && StringUtils.equals(methodName, DiscoveryConstant.INSPECTOR_ENDPOINT_METHOD_NAME)) {
                // 埋点创建、日志输出、告警输出
                serviceStrategyMonitor.monitor(this, invocation);
                isMonitored = true;

                // 埋点输出
                serviceStrategyMonitor.monitor(this, invocation, "* " + DiscoveryConstant.IGNORED);
                isMethodContextMonitored = true;

                return invocation.proceed();
            } else {
                isMonitorIgnored = getMethod(invocation).isAnnotationPresent(ServiceMonitorIgnore.class);
                if (isMonitorIgnored) {
                    return invocation.proceed();
                }

                // 埋点创建、日志输出、告警输出
                serviceStrategyMonitor.monitor(this, invocation);
                isMonitored = true;

                if (tracerMethodContextOutputEnabled) {
                    // 先执行调用，根据调用结果再输出返回值的埋点
                    Object returnValue = invocation.proceed();

                    // 埋点输出
                    serviceStrategyMonitor.monitor(this, invocation, returnValue);
                    isMethodContextMonitored = true;

                    return returnValue;
                } else {
                    // 埋点方法上下文输出
                    serviceStrategyMonitor.monitor(this, invocation, null);
                    isMethodContextMonitored = true;

                    return invocation.proceed();
                }
            }
        } catch (Throwable e) {
            if (!isMonitorIgnored) {
                if (!isMonitored) {
                    // 埋点创建、日志输出、告警输出
                    serviceStrategyMonitor.monitor(this, invocation);
                    isMonitored = true;
                }
                if (!isMethodContextMonitored) {
                    // 埋点输出
                    serviceStrategyMonitor.monitor(this, invocation, null);
                    isMethodContextMonitored = true;
                }
                // 埋点异常输出
                serviceStrategyMonitor.error(this, invocation, e);
            }

            throw e;
        } finally {
            if (!isMonitorIgnored && isMonitored && isMethodContextMonitored) {
                // 埋点提交、日志清除
                serviceStrategyMonitor.release(this, invocation);
            }
        }
    }
}
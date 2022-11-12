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
import com.nepxion.matrix.proxy.aop.AbstractInterceptor;

public class ServiceStrategyMonitorInterceptor extends AbstractInterceptor {
    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACER_METHOD_CONTEXT_OUTPUT_ENABLED + ":false}")
    protected Boolean tracerMethodContextOutputEnabled;

    @Autowired
    protected ServiceStrategyMonitor serviceStrategyMonitor;

    @Autowired(required = false)
    protected ServiceStrategyMonitorInterceptorAdapter serviceStrategyMonitorInterceptorAdapter;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        boolean hasInitBinderAnnotation = getMethod(invocation).isAnnotationPresent(InitBinder.class);
        if (hasInitBinderAnnotation) {
            return invocation.proceed();
        }

        String className = getMethod(invocation).getDeclaringClass().getName();
        String methodName = getMethodName(invocation);
        boolean isInterceptionAllowed = true;
        boolean isMonitored = false;
        boolean isMethodContextMonitored = false;
        try {
            // 拦截侦测请求  
            if (StringUtils.equals(className, DiscoveryConstant.INSPECTOR_ENDPOINT_CLASS_NAME) && StringUtils.equals(methodName, DiscoveryConstant.INSPECTOR_ENDPOINT_METHOD_NAME)) {
                // 埋点创建
                serviceStrategyMonitor.monitor(this, invocation);
                isMonitored = true;

                // 埋点方法上下文输出
                serviceStrategyMonitor.monitor(this, invocation, "* " + DiscoveryConstant.IGNORED);
                isMethodContextMonitored = true;

                return invocation.proceed();
            } else {
                isInterceptionAllowed = allowInterception(className, methodName);
                if (!isInterceptionAllowed) {
                    return invocation.proceed();
                }

                // 埋点创建
                serviceStrategyMonitor.monitor(this, invocation);
                isMonitored = true;

                if (tracerMethodContextOutputEnabled) {
                    // 先执行调用，根据调用结果再输出返回值的埋点
                    Object returnValue = invocation.proceed();

                    // 埋点方法上下文输出
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
            if (isInterceptionAllowed) {
                if (!isMonitored) {
                    // 埋点创建
                    serviceStrategyMonitor.monitor(this, invocation);
                    isMonitored = true;
                }
                if (!isMethodContextMonitored) {
                    // 埋点方法上下文输出
                    serviceStrategyMonitor.monitor(this, invocation, null);
                    isMethodContextMonitored = true;
                }
                // 埋点异常上下文输出
                serviceStrategyMonitor.error(this, invocation, e);
            }

            throw e;
        } finally {
            if (isInterceptionAllowed && isMonitored && isMethodContextMonitored) {
                // 埋点提交
                serviceStrategyMonitor.release(this, invocation);
            }
        }
    }

    protected boolean allowInterception(String className, String methodName) {
        if (serviceStrategyMonitorInterceptorAdapter != null) {
            return serviceStrategyMonitorInterceptorAdapter.allowInterception(className, methodName);
        }

        return true;
    }
}
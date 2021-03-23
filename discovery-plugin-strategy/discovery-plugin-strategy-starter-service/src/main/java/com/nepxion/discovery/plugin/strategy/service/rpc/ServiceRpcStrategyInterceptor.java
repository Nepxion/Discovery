package com.nepxion.discovery.plugin.strategy.service.rpc;

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
import org.springframework.web.bind.annotation.InitBinder;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.util.ClassUtil;
import com.nepxion.discovery.plugin.strategy.service.context.RpcStrategyContext;
import com.nepxion.matrix.proxy.aop.AbstractInterceptor;

public class ServiceRpcStrategyInterceptor extends AbstractInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        boolean hasInitBinderAnnotation = getMethod(invocation).isAnnotationPresent(InitBinder.class);
        if (hasInitBinderAnnotation) {
            return invocation.proceed();
        }

        Class<?> clazz = getMethod(invocation).getDeclaringClass();
        String methodName = getMethodName(invocation);
        String[] methodParameterNames = getMethodParameterNames(invocation);
        Object[] arguments = getArguments(invocation);
        Map<String, Object> parameterMap = ClassUtil.getParameterMap(methodParameterNames, arguments);

        RpcStrategyContext context = RpcStrategyContext.getCurrentContext();
        context.add(DiscoveryConstant.CLASS, clazz);
        context.add(DiscoveryConstant.METHOD, methodName);
        context.add(DiscoveryConstant.PARAMETER_MAP, parameterMap);

        try {
            return invocation.proceed();
        } finally {
            RpcStrategyContext.clearCurrentContext();
        }
    }
}
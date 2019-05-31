package com.nepxion.discovery.plugin.strategy.service.aop;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.LinkedHashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nepxion.discovery.plugin.strategy.service.constant.ServiceStrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.context.RpcStrategyContext;
import com.nepxion.matrix.proxy.aop.AbstractInterceptor;

public class RpcStrategyInterceptor extends AbstractInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(RpcStrategyInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Class<?> proxiedClass = getProxiedClass(invocation);
        String methodName = getMethodName(invocation);
        String[] methodParameterNames = getMethodParameterNames(invocation);
        Object[] arguments = getArguments(invocation);

        Map<String, Object> parameterMap = new LinkedHashMap<String, Object>();
        if (ArrayUtils.isNotEmpty(arguments)) {
            for (int i = 0; i < arguments.length; i++) {
                String parameterName = null;
                if (ArrayUtils.isNotEmpty(methodParameterNames)) {
                    parameterName = methodParameterNames[i];
                } else {
                    parameterName = String.valueOf(i);
                }
                Object argument = arguments[i];

                parameterMap.put(parameterName, argument);
            }
        }

        RpcStrategyContext context = RpcStrategyContext.getCurrentContext();
        context.add(ServiceStrategyConstant.CLASS, proxiedClass);
        context.add(ServiceStrategyConstant.METHOD, methodName);
        context.add(ServiceStrategyConstant.PARAMETER_MAP, parameterMap);

        LOG.debug("Rpc strategy context is set with {}", context);

        try {
            return invocation.proceed();
        } finally {
            RpcStrategyContext.clearCurrentContext();

            LOG.debug("Rpc strategy context is cleared");
        }
    }
}
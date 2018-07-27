package com.nepxion.discovery.plugin.strategy.extension.aop;

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

import com.nepxion.discovery.plugin.strategy.extension.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.extension.context.StrategyContext;
import com.nepxion.discovery.plugin.strategy.extension.context.StrategyContextHolder;
import com.nepxion.matrix.proxy.aop.AbstractInterceptor;

public class StrategyInterceptor extends AbstractInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(StrategyInterceptor.class);

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

        LOG.debug("Context is set with class={}, methodName={}, parameterMap={}", proxiedClass, methodName, parameterMap);

        StrategyContext context = StrategyContextHolder.currentContext();
        context.add(StrategyConstant.CLASS, proxiedClass);
        context.add(StrategyConstant.METHOD, methodName);
        context.add(StrategyConstant.PARAMETER_MAP, parameterMap);

        try {
            return invocation.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            context.clear();
        }
    }
}
package com.nepxion.discovery.plugin.strategy.service.tracer;

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
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.matrix.proxy.aop.AbstractInterceptor;

public class ServiceStrategyTracerInterceptor extends AbstractInterceptor {
    @Autowired(required = false)
    private List<ServiceStrategyTracer> serviceStrategyTracerList;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            // 调用链追踪
            if (CollectionUtils.isNotEmpty(serviceStrategyTracerList)) {
                for (ServiceStrategyTracer serviceStrategyTracer : serviceStrategyTracerList) {
                    serviceStrategyTracer.trace(this, invocation);
                }
            }

            return invocation.proceed();
        } catch (Throwable e) {
            if (CollectionUtils.isNotEmpty(serviceStrategyTracerList)) {
                for (ServiceStrategyTracer serviceStrategyTracer : serviceStrategyTracerList) {
                    serviceStrategyTracer.error(this, invocation, e);
                }
            }

            throw e;
        } finally {
            if (CollectionUtils.isNotEmpty(serviceStrategyTracerList)) {
                for (ServiceStrategyTracer serviceStrategyTracer : serviceStrategyTracerList) {
                    serviceStrategyTracer.release(this, invocation);
                }
            }
        }
    }
}
package com.nepxion.discovery.plugin.strategy.service.isolation;

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
import org.springframework.web.bind.annotation.InitBinder;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.service.context.ServiceStrategyContextHolder;
import com.nepxion.matrix.proxy.aop.AbstractInterceptor;

public class ServiceProviderIsolationStrategyInterceptor extends AbstractInterceptor {
    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected ServiceStrategyContextHolder serviceStrategyContextHolder;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        boolean hasInitBinderAnnotation = getMethod(invocation).isAnnotationPresent(InitBinder.class);
        if (hasInitBinderAnnotation) {
            return invocation.proceed();
        }

        String groupHeader = serviceStrategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_GROUP);
        String group = pluginAdapter.getGroup();
        String serviceId = pluginAdapter.getServiceId();
        if (!StringUtils.equals(groupHeader, group)) {
            throw new DiscoveryException("Reject to invoke because of isolation with different service group for serviceId=" + serviceId);
        }

        return invocation.proceed();
    }
}
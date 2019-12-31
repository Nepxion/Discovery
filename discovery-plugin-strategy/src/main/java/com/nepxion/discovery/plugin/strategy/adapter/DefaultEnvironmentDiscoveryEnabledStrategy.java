package com.nepxion.discovery.plugin.strategy.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.netflix.loadbalancer.Server;

public class DefaultEnvironmentDiscoveryEnabledStrategy extends DefaultDiscoveryEnabledStrategy {
    @Override
    public boolean apply(Server server) {
        String environment = pluginAdapter.getEnvironment();
        // 本地配置了environment，说明本服务（一般来说是网关）也处在子环境里，则由EnvironmentFilterLoadBalanceListener方式去执行环境隔离，返回true
        if (!StringUtils.equals(environment, DiscoveryConstant.DEFAULT)) {
            return true;
        }

        String headerEnvironment = strategyContextHolder.getHeader(DiscoveryConstant.ENVIRONMENT);
        // 传入headerEnvironment为空，返回true
        if (StringUtils.isEmpty(headerEnvironment)) {
            return true;
        }

        String serverEnvironment = pluginAdapter.getServerEnvironment(server);

        // 本服务（一般来说是网关）不隶属任何子环境，由它来调度子环境的服务调用
        return StringUtils.equals(headerEnvironment, serverEnvironment);
    }
}
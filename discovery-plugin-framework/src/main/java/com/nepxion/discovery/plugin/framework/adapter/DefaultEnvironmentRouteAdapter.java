package com.nepxion.discovery.plugin.framework.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.constant.DiscoveryConstant;

public class DefaultEnvironmentRouteAdapter implements EnvironmentRouteAdapter {
    @Autowired
    protected PluginAdapter pluginAdapter;

    @Value("${" + DiscoveryConstant.SPRING_APPLICATION_ENVIRONMENT_ROUTE_ENABLED + ":false}")
    protected Boolean environmentRouteEnabled;

    @Value("${" + DiscoveryConstant.SPRING_APPLICATION_ENVIRONMENT_ROUTE + ":" + DiscoveryConstant.SPRING_APPLICATION_ENVIRONMENT_ROUTE_VALUE + "}")
    protected String environmentRoute;

    @Override
    public boolean isRouteEnabled() {
        return environmentRouteEnabled;
    }

    @Override
    public String getEnvironmentRoute() {
        return environmentRoute;
    }

    public PluginAdapter getPluginAdapter() {
        return pluginAdapter;
    }
}
package com.nepxion.discovery.plugin.strategy.zuul.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;
import com.nepxion.discovery.plugin.strategy.zuul.constant.ZuulStrategyConstant;
import com.netflix.zuul.ZuulFilter;

public abstract class AbstractZuulStrategyRouteFilter extends ZuulFilter {
    @Autowired
    private ConfigurableEnvironment environment;

    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected StrategyContextHolder strategyContextHolder;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return environment.getProperty(ZuulStrategyConstant.SPRING_APPLICATION_STRATEGY_ZUUL_ROUTE_FILTER_ORDER, Integer.class, ZuulStrategyConstant.SPRING_APPLICATION_STRATEGY_ZUUL_ROUTE_FILTER_ORDER_VALUE);
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        String routeVersion = getRouteVersion();
        String routeRegion = getRouteRegion();
        String routeAddress = getRouteAddress();

        // 通过过滤器设置路由Header头部信息，来取代界面（Postman）上的设置，并全链路传递到服务端
        if (StringUtils.isNotEmpty(routeVersion)) {
            ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_VERSION, routeVersion);
        }
        if (StringUtils.isNotEmpty(routeRegion)) {
            ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_REGION, routeRegion);
        }
        if (StringUtils.isNotEmpty(routeAddress)) {
            ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_ADDRESS, routeAddress);
        }

        // 开启此项，将启动提供端的服务隔离机制，需要在网关上传递Group Header
        if (environment.getProperty(StrategyConstant.SPRING_APPLICATION_STRATEGY_PROVIDER_ISOLATION_ENABLED, Boolean.class, Boolean.FALSE)) {
            ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_GROUP, pluginAdapter.getGroup());
        }

        return null;
    }

    public PluginAdapter getPluginAdapter() {
        return pluginAdapter;
    }

    public StrategyContextHolder getStrategyContextHolder() {
        return strategyContextHolder;
    }

    protected abstract String getRouteVersion();

    protected abstract String getRouteRegion();

    protected abstract String getRouteAddress();
}
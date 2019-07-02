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

public abstract class AbstractZuulStrategyRouteFilter extends ZuulFilter implements ZuulStrategyRouteFilter {
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
        String routeVersionWeight = getRouteVersionWeight();
        String routeRegionWeight = getRouteRegionWeight();

        // 通过过滤器设置路由Header头部信息，并全链路传递到服务端
        // 如果外界也传了相同的Header，例如，从Postman传递过来的Header，当下面的变量为true，以网关设置为优先，否则以外界传值为优先
        Boolean zuulHeaderPriority = environment.getProperty(ZuulStrategyConstant.SPRING_APPLICATION_STRATEGY_ZUUL_HEADER_PRIORITY, Boolean.class, Boolean.TRUE);

        // 通过过滤器设置路由Header头部信息，并全链路传递到服务端
        if (StringUtils.isNotEmpty(routeVersion)) {
            ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_VERSION, routeVersion, zuulHeaderPriority);
        }
        if (StringUtils.isNotEmpty(routeRegion)) {
            ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_REGION, routeRegion, zuulHeaderPriority);
        }
        if (StringUtils.isNotEmpty(routeAddress)) {
            ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_ADDRESS, routeAddress, zuulHeaderPriority);
        }
        if (StringUtils.isNotEmpty(routeVersionWeight)) {
            ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_VERSION_WEIGHT, routeVersionWeight, zuulHeaderPriority);
        }
        if (StringUtils.isNotEmpty(routeRegionWeight)) {
            ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_REGION_WEIGHT, routeRegionWeight, zuulHeaderPriority);
        }

        // 开启此项，将启动提供端的服务隔离机制，则传递到服务的是网关自身ServiceType, ServiceId, ServiceHost, Group
        // 如果不隔离，为了调用链完整，最好外界自身传递ServiceType, ServiceId, ServiceHost, Group
        Boolean providerIsolationEnabled = environment.getProperty(StrategyConstant.SPRING_APPLICATION_STRATEGY_PROVIDER_ISOLATION_ENABLED, Boolean.class, Boolean.FALSE);

        ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_SERVICE_TYPE, pluginAdapter.getServiceType(), providerIsolationEnabled ? providerIsolationEnabled : zuulHeaderPriority);
        ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_SERVICE_ID, pluginAdapter.getServiceId(), providerIsolationEnabled ? providerIsolationEnabled : zuulHeaderPriority);
        ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_SERVICE_ADDRESS, pluginAdapter.getHost() + ":" + pluginAdapter.getPort(), providerIsolationEnabled ? providerIsolationEnabled : zuulHeaderPriority);
        ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_SERVICE_GROUP, pluginAdapter.getGroup(), providerIsolationEnabled ? providerIsolationEnabled : zuulHeaderPriority);
        ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_SERVICE_VERSION, pluginAdapter.getVersion(), providerIsolationEnabled ? providerIsolationEnabled : zuulHeaderPriority);
        ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_SERVICE_REGION, pluginAdapter.getRegion(), providerIsolationEnabled ? providerIsolationEnabled : zuulHeaderPriority);

        return null;
    }

    public PluginAdapter getPluginAdapter() {
        return pluginAdapter;
    }

    public StrategyContextHolder getStrategyContextHolder() {
        return strategyContextHolder;
    }
}
package com.nepxion.discovery.plugin.example.zuul.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.StrategyEntity;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.zuul.filter.ZuulStrategyFilterResolver;
import com.netflix.zuul.ZuulFilter;

public class MyZuulFilter extends ZuulFilter {
    @Autowired
    private PluginAdapter pluginAdapter;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        String routeVersion = getRouteVersionFromConfig();
        // String routeVersion = getRouteVersionFromCustomer();

        String routeRegion = getRouteRegionFromConfig();
        // String routeRegion = getRouteRegionFromCustomer();

        System.out.println("Route Version=" + routeVersion);
        System.out.println("Route Region=" + routeRegion);

        // 通过过滤器设置路由Header头部信息，来取代界面（Postman）上的设置，并全链路传递到服务端
        ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_VERSION, routeVersion);
        ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_REGION, routeRegion);

        return null;
    }

    // 从远程配置中心或者本地配置文件获取版本路由配置。如果是远程配置中心，则值会动态改变
    protected String getRouteVersionFromConfig() {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity != null) {
            StrategyEntity strategyEntity = ruleEntity.getStrategyEntity();
            if (strategyEntity != null) {
                return strategyEntity.getVersionValue();
            }
        }

        return null;
    }

    // 从远程配置中心或者本地配置文件获取区域路由配置。如果是远程配置中心，则值会动态改变
    protected String getRouteRegionFromConfig() {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity != null) {
            StrategyEntity strategyEntity = ruleEntity.getStrategyEntity();
            if (strategyEntity != null) {
                return strategyEntity.getRegionValue();
            }
        }

        return null;
    }

    // 自定义版本路由配置
    protected String getRouteVersionFromCustomer() {
        return "{\"discovery-springcloud-example-a\":\"1.0\", \"discovery-springcloud-example-b\":\"1.0\", \"discovery-springcloud-example-c\":\"1.0;1.2\"}";
    }

    // 自定义区域路由配置
    protected String getRouteRegionFromCustomer() {
        return "{\"discovery-springcloud-example-a\":\"qa;dev\", \"discovery-springcloud-example-b\":\"dev\", \"discovery-springcloud-example-c\":\"qa\"}";
    }
}
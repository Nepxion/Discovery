package com.nepxion.discovery.plugin.strategy.service.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;

// 负载均衡中执行路由过滤
public class DefaultServiceStrategyRouteFilter extends AbstractServiceStrategyRouteFilter {
    @Autowired
    protected StrategyContextHolder strategyContextHolder;

    @Override
    public String getRouteVersion() {
        return null;
    }

    @Override
    public String getRouteRegion() {
        return null;
    }

    @Override
    public String getRouteEnvironment() {
        return null;
    }

    @Override
    public String getRouteAddress() {
        return null;
    }

    @Override
    public String getRouteVersionWeight() {
        return null;
    }

    @Override
    public String getRouteRegionWeight() {
        return null;
    }

    @Override
    public String getRouteIdBlacklist() {
        return null;
    }

    @Override
    public String getRouteAddressBlacklist() {
        return null;
    }

    public StrategyContextHolder getStrategyContextHolder() {
        return strategyContextHolder;
    }
}
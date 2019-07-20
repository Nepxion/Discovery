package com.nepxion.discovery.plugin.strategy.zuul.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.strategy.wrapper.StrategyWrapper;

public class DefaultZuulStrategyRouteFilter extends AbstractZuulStrategyRouteFilter {
    @Autowired
    protected StrategyWrapper strategyWrapper;

    @Override
    public String getRouteVersion() {
        return strategyWrapper.getRouteVersion();
    }

    @Override
    public String getRouteRegion() {
        return strategyWrapper.getRouteRegion();
    }

    @Override
    public String getRouteAddress() {
        return strategyWrapper.getRouteAddress();
    }

    @Override
    public String getRouteVersionWeight() {
        return strategyWrapper.getRouteVersionWeight();
    }

    @Override
    public String getRouteRegionWeight() {
        return strategyWrapper.getRouteRegionWeight();
    }
}
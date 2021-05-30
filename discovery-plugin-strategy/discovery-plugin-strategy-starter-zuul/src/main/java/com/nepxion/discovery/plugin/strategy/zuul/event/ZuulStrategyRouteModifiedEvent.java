package com.nepxion.discovery.plugin.strategy.zuul.event;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.Serializable;

import com.nepxion.discovery.common.entity.ZuulStrategyRouteEntity;

public class ZuulStrategyRouteModifiedEvent implements Serializable {
    private static final long serialVersionUID = 3805426536724590753L;
    
    private ZuulStrategyRouteEntity zuulStrategyRouteEntity;

    public ZuulStrategyRouteModifiedEvent(ZuulStrategyRouteEntity zuulStrategyRouteEntity) {
        this.zuulStrategyRouteEntity = zuulStrategyRouteEntity;
    }

    public ZuulStrategyRouteEntity getZuulStrategyRouteEntity() {
        return zuulStrategyRouteEntity;
    }
}
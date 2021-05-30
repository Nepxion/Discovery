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
import java.util.List;

import com.nepxion.discovery.common.entity.ZuulStrategyRouteEntity;

public class ZuulStrategyRouteUpdatedAllEvent implements Serializable {
    private static final long serialVersionUID = 4059761231628294140L;

    private List<ZuulStrategyRouteEntity> zuulStrategyRouteEntityList;

    public ZuulStrategyRouteUpdatedAllEvent(List<ZuulStrategyRouteEntity> zuulStrategyRouteEntityList) {
        this.zuulStrategyRouteEntityList = zuulStrategyRouteEntityList;
    }

    public List<ZuulStrategyRouteEntity> getZuulStrategyRouteEntityList() {
        return zuulStrategyRouteEntityList;
    }
}
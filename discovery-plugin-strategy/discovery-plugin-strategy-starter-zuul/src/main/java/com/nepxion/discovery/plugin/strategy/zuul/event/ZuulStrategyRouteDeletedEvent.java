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

public class ZuulStrategyRouteDeletedEvent implements Serializable {
    private static final long serialVersionUID = -819386359251658004L;

    private String routeId;

    public ZuulStrategyRouteDeletedEvent(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteId() {
        return routeId;
    }
}
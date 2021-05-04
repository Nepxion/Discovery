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

public class ZuulStrategyDeleteRouteEvent implements Serializable {
    private static final long serialVersionUID = -819386359251658004L;

    private String routeId;

    public ZuulStrategyDeleteRouteEvent(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteId() {
        return routeId;
    }
}
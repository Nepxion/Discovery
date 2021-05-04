package com.nepxion.discovery.plugin.strategy.gateway.event;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.Serializable;

public class GatewayStrategyRouteDeletedEvent implements Serializable {
    private static final long serialVersionUID = 9073567694566348300L;

    private String routeId;

    public GatewayStrategyRouteDeletedEvent(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteId() {
        return routeId;
    }
}
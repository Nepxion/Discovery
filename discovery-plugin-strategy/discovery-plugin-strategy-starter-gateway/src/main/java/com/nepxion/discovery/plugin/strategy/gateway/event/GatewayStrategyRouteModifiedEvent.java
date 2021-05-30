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

import com.nepxion.discovery.common.entity.GatewayStrategyRouteEntity;

public class GatewayStrategyRouteModifiedEvent implements Serializable {
    private static final long serialVersionUID = -64955673371333231L;

    private GatewayStrategyRouteEntity gatewayStrategyRouteEntity;

    public GatewayStrategyRouteModifiedEvent(GatewayStrategyRouteEntity gatewayStrategyRouteEntity) {
        this.gatewayStrategyRouteEntity = gatewayStrategyRouteEntity;
    }

    public GatewayStrategyRouteEntity getGatewayStrategyRouteEntity() {
        return gatewayStrategyRouteEntity;
    }
}
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
import java.util.List;

import com.nepxion.discovery.common.entity.GatewayStrategyRouteEntity;

public class GatewayStrategyRouteUpdatedAllEvent implements Serializable {
    private static final long serialVersionUID = -153096630379952734L;

    private List<GatewayStrategyRouteEntity> gatewayStrategyRouteEntityList;

    public GatewayStrategyRouteUpdatedAllEvent(List<GatewayStrategyRouteEntity> gatewayStrategyRouteEntityList) {
        this.gatewayStrategyRouteEntityList = gatewayStrategyRouteEntityList;
    }

    public List<GatewayStrategyRouteEntity> getGatewayStrategyRouteEntityList() {
        return gatewayStrategyRouteEntityList;
    }
}
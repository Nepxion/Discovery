package com.nepxion.discovery.plugin.strategy.gateway.route;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Ning Zhang
 * @version 1.0
 */

import java.util.List;

import com.nepxion.discovery.common.entity.GatewayStrategyRouteEntity;

public interface GatewayStrategyRoute {
    void add(GatewayStrategyRouteEntity gatewayStrategyRouteEntity);

    void modify(GatewayStrategyRouteEntity gatewayStrategyRouteEntity);

    void delete(String routeId);

    void updateAll(List<GatewayStrategyRouteEntity> gatewayStrategyRouteEntityList);

    void updateAll(String gatewayStrategyRouteConfig);

    GatewayStrategyRouteEntity view(String routeId);

    List<GatewayStrategyRouteEntity> viewAll();
}
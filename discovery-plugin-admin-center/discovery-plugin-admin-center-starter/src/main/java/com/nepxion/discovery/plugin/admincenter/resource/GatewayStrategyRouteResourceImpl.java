package com.nepxion.discovery.plugin.admincenter.resource;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.entity.GatewayStrategyRouteEntity;
import com.nepxion.discovery.plugin.strategy.gateway.route.GatewayStrategyRoute;

public class GatewayStrategyRouteResourceImpl implements GatewayStrategyRouteResource {
    @Autowired
    private GatewayStrategyRoute gatewayStrategyRoute;

    @Override
    public void add(GatewayStrategyRouteEntity gatewayStrategyRouteEntity) {
        gatewayStrategyRoute.add(gatewayStrategyRouteEntity);
    }

    @Override
    public void modify(GatewayStrategyRouteEntity gatewayStrategyRouteEntity) {
        gatewayStrategyRoute.modify(gatewayStrategyRouteEntity);
    }

    @Override
    public void delete(String routeId) {
        gatewayStrategyRoute.delete(routeId);
    }

    @Override
    public void updateAll(List<GatewayStrategyRouteEntity> gatewayStrategyRouteEntityList) {
        gatewayStrategyRoute.updateAll(gatewayStrategyRouteEntityList);
    }

    @Override
    public void updateAll(String gatewayStrategyRouteConfig) {
        gatewayStrategyRoute.updateAll(gatewayStrategyRouteConfig);
    }

    @Override
    public GatewayStrategyRouteEntity view(String routeId) {
        return gatewayStrategyRoute.view(routeId);
    }

    @Override
    public List<GatewayStrategyRouteEntity> viewAll() {
        return gatewayStrategyRoute.viewAll();
    }
}
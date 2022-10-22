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

import com.nepxion.discovery.common.entity.ZuulStrategyRouteEntity;
import com.nepxion.discovery.plugin.strategy.zuul.route.ZuulStrategyRoute;

public class ZuulStrategyRouteResourceImpl implements ZuulStrategyRouteResource {
    @Autowired
    private ZuulStrategyRoute zuulStrategyRoute;

    @Override
    public void add(ZuulStrategyRouteEntity zuulStrategyRouteEntity) {
        zuulStrategyRoute.add(zuulStrategyRouteEntity);
    }

    @Override
    public void modify(ZuulStrategyRouteEntity zuulStrategyRouteEntity) {
        zuulStrategyRoute.modify(zuulStrategyRouteEntity);
    }

    @Override
    public void delete(String routeId) {
        zuulStrategyRoute.delete(routeId);
    }

    @Override
    public void updateAll(List<ZuulStrategyRouteEntity> zuulStrategyRouteEntityList) {
        zuulStrategyRoute.updateAll(zuulStrategyRouteEntityList);
    }

    @Override
    public void updateAll(String zuulStrategyRouteConfig) {
        zuulStrategyRoute.updateAll(zuulStrategyRouteConfig);
    }

    @Override
    public ZuulStrategyRouteEntity view(String routeId) {
        return zuulStrategyRoute.view(routeId);
    }

    @Override
    public List<ZuulStrategyRouteEntity> viewAll() {
        return zuulStrategyRoute.viewAll();
    }
}
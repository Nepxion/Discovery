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

import com.nepxion.discovery.common.entity.ZuulStrategyRouteEntity;

public interface ZuulStrategyRouteResource {
    void add(ZuulStrategyRouteEntity zuulStrategyRouteEntity);

    void modify(ZuulStrategyRouteEntity zuulStrategyRouteEntity);

    void delete(String routeId);

    void updateAll(List<ZuulStrategyRouteEntity> zuulStrategyRouteEntityList);

    void updateAll(String zuulStrategyRouteConfig);

    ZuulStrategyRouteEntity view(String routeId);

    List<ZuulStrategyRouteEntity> viewAll();
}
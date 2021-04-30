package com.nepxion.discovery.plugin.strategy.zuul.route;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Ning Zhang
 * @version 1.0
 */

import java.util.List;

import com.nepxion.discovery.plugin.strategy.zuul.entity.ZuulStrategyRouteEntity;

public interface ZuulStrategyRoute {
    void add(ZuulStrategyRouteEntity zuulStrategyRouteEntity);

    // 网关路由对象列表，只允许包含2个动态路由对象，第一个为旧对象，第二个为新对象
    void modify(List<ZuulStrategyRouteEntity> zuulStrategyRouteEntityList);

    void delete(String path);

    void deleteAll(String serviceId);

    List<String> view(String serviceId);

    List<String> viewAll();
}
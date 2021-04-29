package com.nepxion.discovery.plugin.strategy.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Ning Zhang
 * @version 1.0
 */

import java.util.List;

import com.nepxion.discovery.common.entity.DynamicRouteEntity;

public interface StrategyDynamicRouteAdapter {
    void update(List<DynamicRouteEntity> dynamicRouteEntityList);

    List<String> view();
}
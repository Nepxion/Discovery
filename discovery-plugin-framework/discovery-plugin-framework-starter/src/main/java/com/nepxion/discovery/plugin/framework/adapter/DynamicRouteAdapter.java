package com.nepxion.discovery.plugin.framework.adapter;

import com.nepxion.discovery.common.entity.RouteEntity;

import java.util.List;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 *
 * @author Ning Zhang
 * @version 1.0
 */
public interface DynamicRouteAdapter {
    void update(final List<RouteEntity> newRouteList);

    List<String> view();
}
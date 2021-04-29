package com.nepxion.discovery.plugin.framework.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 *
 * @author Ning Zhang
 * @version 1.0
 */

import com.nepxion.discovery.common.entity.DynamicRouteEntity;

import java.util.List;

public interface DynamicRouteAdapter {
    void update(final List<DynamicRouteEntity> dynamicRouteEntityList);

    List<String> view();
}
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

import com.nepxion.discovery.common.entity.RouterEntity;

public interface RouterResource {
    RouterEntity getRouterEntity();

    List<RouterEntity> getRouterEntityList(String routeServiceId);

    List<RouterEntity> getRouterEntityList(String routeServiceId, String routeProtocol, String routeHost, int routePort, String routeContextPath);

    RouterEntity routeTree(String routeServiceIds);
}
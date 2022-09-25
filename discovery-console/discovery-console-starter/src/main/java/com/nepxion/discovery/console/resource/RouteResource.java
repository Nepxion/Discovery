package com.nepxion.discovery.console.resource;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import com.nepxion.discovery.common.entity.GatewayType;
import com.nepxion.discovery.common.entity.ResultEntity;

public interface RouteResource {
    boolean updateRemoteRoute(GatewayType gatewayType, String group, String serviceId, String route);

    boolean clearRemoteRoute(GatewayType gatewayType, String group, String serviceId);

    String getRemoteRoute(GatewayType gatewayType, String group, String serviceId);

    List<ResultEntity> addRoute(GatewayType gatewayType, String serviceId, String route);

    List<ResultEntity> modifyRoute(GatewayType gatewayType, String serviceId, String route);

    List<ResultEntity> deleteRoute(GatewayType gatewayType, String serviceId, String routeId);

    List<ResultEntity> updateAllRoute(GatewayType gatewayType, String serviceId, String route);

    List<ResultEntity> viewAllRoute(GatewayType gatewayType, String serviceId);
}
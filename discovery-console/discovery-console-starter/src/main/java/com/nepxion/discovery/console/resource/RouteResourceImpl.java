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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.FormatType;
import com.nepxion.discovery.common.entity.GatewayType;
import com.nepxion.discovery.common.entity.ResultEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.console.rest.RouteAddRestInvoker;
import com.nepxion.discovery.console.rest.RouteDeleteRestInvoker;
import com.nepxion.discovery.console.rest.RouteModifyRestInvoker;
import com.nepxion.discovery.console.rest.RouteUpdateAllRestInvoker;
import com.nepxion.discovery.console.rest.RouteViewAllRestInvoker;

public class RouteResourceImpl implements RouteResource {
    @Autowired
    private ServiceResource serviceResource;

    @Autowired
    private ConfigResource configResource;

    @Autowired
    private RestTemplate consoleRestTemplate;

    @Override
    public boolean updateRemoteRoute(GatewayType gatewayType, String group, String serviceId, String route) {
        try {
            return configResource.updateRemoteConfig(group, serviceId + "-" + DiscoveryConstant.DYNAMIC_ROUTE_KEY, route, FormatType.JSON_FORMAT);
        } catch (Exception e) {
            throw new DiscoveryException("Update remote " + gatewayType.getName() + " dynamic route failed, group={}, serviceId={}", e);
        }
    }

    @Override
    public boolean clearRemoteRoute(GatewayType gatewayType, String group, String serviceId) {
        try {
            return configResource.updateRemoteConfig(group, serviceId + "-" + DiscoveryConstant.DYNAMIC_ROUTE_KEY, DiscoveryConstant.EMPTY_JSON_RULE_MULTIPLE, FormatType.JSON_FORMAT);
        } catch (Exception e) {
            throw new DiscoveryException("Clear remote " + gatewayType.getName() + " dynamic route failed, group={}, serviceId={}", e);
        }
    }

    @Override
    public String getRemoteRoute(GatewayType gatewayType, String group, String serviceId) {
        try {
            return configResource.getRemoteConfig(group, serviceId + "-" + DiscoveryConstant.DYNAMIC_ROUTE_KEY);
        } catch (Exception e) {
            throw new DiscoveryException("Get remote " + gatewayType.getName() + " dynamic route failed, group={}, serviceId={}", e);
        }
    }

    @Override
    public List<ResultEntity> addRoute(GatewayType gatewayType, String serviceId, String route) {
        RouteAddRestInvoker routeAddRestInvoker = new RouteAddRestInvoker(serviceResource, serviceId, consoleRestTemplate, gatewayType, route);

        return routeAddRestInvoker.invoke();
    }

    @Override
    public List<ResultEntity> modifyRoute(GatewayType gatewayType, String serviceId, String route) {
        RouteModifyRestInvoker routeModifyRestInvoker = new RouteModifyRestInvoker(serviceResource, serviceId, consoleRestTemplate, gatewayType, route);

        return routeModifyRestInvoker.invoke();
    }

    @Override
    public List<ResultEntity> deleteRoute(GatewayType gatewayType, String serviceId, String routeId) {
        RouteDeleteRestInvoker routeDeleteRestInvoker = new RouteDeleteRestInvoker(serviceResource, serviceId, consoleRestTemplate, gatewayType, routeId);

        return routeDeleteRestInvoker.invoke();
    }

    @Override
    public List<ResultEntity> updateAllRoute(GatewayType gatewayType, String serviceId, String route) {
        RouteUpdateAllRestInvoker routeUpdateAllRestInvoker = new RouteUpdateAllRestInvoker(serviceResource, serviceId, consoleRestTemplate, gatewayType, route);

        return routeUpdateAllRestInvoker.invoke();
    }

    @Override
    public List<ResultEntity> viewAllRoute(GatewayType gatewayType, String serviceId) {
        RouteViewAllRestInvoker routeViewAllRestInvoker = new RouteViewAllRestInvoker(serviceResource, serviceId, consoleRestTemplate, gatewayType);

        return routeViewAllRestInvoker.invoke();
    }
}
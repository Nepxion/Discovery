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

import com.nepxion.discovery.common.entity.GatewayType;
import com.nepxion.discovery.common.entity.ResultEntity;
import com.nepxion.discovery.console.rest.RouteAddRestInvoker;
import com.nepxion.discovery.console.rest.RouteDeleteRestInvoker;
import com.nepxion.discovery.console.rest.RouteModifyRestInvoker;
import com.nepxion.discovery.console.rest.RouteUpdateAllRestInvoker;
import com.nepxion.discovery.console.rest.RouteViewAllRestInvoker;

public class RouteResourceImpl implements RouteResource {
    @Autowired
    private ServiceResource serviceResource;

    @Autowired
    private RestTemplate consoleRestTemplate;

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
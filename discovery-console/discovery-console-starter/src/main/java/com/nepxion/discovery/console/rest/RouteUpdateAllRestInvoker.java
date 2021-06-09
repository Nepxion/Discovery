package com.nepxion.discovery.console.rest;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.common.entity.GatewayType;
import com.nepxion.discovery.console.resource.ServiceResource;

public class RouteUpdateAllRestInvoker extends AbstractRestInvoker {
    private GatewayType gatewayType;
    private String route;

    public RouteUpdateAllRestInvoker(ServiceResource serviceResource, String serviceId, RestTemplate restTemplate, GatewayType gatewayType, String route) {
        super(serviceResource, serviceId, restTemplate);

        this.gatewayType = gatewayType;
        this.route = route;
    }

    @Override
    protected String getDescription() {
        return "Route updated all";
    }

    @Override
    protected String getSuffixPath() {
        return gatewayType + "-route/update-all";
    }

    @Override
    protected String doRest(String url) {
        return restTemplate.postForEntity(url, getInvokeEntity(route), String.class).getBody();
    }
}
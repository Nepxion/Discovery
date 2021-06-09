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

public class RouteViewAllRestInvoker extends AbstractRestInvoker {
    private GatewayType gatewayType;

    public RouteViewAllRestInvoker(ServiceResource serviceResource, String serviceId, RestTemplate restTemplate, GatewayType gatewayType) {
        super(serviceResource, serviceId, restTemplate);

        this.gatewayType = gatewayType;
    }

    @Override
    protected String getDescription() {
        return "Route viewed";
    }

    @Override
    protected String getSuffixPath() {
        return gatewayType + "-route/view-all";
    }

    @Override
    protected String doRest(String url) {
        return restTemplate.getForEntity(url, String.class).getBody();
    }
}
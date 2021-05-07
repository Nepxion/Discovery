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

import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.console.constant.ConsoleConstant;
import com.nepxion.discovery.console.resource.ServiceResource;

public class RouteDeleteRestInvoker extends AbstractRestInvoker {
    private String type;
    private String routeId;

    public RouteDeleteRestInvoker(ServiceResource serviceResource, String serviceId, RestTemplate restTemplate, String type, String routeId) {
        super(serviceResource, serviceId, restTemplate);

        this.type = type.toLowerCase().trim();
        this.routeId = routeId;

        if (!ConsoleConstant.GATEWAY_TYPES.contains(type)) {
            throw new DiscoveryException("Invalid gateway type for '" + type + "', it must be one of " + ConsoleConstant.GATEWAY_TYPES);
        }
    }

    @Override
    protected String getDescription() {
        return "Route deleted";
    }

    @Override
    protected String getSuffixPath() {
        return type + "-route/delete";
    }

    @Override
    protected String doRest(String url) {
        return restTemplate.postForEntity(url, getInvokeEntity(routeId), String.class).getBody();
    }
}
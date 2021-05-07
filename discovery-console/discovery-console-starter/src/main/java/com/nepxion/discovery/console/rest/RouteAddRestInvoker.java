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

import com.nepxion.discovery.console.resource.ServiceResource;

public class RouteAddRestInvoker extends AbstractRestInvoker {
    private String type;
    private String route;

    public RouteAddRestInvoker(ServiceResource serviceResource, String serviceId, RestTemplate restTemplate, String type, String route) {
        super(serviceResource, serviceId, restTemplate);

        this.type = type.trim();
        this.route = route;
    }

    @Override
    protected String getDescription() {
        return "Route added";
    }

    @Override
    protected String getSuffixPath() {
        return type + "-route/add";
    }

    @Override
    protected String doRest(String url) {
        return restTemplate.postForEntity(url, getInvokeEntity(route), String.class).getBody();
    }
}
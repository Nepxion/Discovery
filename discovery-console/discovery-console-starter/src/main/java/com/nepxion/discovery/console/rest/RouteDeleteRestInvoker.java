package com.nepxion.discovery.console.rest;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class RouteDeleteRestInvoker extends AbstractRestInvoker {
    private String type;
    private String routeId;

    public RouteDeleteRestInvoker(List<ServiceInstance> instances, RestTemplate restTemplate, String type, String routeId) {
        super(instances, restTemplate);

        this.type = type.trim();
        this.routeId = routeId;
    }

    @Override
    protected String getInfo() {
        return "Route deleted";
    }

    @Override
    protected String getSuffixPath() {
        return type + "-route/delete";
    }

    @Override
    protected String doRest(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<String> entity = new HttpEntity<String>(routeId, headers);

        return restTemplate.postForEntity(url, entity, String.class).getBody();
    }

    @Override
    protected void checkPermission(ServiceInstance instance) throws Exception {
        checkConfigRestControlPermission(instance);
    }
}
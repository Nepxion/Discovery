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
import org.springframework.web.client.RestTemplate;

public class RouteModifyRestInvoker extends AbstractRestInvoker {
    private String type;
    private String json;

    public RouteModifyRestInvoker(List<ServiceInstance> instances, RestTemplate restTemplate, String type, String json) {
        super(instances, restTemplate);

        this.type = type.trim();
        this.json = json;
    }

    @Override
    protected String getInfo() {
        return "Route modified";
    }

    @Override
    protected String getSuffixPath() {
        return type + "-route/modify";
    }

    @Override
    protected String doRest(String url) {
        return restTemplate.postForEntity(url, getInvokeEntity(json), String.class).getBody();
    }

    @Override
    protected void checkPermission(ServiceInstance instance) throws Exception {

    }
}
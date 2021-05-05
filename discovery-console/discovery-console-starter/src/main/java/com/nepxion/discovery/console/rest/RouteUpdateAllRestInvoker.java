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

public class RouteUpdateAllRestInvoker extends AbstractRestInvoker {
    private String type;
    private String json;

    public RouteUpdateAllRestInvoker(List<ServiceInstance> instances, RestTemplate restTemplate, String type, String json) {
        super(instances, restTemplate);

        this.type = type.trim();
        this.json = json;
    }

    @Override
    protected String getInfo() {
        return "Route updated all";
    }

    @Override
    protected String getSuffixPath() {
        return type + "-route/update-all";
    }

    @Override
    protected String doRest(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<String> entity = new HttpEntity<String>(json, headers);

        return restTemplate.postForEntity(url, entity, String.class).getBody();
    }

    @Override
    protected void checkPermission(ServiceInstance instance) throws Exception {

    }
}
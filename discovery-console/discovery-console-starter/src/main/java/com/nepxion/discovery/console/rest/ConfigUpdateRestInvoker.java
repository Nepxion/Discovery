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

public class ConfigUpdateRestInvoker extends AbstractRestInvoker {
    private String config;

    public ConfigUpdateRestInvoker(ServiceResource serviceResource, String serviceId, RestTemplate restTemplate, boolean async, String config) {
        super(serviceResource, serviceId, restTemplate, async);

        this.config = config;
    }

    @Override
    protected String getDescription() {
        return "Config updated";
    }

    @Override
    protected String getSuffixPath() {
        return "config/update-" + getInvokeType();
    }

    @Override
    protected String doRest(String url) {
        return restTemplate.postForEntity(url, getInvokeEntity(config), String.class).getBody();
    }
}
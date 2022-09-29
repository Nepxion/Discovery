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

public class ConfigClearRestInvoker extends AbstractRestInvoker {
    public ConfigClearRestInvoker(ServiceResource serviceResource, String serviceId, RestTemplate restTemplate, boolean async) {
        super(serviceResource, serviceId, restTemplate, async);
    }

    @Override
    protected String getDescription() {
        return "Config cleared";
    }

    @Override
    protected String getSuffixPath() {
        return "config/clear-" + getInvokeType();
    }

    @Override
    protected String doRest(String url) {
        return restTemplate.postForEntity(url, null, String.class).getBody();
    }
}
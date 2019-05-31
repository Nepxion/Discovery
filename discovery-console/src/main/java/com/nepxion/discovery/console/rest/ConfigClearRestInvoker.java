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

public class ConfigClearRestInvoker extends AbstractRestInvoker {
    public ConfigClearRestInvoker(List<ServiceInstance> serviceInstances, RestTemplate restTemplate, boolean async) {
        super(serviceInstances, restTemplate, async);
    }

    @Override
    protected String getInfo() {
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

    @Override
    protected void checkPermission(ServiceInstance serviceInstance) throws Exception {
        checkDiscoveryControlPermission(serviceInstance);
        checkConfigRestControlPermission(serviceInstance);
    }
}
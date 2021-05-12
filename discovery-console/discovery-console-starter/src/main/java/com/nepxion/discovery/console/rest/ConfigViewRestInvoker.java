package com.nepxion.discovery.console.rest;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.console.resource.ServiceResource;

public class ConfigViewRestInvoker extends AbstractRestInvoker {
    public ConfigViewRestInvoker(ServiceResource serviceResource, String serviceId, RestTemplate restTemplate) {
        super(serviceResource, serviceId, restTemplate);
    }

    @Override
    protected String getDescription() {
        return "Config viewed";
    }

    @Override
    protected String getSuffixPath() {
        return "config/view";
    }

    @Override
    protected String doRest(String url) {
        return restTemplate.getForEntity(url, String.class).getBody();
    }

    @Override
    protected void checkPermission(ServiceInstance instance) throws Exception {
        checkDiscoveryControlPermission(instance);
        checkConfigRestControlPermission(instance);
    }
}
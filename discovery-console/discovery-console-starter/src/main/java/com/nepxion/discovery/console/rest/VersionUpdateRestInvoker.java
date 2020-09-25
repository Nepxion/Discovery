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

public class VersionUpdateRestInvoker extends AbstractRestInvoker {
    private String version;

    public VersionUpdateRestInvoker(List<ServiceInstance> instances, RestTemplate restTemplate, String version, boolean async) {
        super(instances, restTemplate, async);

        this.version = version;
    }

    @Override
    protected String getInfo() {
        return "Version updated";
    }

    @Override
    protected String getSuffixPath() {
        return "version/update-" + getInvokeType();
    }

    @Override
    protected String doRest(String url) {
        return restTemplate.postForEntity(url, version, String.class).getBody();
    }

    @Override
    protected void checkPermission(ServiceInstance instance) throws Exception {
        checkDiscoveryControlPermission(instance);
        checkConfigRestControlPermission(instance);
    }
}
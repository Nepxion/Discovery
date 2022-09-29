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

public class VersionUpdateRestInvoker extends AbstractRestInvoker {
    private String version;

    public VersionUpdateRestInvoker(ServiceResource serviceResource, String serviceId, RestTemplate restTemplate, boolean async, String version) {
        super(serviceResource, serviceId, restTemplate, async);

        this.version = version;
    }

    @Override
    protected String getDescription() {
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
}
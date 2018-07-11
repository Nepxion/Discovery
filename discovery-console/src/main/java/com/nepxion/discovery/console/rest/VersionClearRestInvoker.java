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

public class VersionClearRestInvoker extends AbstractRestInvoker {
    private String version;

    public VersionClearRestInvoker(List<ServiceInstance> serviceInstances, RestTemplate restTemplate, String version) {
        super(serviceInstances, restTemplate);

        this.version = version;
    }

    @Override
    protected String getInfo() {
        return "Version cleared";
    }

    @Override
    protected String getUrl(String host, int port) {
        return "http://" + host + ":" + port + "/version/clear";
    }

    @Override
    protected String doRest(String url) {
        return restTemplate.postForEntity(url, version, String.class).getBody();
    }
}
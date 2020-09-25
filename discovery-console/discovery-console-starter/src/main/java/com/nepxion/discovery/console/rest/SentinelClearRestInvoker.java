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

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.client.RestTemplate;

public class SentinelClearRestInvoker extends AbstractRestInvoker {
    private String type;

    public SentinelClearRestInvoker(List<ServiceInstance> instances, RestTemplate restTemplate, String type) {
        super(instances, restTemplate);

        this.type = type.trim();
    }

    @Override
    protected String getInfo() {
        return "Sentinel rules cleared";
    }

    @Override
    protected String getSuffixPath() {
        return getPrefixPath(type) + "/clear-" + type + "-rules";
    }

    @Override
    protected String doRest(String url) {
        return restTemplate.postForEntity(url, null, String.class).getBody();
    }

    @Override
    protected void checkPermission(ServiceInstance instance) throws Exception {
        checkConfigRestControlPermission(instance);
    }

    private String getPrefixPath(String type) {
        return StringUtils.equals(type, "param-flow") ? "sentinel-param" : "sentinel-core";
    }
}
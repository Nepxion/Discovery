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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class SentinelUpdateRestInvoker extends AbstractRestInvoker {
    private String type;
    private String rule;

    public SentinelUpdateRestInvoker(List<ServiceInstance> instances, RestTemplate restTemplate, String type, String rule) {
        super(instances, restTemplate);

        this.type = type.trim();
        this.rule = rule;
    }

    @Override
    protected String getInfo() {
        return "Sentinel rules updated";
    }

    @Override
    protected String getSuffixPath() {
        return getPrefixPath(type) + "/update-" + type + "-rules";
    }

    @Override
    protected String doRest(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<String> entity = new HttpEntity<String>(rule, headers);

        return restTemplate.postForEntity(url, entity, String.class).getBody();
    }

    @Override
    protected void checkPermission(ServiceInstance instance) throws Exception {
        checkConfigRestControlPermission(instance);
    }

    private String getPrefixPath(String type) {
        return StringUtils.equals(type, "param-flow") ? "sentinel-param" : "sentinel-core";
    }
}
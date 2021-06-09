package com.nepxion.discovery.console.rest;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.common.entity.SentinelRuleType;
import com.nepxion.discovery.console.resource.ServiceResource;

public class SentinelUpdateRestInvoker extends AbstractRestInvoker {
    private SentinelRuleType ruleType;
    private String rule;

    public SentinelUpdateRestInvoker(ServiceResource serviceResource, String serviceId, RestTemplate restTemplate, SentinelRuleType ruleType, String rule) {
        super(serviceResource, serviceId, restTemplate);

        this.ruleType = ruleType;
        this.rule = rule;
    }

    @Override
    protected String getDescription() {
        return "Sentinel rules updated";
    }

    @Override
    protected String getSuffixPath() {
        String path = StringUtils.equals(ruleType.toString(), "param-flow") ? "sentinel-param" : "sentinel-core";

        return path + "/update-" + ruleType + "-rules";
    }

    @Override
    protected String doRest(String url) {
        return restTemplate.postForEntity(url, getInvokeEntity(rule), String.class).getBody();
    }
}
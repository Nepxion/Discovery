package com.nepxion.discovery.console.resource;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.FormatType;
import com.nepxion.discovery.common.entity.ResultEntity;
import com.nepxion.discovery.common.entity.SentinelRuleType;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.console.rest.SentinelClearRestInvoker;
import com.nepxion.discovery.console.rest.SentinelUpdateRestInvoker;
import com.nepxion.discovery.console.rest.SentinelViewRestInvoker;

public class SentinelResourceImpl implements SentinelResource {
    @Autowired
    private ServiceResource serviceResource;

    @Autowired
    private ConfigResource configResource;

    @Autowired
    private RestTemplate consoleRestTemplate;

    @Override
    public boolean updateRemoteSentinel(SentinelRuleType ruleType, String group, String serviceId, String rule) {
        try {
            return configResource.updateRemoteConfig(group, serviceId + "-" + ruleType.getKey(), rule, FormatType.JSON_FORMAT);
        } catch (Exception e) {
            throw new DiscoveryException("Update remote " + ruleType.getDescription() + " failed, group={}, serviceId={}", e);
        }
    }

    @Override
    public boolean clearRemoteSentinel(SentinelRuleType ruleType, String group, String serviceId) {
        try {
            return configResource.updateRemoteConfig(group, serviceId + "-" + ruleType.getKey(), DiscoveryConstant.EMPTY_JSON_RULE_MULTIPLE, FormatType.JSON_FORMAT);
        } catch (Exception e) {
            throw new DiscoveryException("Clear remote " + ruleType.getDescription() + " failed, group={}, serviceId={}", e);
        }
    }

    @Override
    public String getRemoteSentinel(SentinelRuleType ruleType, String group, String serviceId) {
        try {
            return configResource.getRemoteConfig(group, serviceId + "-" + ruleType.getKey());
        } catch (Exception e) {
            throw new DiscoveryException("Get remote " + ruleType.getDescription() + " failed, group={}, serviceId={}", e);
        }
    }

    @Override
    public List<ResultEntity> updateSentinel(SentinelRuleType ruleType, String serviceId, String rule) {
        SentinelUpdateRestInvoker sentinelUpdateRestInvoker = new SentinelUpdateRestInvoker(serviceResource, serviceId, consoleRestTemplate, ruleType, rule);

        return sentinelUpdateRestInvoker.invoke();
    }

    @Override
    public List<ResultEntity> clearSentinel(SentinelRuleType ruleType, String serviceId) {
        SentinelClearRestInvoker sentinelClearRestInvoker = new SentinelClearRestInvoker(serviceResource, serviceId, consoleRestTemplate, ruleType);

        return sentinelClearRestInvoker.invoke();
    }

    @Override
    public List<ResultEntity> viewSentinel(SentinelRuleType ruleType, String serviceId) {
        SentinelViewRestInvoker sentinelViewRestInvoker = new SentinelViewRestInvoker(serviceResource, serviceId, consoleRestTemplate, ruleType);

        return sentinelViewRestInvoker.invoke();
    }
}
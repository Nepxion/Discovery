package com.nepxion.discovery.plugin.framework.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.plugin.framework.cache.PluginCache;
import com.nepxion.discovery.plugin.framework.cache.RuleCache;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.netflix.loadbalancer.Server;

public abstract class AbstractPluginAdapter implements PluginAdapter {
    @Autowired
    protected Registration registration;

    @Autowired
    protected PluginContextAware pluginContextAware;

    @Autowired
    protected PluginCache pluginCache;

    @Autowired
    protected RuleCache ruleCache;

    @Override
    public String getGroup() {
        String groupKey = pluginContextAware.getGroupKey();

        String group = getGroup(groupKey);
        if (StringUtils.isEmpty(group)) {
            throw new DiscoveryException("The value is null or empty for metadata key=" + groupKey + ", please check your configuration");
        }

        return group;
    }

    protected String getGroup(String groupKey) {
        return getMetadata().get(groupKey);
    }

    @Override
    public String getServiceId() {
        return registration.getServiceId();
    }

    @Override
    public String getHost() {
        return registration.getHost();
    }

    @Override
    public int getPort() {
        return registration.getPort();
    }

    @Override
    public String getContextPath() {
        return getMetadata().get(DiscoveryConstant.SPRING_APPLICATION_CONTEXT_PATH);
    }

    @Override
    public Map<String, String> getMetadata() {
        return registration.getMetadata();
    }

    @Override
    public String getVersion() {
        String dynamicVersion = getDynamicVersion();
        if (StringUtils.isNotEmpty(dynamicVersion)) {
            return dynamicVersion;
        }

        return getLocalVersion();
    }

    @Override
    public String getLocalVersion() {
        return getMetadata().get(DiscoveryConstant.VERSION);
    }

    @Override
    public String getDynamicVersion() {
        return pluginCache.get(DiscoveryConstant.DYNAMIC_VERSION);
    }

    @Override
    public void setDynamicVersion(String version) {
        pluginCache.put(DiscoveryConstant.DYNAMIC_VERSION, version);
    }

    @Override
    public void clearDynamicVersion() {
        pluginCache.clear(DiscoveryConstant.DYNAMIC_VERSION);
    }

    @Override
    public RuleEntity getRule() {
        RuleEntity dynamicRuleEntity = getDynamicRule();
        if (dynamicRuleEntity != null) {
            return dynamicRuleEntity;
        }

        return getLocalRule();
    }

    @Override
    public RuleEntity getLocalRule() {
        return ruleCache.get(DiscoveryConstant.RULE);
    }

    @Override
    public void setLocalRule(RuleEntity ruleEntity) {
        ruleCache.put(DiscoveryConstant.RULE, ruleEntity);
    }

    @Override
    public RuleEntity getDynamicRule() {
        return ruleCache.get(DiscoveryConstant.DYNAMIC_RULE);
    }

    @Override
    public void setDynamicRule(RuleEntity ruleEntity) {
        ruleCache.put(DiscoveryConstant.DYNAMIC_RULE, ruleEntity);
    }

    @Override
    public void clearDynamicRule() {
        ruleCache.clear(DiscoveryConstant.DYNAMIC_RULE);
    }

    @Override
    public String getServerVersion(Server server) {
        return getServerMetadata(server).get(DiscoveryConstant.VERSION);
    }
}
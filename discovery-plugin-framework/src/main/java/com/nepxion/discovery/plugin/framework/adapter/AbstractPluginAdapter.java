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
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.serviceregistry.Registration;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.RuleEntity;
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
    public String getGroupKey() {
        return pluginContextAware.getGroupKey();
    }

    @Override
    public String getGroup() {
        String groupKey = getGroupKey();

        String group = getGroup(groupKey);
        if (StringUtils.isEmpty(group)) {
            group = DiscoveryConstant.DEFAULT;
        }

        return group;
    }

    protected String getGroup(String groupKey) {
        return getMetadata().get(groupKey);
    }

    @Override
    public String getServiceType() {
        return pluginContextAware.getApplicationType();
    }

    @Override
    public String getServiceId() {
        return registration.getServiceId().toLowerCase();
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
        String version = getMetadata().get(DiscoveryConstant.VERSION);
        if (StringUtils.isEmpty(version)) {
            version = DiscoveryConstant.DEFAULT;
        }

        return version;
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
    public String getRegion() {
        String region = getMetadata().get(DiscoveryConstant.REGION);

        if (StringUtils.isEmpty(region)) {
            region = DiscoveryConstant.DEFAULT;
        }

        return region;
    }

    @Override
    public String getServerGroupKey(Server server) {
        return getServerMetadata(server).get(DiscoveryConstant.SPRING_APPLICATION_GROUP_KEY);
    }

    @Override
    public String getServerGroup(Server server) {
        String serverGroupKey = getServerGroupKey(server);

        String serverGroup = getServerMetadata(server).get(serverGroupKey);
        if (StringUtils.isEmpty(serverGroup)) {
            serverGroup = DiscoveryConstant.DEFAULT;
        }

        return serverGroup;
    }

    @Override
    public String getServerServiceType(Server server) {
        return getServerMetadata(server).get(DiscoveryConstant.SPRING_APPLICATION_TYPE);
    }

    @Override
    public String getServerServiceId(Server server) {
        return getServerMetadata(server).get(DiscoveryConstant.SPRING_APPLICATION_NAME).toLowerCase();
    }

    @Override
    public String getServerVersion(Server server) {
        String serverVersion = getServerMetadata(server).get(DiscoveryConstant.VERSION);
        if (StringUtils.isEmpty(serverVersion)) {
            serverVersion = DiscoveryConstant.DEFAULT;
        }

        return serverVersion;
    }

    @Override
    public String getServerRegion(Server server) {
        String serverRegion = getServerMetadata(server).get(DiscoveryConstant.REGION);
        if (StringUtils.isEmpty(serverRegion)) {
            serverRegion = DiscoveryConstant.DEFAULT;
        }

        return serverRegion;
    }

    @Override
    public Map<String, String> getInstanceMetadata(ServiceInstance serviceInstance) {
        return serviceInstance.getMetadata();
    }

    @Override
    public String getInstanceGroupKey(ServiceInstance serviceInstance) {
        return getInstanceMetadata(serviceInstance).get(DiscoveryConstant.SPRING_APPLICATION_GROUP_KEY);
    }

    @Override
    public String getInstanceGroup(ServiceInstance serviceInstance) {
        String instanceGroupKey = getInstanceGroupKey(serviceInstance);

        String instanceGroup = getInstanceMetadata(serviceInstance).get(instanceGroupKey);
        if (StringUtils.isEmpty(instanceGroup)) {
            instanceGroup = DiscoveryConstant.DEFAULT;
        }

        return instanceGroup;
    }

    @Override
    public String getInstanceServiceType(ServiceInstance serviceInstance) {
        return getInstanceMetadata(serviceInstance).get(DiscoveryConstant.SPRING_APPLICATION_TYPE);
    }

    @Override
    public String getInstanceServiceId(ServiceInstance serviceInstance) {
        return serviceInstance.getServiceId().toLowerCase();
    }

    @Override
    public String getInstanceVersion(ServiceInstance serviceInstance) {
        String instanceVersion = getInstanceMetadata(serviceInstance).get(DiscoveryConstant.VERSION);
        if (StringUtils.isEmpty(instanceVersion)) {
            instanceVersion = DiscoveryConstant.DEFAULT;
        }

        return instanceVersion;
    }

    @Override
    public String getInstanceRegion(ServiceInstance serviceInstance) {
        String instanceRegion = getInstanceMetadata(serviceInstance).get(DiscoveryConstant.REGION);
        if (StringUtils.isEmpty(instanceRegion)) {
            instanceRegion = DiscoveryConstant.DEFAULT;
        }

        return instanceRegion;
    }
}
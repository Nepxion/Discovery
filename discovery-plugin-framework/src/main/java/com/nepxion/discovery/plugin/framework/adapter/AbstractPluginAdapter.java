package com.nepxion.discovery.plugin.framework.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.serviceregistry.Registration;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.plugin.framework.cache.PluginCache;
import com.nepxion.discovery.plugin.framework.cache.RuleCache;
import com.nepxion.discovery.plugin.framework.context.PluginContextHolder;
import com.netflix.loadbalancer.Server;

public abstract class AbstractPluginAdapter implements PluginAdapter {
    @Autowired
    protected Registration registration;

    @Autowired
    protected PluginCache pluginCache;

    @Autowired(required = false)
    protected PluginContextHolder pluginContextHolder;

    @Autowired
    protected RuleCache ruleCache;

    @Autowired(required = false)
    protected ApplicationInfoAdapter applicationInfoAdapter;

    @Value("${" + DiscoveryConstant.SPRING_APPLICATION_GROUP_KEY + ":" + DiscoveryConstant.GROUP + "}")
    private String groupKey;

    @Value("${" + DiscoveryConstant.SPRING_APPLICATION_TYPE + ":" + DiscoveryConstant.UNKNOWN + "}")
    private String applicationType;

    protected Map<String, String> emptyMetadata = new HashMap<String, String>();

    @Override
    public String getPlugin() {
        return getMetadata().get(DiscoveryConstant.SPRING_APPLICATION_DISCOVERY_PLUGIN);
    }

    @Override
    public String getGroupKey() {
        return groupKey;
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
        return applicationType;
    }

    @Override
    public String getServiceId() {
        return registration.getServiceId().toLowerCase();
    }

    @Override
    public String getServiceAppId() {
        if (applicationInfoAdapter != null) {
            return applicationInfoAdapter.getAppId();
        }

        return null;
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
    public String getEnvironment() {
        String environment = getMetadata().get(DiscoveryConstant.ENVIRONMENT);
        if (StringUtils.isEmpty(environment)) {
            environment = DiscoveryConstant.DEFAULT;
        }

        return environment;
    }

    @Override
    public String getServerPlugin(Server server) {
        return getServerMetadata(server).get(DiscoveryConstant.SPRING_APPLICATION_DISCOVERY_PLUGIN);
    }

    @Override
    public String getServerGroupKey(Server server) {
        String groupKey = getServerMetadata(server).get(DiscoveryConstant.SPRING_APPLICATION_GROUP_KEY);

        if (StringUtils.isEmpty(groupKey)) {
            groupKey = DiscoveryConstant.GROUP;
        }

        return groupKey;
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
        String serviceId = getServerMetadata(server).get(DiscoveryConstant.SPRING_APPLICATION_NAME);
        if (StringUtils.isEmpty(serviceId)) {
            serviceId = server.getMetaInfo().getAppName();
        }

        if (StringUtils.isEmpty(serviceId)) {
            throw new DiscoveryException("Server ServiceId is null");
        }

        return serviceId.toLowerCase();
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
    public String getServerEnvironment(Server server) {
        String serverEnvironment = getServerMetadata(server).get(DiscoveryConstant.ENVIRONMENT);
        if (StringUtils.isEmpty(serverEnvironment)) {
            serverEnvironment = DiscoveryConstant.DEFAULT;
        }

        return serverEnvironment;
    }

    @Override
    public String getServerContextPath(Server server) {
        return getServerMetadata(server).get(DiscoveryConstant.SPRING_APPLICATION_CONTEXT_PATH);
    }

    @Override
    public Map<String, String> getInstanceMetadata(ServiceInstance serviceInstance) {
        return serviceInstance.getMetadata();
    }

    @Override
    public String getInstancePlugin(ServiceInstance serviceInstance) {
        return getInstanceMetadata(serviceInstance).get(DiscoveryConstant.SPRING_APPLICATION_DISCOVERY_PLUGIN);
    }

    @Override
    public String getInstanceGroupKey(ServiceInstance serviceInstance) {
        String groupKey = getInstanceMetadata(serviceInstance).get(DiscoveryConstant.SPRING_APPLICATION_GROUP_KEY);

        if (StringUtils.isEmpty(groupKey)) {
            groupKey = DiscoveryConstant.GROUP;
        }

        return groupKey;
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

    @Override
    public String getInstanceEnvironment(ServiceInstance serviceInstance) {
        String instanceEnvironment = getInstanceMetadata(serviceInstance).get(DiscoveryConstant.ENVIRONMENT);
        if (StringUtils.isEmpty(instanceEnvironment)) {
            instanceEnvironment = DiscoveryConstant.DEFAULT;
        }

        return instanceEnvironment;
    }

    @Override
    public String getInstanceContextPath(ServiceInstance serviceInstance) {
        return getInstanceMetadata(serviceInstance).get(DiscoveryConstant.SPRING_APPLICATION_CONTEXT_PATH);
    }

    @Override
    public String getPluginInfo(String previousPluginInfo) {
        String plugin = getPlugin();
        String serviceId = getServiceId();
        String host = getHost();
        int port = getPort();
        String version = getVersion();
        String region = getRegion();
        String environment = getEnvironment();
        String group = getGroup();

        StringBuilder stringBuilder = new StringBuilder();
        if (StringUtils.isNotEmpty(previousPluginInfo)) {
            stringBuilder.append(previousPluginInfo + " -> ");
        }

        stringBuilder.append("[ID=" + serviceId + "]");
        stringBuilder.append("[P=" + plugin + "]");
        stringBuilder.append("[H=" + host + ":" + port + "]");
        if (StringUtils.isNotEmpty(version)) {
            stringBuilder.append("[V=" + version + "]");
        }
        if (StringUtils.isNotEmpty(region)) {
            stringBuilder.append("[R=" + region + "]");
        }
        if (StringUtils.isNotEmpty(environment)) {
            stringBuilder.append("[E=" + environment + "]");
        }
        if (StringUtils.isNotEmpty(group)) {
            stringBuilder.append("[G=" + group + "]");
        }

        if (pluginContextHolder != null) {
            String traceId = pluginContextHolder.getTraceId();
            if (StringUtils.isNotEmpty(traceId)) {
                stringBuilder.append("[TID=" + traceId + "]");
            }

            String spanId = pluginContextHolder.getSpanId();
            if (StringUtils.isNotEmpty(spanId)) {
                stringBuilder.append("[SID=" + spanId + "]");
            }
        }

        return stringBuilder.toString();
    }
}
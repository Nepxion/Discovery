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
import com.nepxion.discovery.common.constant.DiscoveryMetaDataConstant;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.RuleEntityWrapper;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.common.util.UrlUtil;
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

    @Value("${" + DiscoveryConstant.SPRING_APPLICATION_GROUP_KEY + ":" + DiscoveryConstant.GROUP + "}")
    private String groupKey;

    @Value("${" + DiscoveryConstant.SPRING_APPLICATION_TYPE + ":" + DiscoveryConstant.UNKNOWN + "}")
    private String applicationType;

    protected Map<String, String> emptyMetadata = new HashMap<String, String>();

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
    public RuleEntity getDynamicPartialRule() {
        return ruleCache.get(DiscoveryConstant.DYNAMIC_PARTIAL_RULE);
    }

    @Override
    public void setDynamicPartialRule(RuleEntity ruleEntity) {
        ruleCache.put(DiscoveryConstant.DYNAMIC_PARTIAL_RULE, ruleEntity);

        assembleDynamicRule();
    }

    @Override
    public void clearDynamicPartialRule() {
        ruleCache.clear(DiscoveryConstant.DYNAMIC_PARTIAL_RULE);

        assembleDynamicRule();
    }

    @Override
    public RuleEntity getDynamicGlobalRule() {
        return ruleCache.get(DiscoveryConstant.DYNAMIC_GLOBAL_RULE);
    }

    @Override
    public void setDynamicGlobalRule(RuleEntity ruleEntity) {
        ruleCache.put(DiscoveryConstant.DYNAMIC_GLOBAL_RULE, ruleEntity);

        assembleDynamicRule();
    }

    @Override
    public void clearDynamicGlobalRule() {
        ruleCache.clear(DiscoveryConstant.DYNAMIC_GLOBAL_RULE);

        assembleDynamicRule();
    }

    // 从动态全局规则和动态局部规则缓存组装出最终的动态规则
    private void assembleDynamicRule() {
        RuleEntity dynamicPartialRule = getDynamicPartialRule();
        RuleEntity dynamicGlobalRule = getDynamicGlobalRule();

        RuleEntity dynamicRule = RuleEntityWrapper.assemble(dynamicPartialRule, dynamicGlobalRule);
        ruleCache.put(DiscoveryConstant.DYNAMIC_RULE, dynamicRule);
    }

    @Override
    public String getPlugin() {
        String plugin = getMetadata().get(DiscoveryMetaDataConstant.SPRING_APPLICATION_DISCOVERY_PLUGIN);
        if (StringUtils.isEmpty(plugin)) {
            plugin = DiscoveryConstant.UNKNOWN;
        }

        return plugin;
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
        return getMetadata().get(DiscoveryMetaDataConstant.SPRING_APPLICATION_APP_ID);
    }

    @Override
    public String getServiceUUId() {
        return getMetadata().get(DiscoveryMetaDataConstant.SPRING_APPLICATION_UUID);
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
    public String getZone() {
        String zone = getMetadata().get(DiscoveryConstant.ZONE);
        if (StringUtils.isEmpty(zone)) {
            zone = DiscoveryConstant.DEFAULT;
        }

        return zone;
    }

    @Override
    public boolean isActive() {
        String active = getMetadata().get(DiscoveryConstant.ACTIVE);
        if (StringUtils.isEmpty(active)) {
            return false;
        }

        return Boolean.valueOf(active);
    }

    @Override
    public String getProtocol() {
        String protocol = getMetadata().get(DiscoveryMetaDataConstant.SPRING_APPLICATION_PROTOCOL);
        if (StringUtils.isEmpty(protocol)) {
            protocol = "http";
        }

        return protocol;
    }

    @Override
    public String getContextPath() {
        String contextPath = getMetadata().get(DiscoveryMetaDataConstant.SPRING_APPLICATION_CONTEXT_PATH);
        if (StringUtils.isEmpty(contextPath)) {
            contextPath = "/";
        }

        return contextPath;
    }

    @Override
    public String getFormatContextPath() {
        String contextPath = getContextPath();

        return UrlUtil.formatContextPath(contextPath);
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
    public Map<String, String> getMetadata() {
        return registration.getMetadata();
    }

    @Override
    public String getServerPlugin(Server server) {
        String plugin = getServerMetadata(server).get(DiscoveryMetaDataConstant.SPRING_APPLICATION_DISCOVERY_PLUGIN);
        if (StringUtils.isEmpty(plugin)) {
            plugin = DiscoveryConstant.UNKNOWN;
        }

        return plugin;
    }

    @Override
    public String getServerGroupKey(Server server) {
        String groupKey = getServerMetadata(server).get(DiscoveryMetaDataConstant.SPRING_APPLICATION_GROUP_KEY);

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
        return getServerMetadata(server).get(DiscoveryMetaDataConstant.SPRING_APPLICATION_TYPE);
    }

    @Override
    public String getServerServiceId(Server server) {
        String serviceId = getServerMetadata(server).get(DiscoveryMetaDataConstant.SPRING_APPLICATION_NAME);
        if (StringUtils.isEmpty(serviceId)) {
            serviceId = server.getMetaInfo().getAppName();
        }

        if (StringUtils.isEmpty(serviceId)) {
            throw new DiscoveryException("Server ServiceId is null");
        }

        return serviceId.toLowerCase();
    }

    @Override
    public String getServerServiceAppId(Server server) {
        return getServerMetadata(server).get(DiscoveryMetaDataConstant.SPRING_APPLICATION_APP_ID);
    }

    @Override
    public String getServerServiceUUId(Server server) {
        return getServerMetadata(server).get(DiscoveryMetaDataConstant.SPRING_APPLICATION_UUID);
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
    public String getServerZone(Server server) {
        String serverZone = getServerMetadata(server).get(DiscoveryConstant.ZONE);
        if (StringUtils.isEmpty(serverZone)) {
            serverZone = DiscoveryConstant.DEFAULT;
        }

        return serverZone;
    }

    @Override
    public boolean isServerActive(Server server) {
        String active = getServerMetadata(server).get(DiscoveryConstant.ACTIVE);
        if (StringUtils.isEmpty(active)) {
            return false;
        }

        return Boolean.valueOf(active);
    }

    @Override
    public String getServerProtocol(Server server) {
        String protocol = getServerMetadata(server).get(DiscoveryMetaDataConstant.SPRING_APPLICATION_PROTOCOL);
        if (StringUtils.isEmpty(protocol)) {
            protocol = "http";
        }

        return protocol;
    }

    @Override
    public String getServerContextPath(Server server) {
        String contextPath = getServerMetadata(server).get(DiscoveryMetaDataConstant.SPRING_APPLICATION_CONTEXT_PATH);
        if (StringUtils.isEmpty(contextPath)) {
            contextPath = "/";
        }

        return contextPath;
    }

    @Override
    public String getServerFormatContextPath(Server server) {
        String contextPath = getServerContextPath(server);

        return UrlUtil.formatContextPath(contextPath);
    }

    @Override
    public String getInstancePlugin(ServiceInstance instance) {
        String plugin = getInstanceMetadata(instance).get(DiscoveryMetaDataConstant.SPRING_APPLICATION_DISCOVERY_PLUGIN);
        if (StringUtils.isEmpty(plugin)) {
            plugin = DiscoveryConstant.UNKNOWN;
        }

        return plugin;
    }

    @Override
    public String getInstanceGroupKey(ServiceInstance instance) {
        String groupKey = getInstanceMetadata(instance).get(DiscoveryMetaDataConstant.SPRING_APPLICATION_GROUP_KEY);

        if (StringUtils.isEmpty(groupKey)) {
            groupKey = DiscoveryConstant.GROUP;
        }

        return groupKey;
    }

    @Override
    public String getInstanceGroup(ServiceInstance instance) {
        String instanceGroupKey = getInstanceGroupKey(instance);

        String instanceGroup = getInstanceMetadata(instance).get(instanceGroupKey);
        if (StringUtils.isEmpty(instanceGroup)) {
            instanceGroup = DiscoveryConstant.DEFAULT;
        }

        return instanceGroup;
    }

    @Override
    public String getInstanceServiceType(ServiceInstance instance) {
        return getInstanceMetadata(instance).get(DiscoveryMetaDataConstant.SPRING_APPLICATION_TYPE);
    }

    @Override
    public String getInstanceServiceId(ServiceInstance instance) {
        return instance.getServiceId().toLowerCase();
    }

    @Override
    public String getInstanceServiceAppId(ServiceInstance instance) {
        return getInstanceMetadata(instance).get(DiscoveryMetaDataConstant.SPRING_APPLICATION_APP_ID);
    }

    @Override
    public String getInstanceServiceUUId(ServiceInstance instance) {
        return getInstanceMetadata(instance).get(DiscoveryMetaDataConstant.SPRING_APPLICATION_UUID);
    }

    @Override
    public String getInstanceVersion(ServiceInstance instance) {
        String instanceVersion = getInstanceMetadata(instance).get(DiscoveryConstant.VERSION);
        if (StringUtils.isEmpty(instanceVersion)) {
            instanceVersion = DiscoveryConstant.DEFAULT;
        }

        return instanceVersion;
    }

    @Override
    public String getInstanceRegion(ServiceInstance instance) {
        String instanceRegion = getInstanceMetadata(instance).get(DiscoveryConstant.REGION);
        if (StringUtils.isEmpty(instanceRegion)) {
            instanceRegion = DiscoveryConstant.DEFAULT;
        }

        return instanceRegion;
    }

    @Override
    public String getInstanceEnvironment(ServiceInstance instance) {
        String instanceEnvironment = getInstanceMetadata(instance).get(DiscoveryConstant.ENVIRONMENT);
        if (StringUtils.isEmpty(instanceEnvironment)) {
            instanceEnvironment = DiscoveryConstant.DEFAULT;
        }

        return instanceEnvironment;
    }

    @Override
    public String getInstanceZone(ServiceInstance instance) {
        String instanceZone = getInstanceMetadata(instance).get(DiscoveryConstant.ZONE);
        if (StringUtils.isEmpty(instanceZone)) {
            instanceZone = DiscoveryConstant.DEFAULT;
        }

        return instanceZone;
    }

    @Override
    public boolean isInstanceActive(ServiceInstance instance) {
        String active = getInstanceMetadata(instance).get(DiscoveryConstant.ACTIVE);
        if (StringUtils.isEmpty(active)) {
            return false;
        }

        return Boolean.valueOf(active);
    }

    @Override
    public String getInstanceProtocol(ServiceInstance instance) {
        String protocol = getInstanceMetadata(instance).get(DiscoveryMetaDataConstant.SPRING_APPLICATION_PROTOCOL);
        if (StringUtils.isEmpty(protocol)) {
            protocol = "http";
        }

        return protocol;
    }

    @Override
    public String getInstanceContextPath(ServiceInstance instance) {
        String contextPath = getInstanceMetadata(instance).get(DiscoveryMetaDataConstant.SPRING_APPLICATION_CONTEXT_PATH);
        if (StringUtils.isEmpty(contextPath)) {
            contextPath = "/";
        }

        return contextPath;
    }

    @Override
    public String getInstanceFormatContextPath(ServiceInstance instance) {
        String contextPath = getInstanceContextPath(instance);

        return UrlUtil.formatContextPath(contextPath);
    }

    @Override
    public Map<String, String> getInstanceMetadata(ServiceInstance instance) {
        return instance.getMetadata();
    }

    @Override
    public String getPluginInfo(String previousPluginInfo) {
        String plugin = getPlugin();
        String serviceId = getServiceId();
        String serviceUUId = getServiceUUId();
        String serviceAppId = getServiceAppId();
        String serviceType = getServiceType();
        String host = getHost();
        int port = getPort();
        String version = getVersion();
        String region = getRegion();
        String environment = getEnvironment();
        String zone = getZone();
        String group = getGroup();
        boolean active = isActive();

        StringBuilder stringBuilder = new StringBuilder();
        if (StringUtils.isNotEmpty(previousPluginInfo)) {
            stringBuilder.append(previousPluginInfo + " -> ");
        }

        stringBuilder.append("[ID=" + serviceId + "]");
        if (StringUtils.isNotEmpty(serviceUUId)) {
            stringBuilder.append("[UID=" + serviceUUId + "]");
        }
        if (StringUtils.isNotEmpty(serviceAppId)) {
            stringBuilder.append("[AID=" + serviceAppId + "]");
        }
        stringBuilder.append("[T=" + serviceType + "]");
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
        if (StringUtils.isNotEmpty(zone)) {
            stringBuilder.append("[Z=" + zone + "]");
        }
        if (StringUtils.isNotEmpty(group)) {
            stringBuilder.append("[G=" + group + "]");
        }
        stringBuilder.append("[A=" + active + "]");

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
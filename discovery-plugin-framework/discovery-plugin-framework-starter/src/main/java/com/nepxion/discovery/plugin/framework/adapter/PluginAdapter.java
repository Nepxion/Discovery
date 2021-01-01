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

import org.springframework.cloud.client.ServiceInstance;

import com.nepxion.discovery.common.entity.RuleEntity;

public interface PluginAdapter {
    String getPlugin();

    String getGroupKey();

    String getGroup();

    String getServiceType();

    String getServiceId();

    String getServiceAppId();

    String getServiceUUId();

    String getHost();

    int getPort();

    Map<String, String> getMetadata();

    String getVersion();

    String getLocalVersion();

    String getDynamicVersion();

    void setDynamicVersion(String version);

    void clearDynamicVersion();

    RuleEntity getRule();

    RuleEntity getLocalRule();

    void setLocalRule(RuleEntity ruleEntity);

    RuleEntity getDynamicRule();

    RuleEntity getDynamicPartialRule();

    void setDynamicPartialRule(RuleEntity ruleEntity);

    void clearDynamicPartialRule();

    RuleEntity getDynamicGlobalRule();

    void setDynamicGlobalRule(RuleEntity ruleEntity);

    void clearDynamicGlobalRule();

    String getRegion();

    String getEnvironment();

    String getZone();

    String getContextPath();

    Map<String, String> getServerMetadata(ServiceInstance server);

    String getServerPlugin(ServiceInstance server);

    String getServerGroupKey(ServiceInstance server);

    String getServerGroup(ServiceInstance server);

    String getServerServiceType(ServiceInstance server);

    String getServerServiceId(ServiceInstance server);

    String getServerServiceUUId(ServiceInstance server);

    String getServerVersion(ServiceInstance server);

    String getServerRegion(ServiceInstance server);

    String getServerEnvironment(ServiceInstance server);

    String getServerZone(ServiceInstance server);

    String getServerContextPath(ServiceInstance server);

    Map<String, String> getInstanceMetadata(ServiceInstance instance);

    String getInstancePlugin(ServiceInstance instance);

    String getInstanceGroupKey(ServiceInstance instance);

    String getInstanceGroup(ServiceInstance instance);

    String getInstanceServiceType(ServiceInstance instance);

    String getInstanceServiceId(ServiceInstance instance);

    String getInstanceServiceUUId(ServiceInstance instance);

    String getInstanceVersion(ServiceInstance instance);

    String getInstanceRegion(ServiceInstance instance);

    String getInstanceEnvironment(ServiceInstance instance);

    String getInstanceZone(ServiceInstance instance);

    String getInstanceContextPath(ServiceInstance instance);

    String getPluginInfo(String previousPluginInfo);
}
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
import com.netflix.loadbalancer.Server;

public interface PluginAdapter {
    String getPlugin();

    String getGroupKey();

    String getGroup();

    String getServiceType();

    String getServiceAppId();

    String getServiceId();

    String getHost();

    int getPort();

    String getContextPath();

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

    void setDynamicRule(RuleEntity ruleEntity);

    void clearDynamicRule();

    String getRegion();

    String getEnvironment();

    Map<String, String> getServerMetadata(Server server);

    String getServerPlugin(Server server);

    String getServerGroupKey(Server server);

    String getServerGroup(Server server);

    String getServerServiceType(Server server);

    String getServerServiceId(Server server);

    String getServerVersion(Server server);

    String getServerRegion(Server server);

    String getServerEnvironment(Server server);

    String getServerContextPath(Server server);

    Map<String, String> getInstanceMetadata(ServiceInstance serviceInstance);

    String getInstancePlugin(ServiceInstance serviceInstance);

    String getInstanceGroupKey(ServiceInstance serviceInstance);

    String getInstanceGroup(ServiceInstance serviceInstance);

    String getInstanceServiceType(ServiceInstance serviceInstance);

    String getInstanceServiceId(ServiceInstance serviceInstance);

    String getInstanceVersion(ServiceInstance serviceInstance);

    String getInstanceRegion(ServiceInstance serviceInstance);

    String getInstanceEnvironment(ServiceInstance serviceInstance);

    String getInstanceContextPath(ServiceInstance serviceInstance);

    String getPluginInfo(String previousPluginInfo);
}
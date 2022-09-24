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

    String getPlugin();

    String getGroupKey();

    String getGroup();

    String getServiceType();

    String getServiceId();

    String getServiceAppId();

    String getServiceUUId();

    String getVersion();

    String getLocalVersion();

    String getDynamicVersion();

    void setDynamicVersion(String version);

    void clearDynamicVersion();

    String getRegion();

    String getEnvironment();

    String getZone();

    boolean isActive();

    String getProtocol();

    String getContextPath();

    String getFormatContextPath();

    String getHost();

    int getPort();

    Map<String, String> getMetadata();

    String getServerPlugin(Server server);

    String getServerGroupKey(Server server);

    String getServerGroup(Server server);

    String getServerServiceType(Server server);

    String getServerServiceId(Server server);

    String getServerServiceAppId(Server server);

    String getServerServiceUUId(Server server);

    String getServerVersion(Server server);

    String getServerRegion(Server server);

    String getServerEnvironment(Server server);

    String getServerZone(Server server);

    boolean isServerActive(Server server);

    String getServerProtocol(Server server);

    String getServerContextPath(Server server);

    String getServerFormatContextPath(Server server);

    Map<String, String> getServerMetadata(Server server);

    String getInstancePlugin(ServiceInstance instance);

    String getInstanceGroupKey(ServiceInstance instance);

    String getInstanceGroup(ServiceInstance instance);

    String getInstanceServiceType(ServiceInstance instance);

    String getInstanceServiceId(ServiceInstance instance);

    String getInstanceServiceAppId(ServiceInstance instance);

    String getInstanceServiceUUId(ServiceInstance instance);

    String getInstanceVersion(ServiceInstance instance);

    String getInstanceRegion(ServiceInstance instance);

    String getInstanceEnvironment(ServiceInstance instance);

    String getInstanceZone(ServiceInstance instance);

    boolean isInstanceActive(ServiceInstance instance);

    String getInstanceProtocol(ServiceInstance instance);

    String getInstanceContextPath(ServiceInstance instance);

    String getInstanceFormatContextPath(ServiceInstance instance);

    Map<String, String> getInstanceMetadata(ServiceInstance instance);

    String getPluginInfo(String previousPluginInfo);
}
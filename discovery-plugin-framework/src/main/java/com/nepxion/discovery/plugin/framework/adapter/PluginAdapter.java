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

import org.springframework.cloud.client.serviceregistry.Registration;

import com.nepxion.discovery.plugin.framework.entity.RuleEntity;
import com.netflix.loadbalancer.Server;

public interface PluginAdapter {
    String getServiceId();

    String getHost(Registration registration);

    int getPort(Registration registration);

    Map<String, String> getMetaData(Server server);

    String getServerVersion(Server server);

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

    String mock(Registration registration, String mockValue);
}
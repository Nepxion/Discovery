package com.nepxion.discovery.console.desktop.entity;

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

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.InstanceEntity;

public class Instance extends InstanceEntity {
    private static final long serialVersionUID = -3381928574242229614L;

    private String dynamicVersion;
    private String rule;
    private String dynamicRule;

    public String getDynamicVersion() {
        return dynamicVersion;
    }

    public void setDynamicVersion(String dynamicVersion) {
        this.dynamicVersion = dynamicVersion;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getDynamicRule() {
        return dynamicRule;
    }

    public void setDynamicRule(String dynamicRule) {
        this.dynamicRule = dynamicRule;
    }

    public String getContextPath() {
        Map<String, String> metadata = getMetadata();

        return metadata.get(DiscoveryConstant.SPRING_APPLICATION_CONTEXT_PATH);
    }

    public String getFilter() {
        Map<String, String> metadata = getMetadata();
        String filterKey = metadata.get(DiscoveryConstant.SPRING_APPLICATION_GROUP_KEY);
        if (StringUtils.isEmpty(filterKey)) {
            return "";
        }

        String filter = metadata.get(filterKey);
        if (filter == null) {
            return "";
        }

        return filter;
    }

    public String getPlugin() {
        Map<String, String> metadata = getMetadata();
        String plugin = metadata.get(DiscoveryConstant.SPRING_APPLICATION_DISCOVERY_PLUGIN);
        if (plugin == null) {
            return "";
        }

        return plugin;
    }

    public boolean isDiscoveryControlEnabled() {
        Map<String, String> metadata = getMetadata();
        String flag = metadata.get(DiscoveryConstant.SPRING_APPLICATION_DISCOVERY_CONTROL_ENABLED);
        if (flag == null) {
            return true;
        }

        return Boolean.valueOf(flag);
    }

    public boolean isConfigRestControlEnabled() {
        Map<String, String> metadata = getMetadata();
        String flag = metadata.get(DiscoveryConstant.SPRING_APPLICATION_CONFIG_REST_CONTROL_ENABLED);
        if (flag == null) {
            return true;
        }

        return Boolean.valueOf(flag);
    }
}
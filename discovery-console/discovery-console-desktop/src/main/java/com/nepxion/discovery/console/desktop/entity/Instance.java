package com.nepxion.discovery.console.desktop.entity;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

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
}
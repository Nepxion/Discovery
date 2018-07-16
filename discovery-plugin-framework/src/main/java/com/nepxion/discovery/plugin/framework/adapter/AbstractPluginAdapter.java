package com.nepxion.discovery.plugin.framework.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.framework.cache.PluginCache;
import com.nepxion.discovery.plugin.framework.cache.RuleCache;
import com.nepxion.discovery.plugin.framework.constant.PluginConstant;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.entity.RuleEntity;

public abstract class AbstractPluginAdapter implements PluginAdapter {
    @Autowired
    protected PluginContextAware pluginContextAware;

    @Autowired
    protected PluginCache pluginCache;

    @Autowired
    protected RuleCache ruleCache;

    @Override
    public String getServiceId() {
        return pluginContextAware.getEnvironment().getProperty(PluginConstant.SPRING_APPLICATION_NAME);
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
    public String getDynamicVersion() {
        return pluginCache.get(PluginConstant.VERSION);
    }

    @Override
    public void setDynamicVersion(String version) {
        pluginCache.put(PluginConstant.VERSION, version);
    }

    @Override
    public void clearDynamicVersion() {
        pluginCache.clear(PluginConstant.VERSION);
    }

    @Override
    public RuleEntity getRule() {
        return ruleCache.get(PluginConstant.RULE);
    }

    @Override
    public void setRule(RuleEntity ruleEntity) {
        ruleCache.put(PluginConstant.RULE, ruleEntity);
    }
}
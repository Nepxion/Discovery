package com.nepxion.discovery.plugin.configcenter.adapter;

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

import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.SubscriptionType;
import com.nepxion.discovery.plugin.configcenter.loader.RemoteConfigLoader;
import com.nepxion.discovery.plugin.configcenter.logger.ConfigLogger;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.event.PluginEventWapper;
import com.nepxion.discovery.plugin.framework.event.RuleClearedEvent;
import com.nepxion.discovery.plugin.framework.event.RuleUpdatedEvent;

public abstract class ConfigAdapter extends RemoteConfigLoader {
    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private PluginEventWapper pluginEventWapper;

    @Autowired
    private ConfigLogger configLogger;

    @Override
    public String[] getConfigList() throws Exception {
        String[] configList = new String[2];
        configList[0] = getConfig(false);
        configList[1] = getConfig(true);

        return configList;
    }

    public String getConfig(boolean globalConfig) throws Exception {
        String group = getGroup();
        String dataId = getDataId(globalConfig);

        String config = getConfig(group, dataId);

        if (StringUtils.isNotEmpty(config)) {
            configLogger.logFound(globalConfig);
        } else {
            configLogger.logNotFound(globalConfig);
        }

        return config;
    }

    public void callbackConfig(String config, boolean globalConfig) {
        SubscriptionType subscriptionType = getSubscriptionType(globalConfig);

        if (StringUtils.isNotEmpty(config)) {
            configLogger.logUpdatedEvent(globalConfig);

            RuleEntity ruleEntity = null;
            if (globalConfig) {
                ruleEntity = pluginAdapter.getDynamicGlobalRule();
            } else {
                ruleEntity = pluginAdapter.getDynamicPartialRule();
            }

            String rule = null;
            if (ruleEntity != null) {
                rule = ruleEntity.getContent();
            }
            if (!StringUtils.equals(rule, config)) {
                fireRuleUpdated(new RuleUpdatedEvent(subscriptionType, config), true);
            } else {
                configLogger.logUpdatedSame(globalConfig);
            }
        } else {
            configLogger.logClearedEvent(globalConfig);

            fireRuleCleared(new RuleClearedEvent(subscriptionType), true);
        }
    }

    public String getGroup() {
        return pluginAdapter.getGroup();
    }

    public String getServiceId() {
        return pluginAdapter.getServiceId();
    }

    public String getDataId(boolean globalConfig) {
        String group = getGroup();
        String serviceId = getServiceId();

        return globalConfig ? group : serviceId;
    }

    public void fireRuleUpdated(RuleUpdatedEvent ruleUpdatedEvent, boolean async) {
        pluginEventWapper.fireRuleUpdated(ruleUpdatedEvent, async);
    }

    public void fireRuleCleared(RuleClearedEvent ruleClearedEvent, boolean async) {
        pluginEventWapper.fireRuleCleared(ruleClearedEvent, async);
    }

    public SubscriptionType getSubscriptionType(boolean globalConfig) {
        return globalConfig ? SubscriptionType.GLOBAL : SubscriptionType.PARTIAL;
    }

    public abstract String getConfig(String group, String dataId) throws Exception;

    public abstract String getConfigType();

    public abstract boolean isConfigSingleKey();
}
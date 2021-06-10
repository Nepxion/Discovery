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

import com.nepxion.discovery.common.entity.ConfigType;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.SubscriptionType;
import com.nepxion.discovery.plugin.configcenter.loader.RemoteConfigLoader;
import com.nepxion.discovery.plugin.configcenter.logger.ConfigLogger;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.adapter.PluginConfigAdapter;
import com.nepxion.discovery.plugin.framework.event.PluginEventWapper;
import com.nepxion.discovery.plugin.framework.event.RuleClearedEvent;
import com.nepxion.discovery.plugin.framework.event.RuleUpdatedEvent;

public abstract class ConfigAdapter extends RemoteConfigLoader implements PluginConfigAdapter {
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
        logGetStarted(globalConfig);

        String group = getGroup();
        String dataId = getDataId(globalConfig);

        String config = getConfig(group, dataId);

        if (StringUtils.isNotBlank(config)) {
            logFound(globalConfig);
        } else {
            logNotFound(globalConfig);
        }

        return config;
    }

    public void callbackConfig(String config, boolean globalConfig) {
        SubscriptionType subscriptionType = getSubscriptionType(globalConfig);

        if (StringUtils.isNotBlank(config)) {
            logUpdatedEvent(globalConfig);

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
                logUpdatedSame(globalConfig);
            }
        } else {
            logClearedEvent(globalConfig);

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

    public void logGetStarted(boolean globalConfig) {
        configLogger.logGetStarted(globalConfig);
    }

    public void logSubscribeStarted(boolean globalConfig) {
        configLogger.logSubscribeStarted(globalConfig);
    }

    public void logSubscribeFailed(Exception e, boolean globalConfig) {
        configLogger.logSubscribeFailed(e, globalConfig);
    }

    public void logUnsubscribeStarted(boolean globalConfig) {
        configLogger.logUnsubscribeStarted(globalConfig);
    }

    public void logUnsubscribeFailed(Exception e, boolean globalConfig) {
        configLogger.logUnsubscribeFailed(e, globalConfig);
    }

    public void logFound(boolean globalConfig) {
        configLogger.logFound(globalConfig);
    }

    public void logNotFound(boolean globalConfig) {
        configLogger.logNotFound(globalConfig);
    }

    public void logUpdatedEvent(boolean globalConfig) {
        configLogger.logUpdatedEvent(globalConfig);
    }

    public void logClearedEvent(boolean globalConfig) {
        configLogger.logClearedEvent(globalConfig);
    }

    public void logUpdatedSame(boolean globalConfig) {
        configLogger.logUpdatedSame(globalConfig);
    }

    public boolean isConfigSingleKey() {
        ConfigType configType = getConfigType();

        return ConfigType.isSingleKey(configType);
    }

    public abstract String getConfig(String group, String dataId) throws Exception;
}
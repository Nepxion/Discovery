package com.nepxion.discovery.plugin.configcenter.logger;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.entity.ConfigType;
import com.nepxion.discovery.common.entity.SubscriptionType;
import com.nepxion.discovery.plugin.configcenter.adapter.ConfigAdapter;

public class ConfigLogger {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigLogger.class);

    @Autowired(required = false)
    private ConfigAdapter configAdapter;

    public void logGetStarted(boolean globalConfig) {
        if (configAdapter == null) {
            return;
        }

        String group = configAdapter.getGroup();
        String dataId = configAdapter.getDataId(globalConfig);
        SubscriptionType subscriptionType = configAdapter.getSubscriptionType(globalConfig);
        ConfigType configType = configAdapter.getConfigType();

        LOG.info("Get {} config from {} server, " + getLogKey(), subscriptionType, configType, group, dataId);
    }

    public void logSubscribeStarted(boolean globalConfig) {
        if (configAdapter == null) {
            return;
        }

        String group = configAdapter.getGroup();
        String dataId = configAdapter.getDataId(globalConfig);
        SubscriptionType subscriptionType = configAdapter.getSubscriptionType(globalConfig);
        ConfigType configType = configAdapter.getConfigType();

        LOG.info("Subscribe {} config from {} server, " + getLogKey(), subscriptionType, configType, group, dataId);
    }

    public void logSubscribeFailed(Exception e, boolean globalConfig) {
        if (configAdapter == null) {
            return;
        }

        String group = configAdapter.getGroup();
        String dataId = configAdapter.getDataId(globalConfig);
        SubscriptionType subscriptionType = configAdapter.getSubscriptionType(globalConfig);
        ConfigType configType = configAdapter.getConfigType();

        LOG.error("Subscribe {} config from {} server failed, " + getLogKey(), subscriptionType, configType, group, dataId, e);
    }

    public void logUnsubscribeStarted(boolean globalConfig) {
        if (configAdapter == null) {
            return;
        }

        String group = configAdapter.getGroup();
        String dataId = configAdapter.getDataId(globalConfig);
        SubscriptionType subscriptionType = configAdapter.getSubscriptionType(globalConfig);
        ConfigType configType = configAdapter.getConfigType();

        LOG.info("Unsubscribe {} config from {} server, " + getLogKey(), subscriptionType, configType, group, dataId);
    }

    public void logUnsubscribeFailed(Exception e, boolean globalConfig) {
        if (configAdapter == null) {
            return;
        }

        String group = configAdapter.getGroup();
        String dataId = configAdapter.getDataId(globalConfig);
        SubscriptionType subscriptionType = configAdapter.getSubscriptionType(globalConfig);
        ConfigType configType = configAdapter.getConfigType();

        LOG.error("Unsubscribe {} config from {} server failed, " + getLogKey(), subscriptionType, configType, group, dataId, e);
    }

    public void logFound(boolean globalConfig) {
        if (configAdapter == null) {
            return;
        }

        SubscriptionType subscriptionType = configAdapter.getSubscriptionType(globalConfig);
        ConfigType configType = configAdapter.getConfigType();

        LOG.info("Found {} config from {} server", subscriptionType, configType);
    }

    public void logNotFound(boolean globalConfig) {
        if (configAdapter == null) {
            return;
        }

        SubscriptionType subscriptionType = configAdapter.getSubscriptionType(globalConfig);
        ConfigType configType = configAdapter.getConfigType();

        LOG.info("Not found {} config from {} server", subscriptionType, configType);
    }

    public void logUpdatedEvent(boolean globalConfig) {
        if (configAdapter == null) {
            return;
        }

        String group = configAdapter.getGroup();
        String dataId = configAdapter.getDataId(globalConfig);
        SubscriptionType subscriptionType = configAdapter.getSubscriptionType(globalConfig);
        ConfigType configType = configAdapter.getConfigType();

        LOG.info("Get {} config updated event from {} server, " + getLogKey(), subscriptionType, configType, group, dataId);
    }

    public void logClearedEvent(boolean globalConfig) {
        if (configAdapter == null) {
            return;
        }

        String group = configAdapter.getGroup();
        String dataId = configAdapter.getDataId(globalConfig);
        SubscriptionType subscriptionType = configAdapter.getSubscriptionType(globalConfig);
        ConfigType configType = configAdapter.getConfigType();

        LOG.info("Get {} config cleared event from {} server, " + getLogKey(), subscriptionType, configType, group, dataId);
    }

    public void logUpdatedSame(boolean globalConfig) {
        if (configAdapter == null) {
            return;
        }

        String group = configAdapter.getGroup();
        String dataId = configAdapter.getDataId(globalConfig);
        SubscriptionType subscriptionType = configAdapter.getSubscriptionType(globalConfig);
        ConfigType configType = configAdapter.getConfigType();

        LOG.info("Updated {} config from {} server is same as current config, ignore to update, " + getLogKey(), subscriptionType, configType, group, dataId);
    }

    private String getLogKey() {
        if (configAdapter == null) {
            return null;
        }

        return configAdapter.isConfigSingleKey() ? "key={}-{}" : "group={}, dataId={}";
    }
}
package com.nepxion.discovery.plugin.configcenter.apollo.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ctrip.framework.apollo.ConfigChangeListener;
import com.nepxion.discovery.common.apollo.constant.ApolloConstant;
import com.nepxion.discovery.common.apollo.operation.ApolloOperation;
import com.nepxion.discovery.common.apollo.operation.ApolloSubscribeCallback;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.plugin.configcenter.adapter.ConfigAdapter;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.event.RuleClearedEvent;
import com.nepxion.discovery.plugin.framework.event.RuleUpdatedEvent;

public class ApolloConfigAdapter extends ConfigAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(ApolloConfigAdapter.class);

    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private ApolloOperation apolloOperation;

    private ConfigChangeListener partialListener;
    private ConfigChangeListener globalListener;

    @Override
    public String getConfig() throws Exception {
        String config = getConfig(false);
        if (StringUtils.isNotEmpty(config)) {
            LOG.info("Found {} config from {} server", getConfigScope(false), getConfigType());

            return config;
        } else {
            LOG.info("No {} config is found from {} server", getConfigScope(false), getConfigType());
        }

        config = getConfig(true);
        if (StringUtils.isNotEmpty(config)) {
            LOG.info("Found {} config from {} server", getConfigScope(true), getConfigType());

            return config;
        } else {
            LOG.info("No {} config is found from {} server", getConfigScope(true), getConfigType());
        }

        return null;
    }

    private String getConfig(boolean globalConfig) throws Exception {
        String group = pluginAdapter.getGroup();
        String serviceId = pluginAdapter.getServiceId();

        return apolloOperation.getConfig(group, globalConfig ? group : serviceId);
    }

    @PostConstruct
    public void subscribeConfig() {
        partialListener = subscribeConfig(false);
        globalListener = subscribeConfig(true);
    }

    private ConfigChangeListener subscribeConfig(boolean globalConfig) {
        String groupKey = pluginAdapter.getGroupKey();
        String group = pluginAdapter.getGroup();
        String serviceId = pluginAdapter.getServiceId();

        LOG.info("Subscribe {} config from {} server, {}={}, serviceId={}", getConfigScope(globalConfig), getConfigType(), groupKey, group, serviceId);

        try {
            return apolloOperation.subscribeConfig(group, globalConfig ? group : serviceId, new ApolloSubscribeCallback() {
                @Override
                public void callback(String config) {
                    if (StringUtils.isNotEmpty(config)) {
                        LOG.info("Get {} config updated event from {} server, {}={}, serviceId={}", getConfigScope(globalConfig), getConfigType(), groupKey, group, serviceId);

                        RuleEntity ruleEntity = pluginAdapter.getRule();
                        String rule = null;
                        if (ruleEntity != null) {
                            rule = ruleEntity.getContent();
                        }
                        if (!StringUtils.equals(rule, config)) {
                            fireRuleUpdated(new RuleUpdatedEvent(config), true);
                        } else {
                            LOG.info("Updated {} config from {} server is same as current config, ignore to update, {}={}, serviceId={}", getConfigScope(globalConfig), getConfigType(), groupKey, group, serviceId);
                        }
                    } else {
                        LOG.info("Get {} config cleared event from {} server, {}={}, serviceId={}", getConfigScope(globalConfig), getConfigType(), groupKey, group, serviceId);

                        fireRuleCleared(new RuleClearedEvent(), true);
                    }
                }
            });
        } catch (Exception e) {
            LOG.error("Subscribe {} config from {} server failed, {}={}, serviceId={}", getConfigScope(globalConfig), getConfigType(), groupKey, group, serviceId, e);
        }

        return null;
    }

    @Override
    public void close() {
        unsubscribeConfig(partialListener, false);
        unsubscribeConfig(globalListener, true);
    }

    private void unsubscribeConfig(ConfigChangeListener configListener, boolean globalConfig) {
        if (configListener == null) {
            return;
        }

        String groupKey = pluginAdapter.getGroupKey();
        String group = pluginAdapter.getGroup();
        String serviceId = pluginAdapter.getServiceId();

        LOG.info("Unsubscribe {} config from {} server, {}={}, serviceId={}", getConfigScope(globalConfig), getConfigType(), groupKey, group, serviceId);

        apolloOperation.unsubscribeConfig(group, globalConfig ? group : serviceId, configListener);
    }

    public String getConfigScope(boolean globalConfig) {
        return globalConfig ? DiscoveryConstant.GLOBAL : DiscoveryConstant.PARTIAL;
    }

    @Override
    public String getConfigType() {
        return ApolloConstant.APOLLO_TYPE;
    }
}
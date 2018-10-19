package com.nepxion.discovery.plugin.configcenter.scconfig.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author Ankeway
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.event.EventListener;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.scconfig.constant.SCConfigConstant;
import com.nepxion.discovery.common.scconfig.operation.SCConfigOperation;
import com.nepxion.discovery.common.scconfig.operation.SCConfigSubscribeCallback;
import com.nepxion.discovery.plugin.configcenter.adapter.ConfigAdapter;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.event.RuleClearedEvent;
import com.nepxion.discovery.plugin.framework.event.RuleUpdatedEvent;

public class SCConfigConfigAdapter extends ConfigAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(SCConfigConfigAdapter.class);

    @Autowired
    protected PluginContextAware pluginContextAware;

    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private SCConfigOperation scConfigOperation;

    @Override
    public String getConfig() throws Exception {
        String config = getConfig(false);
        if (StringUtils.isNotEmpty(config)) {
            return config;
        } else {
            LOG.info("No {} config is retrieved from {} server", getConfigScope(false), getConfigType());
        }

        config = getConfig(true);
        if (StringUtils.isNotEmpty(config)) {
            return config;
        } else {
            LOG.info("No {} config is retrieved from {} server", getConfigScope(true), getConfigType());
        }

        return null;
    }

    private String getConfig(boolean globalConfig) throws Exception {
        String groupKey = pluginContextAware.getGroupKey();
        String group = pluginAdapter.getGroup();
        String serviceId = pluginAdapter.getServiceId();

        LOG.info("Get {} config from {} server, {}={}, serviceId={}", getConfigScope(globalConfig), getConfigType(), groupKey, group, serviceId);

        return scConfigOperation.getConfig(group, globalConfig ? group : serviceId);
    }

    @EventListener(classes = EnvironmentChangeEvent.class)
    public void subscribeConfig(EnvironmentChangeEvent event) {
        subscribeConfig(false);
        subscribeConfig(true);
    }

    private void subscribeConfig(boolean globalConfig) {
        String groupKey = pluginContextAware.getGroupKey();
        String group = pluginAdapter.getGroup();
        String serviceId = pluginAdapter.getServiceId();

        LOG.info("Subscribe {} config from {} server, {}={}, serviceId={}", getConfigScope(globalConfig), getConfigType(), groupKey, group, serviceId);

        try {
            scConfigOperation.subscribeConfig(group, globalConfig ? group : serviceId, new SCConfigSubscribeCallback() {
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
                            LOG.info("Retrieved {} config from {} server is same as current config, ignore to update, {}={}, serviceId={}", getConfigScope(globalConfig), getConfigType(), groupKey, group, serviceId);
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
    }

    @Override
    public void close() {

    }

    public String getConfigScope(boolean globalConfig) {
        return globalConfig ? DiscoveryConstant.GLOBAL : DiscoveryConstant.PARTIAL;
    }

    @Override
    public String getConfigType() {
        return SCConfigConstant.TYPE;
    }
}
package com.nepxion.discovery.plugin.configcenter.extension.nacos.adapter;

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

import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.nacos.operation.NacosOperation;
import com.nepxion.discovery.common.nacos.operation.NacosSubscribeCallback;
import com.nepxion.discovery.plugin.configcenter.ConfigAdapter;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.event.RuleClearedEvent;
import com.nepxion.discovery.plugin.framework.event.RuleUpdatedEvent;

public class NacosConfigAdapter extends ConfigAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(NacosConfigAdapter.class);

    @Autowired
    protected PluginContextAware pluginContextAware;

    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private NacosOperation nacosOperation;

    @Override
    public String getConfig() throws Exception {
        String config = getConfig(true);
        if (StringUtils.isNotEmpty(config)) {
            return config;
        } else {
            LOG.info("No global config is retrieved from Nacos server");
        }

        config = getConfig(false);
        if (StringUtils.isNotEmpty(config)) {
            return config;
        } else {
            LOG.info("No partial config is retrieved from Nacos server");
        }

        return null;
    }

    private String getConfig(boolean globalConfig) throws Exception {
        String groupKey = pluginContextAware.getGroupKey();
        String group = pluginAdapter.getGroup();
        String serviceId = pluginAdapter.getServiceId();

        LOG.info("Get config from Nacos server, {}={}, serviceId={}, globalConfig={}", groupKey, group, serviceId, globalConfig);

        return nacosOperation.getConfig(group, globalConfig ? group : serviceId);
    }

    @PostConstruct
    public void subscribeConfig() {
        subscribeConfig(true);
        subscribeConfig(false);
    }

    private void subscribeConfig(boolean globalConfig) {
        String groupKey = pluginContextAware.getGroupKey();
        String group = pluginAdapter.getGroup();
        String serviceId = pluginAdapter.getServiceId();

        LOG.info("Subscribe config from Nacos server, {}={}, serviceId={}, globalConfig={}", groupKey, group, serviceId, globalConfig);

        try {
            nacosOperation.subscribeConfig(group, globalConfig ? group : serviceId, new NacosSubscribeCallback() {
                @Override
                public void callback(String config) {
                    if (StringUtils.isNotEmpty(config)) {
                        LOG.info("Get config updated event from Nacos server, {}={}, serviceId={}, globalConfig={}", groupKey, group, serviceId, globalConfig);

                        RuleEntity ruleEntity = pluginAdapter.getRule();
                        String rule = null;
                        if (ruleEntity != null) {
                            rule = ruleEntity.getContent();
                        }
                        if (!StringUtils.equals(rule, config)) {
                            fireRuleUpdated(new RuleUpdatedEvent(config), true);
                        } else {
                            LOG.info("Retrieved config is same as current config, ignore to update, {}={}, serviceId={}, globalConfig={}", groupKey, group, serviceId, globalConfig);
                        }
                    } else {
                        LOG.info("Get config cleared event from Nacos server, {}={}, serviceId={}, globalConfig={}", groupKey, group, serviceId, globalConfig);

                        fireRuleCleared(new RuleClearedEvent(), true);
                    }
                }
            });
        } catch (Exception e) {
            LOG.error("Subscribe config from Nacos server failed, " + groupKey + "=" + group + ", serviceId=" + serviceId + ", globalConfig=" + globalConfig, e);
        }
    }
}
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

import com.nepxion.discovery.common.nacos.operation.NacosOperation;
import com.nepxion.discovery.common.nacos.operation.NacosSubscribeCallback;
import com.nepxion.discovery.plugin.configcenter.ConfigAdapter;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.entity.RuleEntity;
import com.nepxion.discovery.plugin.framework.event.RuleClearedEvent;
import com.nepxion.discovery.plugin.framework.event.RuleUpdatedEvent;

public class NacosConfigAdapter extends ConfigAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(NacosConfigAdapter.class);

    @Autowired
    private NacosOperation nacosOperation;

    @Autowired
    protected PluginContextAware pluginContextAware;

    @Autowired
    private PluginAdapter pluginAdapter;

    @Override
    public String getConfig() throws Exception {
        String groupKey = pluginContextAware.getGroupKey();
        String group = pluginAdapter.getGroup();
        String serviceId = pluginAdapter.getServiceId();

        LOG.info("Get config from Nacos server, {}={}, serviceId={}", groupKey, group, serviceId);

        return nacosOperation.getConfig(group, serviceId);
    }

    @PostConstruct
    public void subscribeConfig() {
        String groupKey = pluginContextAware.getGroupKey();
        String group = pluginAdapter.getGroup();
        String serviceId = pluginAdapter.getServiceId();

        LOG.info("Subscribe config from Nacos server, {}={}, serviceId={}", groupKey, group, serviceId);

        try {
            nacosOperation.subscribeConfig(group, serviceId, new NacosSubscribeCallback() {
                @Override
                public void callback(String config) {
                    if (StringUtils.isNotEmpty(config)) {
                        LOG.info("Get config updated event from Nacos server, {}={}, serviceId={}", groupKey, group, serviceId);

                        RuleEntity ruleEntity = pluginAdapter.getRule();
                        String rule = ruleEntity.getContent();
                        if (!StringUtils.equals(rule, config)) {
                            fireRuleUpdated(new RuleUpdatedEvent(config), true);
                        } else {
                            LOG.info("Retrieved config is same as current config, ignore to update, {}={}, serviceId={}", groupKey, group, serviceId);
                        }
                    } else {
                        LOG.info("Get config cleared event from Nacos server, {}={}, serviceId={}", groupKey, group, serviceId);

                        fireRuleCleared(new RuleClearedEvent(), true);
                    }
                }
            });
        } catch (Exception e) {
            LOG.error("Subscribe config failed", e);
        }
    }
}
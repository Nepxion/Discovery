package com.nepxion.discovery.plugin.configcenter.extension.nacos.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.concurrent.Executor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.nepxion.discovery.plugin.configcenter.ConfigAdapter;
import com.nepxion.discovery.plugin.configcenter.extension.nacos.constant.NacosConstant;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.entity.RuleEntity;
import com.nepxion.discovery.plugin.framework.event.RuleClearedEvent;
import com.nepxion.discovery.plugin.framework.event.RuleUpdatedEvent;

public class NacosConfigAdapter extends ConfigAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(NacosConfigAdapter.class);

    @Autowired
    private ConfigService configService;

    @Autowired
    protected PluginContextAware pluginContextAware;

    @Autowired
    private PluginAdapter pluginAdapter;

    @Override
    public String getConfig() throws Exception {
        String groupKey = pluginContextAware.getGroupKey();
        String group = pluginAdapter.getGroup();
        String serviceId = pluginAdapter.getServiceId();

        long timeout = pluginContextAware.getEnvironment().getProperty(NacosConstant.TIMEOUT, Long.class, NacosConstant.DEFAULT_TIMEOUT);

        LOG.info("Get remote config from Nacos server, {}={}, serviceId={}, timeout={}", groupKey, group, serviceId, timeout);

        return configService.getConfig(serviceId, group, timeout);
    }

    @Override
    public void subscribeConfig() throws Exception {
        String groupKey = pluginContextAware.getGroupKey();
        String group = pluginAdapter.getGroup();
        String serviceId = pluginAdapter.getServiceId();

        LOG.info("Subscribe remote config from Nacos server, {}={}, serviceId={}", groupKey, group, serviceId);

        configService.addListener(serviceId, group, new Listener() {
            @Override
            public void receiveConfigInfo(String config) {
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

            @Override
            public Executor getExecutor() {
                return null;
            }
        });
    }
}
package com.nepxion.discovery.plugin.admincenter.resource;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.entity.ConfigType;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.SubscriptionType;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.adapter.PluginConfigAdapter;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.event.PluginEventWapper;
import com.nepxion.discovery.plugin.framework.event.RuleClearedEvent;
import com.nepxion.discovery.plugin.framework.event.RuleUpdatedEvent;

public class ConfigResourceImpl implements ConfigResource {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigResourceImpl.class);

    @Autowired
    private PluginContextAware pluginContextAware;

    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired(required = false)
    private PluginConfigAdapter pluginConfigAdapter;

    @Autowired
    private PluginEventWapper pluginEventWapper;

    @Override
    public ConfigType getConfigType() {
        if (pluginConfigAdapter == null) {
            LOG.error("Remote config adapter isn't provided");

            throw new DiscoveryException("Remote config adapter isn't provided");
        }

        return pluginConfigAdapter.getConfigType();
    }

    @Override
    public void update(String config, boolean async) {
        Boolean discoveryControlEnabled = pluginContextAware.isDiscoveryControlEnabled();
        if (!discoveryControlEnabled) {
            throw new DiscoveryException("Discovery control is disabled");
        }

        Boolean isConfigRestControlEnabled = pluginContextAware.isConfigRestControlEnabled();
        if (!isConfigRestControlEnabled) {
            throw new DiscoveryException("Config rest control is disabled");
        }

        pluginEventWapper.fireRuleUpdated(new RuleUpdatedEvent(SubscriptionType.PARTIAL, config), async);
    }

    @Override
    public void clear(boolean async) {
        Boolean discoveryControlEnabled = pluginContextAware.isDiscoveryControlEnabled();
        if (!discoveryControlEnabled) {
            throw new DiscoveryException("Discovery control is disabled");
        }

        Boolean isConfigRestControlEnabled = pluginContextAware.isConfigRestControlEnabled();
        if (!isConfigRestControlEnabled) {
            throw new DiscoveryException("Config rest control is disabled");
        }

        pluginEventWapper.fireRuleCleared(new RuleClearedEvent(SubscriptionType.PARTIAL), async);
    }

    @Override
    public List<String> view() {
        List<String> ruleList = new ArrayList<String>(3);

        String localRuleContent = StringUtils.EMPTY;
        RuleEntity localRuleEntity = pluginAdapter.getLocalRule();
        if (localRuleEntity != null && StringUtils.isNotEmpty(localRuleEntity.getContent())) {
            localRuleContent = localRuleEntity.getContent();
        }

        String dynamicGlobalRuleContent = StringUtils.EMPTY;
        RuleEntity dynamicGlobalRuleEntity = pluginAdapter.getDynamicGlobalRule();
        if (dynamicGlobalRuleEntity != null && StringUtils.isNotEmpty(dynamicGlobalRuleEntity.getContent())) {
            dynamicGlobalRuleContent = dynamicGlobalRuleEntity.getContent();
        }

        String dynamicPartialRuleContent = StringUtils.EMPTY;
        RuleEntity dynamicPartialRuleEntity = pluginAdapter.getDynamicPartialRule();
        if (dynamicPartialRuleEntity != null && StringUtils.isNotEmpty(dynamicPartialRuleEntity.getContent())) {
            dynamicPartialRuleContent = dynamicPartialRuleEntity.getContent();
        }

        ruleList.add(localRuleContent);
        ruleList.add(dynamicGlobalRuleContent);
        ruleList.add(dynamicPartialRuleContent);

        return ruleList;
    }
}
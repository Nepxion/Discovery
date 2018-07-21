package com.nepxion.discovery.plugin.framework.event;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.eventbus.Subscribe;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.config.PluginConfigParser;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.entity.RuleEntity;
import com.nepxion.discovery.plugin.framework.exception.PluginException;
import com.nepxion.discovery.plugin.framework.listener.loadbalance.LoadBalanceListenerExecutor;
import com.nepxion.eventbus.annotation.EventBus;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;

@EventBus
public class PluginSubscriber {
    private static final Logger LOG = LoggerFactory.getLogger(PluginSubscriber.class);

    @Autowired
    private PluginContextAware pluginContextAware;

    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private PluginConfigParser pluninConfigParser;

    @Autowired
    private LoadBalanceListenerExecutor loadBalanceListenerExecutor;

    @Subscribe
    public void onRuleUpdated(RuleUpdatedEvent ruleUpdatedEvent) {
        Boolean discoveryControlEnabled = pluginContextAware.isDiscoveryControlEnabled();
        if (!discoveryControlEnabled) {
            LOG.info("Discovery control is disabled, ignore to subscribe");

            return;
        }

        LOG.info("Rule updating has been triggered");

        if (ruleUpdatedEvent == null) {
            throw new PluginException("RuleUpdatedEvent can't be null");
        }

        InputStream inputStream = ruleUpdatedEvent.getInputStream();
        try {
            RuleEntity ruleEntity = pluninConfigParser.parse(inputStream);
            pluginAdapter.setDynamicRule(ruleEntity);
        } catch (Exception e) {
            LOG.error("Parse rule xml failed", e);

            throw e;
        }

        refreshLoadBalancer();
    }

    @Subscribe
    public void onRuleCleared(RuleClearedEvent ruleClearedEvent) {
        Boolean discoveryControlEnabled = pluginContextAware.isDiscoveryControlEnabled();
        if (!discoveryControlEnabled) {
            LOG.info("Discovery control is disabled, ignore to subscribe");

            return;
        }

        LOG.info("Rule clearing has been triggered");

        if (ruleClearedEvent == null) {
            throw new PluginException("RuleClearedEvent can't be null");
        }

        pluginAdapter.clearDynamicRule();

        refreshLoadBalancer();
    }

    @Subscribe
    public void onVersionUpdated(VersionUpdatedEvent versionUpdatedEvent) {
        Boolean discoveryControlEnabled = pluginContextAware.isDiscoveryControlEnabled();
        if (!discoveryControlEnabled) {
            LOG.info("Discovery control is disabled, ignore to subscribe");

            return;
        }

        LOG.info("Version updating has been triggered");

        if (versionUpdatedEvent == null) {
            throw new PluginException("VersionUpdatedEvent can't be null");
        }

        String dynamicVersion = versionUpdatedEvent.getDynamicVersion();
        String localVersion = versionUpdatedEvent.getLocalVersion();

        if (StringUtils.isEmpty(localVersion)) {
            pluginAdapter.setDynamicVersion(dynamicVersion);

            refreshLoadBalancer();

            LOG.info("Version has been updated, new version is {}", dynamicVersion);
        } else {
            if (StringUtils.equals(pluginAdapter.getLocalVersion(), localVersion)) {
                pluginAdapter.setDynamicVersion(dynamicVersion);

                refreshLoadBalancer();

                LOG.info("Version has been updated, new version is {}", dynamicVersion);
            } else {
                throw new PluginException("Version updating will be ignored, because input localVersion=" + localVersion + ", current localVersion=" + pluginAdapter.getLocalVersion());
            }
        }
    }

    @Subscribe
    public void onVersionCleared(VersionClearedEvent versionClearedEvent) {
        Boolean discoveryControlEnabled = pluginContextAware.isDiscoveryControlEnabled();
        if (!discoveryControlEnabled) {
            LOG.info("Discovery control is disabled, ignore to subscribe");

            return;
        }

        LOG.info("Version clearing has been triggered");

        if (versionClearedEvent == null) {
            throw new PluginException("VersionClearedEvent can't be null");
        }

        String localVersion = versionClearedEvent.getLocalVersion();

        if (StringUtils.isEmpty(localVersion)) {
            pluginAdapter.clearDynamicVersion();

            refreshLoadBalancer();

            LOG.info("Version has been cleared");
        } else {
            if (StringUtils.equals(pluginAdapter.getLocalVersion(), localVersion)) {
                pluginAdapter.clearDynamicVersion();

                refreshLoadBalancer();

                LOG.info("Version has been cleared");
            } else {
                throw new PluginException("Version clearing will be ignored, because input localVersion=" + localVersion + ", current localVersion=" + pluginAdapter.getLocalVersion());
            }
        }
    }

    private void refreshLoadBalancer() {
        ZoneAwareLoadBalancer<?> loadBalancer = loadBalanceListenerExecutor.getLoadBalancer();
        if (loadBalancer == null) {
            return;
        }

        // 当规则或者版本更新后，强制刷新Ribbon缓存
        loadBalancer.updateListOfServers();
    }
}
package com.nepxion.discovery.plugin.framework.event;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.eventbus.Subscribe;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.SubscriptionType;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.listener.loadbalance.LoadBalanceListenerExecutor;
import com.nepxion.discovery.plugin.framework.parser.PluginConfigParser;
import com.nepxion.eventbus.annotation.EventBus;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;

@EventBus
public class PluginSubscriber {
    private static final Logger LOG = LoggerFactory.getLogger(PluginSubscriber.class);

    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private PluginConfigParser pluginConfigParser;

    @Autowired
    private PluginEventWapper pluginEventWapper;

    @Autowired
    private LoadBalanceListenerExecutor loadBalanceListenerExecutor;

    @Subscribe
    public void onRuleUpdated(RuleUpdatedEvent ruleUpdatedEvent) {
        LOG.info("Rule updating has been triggered");

        if (ruleUpdatedEvent == null) {
            throw new DiscoveryException("RuleUpdatedEvent can't be null");
        }

        SubscriptionType subscriptionType = ruleUpdatedEvent.getSubscriptionType();
        String rule = ruleUpdatedEvent.getRule();
        try {
            RuleEntity ruleEntity = pluginConfigParser.parse(rule);
            switch (subscriptionType) {
                case GLOBAL:
                    pluginAdapter.setDynamicGlobalRule(ruleEntity);
                    break;
                case PARTIAL:
                    pluginAdapter.setDynamicPartialRule(ruleEntity);
                    break;
            }

            pluginEventWapper.fireParameterChanged();
        } catch (Exception e) {
            LOG.error("Parse rule xml failed", e);

            pluginEventWapper.fireRuleFailure(new RuleFailureEvent(subscriptionType, rule, e));

            throw e;
        }

        refreshLoadBalancer();
    }

    @Subscribe
    public void onRuleCleared(RuleClearedEvent ruleClearedEvent) {
        LOG.info("Rule clearing has been triggered");

        if (ruleClearedEvent == null) {
            throw new DiscoveryException("RuleClearedEvent can't be null");
        }

        SubscriptionType subscriptionType = ruleClearedEvent.getSubscriptionType();
        switch (subscriptionType) {
            case GLOBAL:
                pluginAdapter.clearDynamicGlobalRule();
                break;
            case PARTIAL:
                pluginAdapter.clearDynamicPartialRule();
                break;
        }

        pluginEventWapper.fireParameterChanged();

        refreshLoadBalancer();
    }

    @Subscribe
    public void onVersionUpdated(VersionUpdatedEvent versionUpdatedEvent) {
        LOG.info("Version updating has been triggered");

        if (versionUpdatedEvent == null) {
            throw new DiscoveryException("VersionUpdatedEvent can't be null");
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
                throw new DiscoveryException("Version updating will be ignored, because input localVersion=" + localVersion + ", current localVersion=" + pluginAdapter.getLocalVersion());
            }
        }
    }

    @Subscribe
    public void onVersionCleared(VersionClearedEvent versionClearedEvent) {
        LOG.info("Version clearing has been triggered");

        if (versionClearedEvent == null) {
            throw new DiscoveryException("VersionClearedEvent can't be null");
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
                throw new DiscoveryException("Version clearing will be ignored, because input localVersion=" + localVersion + ", current localVersion=" + pluginAdapter.getLocalVersion());
            }
        }
    }

    // 当规则或者版本更新后，强制刷新负载均衡缓存
    private void refreshLoadBalancer() {
        ZoneAwareLoadBalancer<?> loadBalancer = loadBalanceListenerExecutor.getLoadBalancer();
        if (loadBalancer == null) {
            return;
        }

        loadBalancer.updateListOfServers();
    }
}
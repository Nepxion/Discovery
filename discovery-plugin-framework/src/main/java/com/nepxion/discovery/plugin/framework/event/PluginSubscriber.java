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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.eventbus.Subscribe;
import com.nepxion.discovery.plugin.framework.config.PluginConfigParser;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.listener.loadbalance.LoadBalanceListenerExecutor;
import com.nepxion.eventbus.annotation.EventBus;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;

@EventBus
public class PluginSubscriber {
    private static final Logger LOG = LoggerFactory.getLogger(PluginSubscriber.class);

    @Autowired
    private PluginContextAware pluginContextAware;

    @Autowired
    private PluginConfigParser pluninConfigParser;

    @Autowired
    private LoadBalanceListenerExecutor loadBalanceListenerExecutor;

    @Subscribe
    public void subscribeRuleChanged(RuleChangedEvent ruleChangedEvent) {
        Boolean discoveryControlEnabled = pluginContextAware.isDiscoveryControlEnabled();
        Boolean remoteConfigEnabled = pluginContextAware.isRemoteConfigEnabled();

        if (!discoveryControlEnabled) {
            LOG.info("********** Discovery control is disabled, ignore to subscribe **********");

            return;
        }

        if (!remoteConfigEnabled) {
            LOG.info("********** Remote config is disabled, ignore to subscribe **********");

            return;
        }

        LOG.info("********** Remote config change has been subscribed **********");

        InputStream inputStream = ruleChangedEvent.getInputStream();
        pluninConfigParser.parse(inputStream);

        subscribeVersionChanged(null);
    }

    @Subscribe
    public void subscribeVersionChanged(VersionChangedEvent versionChangedEvent) {
        Boolean discoveryControlEnabled = pluginContextAware.isDiscoveryControlEnabled();
        if (!discoveryControlEnabled) {
            LOG.info("********** Discovery control is disabled, ignore to subscribe **********");

            return;
        }

        ZoneAwareLoadBalancer<?> loadBalancer = loadBalanceListenerExecutor.getLoadBalancer();
        if (loadBalancer == null) {
            return;
        }

        LOG.info("********** Version change has been subscribed **********");

        // 当版本更新后，强制刷新Ribbon缓存
        loadBalancer.updateListOfServers();
    }
}
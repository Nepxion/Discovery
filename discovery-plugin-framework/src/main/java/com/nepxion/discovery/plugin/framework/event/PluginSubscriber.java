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
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.config.PluginConfigParser;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
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
    public void onRuleChanged(RuleChangedEvent ruleChangedEvent) {
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

        if (ruleChangedEvent == null) {
            throw new PluginException("RuleChangedEvent can't be null");
        }

        InputStream inputStream = ruleChangedEvent.getInputStream();
        pluninConfigParser.parse(inputStream);

        refreshLoadBalancer();
    }

    @Subscribe
    public void onVersionChanged(VersionChangedEvent versionChangedEvent) {
        Boolean discoveryControlEnabled = pluginContextAware.isDiscoveryControlEnabled();
        if (!discoveryControlEnabled) {
            LOG.info("********** Discovery control is disabled, ignore to subscribe **********");

            return;
        }

        LOG.info("********** Version change has been subscribed **********");

        if (versionChangedEvent == null) {
            throw new PluginException("VersionChangedEvent can't be null");
        }

        VersionChangedEvent.EventType eventType = versionChangedEvent.getEventType();
        switch (eventType) {
            case VERSION_UPDATE:
                String version = versionChangedEvent.getVersion();
                pluginAdapter.setDynamicVersion(version);

                LOG.info("********** Version has been updated, new version is {} **********", version);

                break;
            case VERSION_CLEAR:
                pluginAdapter.clearDynamicVersion();

                LOG.info("********** Version has been cleared **********");

                break;
        }

        refreshLoadBalancer();
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
package com.nepxion.discovery.plugin.configcenter.nacos.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.nacos.api.config.listener.Listener;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.RuleType;
import com.nepxion.discovery.common.entity.SubscriptionType;
import com.nepxion.discovery.common.nacos.constant.NacosConstant;
import com.nepxion.discovery.common.nacos.operation.NacosOperation;
import com.nepxion.discovery.common.nacos.operation.NacosSubscribeCallback;
import com.nepxion.discovery.common.thread.DiscoveryNamedThreadFactory;
import com.nepxion.discovery.plugin.configcenter.adapter.ConfigAdapter;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.event.RuleClearedEvent;
import com.nepxion.discovery.plugin.framework.event.RuleUpdatedEvent;

public class NacosConfigAdapter extends ConfigAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(NacosConfigAdapter.class);

    private ExecutorService executorService = new ThreadPoolExecutor(2, 4, 0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1), new DiscoveryNamedThreadFactory("nacos-config"), new ThreadPoolExecutor.DiscardOldestPolicy());

    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private NacosOperation nacosOperation;

    private Listener partialListener;
    private Listener globalListener;

    @Override
    public String[] getConfigList() throws Exception {
        String[] configList = new String[2];
        configList[0] = getConfig(false);
        configList[1] = getConfig(true);

        String configType = getConfigType();

        if (StringUtils.isNotEmpty(configList[0])) {
            LOG.info("Found {} config from {} server", getSubscriptionType(false), configType);
        } else {
            LOG.info("No {} config is found from {} server", getSubscriptionType(false), configType);
        }

        if (StringUtils.isNotEmpty(configList[1])) {
            LOG.info("Found {} config from {} server", getSubscriptionType(true), configType);
        } else {
            LOG.info("No {} config is found from {} server", getSubscriptionType(true), configType);
        }

        return configList;
    }

    private String getConfig(boolean globalConfig) throws Exception {
        String group = pluginAdapter.getGroup();
        String serviceId = pluginAdapter.getServiceId();
        String dataId = globalConfig ? group : serviceId;

        return nacosOperation.getConfig(group, dataId);
    }

    @PostConstruct
    public void subscribeConfig() {
        partialListener = subscribeConfig(false);
        globalListener = subscribeConfig(true);
    }

    private Listener subscribeConfig(boolean globalConfig) {
        String group = pluginAdapter.getGroup();
        String serviceId = pluginAdapter.getServiceId();
        String dataId = globalConfig ? group : serviceId;
        SubscriptionType subscriptionType = getSubscriptionType(globalConfig);
        RuleType ruleType = getRuleType(globalConfig);
        String configType = getConfigType();

        LOG.info("Subscribe {} config from {} server, group={}, dataId={}", subscriptionType, configType, group, dataId);

        try {
            return nacosOperation.subscribeConfig(group, dataId, executorService, new NacosSubscribeCallback() {
                @Override
                public void callback(String config) {
                    if (StringUtils.isNotEmpty(config)) {
                        LOG.info("Get {} config updated event from {} server, group={}, dataId={}", subscriptionType, configType, group, dataId);

                        RuleEntity ruleEntity = pluginAdapter.getRule();
                        String rule = null;
                        if (ruleEntity != null) {
                            rule = ruleEntity.getContent();
                        }
                        if (!StringUtils.equals(rule, config)) {
                            fireRuleUpdated(new RuleUpdatedEvent(ruleType, config), true);
                        } else {
                            LOG.info("Updated {} config from {} server is same as current config, ignore to update, group={}, dataId={}", subscriptionType, configType, group, dataId);
                        }
                    } else {
                        LOG.info("Get {} config cleared event from {} server, group={}, dataId={}", subscriptionType, configType, group, dataId);

                        fireRuleCleared(new RuleClearedEvent(ruleType), true);
                    }
                }
            });
        } catch (Exception e) {
            LOG.error("Subscribe {} config from {} server failed, group={}, dataId={}", subscriptionType, configType, group, dataId, e);
        }

        return null;
    }

    @Override
    public void close() {
        unsubscribeConfig(partialListener, false);
        unsubscribeConfig(globalListener, true);

        executorService.shutdownNow();
    }

    private void unsubscribeConfig(Listener configListener, boolean globalConfig) {
        if (configListener == null) {
            return;
        }

        String group = pluginAdapter.getGroup();
        String serviceId = pluginAdapter.getServiceId();
        String dataId = globalConfig ? group : serviceId;
        SubscriptionType subscriptionType = getSubscriptionType(globalConfig);
        String configType = getConfigType();

        LOG.info("Unsubscribe {} config from {} server, group={}, dataId={}", subscriptionType, configType, group, dataId);

        nacosOperation.unsubscribeConfig(group, dataId, configListener);
    }

    @Override
    public String getConfigType() {
        return NacosConstant.NACOS_TYPE;
    }
}
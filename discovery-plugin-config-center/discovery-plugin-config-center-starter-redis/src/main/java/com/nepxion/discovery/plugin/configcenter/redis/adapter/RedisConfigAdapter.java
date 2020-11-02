package com.nepxion.discovery.plugin.configcenter.redis.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author JiKai Sun
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.RuleType;
import com.nepxion.discovery.common.redis.constant.RedisConstant;
import com.nepxion.discovery.common.redis.operation.RedisOperation;
import com.nepxion.discovery.common.redis.operation.RedisSubscribeCallback;
import com.nepxion.discovery.plugin.configcenter.adapter.ConfigAdapter;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.event.RuleClearedEvent;
import com.nepxion.discovery.plugin.framework.event.RuleUpdatedEvent;

public class RedisConfigAdapter extends ConfigAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(RedisConfigAdapter.class);

    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private RedisOperation redisOperation;

    @Autowired
    private RedisMessageListenerContainer configMessageListenerContainer;

    @Autowired
    private MessageListenerAdapter partialMessageListenerAdapter;

    @Autowired
    private MessageListenerAdapter globalMessageListenerAdapter;

    @Override
    public String[] getConfigList() throws Exception {
        String[] configList = new String[2];
        configList[0] = getConfig(false);
        configList[1] = getConfig(true);

        if (StringUtils.isNotEmpty(configList[0])) {
            LOG.info("Found {} config from {} server", getConfigScope(false), getConfigType());
        } else {
            LOG.info("No {} config is found from {} server", getConfigScope(false), getConfigType());
        }

        if (StringUtils.isNotEmpty(configList[1])) {
            LOG.info("Found {} config from {} server", getConfigScope(true), getConfigType());
        } else {
            LOG.info("No {} config is found from {} server", getConfigScope(true), getConfigType());
        }

        return configList;
    }

    private String getConfig(boolean globalConfig) throws Exception {
        String group = pluginAdapter.getGroup();
        String serviceId = pluginAdapter.getServiceId();
        String dataId = globalConfig ? group : serviceId;

        return redisOperation.getConfig(group, dataId);
    }

    public void subscribePartialConfig(String config) {
        subscribeConfig(config, false);
    }

    public void subscribeGlobalConfig(String config) {
        subscribeConfig(config, true);
    }

    private void subscribeConfig(String config, boolean globalConfig) {
        String group = pluginAdapter.getGroup();
        String serviceId = pluginAdapter.getServiceId();
        String dataId = globalConfig ? group : serviceId;
        RuleType ruleType = globalConfig ? RuleType.DYNAMIC_GLOBAL_RULE : RuleType.DYNAMIC_PARTIAL_RULE;

        try {
            redisOperation.subscribeConfig(config, new RedisSubscribeCallback() {
                @Override
                public void callback(String config) {
                    if (StringUtils.isNotEmpty(config)) {
                        LOG.info("Get {} config updated event from {} server, group={}, dataId={}", getConfigScope(globalConfig), getConfigType(), group, dataId);

                        RuleEntity ruleEntity = pluginAdapter.getRule();
                        String rule = null;
                        if (ruleEntity != null) {
                            rule = ruleEntity.getContent();
                        }
                        if (!StringUtils.equals(rule, config)) {
                            fireRuleUpdated(new RuleUpdatedEvent(ruleType, config), true);
                        } else {
                            LOG.info("Updated {} config from {} server is same as current config, ignore to update, group={}, dataId={}", getConfigScope(globalConfig), getConfigType(), group, dataId);
                        }
                    } else {
                        LOG.info("Get {} config cleared event from {} server, group={}, dataId={}", getConfigScope(globalConfig), getConfigType(), group, dataId);

                        fireRuleCleared(new RuleClearedEvent(ruleType), true);
                    }
                }
            });
        } catch (Exception e) {
            LOG.error("Subscribe {} config from {} server failed, group={}, dataId={}", getConfigScope(globalConfig), getConfigType(), group, dataId, e);
        }
    }

    @Override
    public void close() {
        unsubscribeConfig(partialMessageListenerAdapter, false);
        unsubscribeConfig(globalMessageListenerAdapter, true);
    }

    private void unsubscribeConfig(MessageListenerAdapter messageListenerAdapter, boolean globalConfig) {
        String group = pluginAdapter.getGroup();
        String serviceId = pluginAdapter.getServiceId();
        String dataId = globalConfig ? group : serviceId;

        LOG.info("Unsubscribe {} config from {} server, group={}, dataId={}", getConfigScope(globalConfig), getConfigType(), group, dataId);

        configMessageListenerContainer.removeMessageListener(messageListenerAdapter, new PatternTopic(group + "-" + dataId));
    }

    public String getConfigScope(boolean globalConfig) {
        return globalConfig ? DiscoveryConstant.GLOBAL : DiscoveryConstant.PARTIAL;
    }

    @Override
    public String getConfigType() {
        return RedisConstant.REDIS_TYPE;
    }
}
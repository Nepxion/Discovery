package com.nepxion.discovery.plugin.configcenter.extension.redis.adapter;

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

import com.nepxion.discovery.common.redis.operation.RedisOperation;
import com.nepxion.discovery.common.redis.operation.RedisSubscribeCallback;
import com.nepxion.discovery.plugin.configcenter.ConfigAdapter;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.entity.RuleEntity;
import com.nepxion.discovery.plugin.framework.event.RuleClearedEvent;
import com.nepxion.discovery.plugin.framework.event.RuleUpdatedEvent;

public class RedisConfigAdapter extends ConfigAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(RedisConfigAdapter.class);

    @Autowired
    protected PluginContextAware pluginContextAware;

    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private RedisOperation redisOperation;

    @Override
    public String getConfig() throws Exception {
        String groupKey = pluginContextAware.getGroupKey();
        String group = pluginAdapter.getGroup();
        String serviceId = pluginAdapter.getServiceId();

        LOG.info("Get config from Redis server, {}={}, serviceId={}", groupKey, group, serviceId);

        return redisOperation.getConfig(group, serviceId);
    }

    public void subscribeConfig(String config) {
        String groupKey = pluginContextAware.getGroupKey();
        String group = pluginAdapter.getGroup();
        String serviceId = pluginAdapter.getServiceId();

        LOG.info("Subscribe config from Redis server, {}={}, serviceId={}", groupKey, group, serviceId);

        try {
            redisOperation.subscribeConfig(config, new RedisSubscribeCallback() {
                @Override
                public void callback(String config) {
                    if (StringUtils.isNotEmpty(config)) {
                        LOG.info("Get config updated event from Redis server, {}={}, serviceId={}", groupKey, group, serviceId);

                        RuleEntity ruleEntity = pluginAdapter.getRule();
                        String rule = ruleEntity.getContent();
                        if (!StringUtils.equals(rule, config)) {
                            fireRuleUpdated(new RuleUpdatedEvent(config), true);
                        } else {
                            LOG.info("Retrieved config is same as current config, ignore to update, {}={}, serviceId={}", groupKey, group, serviceId);
                        }
                    } else {
                        LOG.info("Get config cleared event from Redis server, {}={}, serviceId={}", groupKey, group, serviceId);

                        fireRuleCleared(new RuleClearedEvent(), true);
                    }
                }
            });
        } catch (Exception e) {
            LOG.error("Subscribe config failed", e);
        }
    }
}
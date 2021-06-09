package com.nepxion.discovery.plugin.configcenter.redis.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author JiKai Sun
 * @author Ning Zhang
 * @version 1.0
 */

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.nepxion.discovery.common.entity.ConfigType;
import com.nepxion.discovery.common.redis.operation.RedisOperation;
import com.nepxion.discovery.common.redis.operation.RedisSubscribeCallback;
import com.nepxion.discovery.plugin.configcenter.adapter.ConfigAdapter;

public class RedisConfigAdapter extends ConfigAdapter {
    @Autowired
    private RedisOperation redisOperation;

    private MessageListenerAdapter partialMessageListenerAdapter;
    private MessageListenerAdapter globalMessageListenerAdapter;

    @Override
    public String getConfig(String group, String dataId) throws Exception {
        return redisOperation.getConfig(group, dataId);
    }

    @PostConstruct
    @Override
    public void subscribeConfig() {
        String group = getGroup();

        logSubscribeStarted(false);

        try {
            partialMessageListenerAdapter = redisOperation.subscribeConfig(group, getDataId(false), this, "subscribePartialConfig");
        } catch (Exception e) {
            logSubscribeFailed(e, false);
        }

        logSubscribeStarted(true);

        try {
            globalMessageListenerAdapter = redisOperation.subscribeConfig(group, getDataId(true), this, "subscribeGlobalConfig");
        } catch (Exception e) {
            logSubscribeFailed(e, true);
        }
    }

    public void subscribePartialConfig(String config) {
        subscribeConfig(config, false);
    }

    public void subscribeGlobalConfig(String config) {
        subscribeConfig(config, true);
    }

    private void subscribeConfig(String config, boolean globalConfig) {
        redisOperation.subscribeConfig(config, new RedisSubscribeCallback() {
            @Override
            public void callback(String config) {
                callbackConfig(config, globalConfig);
            }
        });
    }

    @Override
    public void unsubscribeConfig() {
        unsubscribeConfig(partialMessageListenerAdapter, false);
        unsubscribeConfig(globalMessageListenerAdapter, true);
    }

    private void unsubscribeConfig(MessageListenerAdapter messageListenerAdapter, boolean globalConfig) {
        if (messageListenerAdapter == null) {
            return;
        }

        String group = getGroup();
        String dataId = getDataId(globalConfig);

        logUnsubscribeStarted(globalConfig);

        try {
            redisOperation.unsubscribeConfig(group, dataId, messageListenerAdapter);
        } catch (Exception e) {
            logUnsubscribeFailed(e, globalConfig);
        }
    }

    @Override
    public ConfigType getConfigType() {
        return ConfigType.REDIS;
    }
}
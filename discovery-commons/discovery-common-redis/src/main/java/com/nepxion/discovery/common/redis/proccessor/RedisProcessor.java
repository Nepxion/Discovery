package com.nepxion.discovery.common.redis.proccessor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.nepxion.discovery.common.entity.ConfigType;
import com.nepxion.discovery.common.processor.DiscoveryConfigProcessor;
import com.nepxion.discovery.common.redis.operation.RedisOperation;
import com.nepxion.discovery.common.redis.operation.RedisSubscribeCallback;

public abstract class RedisProcessor extends DiscoveryConfigProcessor {
    @Autowired
    private RedisOperation redisOperation;

    private MessageListenerAdapter messageListenerAdapter;

    @PostConstruct
    public void initialize() {
        beforeInitialization();

        String group = getGroup();
        String dataId = getDataId();

        logSubscribeStarted();

        try {
            messageListenerAdapter = redisOperation.subscribeConfig(group, dataId, this, "subscribeConfig");
        } catch (Exception e) {
            logSubscribeFailed(e);
        }

        logGetStarted();

        try {
            String config = redisOperation.getConfig(group, dataId);
            if (config != null) {
                callbackConfig(config);
            } else {
                logNotFound();
            }
        } catch (Exception e) {
            logGetFailed(e);
        }

        afterInitialization();
    }

    public void subscribeConfig(String config) {
        redisOperation.subscribeConfig(config, new RedisSubscribeCallback() {
            @Override
            public void callback(String config) {
                try {
                    callbackConfig(config);
                } catch (Exception e) {
                    logCallbackFailed(e);
                }
            }
        });
    }

    @Override
    public void destroy() {
        if (messageListenerAdapter == null) {
            return;
        }

        String group = getGroup();
        String dataId = getDataId();

        logUnsubscribeStarted();

        try {
            redisOperation.unsubscribeConfig(group, dataId, messageListenerAdapter);
        } catch (Exception e) {
            logUnsubscribeFailed(e);
        }
    }

    @Override
    public ConfigType getConfigType() {
        return ConfigType.REDIS;
    }
}
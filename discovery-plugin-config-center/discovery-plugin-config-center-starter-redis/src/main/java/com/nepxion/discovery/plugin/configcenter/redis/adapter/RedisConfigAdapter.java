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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.nepxion.discovery.common.redis.constant.RedisConstant;
import com.nepxion.discovery.common.redis.operation.RedisOperation;
import com.nepxion.discovery.common.redis.operation.RedisSubscribeCallback;
import com.nepxion.discovery.plugin.configcenter.adapter.ConfigAdapter;
import com.nepxion.discovery.plugin.configcenter.logger.ConfigLogger;

public class RedisConfigAdapter extends ConfigAdapter {
    @Autowired
    private RedisOperation redisOperation;

    @Autowired
    private RedisMessageListenerContainer configMessageListenerContainer;

    @Autowired
    private MessageListenerAdapter partialMessageListenerAdapter;

    @Autowired
    private MessageListenerAdapter globalMessageListenerAdapter;

    @Autowired
    private ConfigLogger configLogger;

    @Override
    public String getConfig(String group, String dataId) throws Exception {
        return redisOperation.getConfig(group, dataId);
    }

    @Override
    public void subscribeConfig() {
        // No need to implement, that is resolved in RedisConfigAutoConfiguration
    }

    public void subscribePartialConfig(String config) {
        subscribeConfig(config, false);
    }

    public void subscribeGlobalConfig(String config) {
        subscribeConfig(config, true);
    }

    private void subscribeConfig(String config, boolean globalConfig) {
        try {
            redisOperation.subscribeConfig(config, new RedisSubscribeCallback() {
                @Override
                public void callback(String config) {
                    callbackConfig(config, globalConfig);
                }
            });
        } catch (Exception e) {
            configLogger.logSubscribeFailed(e, globalConfig);
        }
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

        configLogger.logUnsubscribeStarted(globalConfig);

        try {
            configMessageListenerContainer.removeMessageListener(messageListenerAdapter, new PatternTopic(group + "-" + dataId));
        } catch (Exception e) {
            configLogger.logUnsubscribeFailed(e, globalConfig);
        }
    }

    @Override
    public String getConfigType() {
        return RedisConstant.REDIS_TYPE;
    }

    @Override
    public boolean isConfigSingleKey() {
        return false;
    }
}
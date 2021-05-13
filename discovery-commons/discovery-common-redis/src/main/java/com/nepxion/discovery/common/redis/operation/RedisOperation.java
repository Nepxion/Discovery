package com.nepxion.discovery.common.redis.operation;

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
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

public class RedisOperation implements DisposableBean {
    private static final Logger LOG = LoggerFactory.getLogger(RedisOperation.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private HashOperations<String, String, String> hashOperations;

    @Autowired
    private RedisMessageListenerContainer configMessageListenerContainer;

    public String getConfig(String group, String serviceId) {
        return hashOperations.get(group, serviceId);
    }

    public boolean removeConfig(String group, String serviceId) {
        publishConfig(group, serviceId, StringUtils.EMPTY);

        return hashOperations.delete(group, serviceId) == 1;
    }

    public boolean publishConfig(String group, String serviceId, String config) {
        hashOperations.put(group, serviceId, config);
        stringRedisTemplate.convertAndSend(group + "-" + serviceId, config);

        return true;
    }

    public MessageListenerAdapter subscribeConfig(String group, String serviceId, Object delegate, String listenerMethod) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(delegate, listenerMethod);
        messageListenerAdapter.afterPropertiesSet();

        configMessageListenerContainer.addMessageListener(messageListenerAdapter, new PatternTopic(group + "-" + serviceId));

        return messageListenerAdapter;
    }

    public void unsubscribeConfig(String group, String serviceId, MessageListenerAdapter messageListenerAdapter) {
        configMessageListenerContainer.removeMessageListener(messageListenerAdapter, new PatternTopic(group + "-" + serviceId));
    }

    public void subscribeConfig(String config, RedisSubscribeCallback redisSubscribeCallback) {
        redisSubscribeCallback.callback(config);
    }

    @Override
    public void destroy() throws Exception {
        configMessageListenerContainer.destroy();

        LOG.info("Shutting down Redis message listener...");
    }
}
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.nepxion.discovery.common.redis.constant.RedisConstant;
import com.nepxion.discovery.common.redis.operation.RedisOperation;
import com.nepxion.discovery.common.redis.operation.RedisSubscribeCallback;

public abstract class RedisProcessor implements DisposableBean {
    private static final Logger LOG = LoggerFactory.getLogger(RedisProcessor.class);

    @Autowired
    private RedisOperation redisOperation;

    private MessageListenerAdapter messageListenerAdapter;

    @PostConstruct
    public void initialize() {
        beforeInitialization();

        String group = getGroup();
        String dataId = getDataId();
        String description = getDescription();
        String configType = getConfigType();

        LOG.info("Get {} config from {} server, group={}, dataId={}", description, configType, group, dataId);

        try {
            String config = redisOperation.getConfig(group, dataId);

            callbackConfig(config);
        } catch (Exception e) {
            LOG.info("Get {} config from {} server failed, group={}, dataId={}", description, configType, group, dataId, e);
        }

        LOG.info("Subscribe {} config from {} server, group={}, dataId={}", description, configType, group, dataId);

        try {
            messageListenerAdapter = redisOperation.subscribeConfig(group, dataId, this, "subscribeConfig");
        } catch (Exception e) {
            LOG.error("Subscribe {} config from {} server failed, group={}, dataId={}", description, configType, group, dataId, e);
        }

        afterInitialization();
    }

    public void subscribeConfig(String config) {
        String description = getDescription();

        redisOperation.subscribeConfig(config, new RedisSubscribeCallback() {
            @Override
            public void callback(String config) {
                try {
                    callbackConfig(config);
                } catch (Exception e) {
                    LOG.error("Callback {} config failed", description, e);
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
        String description = getDescription();
        String configType = getConfigType();

        LOG.info("Unsubscribe {} config from {} server, group={}, dataId={}", description, configType, group, dataId);

        try {
            redisOperation.unsubscribeConfig(group, dataId, messageListenerAdapter);
        } catch (Exception e) {
            LOG.error("Unsubscribe {} config from {} server failed, group={}, dataId={}", description, configType, group, dataId, e);
        }
    }

    public String getConfigType() {
        return RedisConstant.REDIS_TYPE;
    }

    public void beforeInitialization() {

    }

    public void afterInitialization() {

    }

    public abstract String getGroup();

    public abstract String getDataId();

    public abstract String getDescription();

    public abstract void callbackConfig(String config);
}
package com.nepxion.discovery.common.apollo.proccessor;

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

import com.ctrip.framework.apollo.ConfigChangeListener;
import com.nepxion.discovery.common.apollo.operation.ApolloOperation;
import com.nepxion.discovery.common.apollo.operation.ApolloSubscribeCallback;

public abstract class ApolloProcessor implements DisposableBean {
    private static final Logger LOG = LoggerFactory.getLogger(ApolloProcessor.class);

    @Autowired
    private ApolloOperation apolloOperation;

    private ConfigChangeListener configListener;

    @PostConstruct
    public void initialize() {
        String key = getKey();
        String configType = getConfigType();

        LOG.info("Get {} config from Apollo server, key={}", configType, key);

        try {
            String config = apolloOperation.getConfig(key);

            callbackConfig(config);
        } catch (Exception e) {
            LOG.info("Get {} config from Apollo server failed, key={}", configType, key, e);
        }

        LOG.info("Subscribe {} config from Apollo server, key={}", configType, key);

        try {
            configListener = apolloOperation.subscribeConfig(key, new ApolloSubscribeCallback() {
                @Override
                public void callback(String config) {
                    try {
                        callbackConfig(config);
                    } catch (Exception e) {
                        LOG.error("Callback {} config failed", configType, e);
                    }
                }
            });
        } catch (Exception e) {
            LOG.error("Subscribe {} config from Apollo server failed, key={}", configType, key, e);
        }
    }

    @Override
    public void destroy() {
        if (configListener == null) {
            return;
        }

        String key = getKey();
        String configType = getConfigType();

        LOG.info("Unsubscribe {} config from Apollo server, key={}", configType, key);

        try {
            apolloOperation.unsubscribeConfig(key, configListener);
        } catch (Exception e) {
            LOG.error("Unsubscribe {} config from Apollo server failed, key={}", configType, key, e);
        }
    }

    public abstract String getKey();

    public abstract String getConfigType();

    public abstract void callbackConfig(String config);
}
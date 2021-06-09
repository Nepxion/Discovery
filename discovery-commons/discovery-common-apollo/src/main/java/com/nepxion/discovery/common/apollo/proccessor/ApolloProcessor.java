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

import org.springframework.beans.factory.annotation.Autowired;

import com.ctrip.framework.apollo.ConfigChangeListener;
import com.nepxion.discovery.common.apollo.operation.ApolloOperation;
import com.nepxion.discovery.common.apollo.operation.ApolloSubscribeCallback;
import com.nepxion.discovery.common.entity.ConfigType;
import com.nepxion.discovery.common.processor.DiscoveryConfigProcessor;

public abstract class ApolloProcessor extends DiscoveryConfigProcessor {
    @Autowired
    private ApolloOperation apolloOperation;

    private ConfigChangeListener configChangeListener;

    @PostConstruct
    public void initialize() {
        beforeInitialization();

        String group = getGroup();
        String dataId = getDataId();

        logSubscribeStarted();

        try {
            configChangeListener = apolloOperation.subscribeConfig(group, dataId, new ApolloSubscribeCallback() {
                @Override
                public void callback(String config) {
                    try {
                        callbackConfig(config);
                    } catch (Exception e) {
                        logCallbackFailed(e);
                    }
                }
            });
        } catch (Exception e) {
            logSubscribeFailed(e);
        }

        logGetStarted();

        try {
            String config = apolloOperation.getConfig(group, dataId);
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

    @Override
    public void destroy() {
        if (configChangeListener == null) {
            return;
        }

        String group = getGroup();
        String dataId = getDataId();

        logUnsubscribeStarted();

        try {
            apolloOperation.unsubscribeConfig(group, dataId, configChangeListener);
        } catch (Exception e) {
            logUnsubscribeFailed(e);
        }
    }

    @Override
    public ConfigType getConfigType() {
        return ConfigType.APOLLO;
    }
}
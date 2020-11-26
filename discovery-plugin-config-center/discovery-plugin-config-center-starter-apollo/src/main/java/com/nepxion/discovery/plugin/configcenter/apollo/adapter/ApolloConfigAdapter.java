package com.nepxion.discovery.plugin.configcenter.apollo.adapter;

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
import com.nepxion.discovery.common.apollo.constant.ApolloConstant;
import com.nepxion.discovery.common.apollo.operation.ApolloOperation;
import com.nepxion.discovery.common.apollo.operation.ApolloSubscribeCallback;
import com.nepxion.discovery.plugin.configcenter.adapter.ConfigAdapter;
import com.nepxion.discovery.plugin.configcenter.logger.ConfigLogger;

public class ApolloConfigAdapter extends ConfigAdapter {
    @Autowired
    private ApolloOperation apolloOperation;

    @Autowired
    private ConfigLogger configLogger;

    private ConfigChangeListener partialListener;
    private ConfigChangeListener globalListener;

    @Override
    public String getConfig(String group, String dataId) throws Exception {
        return apolloOperation.getConfig(group, dataId);
    }

    @PostConstruct
    @Override
    public void subscribeConfig() {
        partialListener = subscribeConfig(false);
        globalListener = subscribeConfig(true);
    }

    private ConfigChangeListener subscribeConfig(boolean globalConfig) {
        String group = getGroup();
        String dataId = getDataId(globalConfig);

        configLogger.logSubscribeStarted(globalConfig);

        try {
            return apolloOperation.subscribeConfig(group, dataId, new ApolloSubscribeCallback() {
                @Override
                public void callback(String config) {
                    callbackConfig(config, globalConfig);
                }
            });
        } catch (Exception e) {
            configLogger.logSubscribeFailed(e, globalConfig);
        }

        return null;
    }

    @Override
    public void unsubscribeConfig() {
        unsubscribeConfig(partialListener, false);
        unsubscribeConfig(globalListener, true);
    }

    private void unsubscribeConfig(ConfigChangeListener configListener, boolean globalConfig) {
        if (configListener == null) {
            return;
        }

        String group = getGroup();
        String dataId = getDataId(globalConfig);

        configLogger.logUnsubscribeStarted(globalConfig);

        try {
            apolloOperation.unsubscribeConfig(group, dataId, configListener);
        } catch (Exception e) {
            configLogger.logUnsubscribeFailed(e, globalConfig);
        }
    }

    @Override
    public String getConfigType() {
        return ApolloConstant.APOLLO_TYPE;
    }

    @Override
    public boolean isConfigSingleKey() {
        return true;
    }
}
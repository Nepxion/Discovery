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
import com.nepxion.discovery.common.apollo.operation.ApolloOperation;
import com.nepxion.discovery.common.apollo.operation.ApolloSubscribeCallback;
import com.nepxion.discovery.common.entity.ConfigType;
import com.nepxion.discovery.plugin.configcenter.adapter.ConfigAdapter;

public class ApolloConfigAdapter extends ConfigAdapter {
    @Autowired
    private ApolloOperation apolloOperation;

    private ConfigChangeListener partialConfigChangeListener;
    private ConfigChangeListener globalConfigChangeListener;

    @Override
    public String getConfig(String group, String dataId) throws Exception {
        return apolloOperation.getConfig(group, dataId);
    }

    @PostConstruct
    @Override
    public void subscribeConfig() {
        partialConfigChangeListener = subscribeConfig(false);
        globalConfigChangeListener = subscribeConfig(true);
    }

    private ConfigChangeListener subscribeConfig(boolean globalConfig) {
        String group = getGroup();
        String dataId = getDataId(globalConfig);

        logSubscribeStarted(globalConfig);

        try {
            return apolloOperation.subscribeConfig(group, dataId, new ApolloSubscribeCallback() {
                @Override
                public void callback(String config) {
                    callbackConfig(config, globalConfig);
                }
            });
        } catch (Exception e) {
            logSubscribeFailed(e, globalConfig);
        }

        return null;
    }

    @Override
    public void unsubscribeConfig() {
        unsubscribeConfig(partialConfigChangeListener, false);
        unsubscribeConfig(globalConfigChangeListener, true);
    }

    private void unsubscribeConfig(ConfigChangeListener configChangeListener, boolean globalConfig) {
        if (configChangeListener == null) {
            return;
        }

        String group = getGroup();
        String dataId = getDataId(globalConfig);

        logUnsubscribeStarted(globalConfig);

        try {
            apolloOperation.unsubscribeConfig(group, dataId, configChangeListener);
        } catch (Exception e) {
            logUnsubscribeFailed(e, globalConfig);
        }
    }

    @Override
    public ConfigType getConfigType() {
        return ConfigType.APOLLO;
    }
}
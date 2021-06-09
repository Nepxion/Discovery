package com.nepxion.discovery.plugin.configcenter.etcd.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Congwei Xu
 * @version 1.0
 */

import io.etcd.jetcd.Watch;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.entity.ConfigType;
import com.nepxion.discovery.common.etcd.operation.EtcdOperation;
import com.nepxion.discovery.common.etcd.operation.EtcdSubscribeCallback;
import com.nepxion.discovery.plugin.configcenter.adapter.ConfigAdapter;

public class EtcdConfigAdapter extends ConfigAdapter {
    @Autowired
    private EtcdOperation etcdOperation;

    private Watch partialWatchClient;
    private Watch globalWatchClient;

    @Override
    public String getConfig(String group, String dataId) throws Exception {
        return etcdOperation.getConfig(group, dataId);
    }

    @PostConstruct
    @Override
    public void subscribeConfig() {
        partialWatchClient = subscribeConfig(false);
        globalWatchClient = subscribeConfig(true);
    }

    private Watch subscribeConfig(boolean globalConfig) {
        String group = getGroup();
        String dataId = getDataId(globalConfig);

        logSubscribeStarted(globalConfig);

        try {
            return etcdOperation.subscribeConfig(group, dataId, new EtcdSubscribeCallback() {
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
        unsubscribeConfig(partialWatchClient, false);
        unsubscribeConfig(globalWatchClient, true);
    }

    private void unsubscribeConfig(Watch watchClient, boolean globalConfig) {
        if (watchClient == null) {
            return;
        }

        String group = getGroup();
        String dataId = getDataId(globalConfig);

        logUnsubscribeStarted(globalConfig);

        try {
            etcdOperation.unsubscribeConfig(group, dataId, watchClient);
        } catch (Exception e) {
            logUnsubscribeFailed(e, globalConfig);
        }
    }

    @Override
    public ConfigType getConfigType() {
        return ConfigType.ETCD;
    }
}
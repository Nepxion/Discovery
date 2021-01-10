package com.nepxion.discovery.plugin.configcenter.etcd.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Congwei Xu
 * @version 1.0
 */

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.etcd.constant.EtcdConstant;
import com.nepxion.discovery.common.etcd.operation.EtcdOperation;
import com.nepxion.discovery.common.etcd.operation.EtcdSubscribeCallback;
import com.nepxion.discovery.plugin.configcenter.adapter.ConfigAdapter;
import com.nepxion.discovery.plugin.configcenter.logger.ConfigLogger;

import io.etcd.jetcd.Watch;

public class EtcdConfigAdapter extends ConfigAdapter {
    @Autowired
    private EtcdOperation etcdOperation;

    @Autowired
    private ConfigLogger configLogger;

    private Watch.Listener partialListener;
    private Watch.Listener globalListener;

    @Override
    public String getConfig(String group, String dataId) throws Exception {
        return etcdOperation.getConfig(group, dataId);
    }

    @PostConstruct
    @Override
    public void subscribeConfig() {
        partialListener = subscribeConfig(false);
        globalListener = subscribeConfig(true);
    }

    private Watch.Listener subscribeConfig(boolean globalConfig) {
        String group = getGroup();
        String dataId = getDataId(globalConfig);

        configLogger.logSubscribeStarted(globalConfig);

        try {
            return etcdOperation.subscribeConfig(group, dataId, new EtcdSubscribeCallback() {
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

    private void unsubscribeConfig(Watch.Listener configListener, boolean globalConfig) {
        if (configListener == null) {
            return;
        }

        String group = getGroup();
        String dataId = getDataId(globalConfig);

        configLogger.logUnsubscribeStarted(globalConfig);

        try {
            etcdOperation.unsubscribeConfig(group, dataId, configListener);
        } catch (Exception e) {
            configLogger.logUnsubscribeFailed(e, globalConfig);
        }
    }

    @Override
    public String getConfigType() {
        return EtcdConstant.ETCD_TYPE;
    }

    @Override
    public boolean isConfigSingleKey() {
        return false;
    }
}
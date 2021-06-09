package com.nepxion.discovery.common.etcd.proccessor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import io.etcd.jetcd.Watch;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.entity.ConfigType;
import com.nepxion.discovery.common.etcd.operation.EtcdOperation;
import com.nepxion.discovery.common.etcd.operation.EtcdSubscribeCallback;
import com.nepxion.discovery.common.processor.DiscoveryConfigProcessor;

public abstract class EtcdProcessor extends DiscoveryConfigProcessor {
    @Autowired
    private EtcdOperation etcdOperation;

    private Watch watchClient;

    @PostConstruct
    public void initialize() {
        beforeInitialization();

        String group = getGroup();
        String dataId = getDataId();

        logSubscribeStarted();

        try {
            watchClient = etcdOperation.subscribeConfig(group, dataId, new EtcdSubscribeCallback() {
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
            String config = etcdOperation.getConfig(group, dataId);
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
        if (watchClient == null) {
            return;
        }

        String group = getGroup();
        String dataId = getDataId();

        logUnsubscribeStarted();

        try {
            etcdOperation.unsubscribeConfig(group, dataId, watchClient);
        } catch (Exception e) {
            logUnsubscribeFailed(e);
        }
    }

    @Override
    public ConfigType getConfigType() {
        return ConfigType.ETCD;
    }
}
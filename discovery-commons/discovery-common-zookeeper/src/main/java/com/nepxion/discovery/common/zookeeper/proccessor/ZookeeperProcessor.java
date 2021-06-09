package com.nepxion.discovery.common.zookeeper.proccessor;

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

import com.nepxion.discovery.common.entity.ConfigType;
import com.nepxion.discovery.common.processor.DiscoveryConfigProcessor;
import com.nepxion.discovery.common.zookeeper.operation.ZookeeperListener;
import com.nepxion.discovery.common.zookeeper.operation.ZookeeperOperation;
import com.nepxion.discovery.common.zookeeper.operation.ZookeeperSubscribeCallback;

public abstract class ZookeeperProcessor extends DiscoveryConfigProcessor {
    @Autowired
    private ZookeeperOperation zookeeperOperation;

    private ZookeeperListener zookeeperListener;

    @PostConstruct
    public void initialize() {
        beforeInitialization();

        String group = getGroup();
        String dataId = getDataId();

        logSubscribeStarted();

        try {
            zookeeperListener = zookeeperOperation.subscribeConfig(group, dataId, new ZookeeperSubscribeCallback() {
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
            String config = zookeeperOperation.getConfig(group, dataId);
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
        if (zookeeperListener == null) {
            return;
        }

        String group = getGroup();
        String dataId = getDataId();

        logUnsubscribeStarted();

        try {
            zookeeperOperation.unsubscribeConfig(group, dataId, zookeeperListener);
        } catch (Exception e) {
            logUnsubscribeFailed(e);
        }
    }

    @Override
    public ConfigType getConfigType() {
        return ConfigType.ZOOKEEPER;
    }
}
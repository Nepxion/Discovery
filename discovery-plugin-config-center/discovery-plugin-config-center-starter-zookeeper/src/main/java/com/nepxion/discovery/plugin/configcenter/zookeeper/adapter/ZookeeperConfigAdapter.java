package com.nepxion.discovery.plugin.configcenter.zookeeper.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author rotten
 * @author Ning Zhang
 * @version 1.0
 */

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.zookeeper.constant.ZookeeperConstant;
import com.nepxion.discovery.common.zookeeper.operation.ZookeeperListener;
import com.nepxion.discovery.common.zookeeper.operation.ZookeeperOperation;
import com.nepxion.discovery.common.zookeeper.operation.ZookeeperSubscribeCallback;
import com.nepxion.discovery.plugin.configcenter.adapter.ConfigAdapter;
import com.nepxion.discovery.plugin.configcenter.logger.ConfigLogger;

public class ZookeeperConfigAdapter extends ConfigAdapter {
    @Autowired
    private ZookeeperOperation zookeeperOperation;

    @Autowired
    private ConfigLogger configLogger;

    private ZookeeperListener partialZookeeperListener;
    private ZookeeperListener globalZookeeperListener;

    @Override
    public String getConfig(String group, String dataId) throws Exception {
        return zookeeperOperation.getConfig(group, dataId);
    }

    @PostConstruct
    @Override
    public void subscribeConfig() {
        partialZookeeperListener = subscribeConfig(false);
        globalZookeeperListener = subscribeConfig(true);
    }

    private ZookeeperListener subscribeConfig(boolean globalConfig) {
        String group = getGroup();
        String dataId = getDataId(globalConfig);

        configLogger.logSubscribeStarted(globalConfig);

        try {
            return zookeeperOperation.subscribeConfig(group, dataId, new ZookeeperSubscribeCallback() {
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
        unsubscribeConfig(partialZookeeperListener, false);
        unsubscribeConfig(globalZookeeperListener, true);
    }

    private void unsubscribeConfig(ZookeeperListener zookeeperListener, boolean globalConfig) {
        if (zookeeperListener == null) {
            return;
        }

        String group = getGroup();
        String dataId = getDataId(globalConfig);

        configLogger.logUnsubscribeStarted(globalConfig);

        try {
            zookeeperOperation.unsubscribeConfig(group, dataId, zookeeperListener);
        } catch (Exception e) {
            configLogger.logUnsubscribeFailed(e, globalConfig);
        }
    }

    @Override
    public String getConfigType() {
        return ZookeeperConstant.ZOOKEEPER_TYPE;
    }

    @Override
    public boolean isConfigSingleKey() {
        return false;
    }
}
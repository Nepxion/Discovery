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

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.logger.ProcessorLogger;
import com.nepxion.discovery.common.zookeeper.constant.ZookeeperConstant;
import com.nepxion.discovery.common.zookeeper.operation.ZookeeperListener;
import com.nepxion.discovery.common.zookeeper.operation.ZookeeperOperation;
import com.nepxion.discovery.common.zookeeper.operation.ZookeeperSubscribeCallback;

public abstract class ZookeeperProcessor implements DisposableBean {
    @Autowired
    private ZookeeperOperation zookeeperOperation;

    private ZookeeperListener zookeeperListener;

    @PostConstruct
    public void initialize() {
        beforeInitialization();

        String group = getGroup();
        String dataId = getDataId();
        String description = getDescription();
        String configType = getConfigType();
        boolean isConfigSingleKey = isConfigSingleKey();

        ProcessorLogger.logSubscribeStarted(group, dataId, description, configType, isConfigSingleKey);

        try {
            zookeeperListener = zookeeperOperation.subscribeConfig(group, dataId, new ZookeeperSubscribeCallback() {
                @Override
                public void callback(String config) {
                    try {
                        callbackConfig(config);
                    } catch (Exception e) {
                        ProcessorLogger.logCallbackFailed(description, e);
                    }
                }
            });
        } catch (Exception e) {
            ProcessorLogger.logSubscribeFailed(group, dataId, description, configType, isConfigSingleKey, e);
        }

        ProcessorLogger.logGetStarted(group, dataId, description, configType, isConfigSingleKey);

        try {
            String config = zookeeperOperation.getConfig(group, dataId);

            callbackConfig(config);
        } catch (Exception e) {
            ProcessorLogger.logGetFailed(group, dataId, description, configType, isConfigSingleKey, e);
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
        String description = getDescription();
        String configType = getConfigType();
        boolean isConfigSingleKey = isConfigSingleKey();

        ProcessorLogger.logUnsubscribeStarted(group, dataId, description, configType, isConfigSingleKey);

        try {
            zookeeperOperation.unsubscribeConfig(group, dataId, zookeeperListener);
        } catch (Exception e) {
            ProcessorLogger.logUnsubscribeFailed(group, dataId, description, configType, isConfigSingleKey, e);
        }
    }

    public String getConfigType() {
        return ZookeeperConstant.ZOOKEEPER_TYPE;
    }

    public boolean isConfigSingleKey() {
        return false;
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
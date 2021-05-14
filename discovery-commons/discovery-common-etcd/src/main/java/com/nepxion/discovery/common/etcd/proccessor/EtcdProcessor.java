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

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.etcd.constant.EtcdConstant;
import com.nepxion.discovery.common.etcd.operation.EtcdOperation;
import com.nepxion.discovery.common.etcd.operation.EtcdSubscribeCallback;
import com.nepxion.discovery.common.logger.ProcessorLogger;

public abstract class EtcdProcessor implements DisposableBean {
    @Autowired
    private EtcdOperation etcdOperation;

    private Watch watchClient;

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
            watchClient = etcdOperation.subscribeConfig(group, dataId, new EtcdSubscribeCallback() {
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
            String config = etcdOperation.getConfig(group, dataId);

            callbackConfig(config);
        } catch (Exception e) {
            ProcessorLogger.logGetFailed(group, dataId, description, configType, isConfigSingleKey, e);
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
        String description = getDescription();
        String configType = getConfigType();
        boolean isConfigSingleKey = isConfigSingleKey();

        ProcessorLogger.logUnsubscribeStarted(group, dataId, description, configType, isConfigSingleKey);

        try {
            etcdOperation.unsubscribeConfig(group, dataId, watchClient);
        } catch (Exception e) {
            ProcessorLogger.logUnsubscribeFailed(group, dataId, description, configType, isConfigSingleKey, e);
        }
    }

    public String getConfigType() {
        return EtcdConstant.ETCD_TYPE;
    }

    public boolean isConfigSingleKey() {
        return true;
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
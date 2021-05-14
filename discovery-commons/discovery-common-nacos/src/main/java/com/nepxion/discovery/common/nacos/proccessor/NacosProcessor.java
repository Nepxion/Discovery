package com.nepxion.discovery.common.nacos.proccessor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.concurrent.ExecutorService;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.nacos.api.config.listener.Listener;
import com.nepxion.discovery.common.logger.ProcessorLogger;
import com.nepxion.discovery.common.nacos.constant.NacosConstant;
import com.nepxion.discovery.common.nacos.operation.NacosOperation;
import com.nepxion.discovery.common.nacos.operation.NacosSubscribeCallback;
import com.nepxion.discovery.common.thread.DiscoveryThreadPoolFactory;

public abstract class NacosProcessor implements DisposableBean {
    private ExecutorService executorService = DiscoveryThreadPoolFactory.getExecutorService("nacos-config");

    @Autowired
    private NacosOperation nacosOperation;

    private Listener listener;

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
            listener = nacosOperation.subscribeConfig(group, dataId, executorService, new NacosSubscribeCallback() {
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
            String config = nacosOperation.getConfig(group, dataId);

            callbackConfig(config);
        } catch (Exception e) {
            ProcessorLogger.logGetFailed(group, dataId, description, configType, isConfigSingleKey, e);
        }

        afterInitialization();
    }

    @Override
    public void destroy() {
        if (listener == null) {
            return;
        }

        String group = getGroup();
        String dataId = getDataId();
        String description = getDescription();
        String configType = getConfigType();
        boolean isConfigSingleKey = isConfigSingleKey();

        ProcessorLogger.logUnsubscribeStarted(group, dataId, description, configType, isConfigSingleKey);

        try {
            nacosOperation.unsubscribeConfig(group, dataId, listener);
        } catch (Exception e) {
            ProcessorLogger.logUnsubscribeFailed(group, dataId, description, configType, isConfigSingleKey, e);
        }

        executorService.shutdownNow();
    }

    public String getConfigType() {
        return NacosConstant.NACOS_TYPE;
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
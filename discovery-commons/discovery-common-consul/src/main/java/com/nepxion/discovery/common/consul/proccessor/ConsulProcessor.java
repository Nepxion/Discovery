package com.nepxion.discovery.common.consul.proccessor;

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

import com.nepxion.discovery.common.consul.constant.ConsulConstant;
import com.nepxion.discovery.common.consul.operation.ConsulListener;
import com.nepxion.discovery.common.consul.operation.ConsulOperation;
import com.nepxion.discovery.common.consul.operation.ConsulSubscribeCallback;
import com.nepxion.discovery.common.logger.ProcessorLogger;
import com.nepxion.discovery.common.thread.DiscoveryThreadPoolFactory;

public abstract class ConsulProcessor implements DisposableBean {
    private ExecutorService executorService = DiscoveryThreadPoolFactory.getExecutorService("consul-config");

    @Autowired
    private ConsulOperation consulOperation;

    private ConsulListener consulListener;

    @PostConstruct
    public void initialize() {
        beforeInitialization();

        String group = getGroup();
        String dataId = getDataId();
        String description = getDescription();
        String configType = getConfigType();
        boolean isConfigSingleKey = isConfigSingleKey();

        ProcessorLogger.logGetStarted(group, dataId, description, configType, isConfigSingleKey);

        try {
            String config = consulOperation.getConfig(group, dataId);

            callbackConfig(config);
        } catch (Exception e) {
            ProcessorLogger.logGetFailed(group, dataId, description, configType, isConfigSingleKey, e);
        }

        ProcessorLogger.logSubscribeStarted(group, dataId, description, configType, isConfigSingleKey);

        try {
            consulListener = consulOperation.subscribeConfig(group, dataId, executorService, new ConsulSubscribeCallback() {
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

        afterInitialization();
    }

    @Override
    public void destroy() {
        if (consulListener == null) {
            return;
        }

        String group = getGroup();
        String dataId = getDataId();
        String description = getDescription();
        String configType = getConfigType();
        boolean isConfigSingleKey = isConfigSingleKey();

        ProcessorLogger.logUnsubscribeStarted(group, dataId, description, configType, isConfigSingleKey);

        try {
            consulOperation.unsubscribeConfig(group, dataId, consulListener);
        } catch (Exception e) {
            ProcessorLogger.logUnsubscribeFailed(group, dataId, description, configType, isConfigSingleKey, e);
        }

        executorService.shutdownNow();
    }

    public String getConfigType() {
        return ConsulConstant.CONSUL_TYPE;
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
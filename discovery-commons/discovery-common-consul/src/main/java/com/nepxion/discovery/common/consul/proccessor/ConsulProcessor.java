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

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.consul.operation.ConsulListener;
import com.nepxion.discovery.common.consul.operation.ConsulOperation;
import com.nepxion.discovery.common.consul.operation.ConsulSubscribeCallback;
import com.nepxion.discovery.common.entity.ConfigType;
import com.nepxion.discovery.common.processor.DiscoveryConfigProcessor;
import com.nepxion.discovery.common.thread.DiscoveryThreadPoolFactory;

public abstract class ConsulProcessor extends DiscoveryConfigProcessor {
    private ExecutorService executorService = DiscoveryThreadPoolFactory.getExecutorService("consul-config");

    @Autowired
    private ConsulOperation consulOperation;

    private ConsulListener consulListener;

    @PostConstruct
    public void initialize() {
        beforeInitialization();

        String group = getGroup();
        String dataId = getDataId();

        logSubscribeStarted();

        try {
            consulListener = consulOperation.subscribeConfig(group, dataId, executorService, new ConsulSubscribeCallback() {
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
            String config = consulOperation.getConfig(group, dataId);
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
        if (consulListener == null) {
            return;
        }

        String group = getGroup();
        String dataId = getDataId();

        logUnsubscribeStarted();

        try {
            consulOperation.unsubscribeConfig(group, dataId, consulListener);
        } catch (Exception e) {
            logUnsubscribeFailed(e);
        }

        executorService.shutdownNow();
    }

    @Override
    public ConfigType getConfigType() {
        return ConfigType.CONSUL;
    }
}
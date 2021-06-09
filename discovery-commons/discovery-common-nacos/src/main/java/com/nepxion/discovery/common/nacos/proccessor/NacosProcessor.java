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

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.nacos.api.config.listener.Listener;
import com.nepxion.discovery.common.entity.ConfigType;
import com.nepxion.discovery.common.nacos.operation.NacosOperation;
import com.nepxion.discovery.common.nacos.operation.NacosSubscribeCallback;
import com.nepxion.discovery.common.processor.DiscoveryConfigProcessor;
import com.nepxion.discovery.common.thread.DiscoveryThreadPoolFactory;

public abstract class NacosProcessor extends DiscoveryConfigProcessor {
    private ExecutorService executorService = DiscoveryThreadPoolFactory.getExecutorService("nacos-config");

    @Autowired
    private NacosOperation nacosOperation;

    private Listener listener;

    @PostConstruct
    public void initialize() {
        beforeInitialization();

        String group = getGroup();
        String dataId = getDataId();

        logSubscribeStarted();

        try {
            listener = nacosOperation.subscribeConfig(group, dataId, executorService, new NacosSubscribeCallback() {
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
            String config = nacosOperation.getConfig(group, dataId);
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
        if (listener == null) {
            return;
        }

        String group = getGroup();
        String dataId = getDataId();

        logUnsubscribeStarted();

        try {
            nacosOperation.unsubscribeConfig(group, dataId, listener);
        } catch (Exception e) {
            logUnsubscribeFailed(e);
        }

        executorService.shutdownNow();
    }

    @Override
    public ConfigType getConfigType() {
        return ConfigType.NACOS;
    }
}
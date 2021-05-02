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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.nacos.api.config.listener.Listener;
import com.nepxion.discovery.common.nacos.operation.NacosOperation;
import com.nepxion.discovery.common.nacos.operation.NacosSubscribeCallback;
import com.nepxion.discovery.common.thread.DiscoveryThreadPoolFactory;

public abstract class NacosProcessor implements DisposableBean {
    private static final Logger LOG = LoggerFactory.getLogger(NacosProcessor.class);

    private ExecutorService executorService = DiscoveryThreadPoolFactory.getExecutorService("nacos-config");

    @Autowired
    private NacosOperation nacosOperation;

    private Listener configListener;

    @PostConstruct
    public void initialize() {
        String group = getGroup();
        String dataId = getDataId();
        String configType = getConfigType();

        LOG.info("Get {} config from Nacos server, group={}, dataId={}", configType, group, dataId);

        try {
            String config = nacosOperation.getConfig(group, dataId);

            callbackConfig(config);
        } catch (Exception e) {
            LOG.info("Get {} config from Nacos server failed, group={}, dataId={}", configType, group, dataId, e);
        }

        LOG.info("Subscribe {} config from Nacos server, group={}, dataId={}", configType, group, dataId);

        try {
            configListener = nacosOperation.subscribeConfig(group, dataId, executorService, new NacosSubscribeCallback() {
                @Override
                public void callback(String config) {
                    try {
                        callbackConfig(config);
                    } catch (Exception e) {
                        LOG.error("Callback {} config failed", configType, e);
                    }
                }
            });
        } catch (Exception e) {
            LOG.error("Subscribe {} config from Nacos server failed, group={}, dataId={}", configType, group, dataId, e);
        }
    }

    @Override
    public void destroy() {
        if (configListener == null) {
            return;
        }

        String group = getGroup();
        String dataId = getDataId();
        String configType = getConfigType();

        LOG.info("Unsubscribe {} config from Nacos server, group={}, dataId={}", configType, group, dataId);

        try {
            nacosOperation.unsubscribeConfig(group, dataId, configListener);
        } catch (Exception e) {
            LOG.error("Unsubscribe {} config from Nacos server failed, group={}, dataId={}", configType, group, dataId, e);
        }

        executorService.shutdownNow();
    }

    public abstract String getGroup();

    public abstract String getDataId();

    public abstract String getConfigType();

    public abstract void callbackConfig(String config);
}
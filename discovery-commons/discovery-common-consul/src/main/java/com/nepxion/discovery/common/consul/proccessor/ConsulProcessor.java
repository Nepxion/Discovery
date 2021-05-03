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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.consul.constant.ConsulConstant;
import com.nepxion.discovery.common.consul.operation.ConsulListener;
import com.nepxion.discovery.common.consul.operation.ConsulOperation;
import com.nepxion.discovery.common.consul.operation.ConsulSubscribeCallback;
import com.nepxion.discovery.common.thread.DiscoveryThreadPoolFactory;

public abstract class ConsulProcessor implements DisposableBean {
    private static final Logger LOG = LoggerFactory.getLogger(ConsulProcessor.class);

    private ExecutorService executorService = DiscoveryThreadPoolFactory.getExecutorService("consul-config");

    @Autowired
    private ConsulOperation consulOperation;

    private ConsulListener consulListener;

    @PostConstruct
    public void initialize() {
        String group = getGroup();
        String dataId = getDataId();
        String key = group + "-" + dataId;
        String description = getDescription();
        String configType = getConfigType();

        LOG.info("Get {} config from {} server, key={}", description, configType, key);

        try {
            String config = consulOperation.getConfig(group, dataId);

            callbackConfig(config);
        } catch (Exception e) {
            LOG.info("Get {} config from {} server failed, key={}", description, configType, key, e);
        }

        LOG.info("Subscribe {} config from {} server, key={}", description, configType, key);

        try {
            consulListener = consulOperation.subscribeConfig(group, dataId, executorService, new ConsulSubscribeCallback() {
                @Override
                public void callback(String config) {
                    try {
                        callbackConfig(config);
                    } catch (Exception e) {
                        LOG.error("Callback {} config failed", description, e);
                    }
                }
            });
        } catch (Exception e) {
            LOG.error("Subscribe {} config from {} server failed, key={}", description, configType, key, e);
        }
    }

    @Override
    public void destroy() {
        if (consulListener == null) {
            return;
        }

        String group = getGroup();
        String dataId = getDataId();
        String key = group + "-" + dataId;
        String description = getDescription();
        String configType = getConfigType();

        LOG.info("Unsubscribe {} config from {} server, key={}", description, configType, key);

        try {
            consulOperation.unsubscribeConfig(group, dataId, consulListener);
        } catch (Exception e) {
            LOG.error("Unsubscribe {} config from {} server failed, key={}", description, configType, key, e);
        }

        executorService.shutdownNow();
    }

    public String getConfigType() {
        return ConsulConstant.CONSUL_TYPE;
    }

    public abstract String getGroup();

    public abstract String getDataId();

    public abstract String getDescription();

    public abstract void callbackConfig(String config);
}
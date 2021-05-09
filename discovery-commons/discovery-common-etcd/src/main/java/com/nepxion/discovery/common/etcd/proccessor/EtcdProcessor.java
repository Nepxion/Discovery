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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.etcd.constant.EtcdConstant;
import com.nepxion.discovery.common.etcd.operation.EtcdOperation;
import com.nepxion.discovery.common.etcd.operation.EtcdSubscribeCallback;

public abstract class EtcdProcessor implements DisposableBean {
    private static final Logger LOG = LoggerFactory.getLogger(EtcdProcessor.class);

    @Autowired
    private EtcdOperation etcdOperation;

    private Watch watchClient;

    @PostConstruct
    public void initialize() {
        beforeInitialization();

        String group = getGroup();
        String dataId = getDataId();
        String key = group + "-" + dataId;
        String description = getDescription();
        String configType = getConfigType();

        LOG.info("Get {} config from {} server, key={}", description, configType, key);

        try {
            String config = etcdOperation.getConfig(group, dataId);

            callbackConfig(config);
        } catch (Exception e) {
            LOG.info("Get {} config from {} server failed, key={}", description, configType, key, e);
        }

        LOG.info("Subscribe {} config from {} server, key={}", description, configType, key);

        try {
            watchClient = etcdOperation.subscribeConfig(group, dataId, new EtcdSubscribeCallback() {
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

        afterInitialization();
    }

    @Override
    public void destroy() {
        if (watchClient == null) {
            return;
        }

        String group = getGroup();
        String dataId = getDataId();
        String key = group + "-" + dataId;
        String description = getDescription();
        String configType = getConfigType();

        LOG.info("Unsubscribe {} config from {} server, key={}", description, configType, key);

        try {
            etcdOperation.unsubscribeConfig(group, dataId, watchClient);
        } catch (Exception e) {
            LOG.error("Unsubscribe {} config from {} server failed, key={}", description, configType, key, e);
        }
    }

    public String getConfigType() {
        return EtcdConstant.ETCD_TYPE;
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
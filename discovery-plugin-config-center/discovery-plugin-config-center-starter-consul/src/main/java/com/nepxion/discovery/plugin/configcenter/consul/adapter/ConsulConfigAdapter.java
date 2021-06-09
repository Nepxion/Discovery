package com.nepxion.discovery.plugin.configcenter.consul.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Congwei Xu
 * @version 1.0
 */

import java.util.concurrent.ExecutorService;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.consul.operation.ConsulListener;
import com.nepxion.discovery.common.consul.operation.ConsulOperation;
import com.nepxion.discovery.common.consul.operation.ConsulSubscribeCallback;
import com.nepxion.discovery.common.entity.ConfigType;
import com.nepxion.discovery.common.thread.DiscoveryThreadPoolFactory;
import com.nepxion.discovery.plugin.configcenter.adapter.ConfigAdapter;

public class ConsulConfigAdapter extends ConfigAdapter {
    private ExecutorService executorService = DiscoveryThreadPoolFactory.getExecutorService("consul-config");

    @Autowired
    private ConsulOperation consulOperation;

    private ConsulListener partialConsulListener;
    private ConsulListener globalConsulListener;

    @Override
    public String getConfig(String group, String dataId) throws Exception {
        return consulOperation.getConfig(group, dataId);
    }

    @PostConstruct
    @Override
    public void subscribeConfig() {
        partialConsulListener = subscribeConfig(false);
        globalConsulListener = subscribeConfig(true);
    }

    private ConsulListener subscribeConfig(boolean globalConfig) {
        String group = getGroup();
        String dataId = getDataId(globalConfig);

        logSubscribeStarted(globalConfig);

        try {
            return consulOperation.subscribeConfig(group, dataId, executorService, new ConsulSubscribeCallback() {
                @Override
                public void callback(String config) {
                    callbackConfig(config, globalConfig);
                }
            });
        } catch (Exception e) {
            logSubscribeFailed(e, globalConfig);
        }

        return null;
    }

    @Override
    public void unsubscribeConfig() {
        unsubscribeConfig(partialConsulListener, false);
        unsubscribeConfig(globalConsulListener, true);

        executorService.shutdownNow();
    }

    private void unsubscribeConfig(ConsulListener consulListener, boolean globalConfig) {
        if (consulListener == null) {
            return;
        }

        String group = getGroup();
        String dataId = getDataId(globalConfig);

        logUnsubscribeStarted(globalConfig);

        try {
            consulOperation.unsubscribeConfig(group, dataId, consulListener);
        } catch (Exception e) {
            logUnsubscribeFailed(e, globalConfig);
        }
    }

    @Override
    public ConfigType getConfigType() {
        return ConfigType.CONSUL;
    }
}
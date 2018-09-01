package com.nepxion.discovery.common.nacos.operation;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.nepxion.discovery.common.nacos.constant.NacosConstant;

public class NacosOperation {
    @Autowired
    private ConfigService configService;

    @Autowired
    private Environment environment;

    public String getConfig(String group, String serviceId) throws NacosException {
        long timeout = environment.getProperty(NacosConstant.TIMEOUT, Long.class, NacosConstant.DEFAULT_TIMEOUT);

        return configService.getConfig(serviceId, group, timeout);
    }

    public boolean removeConfig(String group, String serviceId) throws NacosException {
        return configService.removeConfig(serviceId, group);
    }

    public boolean publishConfig(String group, String serviceId, String config) throws NacosException {
        return configService.publishConfig(serviceId, group, config);
    }

    public Listener subscribeConfig(String group, String serviceId, Executor executor, NacosSubscribeCallback subscribeCallback) throws NacosException {
        Listener configListener = new Listener() {
            @Override
            public void receiveConfigInfo(String config) {
                subscribeCallback.callback(config);
            }

            @Override
            public Executor getExecutor() {
                return executor;
            }
        };

        configService.addListener(serviceId, group, configListener);

        return configListener;
    }

    public void unsubscribeConfig(String group, String serviceId, Listener configListener) {
        configService.removeListener(serviceId, group, configListener);
    }
}
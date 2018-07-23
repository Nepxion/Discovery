package com.nepxion.discovery.console.extension.nacos.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.alibaba.nacos.api.config.ConfigService;
import com.nepxion.discovery.console.extension.nacos.constant.NacosConstant;
import com.nepxion.discovery.console.remote.ConfigAdapter;

public class NacosConfigAdapter implements ConfigAdapter {
    @Autowired
    private ConfigService configService;

    @Autowired
    private Environment environment;

    @Override
    public boolean updateConfig(String group, String serviceId, String config) throws Exception {
        return configService.publishConfig(serviceId, group, config);
    }

    @Override
    public boolean clearConfig(String group, String serviceId) throws Exception {
        return configService.removeConfig(serviceId, group);
    }

    @Override
    public String getConfig(String group, String serviceId) throws Exception {
        long timeout = environment.getProperty(NacosConstant.TIMEOUT, Long.class, NacosConstant.DEFAULT_TIMEOUT);

        return configService.getConfig(serviceId, group, timeout);
    }
}
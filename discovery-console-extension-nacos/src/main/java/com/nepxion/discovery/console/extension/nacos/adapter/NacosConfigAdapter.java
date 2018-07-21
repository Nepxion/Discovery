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

import com.alibaba.nacos.api.config.ConfigService;
import com.nepxion.discovery.console.remote.ConfigAdapter;

public class NacosConfigAdapter implements ConfigAdapter {
    @Autowired
    private ConfigService configService;

    @Override
    public boolean configUpdate(String group, String serviceId, String config) throws Exception {
        return configService.publishConfig(serviceId, group, config);
    }

    @Override
    public boolean configClear(String group, String serviceId) throws Exception {
        return configService.removeConfig(serviceId, group);
    }
}
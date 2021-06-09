package com.nepxion.discovery.console.consul.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Congwei Xu
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.consul.operation.ConsulOperation;
import com.nepxion.discovery.common.entity.ConfigType;
import com.nepxion.discovery.console.adapter.ConfigAdapter;

public class ConsulConfigAdapter implements ConfigAdapter {
    @Autowired
    private ConsulOperation consulOperation;

    @Override
    public boolean updateConfig(String group, String serviceId, String config) throws Exception {
        return consulOperation.publishConfig(group, serviceId, config);
    }

    @Override
    public boolean clearConfig(String group, String serviceId) throws Exception {
        return consulOperation.removeConfig(group, serviceId);
    }

    @Override
    public String getConfig(String group, String serviceId) throws Exception {
        return consulOperation.getConfig(group, serviceId);
    }

    @Override
    public ConfigType getConfigType() {
        return ConfigType.CONSUL;
    }
}
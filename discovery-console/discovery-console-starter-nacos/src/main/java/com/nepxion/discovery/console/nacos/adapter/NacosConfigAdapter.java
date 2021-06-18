package com.nepxion.discovery.console.nacos.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.entity.ConfigType;
import com.nepxion.discovery.common.entity.FormatType;
import com.nepxion.discovery.common.nacos.operation.NacosOperation;
import com.nepxion.discovery.console.adapter.ConfigAdapter;

public class NacosConfigAdapter implements ConfigAdapter {
    @Autowired
    private NacosOperation nacosOperation;

    @Override
    public boolean updateConfig(String group, String serviceId, String config) throws Exception {
        return nacosOperation.publishConfig(group, serviceId, config);
    }

    @Override
    public boolean updateConfig(String group, String serviceId, String config, FormatType formatType) throws Exception {
        return nacosOperation.publishConfig(group, serviceId, config, formatType);
    }

    @Override
    public boolean clearConfig(String group, String serviceId) throws Exception {
        return nacosOperation.removeConfig(group, serviceId);
    }

    @Override
    public String getConfig(String group, String serviceId) throws Exception {
        return nacosOperation.getConfig(group, serviceId);
    }

    @Override
    public ConfigType getConfigType() {
        return ConfigType.NACOS;
    }
}
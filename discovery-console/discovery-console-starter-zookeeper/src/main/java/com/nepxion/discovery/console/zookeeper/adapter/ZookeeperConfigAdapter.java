package com.nepxion.discovery.console.zookeeper.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author rotten
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.entity.ConfigType;
import com.nepxion.discovery.common.zookeeper.operation.ZookeeperOperation;
import com.nepxion.discovery.console.adapter.ConfigAdapter;

public class ZookeeperConfigAdapter implements ConfigAdapter {
    @Autowired
    private ZookeeperOperation zookeeperOperation;

    @Override
    public boolean updateConfig(String group, String serviceId, String config) throws Exception {
        return zookeeperOperation.publishConfig(group, serviceId, config);
    }

    @Override
    public boolean clearConfig(String group, String serviceId) throws Exception {
        return zookeeperOperation.removeConfig(group, serviceId);
    }

    @Override
    public String getConfig(String group, String serviceId) throws Exception {
        return zookeeperOperation.getConfig(group, serviceId);
    }

    @Override
    public ConfigType getConfigType() {
        return ConfigType.ZOOKEEPER;
    }
}
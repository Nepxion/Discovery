package com.nepxion.discovery.console.etcd.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Congwei Xu
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.entity.ConfigType;
import com.nepxion.discovery.common.etcd.operation.EtcdOperation;
import com.nepxion.discovery.console.adapter.ConfigAdapter;

public class EtcdConfigAdapter implements ConfigAdapter {
    @Autowired
    private EtcdOperation etcdOperation;

    @Override
    public boolean updateConfig(String group, String serviceId, String config) throws Exception {
        return etcdOperation.publishConfig(group, serviceId, config);
    }

    @Override
    public boolean clearConfig(String group, String serviceId) throws Exception {
        return etcdOperation.removeConfig(group, serviceId);
    }

    @Override
    public String getConfig(String group, String serviceId) throws Exception {
        return etcdOperation.getConfig(group, serviceId);
    }

    @Override
    public ConfigType getConfigType() {
        return ConfigType.ETCD;
    }
}
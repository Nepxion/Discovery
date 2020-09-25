package com.nepxion.discovery.plugin.strategy.isolation;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;

import com.nepxion.discovery.common.entity.ServiceType;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.listener.discovery.AbstractDiscoveryListener;

// 当目标服务的元数据中的Group和本服务不相等，禁止被本服务发现（只用于DiscoveryClient.getInstances接口方法用）。如果是网关，则不做处理
public class ConsumerIsolationDiscoveryStrategy extends AbstractDiscoveryListener {
    @Autowired
    protected PluginAdapter pluginAdapter;

    @Override
    public void onGetInstances(String serviceId, List<ServiceInstance> instances) {
        Iterator<ServiceInstance> iterator = instances.iterator();
        while (iterator.hasNext()) {
            ServiceInstance instance = iterator.next();

            String instanceServiceType = pluginAdapter.getInstanceServiceType(instance);
            if (StringUtils.equals(instanceServiceType, ServiceType.GATEWAY.toString())) {
                continue;
            }

            String instanceGroup = pluginAdapter.getInstanceGroup(instance);
            String group = pluginAdapter.getGroup();
            if (!StringUtils.equals(instanceGroup, group)) {
                iterator.remove();
            }
        }
    }

    @Override
    public void onGetServices(List<String> services) {

    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
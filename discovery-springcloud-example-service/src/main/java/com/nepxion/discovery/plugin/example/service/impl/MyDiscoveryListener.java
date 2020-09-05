package com.nepxion.discovery.plugin.example.service.impl;

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
import org.springframework.cloud.client.ServiceInstance;

import com.nepxion.discovery.plugin.framework.listener.discovery.AbstractDiscoveryListener;

// 当目标服务的元数据中的Group为mygroup2，禁止被本服务发现（只用于DiscoveryClient.getInstances接口方法用）
public class MyDiscoveryListener extends AbstractDiscoveryListener {
    @Override
    public void onGetInstances(String serviceId, List<ServiceInstance> instances) {
        Iterator<ServiceInstance> iterator = instances.iterator();
        while (iterator.hasNext()) {
            ServiceInstance instance = iterator.next();
            String group = pluginAdapter.getInstanceGroup(instance);
            if (StringUtils.equals(group, "mygroup2")) {
                iterator.remove();

                System.out.println("********** 服务名=" + serviceId + "，组名=" + group + "的服务禁止被本服务发现");
            }
        }
    }

    @Override
    public void onGetServices(List<String> services) {

    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE - 500;
    }
}
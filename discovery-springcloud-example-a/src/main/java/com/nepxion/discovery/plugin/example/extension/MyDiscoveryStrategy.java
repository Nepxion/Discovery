package com.nepxion.discovery.plugin.example.extension;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.springframework.cloud.client.ServiceInstance;

import com.nepxion.discovery.plugin.framework.strategy.AbstractDiscoveryStrategy;

public class MyDiscoveryStrategy extends AbstractDiscoveryStrategy {
    @Override
    public void fireGetInstances(String serviceId, List<ServiceInstance> instances) {
        System.out.println("========== getInstances() 被触发：serviceId=" + serviceId + " instances=" + instances + " ==========");
    }

    @Override
    public void fireGetServices(List<String> services) {
        System.out.println("========== getServices() 被触发：services=" + services + " ==========");
    }
}
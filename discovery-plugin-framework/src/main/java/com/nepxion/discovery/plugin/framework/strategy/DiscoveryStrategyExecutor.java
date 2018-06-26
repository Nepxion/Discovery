package com.nepxion.discovery.plugin.framework.strategy;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;

import com.nepxion.discovery.plugin.framework.strategy.impl.IpAddressFilterDiscoveryStrategy;
import com.nepxion.discovery.plugin.framework.strategy.impl.VersionFilterDiscoveryStrategy;

// 因为内置监听触发的时候，需要优先过滤，所以顺序执行
public class DiscoveryStrategyExecutor {
    @Autowired
    private IpAddressFilterDiscoveryStrategy ipAddressFilterDiscoveryStrategy;

    @Autowired
    private VersionFilterDiscoveryStrategy versionFilterDiscoveryStrategy;

    @Autowired
    private List<DiscoveryStrategy> discoveryStrategyList;

    @Autowired
    private ReentrantReadWriteLock reentrantReadWriteLock;

    public void fireGetInstances(String serviceId, List<ServiceInstance> instances) {
        try {
            reentrantReadWriteLock.readLock().lock();

            ipAddressFilterDiscoveryStrategy.fireGetInstances(serviceId, instances);
            versionFilterDiscoveryStrategy.fireGetInstances(serviceId, instances);

            for (DiscoveryStrategy discoveryStrategy : discoveryStrategyList) {
                if (discoveryStrategy != ipAddressFilterDiscoveryStrategy && discoveryStrategy != versionFilterDiscoveryStrategy) {
                    discoveryStrategy.fireGetInstances(serviceId, instances);
                }
            }
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }

    public void fireGetServices(List<String> services) {
        ipAddressFilterDiscoveryStrategy.fireGetServices(services);
        versionFilterDiscoveryStrategy.fireGetServices(services);

        for (DiscoveryStrategy discoveryStrategy : discoveryStrategyList) {
            if (discoveryStrategy != ipAddressFilterDiscoveryStrategy && discoveryStrategy != versionFilterDiscoveryStrategy) {
                discoveryStrategy.fireGetServices(services);
            }
        }
    }
}
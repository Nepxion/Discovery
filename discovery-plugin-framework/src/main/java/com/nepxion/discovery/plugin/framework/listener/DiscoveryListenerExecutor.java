package com.nepxion.discovery.plugin.framework.listener;

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

import com.nepxion.discovery.plugin.framework.listener.impl.IpAddressFilterDiscoveryListener;
import com.nepxion.discovery.plugin.framework.listener.impl.VersionFilterDiscoveryListener;

// 因为内置监听触发的时候，需要优先过滤，所以顺序执行
public class DiscoveryListenerExecutor {
    @Autowired
    private IpAddressFilterDiscoveryListener ipAddressFilterDiscoveryListener;

    @Autowired
    private VersionFilterDiscoveryListener versionFilterDiscoveryListener;

    @Autowired
    private List<DiscoveryListener> discoveryListenerList;

    @Autowired
    private ReentrantReadWriteLock reentrantReadWriteLock;

    public void fireGetInstances(String serviceId, List<ServiceInstance> instances) {
        try {
            reentrantReadWriteLock.readLock().lock();

            ipAddressFilterDiscoveryListener.fireGetInstances(serviceId, instances);
            versionFilterDiscoveryListener.fireGetInstances(serviceId, instances);

            for (DiscoveryListener discoveryListener : discoveryListenerList) {
                if (discoveryListener != ipAddressFilterDiscoveryListener && discoveryListener != versionFilterDiscoveryListener) {
                    discoveryListener.fireGetInstances(serviceId, instances);
                }
            }
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }

    public void fireGetServices(List<String> services) {
        ipAddressFilterDiscoveryListener.fireGetServices(services);
        versionFilterDiscoveryListener.fireGetServices(services);

        for (DiscoveryListener discoveryListener : discoveryListenerList) {
            if (discoveryListener != ipAddressFilterDiscoveryListener && discoveryListener != versionFilterDiscoveryListener) {
                discoveryListener.fireGetServices(services);
            }
        }
    }
}
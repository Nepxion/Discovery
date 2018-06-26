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

    public void onGetInstances(String serviceId, List<ServiceInstance> instances) {
        try {
            reentrantReadWriteLock.readLock().lock();

            ipAddressFilterDiscoveryListener.onGetInstances(serviceId, instances);
            versionFilterDiscoveryListener.onGetInstances(serviceId, instances);

            for (DiscoveryListener discoveryListener : discoveryListenerList) {
                if (discoveryListener != ipAddressFilterDiscoveryListener && discoveryListener != versionFilterDiscoveryListener) {
                    discoveryListener.onGetInstances(serviceId, instances);
                }
            }
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }

    public void onGetServices(List<String> services) {
        ipAddressFilterDiscoveryListener.onGetServices(services);
        versionFilterDiscoveryListener.onGetServices(services);

        for (DiscoveryListener discoveryListener : discoveryListenerList) {
            if (discoveryListener != ipAddressFilterDiscoveryListener && discoveryListener != versionFilterDiscoveryListener) {
                discoveryListener.onGetServices(services);
            }
        }
    }
}
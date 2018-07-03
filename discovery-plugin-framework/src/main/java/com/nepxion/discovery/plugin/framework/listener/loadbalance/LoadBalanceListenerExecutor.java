package com.nepxion.discovery.plugin.framework.listener.loadbalance;

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

import com.nepxion.discovery.plugin.framework.listener.discovery.DiscoveryListener;
import com.netflix.loadbalancer.Server;

// 因为内置监听触发的时候，需要优先过滤，所以顺序执行
public class LoadBalanceListenerExecutor {
    @Autowired
    private IpAddressFilterLoadBalanceListener ipAddressFilterLoadBalanceListener;

    @Autowired
    private VersionFilterLoadBalanceListener versionFilterLoadBalanceListener;

    @Autowired
    private List<DiscoveryListener> discoveryListenerList;

    @Autowired
    private ReentrantReadWriteLock reentrantReadWriteLock;

    public void onGetServers(String serviceId, List<? extends Server> servers) {
        try {
            reentrantReadWriteLock.readLock().lock();

            ipAddressFilterLoadBalanceListener.onGetServers(serviceId, servers);
            versionFilterLoadBalanceListener.onGetServers(serviceId, servers);
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }
}
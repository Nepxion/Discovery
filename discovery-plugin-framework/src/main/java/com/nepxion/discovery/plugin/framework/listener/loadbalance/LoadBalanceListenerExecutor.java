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

import org.springframework.beans.factory.annotation.Autowired;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;

// 因为内置监听触发的时候，需要优先过滤，所以顺序执行
public class LoadBalanceListenerExecutor {
    @Autowired
    private List<LoadBalanceListener> loadBalanceListenerList;

    private ZoneAwareLoadBalancer<?> loadBalancer;

    public void onGetServers(String serviceId, List<? extends Server> servers) {
        for (LoadBalanceListener loadBalanceListener : loadBalanceListenerList) {
            loadBalanceListener.onGetServers(serviceId, servers);
        }
    }

    public ZoneAwareLoadBalancer<?> getLoadBalancer() {
        return loadBalancer;
    }

    public void setLoadBalancer(ZoneAwareLoadBalancer<?> loadBalancer) {
        this.loadBalancer = loadBalancer;
    }
}
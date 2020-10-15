package com.nepxion.discovery.plugin.framework.ribbon;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.framework.listener.loadbalance.LoadBalanceListenerExecutor;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;

public class RibbonProcessor {
    @Autowired
    private LoadBalanceListenerExecutor loadBalanceListenerExecutor;

    public void refreshLoadBalancer() {
        ZoneAwareLoadBalancer<?> loadBalancer = loadBalanceListenerExecutor.getLoadBalancer();
        if (loadBalancer == null) {
            return;
        }

        loadBalancer.updateListOfServers();
    }
}
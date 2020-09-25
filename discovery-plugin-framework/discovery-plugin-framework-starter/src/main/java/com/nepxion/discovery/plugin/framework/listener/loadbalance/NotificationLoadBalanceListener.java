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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.loadbalancer.Server;

public class NotificationLoadBalanceListener extends AbstractLoadBalanceListener {
    private static final Logger LOG = LoggerFactory.getLogger(NotificationLoadBalanceListener.class);

    @Override
    public void onGetServers(String serviceId, List<? extends Server> servers) {
        if (servers.size() == 0) {
            LOG.warn("********** No server instances found for serviceId={}, perhaps they are isolated, filtered or not registered **********", serviceId);
        }
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
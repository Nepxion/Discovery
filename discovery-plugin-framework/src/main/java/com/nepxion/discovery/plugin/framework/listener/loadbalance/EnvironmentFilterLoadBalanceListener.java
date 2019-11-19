package com.nepxion.discovery.plugin.framework.listener.loadbalance;

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

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.netflix.loadbalancer.Server;

public class EnvironmentFilterLoadBalanceListener extends AbstractLoadBalanceListener {
    @Override
    public void onGetServers(String serviceId, List<? extends Server> servers) {
        applyEnvironmentFilter(serviceId, servers);
    }

    private void applyEnvironmentFilter(String providerServiceId, List<? extends Server> servers) {
        int priority = validatePriority(servers);

        if (priority > -1) {
            Iterator<? extends Server> iterator = servers.iterator();
            while (iterator.hasNext()) {
                Server server = iterator.next();
                String environment = pluginAdapter.getServerMetadata(server).get(DiscoveryConstant.ENVIRONMENT);
                if (StringUtils.isNotEmpty(environment)) {
                    int environmentPriority = Integer.parseInt(environment);
                    if (environmentPriority < priority) {
                        iterator.remove();
                    }
                } else {
                    iterator.remove();
                }
            }
        }
    }

    private int validatePriority(List<? extends Server> servers) {
        int priority = -1;
        try {
            for (Server server : servers) {
                String environment = pluginAdapter.getServerMetadata(server).get(DiscoveryConstant.ENVIRONMENT);
                if (StringUtils.isNotEmpty(environment)) {
                    int environmentPriority = Integer.parseInt(environment);
                    if (environmentPriority > priority) {
                        priority = environmentPriority;
                    }
                }
            }
        } catch (NumberFormatException e) {
            throw new DiscoveryException("The '" + DiscoveryConstant.ENVIRONMENT + "' value in metadata must be an integer type and >=0");
        }

        return priority;
    }

    @Override
    public int getOrder() {
        // Highest priority
        return HIGHEST_PRECEDENCE + 3;
    }
}
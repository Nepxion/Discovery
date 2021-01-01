package com.nepxion.discovery.plugin.registercenter.consul.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.consul.discovery.ConsulServiceInstance;

import com.nepxion.discovery.plugin.framework.adapter.AbstractPluginAdapter;

public class ConsulAdapter extends AbstractPluginAdapter {
    @Override
    public Map<String, String> getServerMetadata(ServiceInstance server) {
        if (server instanceof ConsulServiceInstance) {
            ConsulServiceInstance consulServer = (ConsulServiceInstance) server;

            return consulServer.getMetadata();
        }

        return emptyMetadata;

        // throw new DiscoveryException("Server instance isn't the type of ConsulServer");
    }
}
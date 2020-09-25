package com.nepxion.discovery.plugin.registercenter.nacos.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.nepxion.discovery.plugin.framework.adapter.AbstractPluginAdapter;
import com.netflix.loadbalancer.Server;

public class NacosAdapter extends AbstractPluginAdapter {
    @Override
    public Map<String, String> getServerMetadata(Server server) {
        if (server instanceof NacosServer) {
            NacosServer nacosServer = (NacosServer) server;

            return nacosServer.getMetadata();
        }

        return emptyMetadata;

        // throw new DiscoveryException("Server instance isn't the type of NacosServer");
    }
}
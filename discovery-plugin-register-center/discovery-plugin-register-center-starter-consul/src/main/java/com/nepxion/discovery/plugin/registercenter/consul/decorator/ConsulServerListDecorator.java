package com.nepxion.discovery.plugin.registercenter.consul.decorator;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.discovery.ConsulServer;
import org.springframework.cloud.consul.discovery.ConsulServerList;

import com.ecwid.consul.v1.ConsulClient;
import com.nepxion.discovery.plugin.framework.listener.loadbalance.LoadBalanceListenerExecutor;

public class ConsulServerListDecorator extends ConsulServerList {
    private LoadBalanceListenerExecutor loadBalanceListenerExecutor;

    public ConsulServerListDecorator(ConsulClient client, ConsulDiscoveryProperties properties) {
        super(client, properties);
    }

    @Override
    public List<ConsulServer> getInitialListOfServers() {
        List<ConsulServer> servers = super.getInitialListOfServers();

        filter(servers);

        return servers;
    }

    @Override
    public List<ConsulServer> getUpdatedListOfServers() {
        List<ConsulServer> servers = super.getUpdatedListOfServers();

        filter(servers);

        return servers;
    }

    private void filter(List<ConsulServer> servers) {
        String serviceId = getServiceId();

        loadBalanceListenerExecutor.onGetServers(serviceId, servers);
    }

    public void setLoadBalanceListenerExecutor(LoadBalanceListenerExecutor loadBalanceListenerExecutor) {
        this.loadBalanceListenerExecutor = loadBalanceListenerExecutor;
    }
}
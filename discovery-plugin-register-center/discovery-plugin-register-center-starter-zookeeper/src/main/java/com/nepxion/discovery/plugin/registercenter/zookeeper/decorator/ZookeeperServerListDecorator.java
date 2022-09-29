package com.nepxion.discovery.plugin.registercenter.zookeeper.decorator;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.apache.curator.x.discovery.ServiceDiscovery;
import org.springframework.cloud.zookeeper.discovery.ZookeeperInstance;
import org.springframework.cloud.zookeeper.discovery.ZookeeperServer;
import org.springframework.cloud.zookeeper.discovery.ZookeeperServerList;

import com.nepxion.discovery.plugin.framework.listener.loadbalance.LoadBalanceListenerExecutor;

public class ZookeeperServerListDecorator extends ZookeeperServerList {
    private LoadBalanceListenerExecutor loadBalanceListenerExecutor;

    private String serviceId;

    public ZookeeperServerListDecorator(ServiceDiscovery<ZookeeperInstance> serviceDiscovery) {
        super(serviceDiscovery);
    }

    @Override
    public List<ZookeeperServer> getInitialListOfServers() {
        List<ZookeeperServer> servers = super.getInitialListOfServers();

        filter(servers);

        return servers;
    }

    @Override
    public List<ZookeeperServer> getUpdatedListOfServers() {
        List<ZookeeperServer> servers = super.getUpdatedListOfServers();

        filter(servers);

        return servers;
    }

    private void filter(List<ZookeeperServer> servers) {
        loadBalanceListenerExecutor.onGetServers(serviceId, servers);
    }

    public void setLoadBalanceListenerExecutor(LoadBalanceListenerExecutor loadBalanceListenerExecutor) {
        this.loadBalanceListenerExecutor = loadBalanceListenerExecutor;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
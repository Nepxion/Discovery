package com.nepxion.discovery.plugin.framework.decorator;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.springframework.cloud.alibaba.nacos.ribbon.NacosServer;
import org.springframework.cloud.alibaba.nacos.ribbon.NacosServerList;
import org.springframework.core.env.ConfigurableEnvironment;

import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.listener.loadbalance.LoadBalanceListenerExecutor;

public class NacosServerListDecorator extends NacosServerList {
    private ConfigurableEnvironment environment;

    private LoadBalanceListenerExecutor loadBalanceListenerExecutor;

    public NacosServerListDecorator() {
        super();
    }

    public NacosServerListDecorator(String serviceId) {
        super(serviceId);
    }

    @Override
    public List<NacosServer> getInitialListOfServers() {
        List<NacosServer> servers = super.getInitialListOfServers();

        filter(servers);

        return servers;
    }

    @Override
    public List<NacosServer> getUpdatedListOfServers() {
        List<NacosServer> servers = super.getUpdatedListOfServers();

        filter(servers);

        return servers;
    }

    private void filter(List<NacosServer> servers) {
        Boolean discoveryControlEnabled = PluginContextAware.isDiscoveryControlEnabled(environment);
        if (discoveryControlEnabled) {
            String serviceId = getServiceId();
            loadBalanceListenerExecutor.onGetServers(serviceId, servers);
        }
    }

    public void setEnvironment(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    public void setLoadBalanceListenerExecutor(LoadBalanceListenerExecutor loadBalanceListenerExecutor) {
        this.loadBalanceListenerExecutor = loadBalanceListenerExecutor;
    }
}
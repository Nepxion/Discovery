package com.nepxion.discovery.plugin.registercenter.zookeeper.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.curator.x.discovery.ServiceDiscovery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cloud.zookeeper.discovery.ZookeeperInstance;
import org.springframework.cloud.zookeeper.discovery.ZookeeperRibbonClientConfiguration;
import org.springframework.cloud.zookeeper.discovery.dependency.ConditionalOnDependenciesNotPassed;
import org.springframework.cloud.zookeeper.discovery.dependency.ConditionalOnDependenciesPassed;
import org.springframework.cloud.zookeeper.discovery.dependency.ZookeeperDependencies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.discovery.plugin.framework.listener.loadbalance.LoadBalanceListenerExecutor;
import com.nepxion.discovery.plugin.registercenter.zookeeper.decorator.ZookeeperServerListDecorator;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ServerList;

@Configuration
@AutoConfigureAfter(ZookeeperRibbonClientConfiguration.class)
public class ZookeeperLoadBalanceConfiguration {
    @Autowired
    private LoadBalanceListenerExecutor loadBalanceListenerExecutor;

    @Bean
    @ConditionalOnDependenciesPassed
    public ServerList<?> ribbonServerListFromDependencies(IClientConfig config, ZookeeperDependencies zookeeperDependencies, ServiceDiscovery<ZookeeperInstance> serviceDiscovery) {
        ZookeeperServerListDecorator serverList = new ZookeeperServerListDecorator(serviceDiscovery);
        serverList.initFromDependencies(config, zookeeperDependencies);
        serverList.setLoadBalanceListenerExecutor(loadBalanceListenerExecutor);
        serverList.setServiceId(config.getClientName());

        return serverList;
    }

    @Bean
    @ConditionalOnDependenciesNotPassed
    public ServerList<?> ribbonServerList(IClientConfig config, ServiceDiscovery<ZookeeperInstance> serviceDiscovery) {
        ZookeeperServerListDecorator serverList = new ZookeeperServerListDecorator(serviceDiscovery);
        serverList.initWithNiwsConfig(config);
        serverList.setLoadBalanceListenerExecutor(loadBalanceListenerExecutor);
        serverList.setServiceId(config.getClientName());

        return serverList;
    }
}
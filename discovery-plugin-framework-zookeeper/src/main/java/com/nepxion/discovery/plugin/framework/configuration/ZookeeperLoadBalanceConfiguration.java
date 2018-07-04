package com.nepxion.discovery.plugin.framework.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.zookeeper.discovery.ZookeeperRibbonClientConfiguration;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperServiceRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

import com.nepxion.discovery.plugin.framework.decorator.ZookeeperServerListDecorator;
import com.nepxion.discovery.plugin.framework.listener.loadbalance.LoadBalanceListenerExecutor;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ServerList;

@Configuration
@AutoConfigureAfter(ZookeeperRibbonClientConfiguration.class)
public class ZookeeperLoadBalanceConfiguration {
    @Autowired
    private ConfigurableEnvironment environment;

    @Autowired
    private LoadBalanceListenerExecutor loadBalanceListenerExecutor;

    @Autowired
    private ZookeeperServiceRegistry registry;

    @Bean
    @ConditionalOnMissingBean
    public ServerList<?> ribbonServerList(IClientConfig config) {
        @SuppressWarnings("deprecation")
        ZookeeperServerListDecorator serverList = new ZookeeperServerListDecorator(this.registry.getServiceDiscoveryRef().get());
        serverList.initWithNiwsConfig(config);
        serverList.setEnvironment(environment);
        serverList.setLoadBalanceListenerExecutor(loadBalanceListenerExecutor);
        serverList.setServiceId(config.getClientName());

        return serverList;
    }
}
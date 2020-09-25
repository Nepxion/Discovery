package com.nepxion.discovery.plugin.registercenter.consul.configuration;

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
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.discovery.ConsulRibbonClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

import com.ecwid.consul.v1.ConsulClient;
import com.nepxion.discovery.plugin.framework.listener.loadbalance.LoadBalanceListenerExecutor;
import com.nepxion.discovery.plugin.registercenter.consul.decorator.ConsulServerListDecorator;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ServerList;

@Configuration
@AutoConfigureAfter(ConsulRibbonClientConfiguration.class)
public class ConsulLoadBalanceConfiguration {
    @Autowired
    private ConsulClient client;

    @Autowired
    private ConfigurableEnvironment environment;

    @Autowired
    private LoadBalanceListenerExecutor loadBalanceListenerExecutor;

    @Bean
    public ServerList<?> ribbonServerList(IClientConfig config, ConsulDiscoveryProperties properties) {
        ConsulServerListDecorator serverList = new ConsulServerListDecorator(client, properties);
        serverList.initWithNiwsConfig(config);
        serverList.setEnvironment(environment);
        serverList.setLoadBalanceListenerExecutor(loadBalanceListenerExecutor);

        return serverList;
    }
}
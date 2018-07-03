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
import org.springframework.cloud.zookeeper.discovery.ZookeeperRibbonClientConfiguration;
import org.springframework.cloud.zookeeper.discovery.ZookeeperServerList;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperServiceRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ServerList;

@Configuration
@AutoConfigureAfter(ZookeeperRibbonClientConfiguration.class)
public class ZookeeperLoadBalanceConfiguration {
    @Autowired
    private ZookeeperServiceRegistry registry;

    @Bean
    public ServerList<?> ribbonServerList(IClientConfig config) {
        @SuppressWarnings("deprecation")
        ZookeeperServerList serverList = new ZookeeperServerList(this.registry.getServiceDiscoveryRef().get());
        serverList.initWithNiwsConfig(config);

        return serverList;
    }
}
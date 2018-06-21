package com.nepxion.discovery.plugin.decorator;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.nepxion.discovery.plugin.constant.DiscoveryPluginConstant;
import com.nepxion.discovery.plugin.strategy.VersionStrategy;

public class DiscoveryClientDecorator implements DiscoveryClient {
    private DiscoveryClient discoveryClient;
    private ConfigurableApplicationContext applicationContext;
    private ConfigurableEnvironment environment;

    public DiscoveryClientDecorator(DiscoveryClient discoveryClient, ConfigurableApplicationContext applicationContext) {
        this.discoveryClient = discoveryClient;
        this.applicationContext = applicationContext;
        this.environment = applicationContext.getEnvironment();
    }

    @Override
    public String description() {
        return discoveryClient.description();
    }

    @Deprecated
    @Override
    public ServiceInstance getLocalServiceInstance() {
        return discoveryClient.getLocalServiceInstance();
    }

    @Override
    public List<ServiceInstance> getInstances(String serviceId) {
        String applicationName = environment.getProperty(DiscoveryPluginConstant.SPRING_APPLICATION_NAME);

        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);

        VersionStrategy versionStrategy = applicationContext.getBean(VersionStrategy.class);
        versionStrategy.apply(applicationName, serviceId, instances);

        return instances;
    }

    @Override
    public List<String> getServices() {
        return discoveryClient.getServices();
    }
}
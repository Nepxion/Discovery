package com.nepxion.discovery.plugin.core.decorator;

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

import com.nepxion.discovery.plugin.core.constant.PluginConstant;
import com.nepxion.discovery.plugin.core.strategy.DiscoveryStrategy;

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
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);

        boolean discoveryVersionEnabled = Boolean.valueOf(environment.getProperty(PluginConstant.SPRING_APPLICATION_DISCOVERY_VERSION_ENABLED));
        if (discoveryVersionEnabled) {
            String applicationName = environment.getProperty(PluginConstant.SPRING_APPLICATION_NAME);
            String metadataVersion = environment.getProperty(PluginConstant.EUREKA_METADATA_VERSION);

            DiscoveryStrategy discoveryStrategy = applicationContext.getBean(DiscoveryStrategy.class);
            discoveryStrategy.apply(applicationName, metadataVersion, serviceId, instances);
        }

        return instances;
    }

    @Override
    public List<String> getServices() {
        return discoveryClient.getServices();
    }

    public ConfigurableEnvironment getEnvironment() {
        return environment;
    }
}
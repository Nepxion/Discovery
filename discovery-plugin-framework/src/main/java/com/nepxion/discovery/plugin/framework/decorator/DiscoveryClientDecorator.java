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

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.nepxion.discovery.plugin.framework.constant.PluginConstant;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.strategy.DiscoveryControlStrategy;

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

        Boolean discoveryControlEnabled = PluginContextAware.isDiscoveryControlEnabled(environment);
        if (discoveryControlEnabled) {
            String applicationName = environment.getProperty(PluginConstant.SPRING_APPLICATION_NAME);
            String metadataVersion = environment.getProperty(PluginConstant.EUREKA_METADATA_VERSION);

            DiscoveryControlStrategy discoveryControlStrategy = applicationContext.getBean(DiscoveryControlStrategy.class);
            discoveryControlStrategy.apply(applicationName, metadataVersion, serviceId, instances);
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
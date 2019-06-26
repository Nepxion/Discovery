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

import org.springframework.beans.BeansException;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.listener.discovery.DiscoveryListenerExecutor;

public class DiscoveryClientDecorator implements DiscoveryClient {
    // private static final Logger LOG = LoggerFactory.getLogger(DiscoveryClientDecorator.class);

    private DiscoveryClient discoveryClient;
    private ConfigurableApplicationContext applicationContext;
    private ConfigurableEnvironment environment;

    public DiscoveryClientDecorator(DiscoveryClient discoveryClient, ConfigurableApplicationContext applicationContext) {
        this.discoveryClient = discoveryClient;
        this.applicationContext = applicationContext;
        this.environment = applicationContext.getEnvironment();
    }

    @Override
    public List<ServiceInstance> getInstances(String serviceId) {
        List<ServiceInstance> instances = getRealInstances(serviceId);

        Boolean discoveryControlEnabled = PluginContextAware.isDiscoveryControlEnabled(environment);
        if (discoveryControlEnabled) {
            try {
                DiscoveryListenerExecutor discoveryListenerExecutor = applicationContext.getBean(DiscoveryListenerExecutor.class);
                discoveryListenerExecutor.onGetInstances(serviceId, instances);
            } catch (BeansException e) {
                // LOG.warn("Get bean for DiscoveryListenerExecutor failed, ignore to executor listener");
            }
        }

        return instances;
    }

    public List<ServiceInstance> getRealInstances(String serviceId) {
        return discoveryClient.getInstances(serviceId);
    }

    @Override
    public List<String> getServices() {
        List<String> services = getRealServices();

        Boolean discoveryControlEnabled = PluginContextAware.isDiscoveryControlEnabled(environment);
        if (discoveryControlEnabled) {
            try {
                DiscoveryListenerExecutor discoveryListenerExecutor = applicationContext.getBean(DiscoveryListenerExecutor.class);
                discoveryListenerExecutor.onGetServices(services);
            } catch (BeansException e) {
                // LOG.warn("Get bean for DiscoveryListenerExecutor failed, ignore to executor listener");
            }
        }

        return services;
    }

    public List<String> getRealServices() {
        return discoveryClient.getServices();
    }

    @Override
    public String description() {
        return discoveryClient.description();
    }

    public ConfigurableEnvironment getEnvironment() {
        return environment;
    }
}
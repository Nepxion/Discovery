package com.nepxion.discovery.plugin.framework.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.BeansException;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;
import org.springframework.context.ConfigurableApplicationContext;

import com.nepxion.discovery.plugin.framework.decorator.ConsulServiceRegistryDecorator;

public class ConsulApplicationContextInitializer extends PluginApplicationContextInitializer {
    @Override
    protected Object afterInitialization(ConfigurableApplicationContext applicationContext, Object bean, String beanName) throws BeansException {
        if (bean instanceof ConsulServiceRegistry) {
            ConsulServiceRegistry consulServiceRegistry = (ConsulServiceRegistry) bean;

            return new ConsulServiceRegistryDecorator(consulServiceRegistry, applicationContext);
        /*} else if (bean instanceof ConsulDiscoveryProperties) {
            ConsulDiscoveryProperties consulDiscoveryProperties = (ConsulDiscoveryProperties) bean;
            consulDiscoveryProperties.setPreferIpAddress(true);

            return bean;*/
        } else {
            return bean;
        }
    }
}
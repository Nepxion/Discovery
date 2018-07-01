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
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperServiceRegistry;
import org.springframework.context.ConfigurableApplicationContext;

import com.nepxion.discovery.plugin.framework.decorator.ZookeeperServiceRegistryDecorator;

public class ZookeeperApplicationContextInitializer extends PluginApplicationContextInitializer {
    @Override
    protected Object afterInitialization(ConfigurableApplicationContext applicationContext, Object bean, String beanName) throws BeansException {
        if (bean instanceof ZookeeperServiceRegistry) {
            ZookeeperServiceRegistry zookeeperServiceRegistry = (ZookeeperServiceRegistry) bean;

            return new ZookeeperServiceRegistryDecorator(zookeeperServiceRegistry, applicationContext);
        /*} else if (bean instanceof ZookeeperDiscoveryProperties) {
            ZookeeperDiscoveryProperties zookeeperDiscoveryProperties = (ZookeeperDiscoveryProperties) bean;
            zookeeperDiscoveryProperties.setPreferIpAddress(true);

            return bean;*/
        } else {
            return bean;
        }
    }
}
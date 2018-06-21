package com.nepxion.discovery.plugin.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaServiceRegistry;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import com.nepxion.discovery.plugin.decorator.DiscoveryClientDecorator;
import com.nepxion.discovery.plugin.decorator.EurekaServiceRegistryDecorator;

public class DiscoveryApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        applicationContext.getBeanFactory().addBeanPostProcessor(new InstantiationAwareBeanPostProcessorAdapter() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof DiscoveryClient) {
                    DiscoveryClient discoveryClient = (DiscoveryClient) bean;
                    return new DiscoveryClientDecorator(discoveryClient, applicationContext);
                } else if (bean instanceof EurekaServiceRegistry) {
                    EurekaServiceRegistry eurekaServiceRegistry = (EurekaServiceRegistry) bean;
                    return new EurekaServiceRegistryDecorator(eurekaServiceRegistry, applicationContext);
                } else if (bean instanceof EurekaInstanceConfigBean) {
                    EurekaInstanceConfigBean instanceConfig = (EurekaInstanceConfigBean) bean;
                    instanceConfig.setPreferIpAddress(true);
                    return bean;
                } else {
                    return bean;
                }
            }
        });
    }
}
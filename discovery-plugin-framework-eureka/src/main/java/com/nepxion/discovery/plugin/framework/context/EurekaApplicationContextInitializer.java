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
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaServiceRegistry;
import org.springframework.context.ConfigurableApplicationContext;

import com.nepxion.discovery.plugin.framework.decorator.EurekaServiceRegistryDecorator;

public class EurekaApplicationContextInitializer extends PluginApplicationContextInitializer {
    @Override
    protected Object afterInitialization(ConfigurableApplicationContext applicationContext, Object bean, String beanName) throws BeansException {
        if (bean instanceof EurekaServiceRegistry) {
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
}
package com.nepxion.discovery.plugin.framework.context;

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
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.constant.ConsulConstant;
import com.nepxion.discovery.plugin.framework.decorator.ConsulServiceRegistryDecorator;
import com.nepxion.discovery.plugin.framework.util.MetadataUtil;

public class ConsulApplicationContextInitializer extends PluginApplicationContextInitializer {
    @Override
    protected Object afterInitialization(ConfigurableApplicationContext applicationContext, Object bean, String beanName) throws BeansException {
        if (bean instanceof ConsulServiceRegistry) {
            ConsulServiceRegistry consulServiceRegistry = (ConsulServiceRegistry) bean;

            return new ConsulServiceRegistryDecorator(consulServiceRegistry, applicationContext);
        } else if (bean instanceof ConsulDiscoveryProperties) {
            ConfigurableEnvironment environment = applicationContext.getEnvironment();

            ConsulDiscoveryProperties consulDiscoveryProperties = (ConsulDiscoveryProperties) bean;
            consulDiscoveryProperties.setPreferIpAddress(true);

            List<String> tags = consulDiscoveryProperties.getTags();
            tags.add(DiscoveryConstant.SPRING_APPLICATION_DISCOVERY_PLUGIN + "=" + ConsulConstant.DISCOVERY_PLUGIN);
            tags.add(DiscoveryConstant.SPRING_APPLICATION_REGISTER_CONTROL_ENABLED + "=" + PluginContextAware.isRegisterControlEnabled(environment));
            tags.add(DiscoveryConstant.SPRING_APPLICATION_DISCOVERY_CONTROL_ENABLED + "=" + PluginContextAware.isDiscoveryControlEnabled(environment));
            tags.add(DiscoveryConstant.SPRING_APPLICATION_CONFIG_REST_CONTROL_ENABLED + "=" + PluginContextAware.isConfigRestControlEnabled(environment));
            tags.add(DiscoveryConstant.SPRING_APPLICATION_GROUP_KEY + "=" + PluginContextAware.getGroupKey(environment));
            tags.add(DiscoveryConstant.SPRING_APPLICATION_CONTEXT_PATH + "=" + PluginContextAware.getContextPath(environment));

            MetadataUtil.filter(tags);

            return bean;
        } else {
            return bean;
        }
    }
}
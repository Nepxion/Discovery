package com.nepxion.discovery.plugin.framework.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.cloud.alibaba.nacos.NacosDiscoveryProperties;
import org.springframework.cloud.alibaba.nacos.registry.NacosServiceRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.constant.NacosConstant;
import com.nepxion.discovery.plugin.framework.decorator.NacosServiceRegistryDecorator;
import com.nepxion.discovery.plugin.framework.util.MetadataUtil;

public class NacosApplicationContextInitializer extends PluginApplicationContextInitializer {
    @Override
    protected Object afterInitialization(ConfigurableApplicationContext applicationContext, Object bean, String beanName) throws BeansException {
        if (bean instanceof NacosServiceRegistry) {
            NacosServiceRegistry nacosServiceRegistry = (NacosServiceRegistry) bean;

            return new NacosServiceRegistryDecorator(nacosServiceRegistry, applicationContext);
        } else if (bean instanceof NacosDiscoveryProperties) {
            ConfigurableEnvironment environment = applicationContext.getEnvironment();

            NacosDiscoveryProperties nacosDiscoveryProperties = (NacosDiscoveryProperties) bean;

            Map<String, String> metadata = nacosDiscoveryProperties.getMetadata();
            metadata.put(DiscoveryConstant.SPRING_APPLICATION_DISCOVERY_PLUGIN, NacosConstant.DISCOVERY_PLUGIN);
            metadata.put(DiscoveryConstant.SPRING_APPLICATION_REGISTER_CONTROL_ENABLED, PluginContextAware.isRegisterControlEnabled(environment).toString());
            metadata.put(DiscoveryConstant.SPRING_APPLICATION_DISCOVERY_CONTROL_ENABLED, PluginContextAware.isDiscoveryControlEnabled(environment).toString());
            metadata.put(DiscoveryConstant.SPRING_APPLICATION_CONFIG_REST_CONTROL_ENABLED, PluginContextAware.isConfigRestControlEnabled(environment).toString());
            metadata.put(DiscoveryConstant.SPRING_APPLICATION_GROUP_KEY, PluginContextAware.getGroupKey(environment));
            metadata.put(DiscoveryConstant.SPRING_APPLICATION_CONTEXT_PATH, PluginContextAware.getContextPath(environment));

            MetadataUtil.filter(metadata);

            return bean;
        } else {
            return bean;
        }
    }
}
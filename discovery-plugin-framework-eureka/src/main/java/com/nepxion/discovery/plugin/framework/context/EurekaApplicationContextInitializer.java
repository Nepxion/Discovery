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
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaServiceRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.constant.EurekaConstant;
import com.nepxion.discovery.plugin.framework.decorator.EurekaServiceRegistryDecorator;
import com.nepxion.discovery.plugin.framework.util.MetadataUtil;

public class EurekaApplicationContextInitializer extends PluginApplicationContextInitializer {
    @Override
    protected Object afterInitialization(ConfigurableApplicationContext applicationContext, Object bean, String beanName) throws BeansException {
        if (bean instanceof EurekaServiceRegistry) {
            EurekaServiceRegistry eurekaServiceRegistry = (EurekaServiceRegistry) bean;

            return new EurekaServiceRegistryDecorator(eurekaServiceRegistry, applicationContext);
        } else if (bean instanceof EurekaInstanceConfigBean) {
            ConfigurableEnvironment environment = applicationContext.getEnvironment();

            EurekaInstanceConfigBean eurekaInstanceConfig = (EurekaInstanceConfigBean) bean;
            eurekaInstanceConfig.setPreferIpAddress(true);

            Map<String, String> metadataMap = eurekaInstanceConfig.getMetadataMap();
            metadataMap.put(DiscoveryConstant.SPRING_APPLICATION_DISCOVERY_PLUGIN, EurekaConstant.DISCOVERY_PLUGIN);
            metadataMap.put(DiscoveryConstant.SPRING_APPLICATION_REGISTER_CONTROL_ENABLED, PluginContextAware.isRegisterControlEnabled(environment).toString());
            metadataMap.put(DiscoveryConstant.SPRING_APPLICATION_DISCOVERY_CONTROL_ENABLED, PluginContextAware.isDiscoveryControlEnabled(environment).toString());
            metadataMap.put(DiscoveryConstant.SPRING_APPLICATION_CONFIG_REST_CONTROL_ENABLED, PluginContextAware.isConfigRestControlEnabled(environment).toString());
            metadataMap.put(DiscoveryConstant.SPRING_APPLICATION_GROUP_KEY, PluginContextAware.getGroupKey(environment));
            metadataMap.put(DiscoveryConstant.SPRING_APPLICATION_CONTEXT_PATH, PluginContextAware.getContextPath(environment));

            MetadataUtil.filter(metadataMap);

            return bean;
        } else {
            return bean;
        }
    }
}
package com.nepxion.discovery.plugin.registercenter.consul.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringBootVersion;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.adapter.ApplicationInfoAdapter;
import com.nepxion.discovery.plugin.framework.context.PluginApplicationContextInitializer;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.util.MetadataUtil;
import com.nepxion.discovery.plugin.registercenter.consul.constant.ConsulConstant;
import com.nepxion.discovery.plugin.registercenter.consul.decorator.ConsulServiceRegistryDecorator;

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

            List<String> metadata = consulDiscoveryProperties.getTags();

            String groupKey = PluginContextAware.getGroupKey(environment);
            if (!MetadataUtil.containsKey(metadata, groupKey)) {
                metadata.add(groupKey + "=" + DiscoveryConstant.DEFAULT);
            }
            if (!MetadataUtil.containsKey(metadata, DiscoveryConstant.VERSION)) {
                metadata.add(DiscoveryConstant.VERSION + "=" + DiscoveryConstant.DEFAULT);
            }
            if (!MetadataUtil.containsKey(metadata, DiscoveryConstant.REGION)) {
                metadata.add(DiscoveryConstant.REGION + "=" + DiscoveryConstant.DEFAULT);
            }
            String prefixGroup = getPrefixGroup(applicationContext);
            if (StringUtils.isNotEmpty(prefixGroup)) {
                metadata.set(MetadataUtil.getIndex(metadata, groupKey), groupKey + "=" + prefixGroup);
            }
            String gitVersion = getGitVersion(applicationContext);
            if (StringUtils.isNotEmpty(gitVersion)) {
                metadata.set(MetadataUtil.getIndex(metadata, DiscoveryConstant.VERSION), DiscoveryConstant.VERSION + "=" + gitVersion);
            }

            metadata.add(DiscoveryConstant.SPRING_BOOT_VERSION + "=" + SpringBootVersion.getVersion());
            metadata.add(DiscoveryConstant.SPRING_APPLICATION_NAME + "=" + PluginContextAware.getApplicationName(environment));
            metadata.add(DiscoveryConstant.SPRING_APPLICATION_TYPE + "=" + PluginContextAware.getApplicationType(environment));
            metadata.add(DiscoveryConstant.SPRING_APPLICATION_UUID + "=" + PluginContextAware.getApplicationUUId(environment));
            metadata.add(DiscoveryConstant.SPRING_APPLICATION_DISCOVERY_PLUGIN + "=" + ConsulConstant.CONSUL_TYPE);
            metadata.add(DiscoveryConstant.SPRING_APPLICATION_DISCOVERY_VERSION + "=" + DiscoveryConstant.DISCOVERY_VERSION);
            String agentVersion = System.getProperty(DiscoveryConstant.SPRING_APPLICATION_DISCOVERY_AGENT_VERSION);
            metadata.add(DiscoveryConstant.SPRING_APPLICATION_DISCOVERY_AGENT_VERSION + "=" + (StringUtils.isEmpty(agentVersion) ? DiscoveryConstant.UNKNOWN : agentVersion));
            metadata.add(DiscoveryConstant.SPRING_APPLICATION_REGISTER_CONTROL_ENABLED + "=" + PluginContextAware.isRegisterControlEnabled(environment));
            metadata.add(DiscoveryConstant.SPRING_APPLICATION_DISCOVERY_CONTROL_ENABLED + "=" + PluginContextAware.isDiscoveryControlEnabled(environment));
            metadata.add(DiscoveryConstant.SPRING_APPLICATION_CONFIG_REST_CONTROL_ENABLED + "=" + PluginContextAware.isConfigRestControlEnabled(environment));
            metadata.add(DiscoveryConstant.SPRING_APPLICATION_GROUP_KEY + "=" + groupKey);
            metadata.add(DiscoveryConstant.SPRING_APPLICATION_CONTEXT_PATH + "=" + PluginContextAware.getContextPath(environment));

            try {
                ApplicationInfoAdapter applicationInfoAdapter = applicationContext.getBean(ApplicationInfoAdapter.class);
                if (applicationInfoAdapter != null) {
                    metadata.add(DiscoveryConstant.APP_ID + "=" + applicationInfoAdapter.getAppId());
                }
            } catch (Exception e) {

            }

            MetadataUtil.filter(metadata);

            return bean;
        } else {
            return bean;
        }
    }
}
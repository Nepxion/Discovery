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
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringBootVersion;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.constant.DiscoveryMetaDataConstant;
import com.nepxion.discovery.common.context.DiscoveryMetaDataPreInstallation;
import com.nepxion.discovery.common.entity.DiscoveryType;
import com.nepxion.discovery.plugin.framework.adapter.ApplicationInfoAdapter;
import com.nepxion.discovery.plugin.framework.context.PluginApplicationContextInitializer;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.util.MetadataUtil;
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
            /*if (!MetadataUtil.containsKey(metadata, groupKey)) {
                metadata.add(groupKey + "=" + DiscoveryConstant.DEFAULT);
            }
            if (!MetadataUtil.containsKey(metadata, DiscoveryConstant.VERSION)) {
                metadata.add(DiscoveryConstant.VERSION + "=" + DiscoveryConstant.DEFAULT);
            }
            if (!MetadataUtil.containsKey(metadata, DiscoveryConstant.REGION)) {
                metadata.add(DiscoveryConstant.REGION + "=" + DiscoveryConstant.DEFAULT);
            }
            if (!MetadataUtil.containsKey(metadata, DiscoveryConstant.ENVIRONMENT)) {
                metadata.add(DiscoveryConstant.ENVIRONMENT + "=" + DiscoveryConstant.DEFAULT);
            }
            if (!MetadataUtil.containsKey(metadata, DiscoveryConstant.ZONE)) {
                metadata.add(DiscoveryConstant.ZONE + "=" + DiscoveryConstant.DEFAULT);
            }
            if (!MetadataUtil.containsKey(metadata, DiscoveryConstant.ACTIVE)) {
                metadata.add(DiscoveryConstant.ACTIVE + "=" + "false");
            }*/
            String prefixGroup = getPrefixGroup(applicationContext);
            if (StringUtils.isNotEmpty(prefixGroup)) {
                metadata.set(MetadataUtil.getIndex(metadata, groupKey), groupKey + "=" + prefixGroup);
            }
            String gitVersion = getGitVersion(applicationContext);
            if (StringUtils.isNotEmpty(gitVersion)) {
                metadata.set(MetadataUtil.getIndex(metadata, DiscoveryConstant.VERSION), DiscoveryConstant.VERSION + "=" + gitVersion);
            }

            metadata.add(DiscoveryMetaDataConstant.SPRING_BOOT_VERSION + "=" + SpringBootVersion.getVersion());
            metadata.add(DiscoveryMetaDataConstant.SPRING_APPLICATION_NAME + "=" + PluginContextAware.getApplicationName(environment));
            metadata.add(DiscoveryMetaDataConstant.SPRING_APPLICATION_TYPE + "=" + PluginContextAware.getApplicationType(environment));
            String applicationGatewayType = PluginContextAware.getApplicationGatewayType(environment);
            if (StringUtils.isNotEmpty(applicationGatewayType)) {
                metadata.add(DiscoveryMetaDataConstant.SPRING_APPLICATION_GATEWAY_TYPE + "=" + applicationGatewayType);
            }
            metadata.add(DiscoveryMetaDataConstant.SPRING_APPLICATION_PROTOCOL + "=" + PluginContextAware.getApplicationProtocol(environment));
            metadata.add(DiscoveryMetaDataConstant.SPRING_APPLICATION_UUID + "=" + PluginContextAware.getApplicationUUId(environment));
            metadata.add(DiscoveryMetaDataConstant.SPRING_APPLICATION_DISCOVERY_PLUGIN + "=" + DiscoveryType.CONSUL);
            metadata.add(DiscoveryMetaDataConstant.SPRING_APPLICATION_DISCOVERY_VERSION + "=" + DiscoveryConstant.DISCOVERY_VERSION);
            String agentVersion = System.getProperty(DiscoveryConstant.SPRING_APPLICATION_DISCOVERY_AGENT_VERSION);
            if (StringUtils.isNotEmpty(agentVersion)) {
                metadata.add(DiscoveryMetaDataConstant.SPRING_APPLICATION_DISCOVERY_AGENT_VERSION + "=" + agentVersion);
            }
            metadata.add(DiscoveryMetaDataConstant.SPRING_APPLICATION_GROUP_KEY + "=" + groupKey);
            metadata.add(DiscoveryMetaDataConstant.SPRING_APPLICATION_CONTEXT_PATH + "=" + PluginContextAware.getContextPath(environment));

            try {
                ApplicationInfoAdapter applicationInfoAdapter = applicationContext.getBean(ApplicationInfoAdapter.class);
                if (applicationInfoAdapter != null) {
                    metadata.add(DiscoveryMetaDataConstant.SPRING_APPLICATION_APP_ID + "=" + applicationInfoAdapter.getAppId());
                }
            } catch (Exception e) {

            }

            for (Map.Entry<String, String> entry : DiscoveryMetaDataPreInstallation.getMetadata().entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (StringUtils.isNotEmpty(value)) {
                    metadata.add(key + "=" + value);
                }
            }

            MetadataUtil.filter(metadata, environment);

            return bean;
        } else {
            return bean;
        }
    }
}
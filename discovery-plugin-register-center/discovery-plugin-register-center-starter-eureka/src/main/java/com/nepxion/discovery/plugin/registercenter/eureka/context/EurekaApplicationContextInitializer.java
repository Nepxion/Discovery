package com.nepxion.discovery.plugin.registercenter.eureka.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringBootVersion;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaServiceRegistry;
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
import com.nepxion.discovery.plugin.registercenter.eureka.decorator.EurekaServiceRegistryDecorator;

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

            Map<String, String> metadata = eurekaInstanceConfig.getMetadataMap();

            String groupKey = PluginContextAware.getGroupKey(environment);
            /*if (!metadata.containsKey(groupKey)) {
                metadata.put(groupKey, DiscoveryConstant.DEFAULT);
            }
            if (!metadata.containsKey(DiscoveryConstant.VERSION)) {
                metadata.put(DiscoveryConstant.VERSION, DiscoveryConstant.DEFAULT);
            }
            if (!metadata.containsKey(DiscoveryConstant.REGION)) {
                metadata.put(DiscoveryConstant.REGION, DiscoveryConstant.DEFAULT);
            }
            if (!metadata.containsKey(DiscoveryConstant.ENVIRONMENT)) {
                metadata.put(DiscoveryConstant.ENVIRONMENT, DiscoveryConstant.DEFAULT);
            }
            if (!metadata.containsKey(DiscoveryConstant.ZONE)) {
                metadata.put(DiscoveryConstant.ZONE, DiscoveryConstant.DEFAULT);
            }
            if (!metadata.containsKey(DiscoveryConstant.ACTIVE)) {
                metadata.put(DiscoveryConstant.ACTIVE, "false");
            }*/
            String prefixGroup = getPrefixGroup(applicationContext);
            if (StringUtils.isNotEmpty(prefixGroup)) {
                metadata.put(groupKey, prefixGroup);
            }
            String gitVersion = getGitVersion(applicationContext);
            if (StringUtils.isNotEmpty(gitVersion)) {
                metadata.put(DiscoveryConstant.VERSION, gitVersion);
            }

            metadata.put(DiscoveryMetaDataConstant.SPRING_BOOT_VERSION, SpringBootVersion.getVersion());
            metadata.put(DiscoveryMetaDataConstant.SPRING_APPLICATION_NAME, PluginContextAware.getApplicationName(environment));
            metadata.put(DiscoveryMetaDataConstant.SPRING_APPLICATION_TYPE, PluginContextAware.getApplicationType(environment));
            String applicationGatewayType = PluginContextAware.getApplicationGatewayType(environment);
            if (StringUtils.isNotEmpty(applicationGatewayType)) {
                metadata.put(DiscoveryMetaDataConstant.SPRING_APPLICATION_GATEWAY_TYPE, applicationGatewayType);
            }
            metadata.put(DiscoveryMetaDataConstant.SPRING_APPLICATION_PROTOCOL, PluginContextAware.getApplicationProtocol(environment));
            metadata.put(DiscoveryMetaDataConstant.SPRING_APPLICATION_UUID, PluginContextAware.getApplicationUUId(environment));
            metadata.put(DiscoveryMetaDataConstant.SPRING_APPLICATION_DISCOVERY_PLUGIN, DiscoveryType.EUREKA.toString());
            metadata.put(DiscoveryMetaDataConstant.SPRING_APPLICATION_DISCOVERY_VERSION, DiscoveryConstant.DISCOVERY_VERSION);
            String agentVersion = System.getProperty(DiscoveryConstant.SPRING_APPLICATION_DISCOVERY_AGENT_VERSION);
            if (StringUtils.isNotEmpty(agentVersion)) {
                metadata.put(DiscoveryMetaDataConstant.SPRING_APPLICATION_DISCOVERY_AGENT_VERSION, agentVersion);
            }
            metadata.put(DiscoveryMetaDataConstant.SPRING_APPLICATION_GROUP_KEY, groupKey);
            metadata.put(DiscoveryMetaDataConstant.SPRING_APPLICATION_CONTEXT_PATH, PluginContextAware.getContextPath(environment));

            try {
                ApplicationInfoAdapter applicationInfoAdapter = applicationContext.getBean(ApplicationInfoAdapter.class);
                if (applicationInfoAdapter != null) {
                    metadata.put(DiscoveryMetaDataConstant.SPRING_APPLICATION_APP_ID, applicationInfoAdapter.getAppId());
                }
            } catch (Exception e) {

            }

            for (Map.Entry<String, String> entry : DiscoveryMetaDataPreInstallation.getMetadata().entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (StringUtils.isNotEmpty(value)) {
                    metadata.put(key, value);
                }
            }

            MetadataUtil.filter(metadata, environment);

            return bean;
        } else {
            return bean;
        }
    }
}
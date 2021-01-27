package com.nepxion.discovery.common.nacos.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.nepxion.discovery.common.nacos.constant.NacosConstant;
import com.nepxion.discovery.common.nacos.operation.NacosOperation;

@Configuration
public class NacosAutoConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    @ConditionalOnMissingBean
    public ConfigService nacosConfigService() throws NacosException {
        Properties properties = createNacosProperties(environment, true);

        return NacosFactory.createConfigService(properties);
    }

    @Bean
    public NacosOperation nacosOperation() {
        return new NacosOperation();
    }

    public static Properties createNacosProperties(Environment environment, boolean enableRemoteSyncConfig) {
        Properties properties = new Properties();

        String serverAddr = environment.getProperty(NacosConstant.NACOS_SERVER_ADDR);
        if (StringUtils.isNotEmpty(serverAddr)) {
            properties.put(NacosConstant.SERVER_ADDR, serverAddr);
        } else {
            throw new IllegalArgumentException(NacosConstant.NACOS_SERVER_ADDR + " can't be null or empty");
        }

        String accessKey = environment.getProperty(NacosConstant.NACOS_ACCESS_KEY);
        if (StringUtils.isNotEmpty(accessKey)) {
            properties.put(NacosConstant.ACCESS_KEY, accessKey);
        }

        String secretKey = environment.getProperty(NacosConstant.NACOS_SECRET_KEY);
        if (StringUtils.isNotEmpty(secretKey)) {
            properties.put(NacosConstant.SECRET_KEY, secretKey);
        }

        String username = environment.getProperty(NacosConstant.NACOS_USERNAME);
        if (StringUtils.isNotEmpty(username)) {
            properties.put(NacosConstant.USERNAME, username);
        }

        String password = environment.getProperty(NacosConstant.NACOS_PASSWORD);
        if (StringUtils.isNotEmpty(password)) {
            properties.put(NacosConstant.PASSWORD, password);
        }

        String namespace = environment.getProperty(NacosConstant.NACOS_PLUGIN_NAMESPACE);
        if (StringUtils.isNotEmpty(namespace)) {
            properties.put(NacosConstant.NAMESPACE, namespace);
        }

        String clusterName = environment.getProperty(NacosConstant.NACOS_PLUGIN_CLUSTER_NAME);
        if (StringUtils.isNotEmpty(clusterName)) {
            properties.put(NacosConstant.CLUSTER_NAME, clusterName);
        }

        String contextPath = environment.getProperty(NacosConstant.NACOS_PLUGIN_CONTEXT_PATH);
        if (StringUtils.isNotEmpty(contextPath)) {
            properties.put(NacosConstant.CONTEXT_PATH, contextPath);
        }

        String configLongPollTimeout = environment.getProperty(NacosConstant.NACOS_PLUGIN_CONFIG_LONG_POLL_TIMEOUT);
        if (StringUtils.isNotEmpty(configLongPollTimeout)) {
            properties.put(NacosConstant.CONFIG_LONG_POLL_TIMEOUT, configLongPollTimeout);
        }

        String configRetryTime = environment.getProperty(NacosConstant.NACOS_PLUGIN_CONFIG_RETRY_TIME);
        if (StringUtils.isNotEmpty(configRetryTime)) {
            properties.put(NacosConstant.CONFIG_RETRY_TIME, configRetryTime);
        }

        String maxRetry = environment.getProperty(NacosConstant.NACOS_PLUGIN_MAX_RETRY);
        if (StringUtils.isNotEmpty(maxRetry)) {
            properties.put(NacosConstant.MAX_RETRY, maxRetry);
        }

        String endpoint = environment.getProperty(NacosConstant.NACOS_PLUGIN_ENDPOINT);
        if (StringUtils.isNotEmpty(endpoint)) {
            properties.put(NacosConstant.ENDPOINT, endpoint);
        }

        String endpointPort = environment.getProperty(NacosConstant.NACOS_PLUGIN_ENDPOINT_PORT);
        if (StringUtils.isNotEmpty(endpointPort)) {
            properties.put(NacosConstant.ENDPOINT_PORT, endpointPort);
        }

        String isUseEndpointParsingRule = environment.getProperty(NacosConstant.NACOS_PLUGIN_IS_USE_ENDPOINT_PARSING_RULE);
        if (StringUtils.isNotEmpty(isUseEndpointParsingRule)) {
            properties.put(NacosConstant.IS_USE_ENDPOINT_PARSING_RULE, isUseEndpointParsingRule);
        }

        String isUseCloudNamespaceParsing = environment.getProperty(NacosConstant.NACOS_PLUGIN_IS_USE_CLOUD_NAMESPACE_PARSING);
        if (StringUtils.isNotEmpty(isUseCloudNamespaceParsing)) {
            properties.put(NacosConstant.IS_USE_CLOUD_NAMESPACE_PARSING, isUseCloudNamespaceParsing);
        }

        String encode = environment.getProperty(NacosConstant.NACOS_PLUGIN_ENCODE);
        if (StringUtils.isNotEmpty(encode)) {
            properties.put(NacosConstant.ENCODE, encode);
        }

        String namingLoadCacheAtStart = environment.getProperty(NacosConstant.NACOS_PLUGIN_NAMING_LOAD_CACHE_AT_START);
        if (StringUtils.isNotEmpty(namingLoadCacheAtStart)) {
            properties.put(NacosConstant.NAMING_LOAD_CACHE_AT_START, namingLoadCacheAtStart);
        }

        String namingClientBeatThreadCount = environment.getProperty(NacosConstant.NACOS_PLUGIN_NAMING_CLIENT_BEAT_THREAD_COUNT);
        if (StringUtils.isNotEmpty(namingClientBeatThreadCount)) {
            properties.put(NacosConstant.NAMING_CLIENT_BEAT_THREAD_COUNT, namingClientBeatThreadCount);
        }

        String namingPollingThreadCount = environment.getProperty(NacosConstant.NACOS_PLUGIN_NAMING_POLLING_THREAD_COUNT);
        if (StringUtils.isNotEmpty(namingPollingThreadCount)) {
            properties.put(NacosConstant.NAMING_POLLING_THREAD_COUNT, namingPollingThreadCount);
        }

        String namingRequestDomainRetryCount = environment.getProperty(NacosConstant.NACOS_PLUGIN_NAMING_REQUEST_DOMAIN_RETRY_COUNT);
        if (StringUtils.isNotEmpty(namingRequestDomainRetryCount)) {
            properties.put(NacosConstant.NAMING_REQUEST_DOMAIN_RETRY_COUNT, namingRequestDomainRetryCount);
        }

        String namingPushEmptyProtection = environment.getProperty(NacosConstant.NACOS_PLUGIN_NAMING_PUSH_EMPTY_PROTECTION);
        if (StringUtils.isNotEmpty(namingPushEmptyProtection)) {
            properties.put(NacosConstant.NAMING_PUSH_EMPTY_PROTECTION, namingPushEmptyProtection);
        }

        String ramRoleName = environment.getProperty(NacosConstant.NACOS_PLUGIN_RAM_ROLE_NAME);
        if (StringUtils.isNotEmpty(ramRoleName)) {
            properties.put(NacosConstant.RAM_ROLE_NAME, ramRoleName);
        }

        properties.put(NacosConstant.ENABLE_REMOTE_SYNC_CONFIG, Boolean.toString(enableRemoteSyncConfig));

        return properties;
    }
}
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

        String endpoint = environment.getProperty(NacosConstant.NACOS_PLUGIN_ENDPOINT);
        if (StringUtils.isNotEmpty(endpoint)) {
            properties.put(NacosConstant.ENDPOINT, endpoint);
        }

        String encode = environment.getProperty(NacosConstant.NACOS_PLUGIN_ENCODE);
        if (StringUtils.isNotEmpty(encode)) {
            properties.put(NacosConstant.ENCODE, encode);
        }

        String namingLoadCacheAtStart = environment.getProperty(NacosConstant.NACOS_PLUGIN_NAMING_LOAD_CACHE_AT_START);
        if (StringUtils.isNotEmpty(namingLoadCacheAtStart)) {
            properties.put(NacosConstant.NAMING_LOAD_CACHE_AT_START, namingLoadCacheAtStart);
        }

        return NacosFactory.createConfigService(properties);
    }

    @Bean
    public NacosOperation nacosOperation() {
        return new NacosOperation();
    }
}
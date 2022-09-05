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
import com.nepxion.discovery.common.util.PropertiesUtil;

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

        // 支持从spring.cloud.nacos.config前缀方式获取
        PropertiesUtil.enrichProperties(properties, environment, NacosConstant.SPRING_CLOUD_NACOS_CONFIG_PREFIX, true, true);

        // 支持从nacos前缀方式获取
        PropertiesUtil.enrichProperties(properties, environment, NacosConstant.NACOS_PREFIX, true, true);

        properties.put(NacosConstant.ENABLE_REMOTE_SYNC_CONFIG, Boolean.toString(enableRemoteSyncConfig));

        return properties;
    }
}
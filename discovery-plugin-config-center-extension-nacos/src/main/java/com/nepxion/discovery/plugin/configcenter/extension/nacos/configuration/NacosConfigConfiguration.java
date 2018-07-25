package com.nepxion.discovery.plugin.configcenter.extension.nacos.configuration;

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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.nepxion.discovery.plugin.configcenter.ConfigAdapter;
import com.nepxion.discovery.plugin.configcenter.extension.nacos.adapter.NacosConfigAdapter;
import com.nepxion.discovery.plugin.configcenter.extension.nacos.constant.NacosConstant;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;

@Configuration
public class NacosConfigConfiguration {
    @Autowired
    private PluginContextAware pluginContextAware;

    @Bean
    public ConfigService configService() throws NacosException {
        String url = pluginContextAware.getEnvironment().getProperty(NacosConstant.URL);

        Properties properties = new Properties();
        properties.put(NacosConstant.URL_KEY, url);

        return NacosFactory.createConfigService(properties);
    }

    @Bean
    public ConfigAdapter configAdapter() throws NacosException {
        return new NacosConfigAdapter();
    }
}
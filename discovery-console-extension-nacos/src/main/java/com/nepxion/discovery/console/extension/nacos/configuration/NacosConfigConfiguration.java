package com.nepxion.discovery.console.extension.nacos.configuration;

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
import org.springframework.core.env.Environment;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.nepxion.discovery.console.extension.nacos.adapter.NacosConfigAdapter;
import com.nepxion.discovery.console.extension.nacos.constant.NacosConstant;
import com.nepxion.discovery.console.remote.ConfigAdapter;

@Configuration
public class NacosConfigConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    public ConfigService configService() throws NacosException {
        String url = environment.getProperty(NacosConstant.URL);

        Properties properties = new Properties();
        properties.put(NacosConstant.URL_KEY, url);

        return NacosFactory.createConfigService(properties);
    }

    @Bean
    public ConfigAdapter configAdapter() throws NacosException {
        return new NacosConfigAdapter();
    }
}
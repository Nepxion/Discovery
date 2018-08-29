package com.nepxion.discovery.plugin.configcenter.nacos.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.discovery.plugin.configcenter.adapter.ConfigAdapter;
import com.nepxion.discovery.plugin.configcenter.nacos.adapter.NacosConfigAdapter;

@Configuration
public class NacosConfigAutoConfiguration {
    @Bean
    public ConfigAdapter configAdapter() {
        return new NacosConfigAdapter();
    }
}
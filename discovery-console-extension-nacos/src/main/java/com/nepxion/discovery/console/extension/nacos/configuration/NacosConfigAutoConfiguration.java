package com.nepxion.discovery.console.extension.nacos.configuration;

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

import com.nepxion.discovery.console.extension.nacos.adapter.NacosConfigAdapter;
import com.nepxion.discovery.console.remote.ConfigAdapter;

@Configuration
public class NacosConfigAutoConfiguration {
    @Bean
    public ConfigAdapter configAdapter() {
        return new NacosConfigAdapter();
    }
}
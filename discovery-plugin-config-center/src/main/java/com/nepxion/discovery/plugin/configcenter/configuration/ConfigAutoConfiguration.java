package com.nepxion.discovery.plugin.configcenter.configuration;

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

import com.nepxion.discovery.plugin.configcenter.ConfigInitializer;
import com.nepxion.discovery.plugin.configcenter.ConfigParser;

@Configuration
public class ConfigAutoConfiguration {
    @Bean
    public ConfigInitializer configInitializer() {
        return new ConfigInitializer();
    }

    @Bean
    public ConfigParser configParser() {
        return new ConfigParser();
    }
}
package com.nepxion.discovery.plugin.configcenter.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.discovery.plugin.configcenter.ConfigParser;
import com.nepxion.discovery.plugin.configcenter.ConfigPublisher;
import com.nepxion.discovery.plugin.configcenter.ConfigRetriever;

@Configuration
public class ConfigAutoConfiguration {
    @Autowired
    private ConfigRetriever configRetriever;

    @Bean
    public ConfigParser configParser() {
        return new ConfigParser();
    }

    @Bean
    public ConfigRetriever configRetriever() {
        return new ConfigRetriever();
    }

    @Bean
    public ConfigPublisher configPublisher() {
        return new ConfigPublisher();
    }

    @PostConstruct
    public void initialize() {
        configRetriever.initialize();
    }
}
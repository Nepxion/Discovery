package com.nepxion.discovery.plugin.configuration.config;

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

import com.nepxion.discovery.plugin.configuration.ConfigurationParser;
import com.nepxion.discovery.plugin.configuration.ConfigurationPublisher;
import com.nepxion.discovery.plugin.configuration.ConfigurationRetriever;

@Configuration
public class ConfigurationConfig {
    @Autowired
    private ConfigurationRetriever configurationRetriever;

    @Bean
    public ConfigurationParser configurationParser() {
        return new ConfigurationParser();
    }

    @Bean
    public ConfigurationRetriever configurationRetriever() {
        return new ConfigurationRetriever();
    }

    @Bean
    public ConfigurationPublisher configurationPoster() {
        return new ConfigurationPublisher();
    }

    @PostConstruct
    public void initialize() {
        configurationRetriever.initialize();
    }
}
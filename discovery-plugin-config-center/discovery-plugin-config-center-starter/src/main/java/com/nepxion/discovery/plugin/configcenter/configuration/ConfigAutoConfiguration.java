package com.nepxion.discovery.plugin.configcenter.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.discovery.common.entity.ConfigFormatType;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.plugin.configcenter.initializer.ConfigInitializer;
import com.nepxion.discovery.plugin.configcenter.loader.LocalConfigLoader;
import com.nepxion.discovery.plugin.configcenter.logger.ConfigLogger;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.parser.PluginConfigParser;
import com.nepxion.discovery.plugin.framework.parser.json.JsonConfigParser;
import com.nepxion.discovery.plugin.framework.parser.xml.XmlConfigParser;

@Configuration
public class ConfigAutoConfiguration {
    @Autowired
    private PluginContextAware pluginContextAware;

    @Bean
    public PluginConfigParser pluginConfigParser() {
        String configFormat = pluginContextAware.getConfigFormat();
        ConfigFormatType configFormatType = ConfigFormatType.fromString(configFormat);
        switch (configFormatType) {
            case XML_FORMAT:
                return new XmlConfigParser();
            case JSON_FORMAT:
                return new JsonConfigParser();
        }

        throw new DiscoveryException("Invalid config format for '" + configFormat + "'");
    }

    @Bean
    public LocalConfigLoader localConfigLoader() {
        return new LocalConfigLoader() {
            @Override
            protected String getPath() {
                return pluginContextAware.getConfigPath();
            }
        };
    }

    @Bean
    public ConfigInitializer configInitializer() {
        return new ConfigInitializer();
    }

    @Bean
    public ConfigLogger configLogger() {
        return new ConfigLogger();
    }
}
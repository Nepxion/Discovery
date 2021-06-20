package com.nepxion.discovery.plugin.framework.configuration;

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
import org.springframework.core.env.Environment;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.ConfigFormatType;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.plugin.framework.parser.PluginConfigDeparser;
import com.nepxion.discovery.plugin.framework.parser.PluginConfigParser;
import com.nepxion.discovery.plugin.framework.parser.json.JsonConfigDeparser;
import com.nepxion.discovery.plugin.framework.parser.json.JsonConfigParser;
import com.nepxion.discovery.plugin.framework.parser.xml.XmlConfigDeparser;
import com.nepxion.discovery.plugin.framework.parser.xml.XmlConfigParser;

@Configuration
public class PluginParserAutoConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    public PluginConfigParser pluginConfigParser() {
        String configFormat = environment.getProperty(DiscoveryConstant.SPRING_APPLICATION_CONFIG_FORMAT, String.class, DiscoveryConstant.XML_FORMAT);
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
    public PluginConfigDeparser pluginDeconfigParser() {
        String configFormat = environment.getProperty(DiscoveryConstant.SPRING_APPLICATION_CONFIG_FORMAT, String.class, DiscoveryConstant.XML_FORMAT);
        ConfigFormatType configFormatType = ConfigFormatType.fromString(configFormat);
        switch (configFormatType) {
            case XML_FORMAT:
                return new XmlConfigDeparser();
            case JSON_FORMAT:
                return new JsonConfigDeparser();
        }

        throw new DiscoveryException("Invalid config format for '" + configFormat + "'");
    }
}
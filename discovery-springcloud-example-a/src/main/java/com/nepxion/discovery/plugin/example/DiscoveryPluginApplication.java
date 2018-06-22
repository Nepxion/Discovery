package com.nepxion.discovery.plugin.example;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.nepxion.discovery.plugin.example.impl.DiscoveryPluginConfigSimulator;
import com.nepxion.discovery.plugin.example.impl.DiscoveryPluginFileLoader;

@SpringBootApplication
@EnableDiscoveryClient
public class DiscoveryPluginApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DiscoveryPluginApplication.class).web(true).run(args);
    }

    @Bean
    public DiscoveryPluginFileLoader discoveryPluginFileLoader() {
        return new DiscoveryPluginFileLoader();
    }

    @Bean
    public DiscoveryPluginConfigSimulator discoveryPluginConfigSimulator() {
        return new DiscoveryPluginConfigSimulator();
    }
}
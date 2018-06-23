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

import com.nepxion.discovery.plugin.example.impl.DiscoveryConfigSubscriber;
import com.nepxion.discovery.plugin.example.impl.DiscoveryConfigLoader;

@SpringBootApplication
@EnableDiscoveryClient
public class DiscoveryApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DiscoveryApplication.class).web(true).run(args);
    }

    @Bean
    public DiscoveryConfigLoader discoveryConfigLoader() {
        return new DiscoveryConfigLoader();
    }

    @Bean
    public DiscoveryConfigSubscriber discoveryConfigSubscriber() {
        return new DiscoveryConfigSubscriber();
    }
}
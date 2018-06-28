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

import com.nepxion.discovery.plugin.example.impl.DiscoveryConfigAdapter;

@SpringBootApplication
@EnableDiscoveryClient
public class DiscoveryApplicationB2 {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "2");

        new SpringApplicationBuilder(DiscoveryApplicationB2.class).web(true).run(args);
    }

    @Bean
    public DiscoveryConfigAdapter discoveryConfigLoader() {
        return new DiscoveryConfigAdapter();
    }
}
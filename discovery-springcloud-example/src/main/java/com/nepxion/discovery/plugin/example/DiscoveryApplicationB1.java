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
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.nepxion.discovery.plugin.example.adapter.DiscoveryConfigAdapter;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class DiscoveryApplicationB1 {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "b1");

        new SpringApplicationBuilder(DiscoveryApplicationB1.class).run(args);
    }

    @Bean
    public DiscoveryConfigAdapter discoveryConfigAdapter() {
        return new DiscoveryConfigAdapter();
    }
}
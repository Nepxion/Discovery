package com.nepxion.discovery.plugin.example.gateway;

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

import com.nepxion.discovery.plugin.example.gateway.extension.MyDiscoveryEnabledStrategy;

@SpringBootApplication
@EnableDiscoveryClient
public class DiscoveryApplicationGateway {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DiscoveryApplicationGateway.class).run(args);
    }

    @Bean
    public MyDiscoveryEnabledStrategy myDiscoveryEnabledStrategy() {
        return new MyDiscoveryEnabledStrategy();
    }

    /*@Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(r -> r.path("/discovery-springcloud-example-a/**").uri("lb://discovery-springcloud-example-a").id("discovery-springcloud-example-a"))
                .build();
    }*/
}
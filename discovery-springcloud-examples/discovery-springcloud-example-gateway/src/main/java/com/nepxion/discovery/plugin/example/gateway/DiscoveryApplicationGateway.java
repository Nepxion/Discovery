package com.nepxion.discovery.plugin.example.gateway;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author Ankeway
 * @version 1.0
 */

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.nepxion.discovery.plugin.example.gateway.impl.MyDiscoveryEnabledStrategy;
import com.nepxion.discovery.plugin.example.gateway.impl.MyDiscoveryListener;
import com.nepxion.discovery.plugin.example.gateway.impl.MyLoadBalanceListener;
import com.nepxion.discovery.plugin.example.gateway.impl.MyRegisterListener;
import com.nepxion.discovery.plugin.example.gateway.impl.MyRouteFilter;
import com.nepxion.discovery.plugin.strategy.gateway.filter.GatewayStrategyRouteFilter;

@SpringBootApplication
@EnableDiscoveryClient
public class DiscoveryApplicationGateway {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DiscoveryApplicationGateway.class).run(args);
    }

    @Bean
    public GatewayStrategyRouteFilter gatewayStrategyRouteFilter() {
        return new MyRouteFilter();
    }

    @Bean
    public MyDiscoveryEnabledStrategy myDiscoveryEnabledStrategy() {
        return new MyDiscoveryEnabledStrategy();
    }

    @Bean
    public MyRegisterListener myRegisterListener() {
        return new MyRegisterListener();
    }

    @Bean
    public MyDiscoveryListener myDiscoveryListener() {
        return new MyDiscoveryListener();
    }

    @Bean
    public MyLoadBalanceListener myLoadBalanceListener() {
        return new MyLoadBalanceListener();
    }

    /*@Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(r -> r.path("/discovery-springcloud-example-a/**").uri("lb://discovery-springcloud-example-a").id("discovery-springcloud-example-a"))
                .build();
    }*/
}
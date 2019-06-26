package com.nepxion.discovery.plugin.example.zuul;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.nepxion.discovery.plugin.example.zuul.impl.MyDiscoveryEnabledStrategy;
import com.nepxion.discovery.plugin.example.zuul.impl.MyDiscoveryListener;
import com.nepxion.discovery.plugin.example.zuul.impl.MyLoadBalanceListener;
import com.nepxion.discovery.plugin.example.zuul.impl.MyRegisterListener;
import com.nepxion.discovery.plugin.example.zuul.impl.MyRouteFilter;
import com.nepxion.discovery.plugin.strategy.zuul.constant.ZuulStrategyConstant;
import com.nepxion.discovery.plugin.strategy.zuul.filter.ZuulStrategyRouteFilter;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class DiscoveryApplicationZuul {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DiscoveryApplicationZuul.class).run(args);
    }

    @Bean
    @ConditionalOnProperty(value = ZuulStrategyConstant.SPRING_APPLICATION_STRATEGY_ZUUL_ROUTE_FILTER_ENABLED, matchIfMissing = true)
    public ZuulStrategyRouteFilter zuulStrategyRouteFilter() {
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
}
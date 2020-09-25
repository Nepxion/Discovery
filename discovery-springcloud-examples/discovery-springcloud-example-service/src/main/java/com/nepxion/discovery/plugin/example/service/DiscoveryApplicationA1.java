package com.nepxion.discovery.plugin.example.service;

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
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.nepxion.discovery.plugin.example.service.impl.MyDiscoveryEnabledStrategy;
import com.nepxion.discovery.plugin.example.service.impl.MyDiscoveryListener;
import com.nepxion.discovery.plugin.example.service.impl.MyLoadBalanceListener;
import com.nepxion.discovery.plugin.example.service.impl.MyRegisterListener;
import com.nepxion.discovery.plugin.example.service.impl.MySubscriber;
import com.nepxion.discovery.plugin.example.service.sentinel.MyRestTemplateBlockHandler;
import com.nepxion.discovery.plugin.example.service.sentinel.MyRestTemplateFallbackHandler;
import com.nepxion.discovery.plugin.example.service.sentinel.MySentinelFlowRuleParser;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
// Hystrix测试
// @EnableCircuitBreaker
public class DiscoveryApplicationA1 {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "a1");

        new SpringApplicationBuilder(DiscoveryApplicationA1.class).run(args);
    }

    @Bean
    @LoadBalanced
    @SentinelRestTemplate(blockHandler = "handleBlock", blockHandlerClass = MyRestTemplateBlockHandler.class, fallback = "handleFallback", fallbackClass = MyRestTemplateFallbackHandler.class)
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public MySentinelFlowRuleParser mySentinelFlowRuleParser() {
        return new MySentinelFlowRuleParser();
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

    @Bean
    public MySubscriber mySubscriber() {
        return new MySubscriber();
    }
}
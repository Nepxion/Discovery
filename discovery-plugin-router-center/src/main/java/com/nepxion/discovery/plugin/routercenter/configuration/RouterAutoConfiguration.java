package com.nepxion.discovery.plugin.routercenter.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.plugin.routercenter.controller.RouterController;

@Configuration
@ComponentScan(basePackages = { "com.nepxion.discovery.plugin.routercenter.controller" })
@Import(SwaggerConfiguration.class)
public class RouterAutoConfiguration {
    @Bean
    public RestTemplate routerRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RouterController routerController() {
        return new RouterController();
    }
}
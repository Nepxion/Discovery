package com.nepxion.discovery.plugin.example.zuul;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.nepxion.discovery.plugin.example.zuul.impl.MyDiscoveryEnabledStrategy;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class DiscoveryApplicationZuul {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DiscoveryApplicationZuul.class).run(args);
    }

    @Bean
    public MyDiscoveryEnabledStrategy myDiscoveryEnabledStrategy() {
        return new MyDiscoveryEnabledStrategy();
    }

    @Bean(name = "hystrixRegistrationBean")
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean registration = new ServletRegistrationBean(
                new HystrixMetricsStreamServlet(), "/hystrix.stream");
        registration.setName("hystrixServlet");
        registration.setLoadOnStartup(1);
        return registration;
    }

    /*@Bean
    public MyZuulFilter myZuulFilter() {
        return new MyZuulFilter();
    }*/
}
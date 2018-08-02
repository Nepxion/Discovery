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
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.nepxion.discovery.plugin.example.zuul.extension.MyDiscoveryEnabledAdapter;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class DiscoveryApplicationZuul {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DiscoveryApplicationZuul.class).run(args);
    }

    @Configuration
    public static class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.authorizeRequests().anyRequest().permitAll().and().csrf().disable();
        }
    }

    @Bean
    public MyDiscoveryEnabledAdapter myDiscoveryEnabledAdapter() {
        return new MyDiscoveryEnabledAdapter();
    }
}
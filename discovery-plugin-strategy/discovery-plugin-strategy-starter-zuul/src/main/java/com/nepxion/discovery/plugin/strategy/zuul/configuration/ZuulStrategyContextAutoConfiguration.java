package com.nepxion.discovery.plugin.strategy.zuul.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.discovery.plugin.strategy.zuul.context.ZuulStrategyContextHolder;

@Configuration
@AutoConfigureBefore(RibbonClientConfiguration.class)
public class ZuulStrategyContextAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ZuulStrategyContextHolder zuulStrategyContextHolder() {
        return new ZuulStrategyContextHolder();
    }
}
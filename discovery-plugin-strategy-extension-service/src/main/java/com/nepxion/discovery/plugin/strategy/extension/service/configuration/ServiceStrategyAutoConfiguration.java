package com.nepxion.discovery.plugin.strategy.extension.service.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.extension.service.aop.ServiceStrategyAutoScanProxy;
import com.nepxion.discovery.plugin.strategy.extension.service.aop.ServiceStrategyInterceptor;
import com.nepxion.discovery.plugin.strategy.extension.service.constant.ServiceStrategyConstant;

@Configuration
@AutoConfigureBefore(RibbonClientConfiguration.class)
@ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_CONTROL_ENABLED, matchIfMissing = true)
public class ServiceStrategyAutoConfiguration {
    @Value("${" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES + ":}")
    private String scanPackages;

    @Bean
    @ConditionalOnProperty(value = ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_BUSINESS_CONTEXT_CONTROL_ENABLED, matchIfMissing = true)
    public ServiceStrategyAutoScanProxy serviceStrategyAutoScanProxy() {
        return new ServiceStrategyAutoScanProxy(scanPackages);
    }

    @Bean
    @ConditionalOnProperty(value = ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_BUSINESS_CONTEXT_CONTROL_ENABLED, matchIfMissing = true)
    public ServiceStrategyInterceptor serviceStrategyInterceptor() {
        return new ServiceStrategyInterceptor();
    }
}
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

import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.extension.service.aop.FeignStrategyInterceptor;
import com.nepxion.discovery.plugin.strategy.extension.service.aop.ServiceStrategyAutoScanProxy;
import com.nepxion.discovery.plugin.strategy.extension.service.aop.ServiceStrategyInterceptor;
import com.nepxion.discovery.plugin.strategy.extension.service.constant.ServiceStrategyConstant;

@Configuration
@AutoConfigureBefore(RibbonClientConfiguration.class)
@ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_CONTROL_ENABLED, matchIfMissing = true)
public class ServiceStrategyAutoConfiguration {
    @Value("${" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES + "}")
    private String scanPackages;

    @Value("${" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_FEIGN_HEADERS + "}")
    private String feignHeaders;

    @Bean
    @ConditionalOnProperty(value = ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES, matchIfMissing = false)
    public ServiceStrategyAutoScanProxy serviceStrategyAutoScanProxy() {
        if (ServiceStrategyConstant.EXCLUSION_SCAN_PACKAGES.contains(scanPackages)) {
            throw new DiscoveryException("It can't scan packages for '" + ServiceStrategyConstant.EXCLUSION_SCAN_PACKAGES + "', please check '" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES + "'");
        }

        return new ServiceStrategyAutoScanProxy(scanPackages);
    }

    @Bean
    @ConditionalOnProperty(value = ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES, matchIfMissing = false)
    public ServiceStrategyInterceptor serviceStrategyInterceptor() {
        if (ServiceStrategyConstant.EXCLUSION_SCAN_PACKAGES.contains(scanPackages)) {
            throw new DiscoveryException("It can't scan packages for '" + ServiceStrategyConstant.EXCLUSION_SCAN_PACKAGES + "', please check '" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES + "'");
        }

        return new ServiceStrategyInterceptor();
    }

    @ConditionalOnProperty(value = ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_FEIGN_HEADERS, matchIfMissing = false)
    @Bean
    public FeignStrategyInterceptor feignStrategyInterceptor() {
        return new FeignStrategyInterceptor(feignHeaders);
    }
}
package com.nepxion.discovery.plugin.strategy.service.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.plugin.strategy.adapter.DiscoveryEnabledAdapter;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.adapter.DefaultDiscoveryEnabledAdapter;
import com.nepxion.discovery.plugin.strategy.service.aop.FeignStrategyInterceptor;
import com.nepxion.discovery.plugin.strategy.service.aop.RestTemplateStrategyInterceptor;
import com.nepxion.discovery.plugin.strategy.service.aop.RpcStrategyAutoScanProxy;
import com.nepxion.discovery.plugin.strategy.service.aop.RpcStrategyInterceptor;
import com.nepxion.discovery.plugin.strategy.service.constant.ServiceStrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.isolation.ProviderIsolationStrategyAutoScanProxy;
import com.nepxion.discovery.plugin.strategy.service.isolation.ProviderIsolationStrategyInterceptor;
import com.nepxion.discovery.plugin.strategy.service.wrapper.DefaultCallableWrapper;
import com.nepxion.discovery.plugin.strategy.wrapper.CallableWrapper;

@Configuration
@AutoConfigureBefore(RibbonClientConfiguration.class)
@ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_CONTROL_ENABLED, matchIfMissing = true)
public class ServiceStrategyAutoConfiguration {
    @Autowired
    private ConfigurableEnvironment environment;

    @Bean
    @ConditionalOnProperty(value = ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_RPC_INTERCEPT_ENABLED, matchIfMissing = false)
    public RpcStrategyAutoScanProxy rpcStrategyAutoScanProxy() {
        String scanPackages = environment.getProperty(ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES);
        if (StringUtils.isEmpty(scanPackages)) {
            throw new DiscoveryException(ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES + "'s value can't be empty");
        }

        if (ServiceStrategyConstant.EXCLUSION_SCAN_PACKAGES.contains(scanPackages)) {
            throw new DiscoveryException("It can't scan packages for '" + ServiceStrategyConstant.EXCLUSION_SCAN_PACKAGES + "', please check '" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES + "'");
        }

        return new RpcStrategyAutoScanProxy(scanPackages);
    }

    @Bean
    @ConditionalOnProperty(value = ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_RPC_INTERCEPT_ENABLED, matchIfMissing = false)
    public RpcStrategyInterceptor rpcStrategyInterceptor() {
        String scanPackages = environment.getProperty(ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES);
        if (StringUtils.isEmpty(scanPackages)) {
            throw new DiscoveryException(ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES + "'s value can't be empty");
        }

        if (ServiceStrategyConstant.EXCLUSION_SCAN_PACKAGES.contains(scanPackages)) {
            throw new DiscoveryException("It can't scan packages for '" + ServiceStrategyConstant.EXCLUSION_SCAN_PACKAGES + "', please check '" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES + "'");
        }

        return new RpcStrategyInterceptor();
    }

    @Bean
    @ConditionalOnProperty(value = ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_REST_INTERCEPT_ENABLED, matchIfMissing = false)
    public FeignStrategyInterceptor feignStrategyInterceptor() {
        String requestHeaders = environment.getProperty(ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_REQUEST_HEADERS);

        return new FeignStrategyInterceptor(requestHeaders);
    }

    @Bean
    @ConditionalOnProperty(value = ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_REST_INTERCEPT_ENABLED, matchIfMissing = false)
    public RestTemplateStrategyInterceptor restTemplateStrategyInterceptor() {
        String requestHeaders = environment.getProperty(ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_REQUEST_HEADERS);

        return new RestTemplateStrategyInterceptor(requestHeaders);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_REST_INTERCEPT_ENABLED, matchIfMissing = false)
    public DiscoveryEnabledAdapter discoveryEnabledAdapter() {
        return new DefaultDiscoveryEnabledAdapter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_PROVIDER_ISOLATION_ENABLED, matchIfMissing = false)
    public ProviderIsolationStrategyAutoScanProxy providerIsolationStrategyAutoScanProxy() {
        String scanPackages = environment.getProperty(ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES);
        if (StringUtils.isEmpty(scanPackages)) {
            throw new DiscoveryException(ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES + "'s value can't be empty");
        }

        if (ServiceStrategyConstant.EXCLUSION_SCAN_PACKAGES.contains(scanPackages)) {
            throw new DiscoveryException("It can't scan packages for '" + ServiceStrategyConstant.EXCLUSION_SCAN_PACKAGES + "', please check '" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES + "'");
        }

        return new ProviderIsolationStrategyAutoScanProxy(scanPackages);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_PROVIDER_ISOLATION_ENABLED, matchIfMissing = false)
    public ProviderIsolationStrategyInterceptor providerIsolationStrategyInterceptor() {
        String scanPackages = environment.getProperty(ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES);
        if (StringUtils.isEmpty(scanPackages)) {
            throw new DiscoveryException(ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES + "'s value can't be empty");
        }

        if (ServiceStrategyConstant.EXCLUSION_SCAN_PACKAGES.contains(scanPackages)) {
            throw new DiscoveryException("It can't scan packages for '" + ServiceStrategyConstant.EXCLUSION_SCAN_PACKAGES + "', please check '" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES + "'");
        }

        return new ProviderIsolationStrategyInterceptor();
    }

    @Bean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_HYSTRIX_THREADLOCAL_SUPPORTED, matchIfMissing = false)
    public CallableWrapper callableWrapper() {
        return new DefaultCallableWrapper();
    }
}
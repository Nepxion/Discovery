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

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.plugin.strategy.adapter.DefaultDiscoveryEnabledAdapter;
import com.nepxion.discovery.plugin.strategy.adapter.DiscoveryEnabledAdapter;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.aop.FeignStrategyInterceptor;
import com.nepxion.discovery.plugin.strategy.service.aop.RestTemplateStrategyBeanPostProcessor;
import com.nepxion.discovery.plugin.strategy.service.aop.RestTemplateStrategyInterceptor;
import com.nepxion.discovery.plugin.strategy.service.aop.RpcStrategyAutoScanProxy;
import com.nepxion.discovery.plugin.strategy.service.aop.RpcStrategyInterceptor;
import com.nepxion.discovery.plugin.strategy.service.constant.ServiceStrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.filter.DefaultServiceStrategyRouteFilter;
import com.nepxion.discovery.plugin.strategy.service.filter.ServiceStrategyRouteFilter;
import com.nepxion.discovery.plugin.strategy.service.isolation.ProviderIsolationStrategyAutoScanProxy;
import com.nepxion.discovery.plugin.strategy.service.isolation.ProviderIsolationStrategyInterceptor;
import com.nepxion.discovery.plugin.strategy.service.monitor.DefaultServiceStrategyMonitor;
import com.nepxion.discovery.plugin.strategy.service.monitor.ServiceStrategyMonitor;
import com.nepxion.discovery.plugin.strategy.service.monitor.ServiceStrategyMonitorAutoScanProxy;
import com.nepxion.discovery.plugin.strategy.service.monitor.ServiceStrategyMonitorInterceptor;
import com.nepxion.discovery.plugin.strategy.service.wrapper.DefaultServiceStrategyCallableWrapper;
import com.nepxion.discovery.plugin.strategy.service.wrapper.ServiceStrategyCallableWrapper;

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

        if (scanPackages.contains(DiscoveryConstant.ENDPOINT_SCAN_PACKAGES)) {
            throw new DiscoveryException("It can't scan packages for '" + DiscoveryConstant.ENDPOINT_SCAN_PACKAGES + "', please check '" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES + "'");
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

        if (scanPackages.contains(DiscoveryConstant.ENDPOINT_SCAN_PACKAGES)) {
            throw new DiscoveryException("It can't scan packages for '" + DiscoveryConstant.ENDPOINT_SCAN_PACKAGES + "', please check '" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES + "'");
        }

        return new RpcStrategyInterceptor();
    }

    @Bean
    @ConditionalOnProperty(value = ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_REST_INTERCEPT_ENABLED, matchIfMissing = true)
    public FeignStrategyInterceptor feignStrategyInterceptor() {
        String contextRequestHeaders = environment.getProperty(ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_CONTEXT_REQUEST_HEADERS);
        String businessRequestHeaders = environment.getProperty(ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_BUSINESS_REQUEST_HEADERS);

        return new FeignStrategyInterceptor(contextRequestHeaders, businessRequestHeaders);
    }

    @Bean
    @ConditionalOnProperty(value = ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_REST_INTERCEPT_ENABLED, matchIfMissing = true)
    public RestTemplateStrategyInterceptor restTemplateStrategyInterceptor() {
        String contextRequestHeaders = environment.getProperty(ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_CONTEXT_REQUEST_HEADERS);
        String businessRequestHeaders = environment.getProperty(ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_BUSINESS_REQUEST_HEADERS);

        return new RestTemplateStrategyInterceptor(contextRequestHeaders, businessRequestHeaders);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_REST_INTERCEPT_ENABLED, matchIfMissing = true)
    public RestTemplateStrategyBeanPostProcessor restTemplateStrategyBeanPostProcessor() {
        return new RestTemplateStrategyBeanPostProcessor();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_REST_INTERCEPT_ENABLED, matchIfMissing = true)
    public DiscoveryEnabledAdapter discoveryEnabledAdapter() {
        return new DefaultDiscoveryEnabledAdapter();
    }

    @Bean
    @ConditionalOnMissingBean
    public ServiceStrategyRouteFilter serviceStrategyRouteFilter() {
        return new DefaultServiceStrategyRouteFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_MONITOR_ENABLED, matchIfMissing = false)
    public ServiceStrategyMonitor serviceStrategyMonitor() {
        return new DefaultServiceStrategyMonitor();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_PROVIDER_ISOLATION_ENABLED, matchIfMissing = false)
    public ProviderIsolationStrategyAutoScanProxy providerIsolationStrategyAutoScanProxy() {
        String scanPackages = environment.getProperty(ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES);
        if (StringUtils.isEmpty(scanPackages)) {
            throw new DiscoveryException(ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES + "'s value can't be empty");
        }

        if (scanPackages.contains(DiscoveryConstant.ENDPOINT_SCAN_PACKAGES)) {
            throw new DiscoveryException("It can't scan packages for '" + DiscoveryConstant.ENDPOINT_SCAN_PACKAGES + "', please check '" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES + "'");
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

        if (scanPackages.contains(DiscoveryConstant.ENDPOINT_SCAN_PACKAGES)) {
            throw new DiscoveryException("It can't scan packages for '" + DiscoveryConstant.ENDPOINT_SCAN_PACKAGES + "', please check '" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES + "'");
        }

        return new ProviderIsolationStrategyInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_MONITOR_ENABLED, matchIfMissing = false)
    public ServiceStrategyMonitorAutoScanProxy serviceStrategyMonitorAutoScanProxy() {
        String scanPackages = environment.getProperty(ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES);
        if (StringUtils.isEmpty(scanPackages)) {
            throw new DiscoveryException(ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES + "'s value can't be empty");
        }

        if (scanPackages.contains(DiscoveryConstant.ENDPOINT_SCAN_PACKAGES)) {
            throw new DiscoveryException("It can't scan packages for '" + DiscoveryConstant.ENDPOINT_SCAN_PACKAGES + "', please check '" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES + "'");
        }

        return new ServiceStrategyMonitorAutoScanProxy(scanPackages.endsWith(DiscoveryConstant.SEPARATE) ? (scanPackages + DiscoveryConstant.ENDPOINT_SCAN_PACKAGES) : (scanPackages + DiscoveryConstant.SEPARATE + DiscoveryConstant.ENDPOINT_SCAN_PACKAGES));
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_MONITOR_ENABLED, matchIfMissing = false)
    public ServiceStrategyMonitorInterceptor serviceStrategyMonitorInterceptor() {
        String scanPackages = environment.getProperty(ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES);
        if (StringUtils.isEmpty(scanPackages)) {
            throw new DiscoveryException(ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES + "'s value can't be empty");
        }

        if (scanPackages.contains(DiscoveryConstant.ENDPOINT_SCAN_PACKAGES)) {
            throw new DiscoveryException("It can't scan packages for '" + DiscoveryConstant.ENDPOINT_SCAN_PACKAGES + "', please check '" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES + "'");
        }

        return new ServiceStrategyMonitorInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_HYSTRIX_THREADLOCAL_SUPPORTED, matchIfMissing = false)
    public ServiceStrategyCallableWrapper serviceStrategyCallableWrapper() {
        return new DefaultServiceStrategyCallableWrapper();
    }
}
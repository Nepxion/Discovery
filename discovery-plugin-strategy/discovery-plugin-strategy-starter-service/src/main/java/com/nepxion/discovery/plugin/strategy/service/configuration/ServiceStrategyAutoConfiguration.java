package com.nepxion.discovery.plugin.strategy.service.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
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
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.extractor.StrategyPackagesExtractor;
import com.nepxion.discovery.plugin.strategy.monitor.StrategyMonitorPackagesInjector;
import com.nepxion.discovery.plugin.strategy.service.constant.ServiceStrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.context.ServiceStrategyContextListener;
import com.nepxion.discovery.plugin.strategy.service.filter.DefaultServiceStrategyFilterExclusion;
import com.nepxion.discovery.plugin.strategy.service.filter.DefaultServiceStrategyRouteFilter;
import com.nepxion.discovery.plugin.strategy.service.filter.ServiceStrategyFilterExclusion;
import com.nepxion.discovery.plugin.strategy.service.filter.ServiceStrategyRouteFilter;
import com.nepxion.discovery.plugin.strategy.service.isolation.ServiceProviderIsolationStrategyAutoScanProxy;
import com.nepxion.discovery.plugin.strategy.service.isolation.ServiceProviderIsolationStrategyInterceptor;
import com.nepxion.discovery.plugin.strategy.service.monitor.DefaultServiceStrategyMonitor;
import com.nepxion.discovery.plugin.strategy.service.monitor.ServiceStrategyMonitor;
import com.nepxion.discovery.plugin.strategy.service.monitor.ServiceStrategyMonitorAutoScanProxy;
import com.nepxion.discovery.plugin.strategy.service.monitor.ServiceStrategyMonitorInterceptor;
import com.nepxion.discovery.plugin.strategy.service.rpc.ServiceRpcStrategyAutoScanProxy;
import com.nepxion.discovery.plugin.strategy.service.rpc.ServiceRpcStrategyInterceptor;
import com.nepxion.discovery.plugin.strategy.service.wrapper.DefaultServiceStrategyCallableWrapper;
import com.nepxion.discovery.plugin.strategy.service.wrapper.ServiceStrategyCallableWrapper;

@Configuration
@AutoConfigureBefore(RibbonClientConfiguration.class)
public class ServiceStrategyAutoConfiguration {
    @Autowired
    private ConfigurableEnvironment environment;

    @Autowired
    private StrategyPackagesExtractor strategyPackagesExtractor;

    @Autowired(required = false)
    private List<StrategyMonitorPackagesInjector> strategyMonitorPackagesInjectorList;

    @Bean
    @ConditionalOnProperty(value = ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_RPC_INTERCEPT_ENABLED, matchIfMissing = false)
    public ServiceRpcStrategyAutoScanProxy serviceRpcStrategyAutoScanProxy() {
        String scanPackages = getScanPackages();

        scanPackages = assembleInjectorScanPackages(scanPackages);

        return new ServiceRpcStrategyAutoScanProxy(scanPackages);
    }

    @Bean
    @ConditionalOnProperty(value = ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_RPC_INTERCEPT_ENABLED, matchIfMissing = false)
    public ServiceRpcStrategyInterceptor serviceRpcStrategyInterceptor() {
        return new ServiceRpcStrategyInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_PROVIDER_ISOLATION_ENABLED, matchIfMissing = false)
    public ServiceProviderIsolationStrategyAutoScanProxy serviceProviderIsolationStrategyAutoScanProxy() {
        String scanPackages = getScanPackages();

        return new ServiceProviderIsolationStrategyAutoScanProxy(scanPackages);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_PROVIDER_ISOLATION_ENABLED, matchIfMissing = false)
    public ServiceProviderIsolationStrategyInterceptor serviceProviderIsolationStrategyInterceptor() {
        return new ServiceProviderIsolationStrategyInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean
    public ServiceStrategyRouteFilter serviceStrategyRouteFilter() {
        return new DefaultServiceStrategyRouteFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public ServiceStrategyFilterExclusion serviceStrategyFilterExclusion() {
        return new DefaultServiceStrategyFilterExclusion();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_MONITOR_ENABLED, matchIfMissing = false)
    public ServiceStrategyMonitor serviceStrategyMonitor() {
        return new DefaultServiceStrategyMonitor();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_MONITOR_ENABLED, matchIfMissing = false)
    public ServiceStrategyMonitorAutoScanProxy serviceStrategyMonitorAutoScanProxy() {
        String scanPackages = getScanPackages();

        scanPackages = assembleInjectorScanPackages(scanPackages);

        scanPackages = assembleEndpointScanPackages(scanPackages);

        return new ServiceStrategyMonitorAutoScanProxy(scanPackages);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_MONITOR_ENABLED, matchIfMissing = false)
    public ServiceStrategyMonitorInterceptor serviceStrategyMonitorInterceptor() {
        return new ServiceStrategyMonitorInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_HYSTRIX_THREADLOCAL_SUPPORTED, matchIfMissing = false)
    public ServiceStrategyCallableWrapper serviceStrategyCallableWrapper() {
        return new DefaultServiceStrategyCallableWrapper();
    }

    @Bean
    public ServiceStrategyContextListener serviceStrategyContextListener() {
        return new ServiceStrategyContextListener();
    }

    public String getScanPackages() {
        String scanPackages = environment.getProperty(ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES, StringUtils.EMPTY);
        if (StringUtils.isEmpty(scanPackages)) {
            scanPackages = strategyPackagesExtractor.getAllPackages();
        }

        if (StringUtils.isEmpty(scanPackages)) {
            throw new DiscoveryException(ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES + "'s value can't be empty");
        }

        if (scanPackages.contains(DiscoveryConstant.ENDPOINT_SCAN_PACKAGES)) {
            throw new DiscoveryException("It can't set scan packages for '" + DiscoveryConstant.ENDPOINT_SCAN_PACKAGES + "', please check '" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SCAN_PACKAGES + "'");
        }

        return scanPackages;
    }

    public String assembleInjectorScanPackages(String scanPackages) {
        if (CollectionUtils.isNotEmpty(strategyMonitorPackagesInjectorList)) {
            for (StrategyMonitorPackagesInjector strategyMonitorPackagesInjector : strategyMonitorPackagesInjectorList) {
                List<String> monitorScanPackages = strategyMonitorPackagesInjector.getScanPackages();
                if (CollectionUtils.isNotEmpty(monitorScanPackages)) {
                    for (String monitorScanPackage : monitorScanPackages) {
                        if (!scanPackages.contains(monitorScanPackage)) {
                            scanPackages += scanPackages.endsWith(DiscoveryConstant.SEPARATE) ? monitorScanPackage : DiscoveryConstant.SEPARATE + monitorScanPackage;
                        }
                    }
                }
            }
        }

        return scanPackages;
    }

    public String assembleEndpointScanPackages(String scanPackages) {
        scanPackages += scanPackages.endsWith(DiscoveryConstant.SEPARATE) ? DiscoveryConstant.ENDPOINT_SCAN_PACKAGES : DiscoveryConstant.SEPARATE + DiscoveryConstant.ENDPOINT_SCAN_PACKAGES;

        return scanPackages;
    }
}
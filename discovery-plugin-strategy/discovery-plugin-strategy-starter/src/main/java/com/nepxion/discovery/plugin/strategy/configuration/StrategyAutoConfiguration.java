package com.nepxion.discovery.plugin.strategy.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import feign.Feign;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.TypeComparator;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.nepxion.discovery.plugin.strategy.adapter.DefaultDiscoveryEnabledAdapter;
import com.nepxion.discovery.plugin.strategy.adapter.DefaultStrategyVersionFilterAdapter;
import com.nepxion.discovery.plugin.strategy.adapter.DiscoveryEnabledAdapter;
import com.nepxion.discovery.plugin.strategy.adapter.StrategyVersionFilterAdapter;
import com.nepxion.discovery.plugin.strategy.aop.FeignStrategyInterceptor;
import com.nepxion.discovery.plugin.strategy.aop.RestTemplateStrategyBeanPostProcessor;
import com.nepxion.discovery.plugin.strategy.aop.RestTemplateStrategyInterceptor;
import com.nepxion.discovery.plugin.strategy.aop.WebClientStrategyBeanPostProcessor;
import com.nepxion.discovery.plugin.strategy.aop.WebClientStrategyInterceptor;
import com.nepxion.discovery.plugin.strategy.condition.DefaultStrategyTypeComparator;
import com.nepxion.discovery.plugin.strategy.condition.ExpressionStrategyCondition;
import com.nepxion.discovery.plugin.strategy.condition.StrategyCondition;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.context.StrategyHeaderContext;
import com.nepxion.discovery.plugin.strategy.extractor.StrategyPackagesExtractor;
import com.nepxion.discovery.plugin.strategy.filter.StrategyAddressBlacklistEnabledFilter;
import com.nepxion.discovery.plugin.strategy.filter.StrategyAddressEnabledFilter;
import com.nepxion.discovery.plugin.strategy.filter.StrategyEnvironmentEnabledFilter;
import com.nepxion.discovery.plugin.strategy.filter.StrategyGroupEnabledFilter;
import com.nepxion.discovery.plugin.strategy.filter.StrategyIdBlacklistEnabledFilter;
import com.nepxion.discovery.plugin.strategy.filter.StrategyRegionEnabledFilter;
import com.nepxion.discovery.plugin.strategy.filter.StrategyVersionEnabledFilter;
import com.nepxion.discovery.plugin.strategy.filter.StrategyZoneEnabledFilter;
import com.nepxion.discovery.plugin.strategy.matcher.DiscoveryAntPathMatcherStrategy;
import com.nepxion.discovery.plugin.strategy.matcher.DiscoveryMatcher;
import com.nepxion.discovery.plugin.strategy.matcher.DiscoveryMatcherStrategy;
import com.nepxion.discovery.plugin.strategy.monitor.DefaultStrategyAlarm;
import com.nepxion.discovery.plugin.strategy.monitor.DefaultStrategyLogger;
import com.nepxion.discovery.plugin.strategy.monitor.StrategyAlarm;
import com.nepxion.discovery.plugin.strategy.monitor.StrategyLogger;
import com.nepxion.discovery.plugin.strategy.monitor.StrategyMonitorContext;
import com.nepxion.discovery.plugin.strategy.monitor.StrategyTracerContextListener;
import com.nepxion.discovery.plugin.strategy.wrapper.StrategyWrapper;

@Configuration
@RibbonClients(defaultConfiguration = { StrategyLoadBalanceConfiguration.class })
public class StrategyAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public StrategyIdBlacklistEnabledFilter strategyIdBlacklistEnabledFilter() {
        return new StrategyIdBlacklistEnabledFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public StrategyAddressBlacklistEnabledFilter strategyAddressBlacklistEnabledFilter() {
        return new StrategyAddressBlacklistEnabledFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public StrategyGroupEnabledFilter strategyGroupEnabledFilter() {
        return new StrategyGroupEnabledFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public StrategyEnvironmentEnabledFilter strategyEnvironmentEnabledFilter() {
        return new StrategyEnvironmentEnabledFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public StrategyZoneEnabledFilter strategyZoneEnabledFilter() {
        return new StrategyZoneEnabledFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public StrategyAddressEnabledFilter strategyAddressEnabledFilter() {
        return new StrategyAddressEnabledFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public StrategyRegionEnabledFilter strategyRegionEnabledFilter() {
        return new StrategyRegionEnabledFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public StrategyVersionEnabledFilter strategyVersionEnabledFilter() {
        return new StrategyVersionEnabledFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public DiscoveryEnabledAdapter discoveryEnabledAdapter() {
        return new DefaultDiscoveryEnabledAdapter();
    }

    @Bean
    @ConditionalOnMissingBean
    public DiscoveryMatcherStrategy discoveryMatcherStrategy() {
        return new DiscoveryAntPathMatcherStrategy();
    }

    @Bean
    @ConditionalOnMissingBean
    public DiscoveryMatcher discoveryMatcher() {
        return new DiscoveryMatcher();
    }

    @Bean
    @ConditionalOnMissingBean
    public StrategyVersionFilterAdapter strategyVersionFilterAdapter() {
        return new DefaultStrategyVersionFilterAdapter();
    }

    @Bean
    public StrategyHeaderContext strategyHeaderContext() {
        return new StrategyHeaderContext();
    }

    @Bean
    public StrategyPackagesExtractor strategyPackagesExtractor() {
        return new StrategyPackagesExtractor();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_MONITOR_ENABLED, matchIfMissing = false)
    public StrategyMonitorContext strategyMonitorContext() {
        return new StrategyMonitorContext();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_MONITOR_ENABLED, matchIfMissing = false)
    public StrategyLogger strategyLogger() {
        return new DefaultStrategyLogger();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_MONITOR_ENABLED, matchIfMissing = false)
    public StrategyAlarm strategyAlarm() {
        return new DefaultStrategyAlarm();
    }

    @Bean
    @ConditionalOnMissingBean
    public StrategyWrapper strategyWrapper() {
        return new StrategyWrapper();
    }

    @Bean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_MONITOR_ENABLED, matchIfMissing = false)
    public StrategyTracerContextListener strategyTracerContextListener() {
        return new StrategyTracerContextListener();
    }

    @Bean
    @ConditionalOnMissingBean
    public TypeComparator strategyTypeComparator() {
        return new DefaultStrategyTypeComparator();
    }

    @Bean
    @ConditionalOnMissingBean
    public StrategyCondition strategyCondition() {
        return new ExpressionStrategyCondition();
    }

    @ConditionalOnClass(Feign.class)
    protected static class FeignStrategyConfiguration {
        @Bean
        @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_REST_INTERCEPT_ENABLED, matchIfMissing = true)
        public FeignStrategyInterceptor feignStrategyInterceptor() {
            return new FeignStrategyInterceptor();
        }
    }

    @ConditionalOnClass(RestTemplate.class)
    protected static class RestTemplateStrategyConfiguration {
        @Bean
        @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_REST_INTERCEPT_ENABLED, matchIfMissing = true)
        public RestTemplateStrategyInterceptor restTemplateStrategyInterceptor() {
            return new RestTemplateStrategyInterceptor();
        }

        @Bean
        @ConditionalOnMissingBean
        @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_REST_INTERCEPT_ENABLED, matchIfMissing = true)
        public RestTemplateStrategyBeanPostProcessor restTemplateStrategyBeanPostProcessor() {
            return new RestTemplateStrategyBeanPostProcessor();
        }
    }

    @ConditionalOnClass(WebClient.class)
    @ConditionalOnBean(WebClient.Builder.class)
    protected static class WebClientStrategyConfiguration {
        @Bean
        @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_REST_INTERCEPT_ENABLED, matchIfMissing = true)
        public WebClientStrategyInterceptor webClientStrategyInterceptor() {
            return new WebClientStrategyInterceptor();
        }

        @Bean
        @ConditionalOnMissingBean
        @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_REST_INTERCEPT_ENABLED, matchIfMissing = true)
        public WebClientStrategyBeanPostProcessor webClientStrategyBeanPostProcessor() {
            return new WebClientStrategyBeanPostProcessor();
        }
    }
}
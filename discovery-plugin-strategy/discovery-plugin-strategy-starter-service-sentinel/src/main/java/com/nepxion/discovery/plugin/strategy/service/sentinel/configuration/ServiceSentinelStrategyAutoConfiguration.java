package com.nepxion.discovery.plugin.strategy.service.sentinel.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Weihua Wang
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.nepxion.discovery.plugin.strategy.service.sentinel.constant.ServiceSentinelStrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.ServiceSentinelRequestOriginParser;

@Configuration
public class ServiceSentinelStrategyAutoConfiguration {
    // 下述两个类是原生Sentinel功能的保证
    @Bean
    @ConditionalOnMissingBean
    public CommonFilter commonFilter() {
        return new CommonFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public SentinelResourceAspect serviceSentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = ServiceSentinelStrategyConstant.SPRING_APPLICATION_STRATEGY_SERVICE_SENTINEL_LIMIT_APP_ENABLED, matchIfMissing = false)
    public RequestOriginParser serviceSentinelRequestOriginParser() {
        return new ServiceSentinelRequestOriginParser();
    }
}
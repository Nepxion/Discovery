package com.nepxion.discovery.plugin.strategy.sentinel.limiter.configuration;

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
import com.nepxion.discovery.plugin.strategy.sentinel.limiter.constant.SentinelStrategyLimiterConstant;
import com.nepxion.discovery.plugin.strategy.sentinel.limiter.parser.SentinelStrategyRequestOriginParser;

@Configuration
@ConditionalOnProperty(value = SentinelStrategyLimiterConstant.SPRING_APPLICATION_STRATEGY_SENTINEL_LIMIT_APP_ENABLED, matchIfMissing = false)
public class SentinelStrategyLimiterAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public CommonFilter commonFilter() {
        return new CommonFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestOriginParser sentinelStrategyRequestOriginParser() {
        return new SentinelStrategyRequestOriginParser();
    }
}
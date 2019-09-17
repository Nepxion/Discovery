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

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.nepxion.discovery.plugin.strategy.sentinel.constant.SentinelStrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.ServiceSentinelRequestOriginParser;

@Configuration
@ConditionalOnProperty(value = SentinelStrategyConstant.SPRING_APPLICATION_STRATEGY_SENTINEL_ENABLED, matchIfMissing = false)
public class ServiceSentinelStrategyAutoConfiguration {
    @Bean
    public CommonFilter commonFilter() {
        return new CommonFilter();
    }

    @Bean
    public SentinelResourceAspect serviceSentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

    @Bean
    public RequestOriginParser serviceSentinelRequestOriginParser() {
        return new ServiceSentinelRequestOriginParser();
    }
}
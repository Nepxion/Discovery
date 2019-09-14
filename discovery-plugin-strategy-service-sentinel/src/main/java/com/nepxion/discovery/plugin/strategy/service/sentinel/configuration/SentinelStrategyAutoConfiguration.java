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

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.plugin.strategy.service.sentinel.constant.SentinelStrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.sentinel.loader.SentinelRuleLoader;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.SentinelRequestOriginParser;

@Configuration
@ConditionalOnProperty(value = SentinelStrategyConstant.SPRING_APPLICATION_STRATEGY_SENTINEL_ENABLED, matchIfMissing = false)
public class SentinelStrategyAutoConfiguration {
    @Autowired(required = false)
    private SentinelRuleLoader sentinelRuleLoader;

    @PostConstruct
    public void initialize() {
        if (sentinelRuleLoader == null) {
            throw new DiscoveryException("Sentinel rule loader hasn't been initialized...");
        }

        sentinelRuleLoader.load();
    }

    @Bean
    public CommonFilter commonFilter() {
        return new CommonFilter();
    }

    @Bean
    public RequestOriginParser sentinelRequestOriginParser() {
        return new SentinelRequestOriginParser();
    }

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }
}
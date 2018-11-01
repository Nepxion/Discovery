package com.nepxion.discovery.plugin.strategy.hystrix.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author Hao Huang
 * @version 1.0
 */

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.hystrix.context.HystrixContextConcurrencyStrategy;
import com.netflix.hystrix.Hystrix;

@Configuration
@ConditionalOnClass(Hystrix.class)
@ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_HYSTRIX_THREADLOCAL_SUPPORTED, matchIfMissing = false)
public class HystrixStrategyAutoConfiguration {
    @Bean
    public HystrixContextConcurrencyStrategy hystrixContextConcurrencyStrategy() {
        return new HystrixContextConcurrencyStrategy();
    }
}
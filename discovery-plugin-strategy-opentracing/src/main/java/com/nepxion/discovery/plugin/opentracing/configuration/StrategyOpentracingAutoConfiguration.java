package com.nepxion.discovery.plugin.opentracing.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.discovery.plugin.opentracing.operation.StrategyOpentracingOperation;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;

@Configuration
@ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_CONTROL_ENABLED, matchIfMissing = true)
public class StrategyOpentracingAutoConfiguration {
    @Bean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACE_ENABLED, matchIfMissing = false)
    public StrategyOpentracingOperation strategyOpentracingOperation() {
        return new StrategyOpentracingOperation();
    }
}
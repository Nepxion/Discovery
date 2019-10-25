package com.nepxion.discovery.plugin.zuul.opentracing.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.zuul.configuration.ZuulStrategyAutoConfiguration;
import com.nepxion.discovery.plugin.strategy.zuul.tracer.ZuulStrategyTracer;
import com.nepxion.discovery.plugin.zuul.opentracing.tracer.DefaultZuulStrategyOpentracingTracer;

@Configuration
@AutoConfigureBefore(ZuulStrategyAutoConfiguration.class)
@ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_CONTROL_ENABLED, matchIfMissing = true)
public class ZuulStrategyOpentracingAutoConfiguration {
    @Bean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACE_ENABLED, matchIfMissing = false)
    public ZuulStrategyTracer zuulStrategyTracer() {
        return new DefaultZuulStrategyOpentracingTracer();
    }
}
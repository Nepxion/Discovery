package com.nepxion.discovery.plugin.service.opentracing.configuration;

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

import com.nepxion.discovery.plugin.service.opentracing.tracer.DefaultServiceStrategyOpentracingTracer;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.configuration.ServiceStrategyAutoConfiguration;
import com.nepxion.discovery.plugin.strategy.service.tracer.ServiceStrategyTracer;

@Configuration
@AutoConfigureBefore(ServiceStrategyAutoConfiguration.class)
@ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_CONTROL_ENABLED, matchIfMissing = true)
public class ServiceStrategyOpentracingAutoConfiguration {
    @Bean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACE_ENABLED, matchIfMissing = false)
    public ServiceStrategyTracer serviceStrategyTracer() {
        return new DefaultServiceStrategyOpentracingTracer();
    }
}
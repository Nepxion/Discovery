package com.nepxion.discovery.plugin.strategy.sentinel.micrometer.monitor.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Tank
 * @version 1.0
 */

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.discovery.plugin.strategy.sentinel.micrometer.monitor.constant.SentinelMicrometerMetricConstant;
import com.nepxion.discovery.plugin.strategy.sentinel.micrometer.monitor.metric.SentinelMicrometerMetricInitializer;

@Configuration
@ConditionalOnProperty(value = SentinelMicrometerMetricConstant.SPRING_APPLICATION_STRATEGY_SENTINEL_MICROMETER_ENABLED, matchIfMissing = false)
public class SentinelMicrometerAutoConfiguration {
    @Bean
    public SentinelMicrometerMetricInitializer sentinelMicrometerMetricInitializer() {
        return new SentinelMicrometerMetricInitializer();
    }
}
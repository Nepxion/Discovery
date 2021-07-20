package com.nepxion.discovery.plugin.strategy.sentinel.micrometer.monitor.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Tank
 * @version 1.0
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.discovery.plugin.strategy.sentinel.micrometer.monitor.metric.SentinelMicrometerMetricInitializer;

@Configuration
public class SentinelMicrometerAutoConfiguration {
    @Bean
    public SentinelMicrometerMetricInitializer sentinelMicrometerMetricInitializer() {
        return new SentinelMicrometerMetricInitializer();
    }
}
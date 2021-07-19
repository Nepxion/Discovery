package com.nepxion.discovery.plugin.strategy.sentinel.prometheus;

import com.nepxion.discovery.plugin.strategy.sentinel.prometheus.tag.SentinelToPrometheusMetricConfig;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * @author Tank
 * @version 1.0.0
 */
@Configuration
@ImportAutoConfiguration(SentinelToPrometheusMetricConfig.class)
public class SentinelPrometheusMetricExtensionAutoConfiguration {
}

package com.nepxion.discovery.plugin.strategy.sentinel.prometheus;

import com.nepxion.discovery.plugin.strategy.sentinel.prometheus.tag.SentinelToPrometheusMetricConfig;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title: Nepxion Discovery</p >
 * <p>Description: Nepxion Discovery</p >
 * <p>Copyright: Copyright (c) 2017-2050</p >
 * <p>Company: Nepxion</p >
 * @author Tank
 * @version 1.0
 */

@Configuration
@ImportAutoConfiguration(SentinelToPrometheusMetricConfig.class)
public class SentinelPrometheusMetricExtensionAutoConfiguration {
}

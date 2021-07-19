package com.nepxion.discovery.plugin.strategy.sentinel.prometheus.monitor.metric;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Tank
 * @version 1.0
 */

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.entity.SentinelMetricType;
import com.nepxion.discovery.plugin.strategy.sentinel.prometheus.monitor.constant.SentinelPrometheusMonitorConstant;

public class SentinelPrometheusMetricInitializer {
    @Autowired
    private MeterRegistry registry;

    @PostConstruct
    public void initialize() {
        for (SentinelMetricType sentinelMetricType : SentinelMetricType.values()) {
            Counter.builder(sentinelMetricType.toString()).tag(SentinelPrometheusMonitorConstant.RESOURCE, StringUtils.EMPTY).register(registry);
        }
    }
}
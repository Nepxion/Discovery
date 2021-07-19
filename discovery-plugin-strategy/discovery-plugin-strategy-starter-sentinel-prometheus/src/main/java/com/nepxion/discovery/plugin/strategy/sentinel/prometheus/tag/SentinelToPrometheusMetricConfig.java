package com.nepxion.discovery.plugin.strategy.sentinel.prometheus.tag;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import javax.annotation.PostConstruct;

/**
 * <p>Title: Nepxion Discovery</p >
 * <p>Description: Nepxion Discovery</p >
 * <p>Copyright: Copyright (c) 2017-2050</p >
 * <p>Company: Nepxion</p >
 * @author Tank
 * @version 1.0
 */

@Configurable
public class SentinelToPrometheusMetricConfig {
    private final MeterRegistry registry;
    public final static String TAG_RESOURCE = "resource";


    @Autowired
    public SentinelToPrometheusMetricConfig(MeterRegistry registry) {
        this.registry = registry;
    }

    @PostConstruct
    public void init() {
        for (SentinelToPrometheusMetricEnum value : SentinelToPrometheusMetricEnum.values()) {
            Counter.builder(value.getName())
                    .tag(TAG_RESOURCE, "")
                    .register(registry);
        }
    }
}

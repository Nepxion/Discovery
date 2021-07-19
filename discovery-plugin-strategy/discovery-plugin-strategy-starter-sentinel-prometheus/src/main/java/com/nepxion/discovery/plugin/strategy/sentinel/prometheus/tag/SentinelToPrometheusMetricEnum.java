package com.nepxion.discovery.plugin.strategy.sentinel.prometheus.tag;

/**
 * @author Tank
 * @version 1.0.0
 */
public enum SentinelToPrometheusMetricEnum {
    PASS_QPS("discovery_sentinel_metric_statistics_pass_qps"),
    BLOCK_QPS("discovery_sentinel_metric_statistics_block_qps"),
    SUCCESS_QPS("discovery_sentinel_metric_statistics_success_qps"),
    EXCEPTION_QPS("discovery_sentinel_metric_statistics_exception_qps");

    private String name;

    SentinelToPrometheusMetricEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

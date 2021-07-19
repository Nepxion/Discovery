package com.nepxion.discovery.plugin.strategy.sentinel.prometheus.monitor.metric;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Tank
 * @version 1.0
 */

import io.micrometer.core.instrument.Metrics;

import com.alibaba.csp.sentinel.metric.extension.MetricExtension;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.nepxion.discovery.common.entity.SentinelMetricType;
import com.nepxion.discovery.plugin.strategy.sentinel.prometheus.monitor.constant.SentinelPrometheusMonitorConstant;

public class SentinelPrometheusMetricExtension implements MetricExtension {
    @Override
    public void addPass(String resource, int n, Object... args) {
        Metrics.counter(SentinelMetricType.PASS_QPS.toString(), SentinelPrometheusMonitorConstant.RESOURCE, resource).increment(n);
    }

    @Override
    public void addBlock(String resource, int n, String origin, BlockException blockException, Object... args) {
        Metrics.counter(SentinelMetricType.BLOCK_QPS.toString(), SentinelPrometheusMonitorConstant.RESOURCE, resource).increment(n);
    }

    @Override
    public void addSuccess(String resource, int n, Object... args) {
        Metrics.counter(SentinelMetricType.SUCCESS_QPS.toString(), SentinelPrometheusMonitorConstant.RESOURCE, resource).increment(n);
    }

    @Override
    public void addException(String resource, int n, Throwable throwable) {
        Metrics.counter(SentinelMetricType.EXCEPTION_QPS.toString(), SentinelPrometheusMonitorConstant.RESOURCE, resource).increment(n);
    }

    @Override
    public void addRt(String resource, long rt, Object... args) {

    }

    @Override
    public void increaseThreadNum(String resource, Object... args) {

    }

    @Override
    public void decreaseThreadNum(String resource, Object... args) {

    }
}
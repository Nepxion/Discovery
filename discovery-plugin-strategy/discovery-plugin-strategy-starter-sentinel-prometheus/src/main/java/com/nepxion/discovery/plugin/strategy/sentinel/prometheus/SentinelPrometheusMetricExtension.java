package com.nepxion.discovery.plugin.strategy.sentinel.prometheus;

import com.alibaba.csp.sentinel.metric.extension.MetricExtension;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.nepxion.discovery.plugin.strategy.sentinel.prometheus.tag.SentinelToPrometheusMetricConfig;
import com.nepxion.discovery.plugin.strategy.sentinel.prometheus.tag.SentinelToPrometheusMetricEnum;
import io.micrometer.core.instrument.Metrics;

/**
 * @author Tank
 * @version 1.0.0
 */
public class SentinelPrometheusMetricExtension implements MetricExtension {
    @Override
    public void addPass(String resource, int i, Object... objects) {
        Metrics.counter(SentinelToPrometheusMetricEnum.PASS_QPS.getName(),
                SentinelToPrometheusMetricConfig.TAG_RESOURCE,resource)
                .increment(i);

    }

    @Override
    public void addBlock(String resource, int i, String s1, BlockException e, Object... objects) {
        Metrics.counter(SentinelToPrometheusMetricEnum.BLOCK_QPS.getName(),
                SentinelToPrometheusMetricConfig.TAG_RESOURCE,resource)
                .increment(i);
    }

    @Override
    public void addSuccess(String resource, int i, Object... objects) {
        Metrics.counter(SentinelToPrometheusMetricEnum.SUCCESS_QPS.getName(),
                SentinelToPrometheusMetricConfig.TAG_RESOURCE,resource)
                .increment(i);

    }

    @Override
    public void addException(String resource, int i, Throwable throwable) {
        Metrics.counter(SentinelToPrometheusMetricEnum.EXCEPTION_QPS.getName(),
                SentinelToPrometheusMetricConfig.TAG_RESOURCE,resource)
                .increment(i);
    }

    @Override
    public void addRt(String s, long l, Object... objects) {

    }

    @Override
    public void increaseThreadNum(String s, Object... objects) {

    }

    @Override
    public void decreaseThreadNum(String s, Object... objects) {

    }
}

package com.nepxion.discovery.plugin.opentracing.operation;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.tag.Tags;

import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.google.common.collect.ImmutableMap;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.opentracing.constant.StrategyOpentracingConstant;
import com.nepxion.discovery.plugin.opentracing.context.StrategyOpentracingContext;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;

public class StrategyOpentracingOperation {
    private static final Logger LOG = LoggerFactory.getLogger(StrategyOpentracingOperation.class);

    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected StrategyContextHolder strategyContextHolder;

    @Autowired
    private Tracer tracer;

    @Value("${" + StrategyOpentracingConstant.SPRING_APPLICATION_STRATEGY_TRACE_OPENTRACING_ENABLED + ":false}")
    protected Boolean traceOpentracingEnabled;

    @Value("${" + StrategyOpentracingConstant.SPRING_APPLICATION_STRATEGY_TRACE_OPENTRACING_SEPARATE_SPAN_ENABLED + ":true}")
    protected Boolean traceOpentracingSeparateSpanEnabled;

    @Value("${" + StrategyOpentracingConstant.SPRING_APPLICATION_STRATEGY_TRACE_OPENTRACING_RULE_OUTPUT_ENABLED + ":false}")
    protected Boolean traceOpentracingRuleOutputEnabled;

    public void opentracingInitialize() {
        if (!traceOpentracingEnabled) {
            return;
        }

        if (!traceOpentracingSeparateSpanEnabled) {
            return;
        }

        Span span = tracer.buildSpan(DiscoveryConstant.SPAN_VALUE).start();
        StrategyOpentracingContext.getCurrentContext().setSpan(span);

        LOG.debug("Trace chain for Opentracing initialized...");
    }

    public void opentracingHeader(Map<String, String> customizationMap) {
        if (!traceOpentracingEnabled) {
            return;
        }

        Span span = getCurrentSpan();
        if (span == null) {
            LOG.error("Span not found in context to opentracing header");

            return;
        }

        if (MapUtils.isNotEmpty(customizationMap)) {
            for (Map.Entry<String, String> entry : customizationMap.entrySet()) {
                span.setTag(entry.getKey(), entry.getValue());
            }
        }

        if (traceOpentracingSeparateSpanEnabled) {
            span.setTag(Tags.COMPONENT.getKey(), DiscoveryConstant.TAG_COMPONENT_VALUE);
        }
        span.setTag(DiscoveryConstant.PLUGIN, DiscoveryConstant.PLUGIN_VALUE);
        span.setTag(DiscoveryConstant.TRACE_ID, span.context().toTraceId());
        span.setTag(DiscoveryConstant.SPAN_ID, span.context().toSpanId());
        span.setTag(DiscoveryConstant.N_D_SERVICE_GROUP, strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_GROUP));
        span.setTag(DiscoveryConstant.N_D_SERVICE_TYPE, strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_TYPE));
        span.setTag(DiscoveryConstant.N_D_SERVICE_ID, strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_ID));
        span.setTag(DiscoveryConstant.N_D_SERVICE_ADDRESS, strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_ADDRESS));
        span.setTag(DiscoveryConstant.N_D_SERVICE_VERSION, strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_VERSION));
        span.setTag(DiscoveryConstant.N_D_SERVICE_REGION, strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_REGION));
        span.setTag(DiscoveryConstant.N_D_SERVICE_ENVIRONMENT, strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_ENVIRONMENT));

        if (traceOpentracingRuleOutputEnabled) {
            String routeVersion = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION);
            if (StringUtils.isNotEmpty(routeVersion)) {
                span.setTag(DiscoveryConstant.N_D_VERSION, routeVersion);
            }
            String routeRegion = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION);
            if (StringUtils.isNotEmpty(routeRegion)) {
                span.setTag(DiscoveryConstant.N_D_REGION, routeRegion);
            }
            String routeAddress = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ADDRESS);
            if (StringUtils.isNotEmpty(routeAddress)) {
                span.setTag(DiscoveryConstant.N_D_ADDRESS, routeAddress);
            }
            String routeVersionWeight = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION_WEIGHT);
            if (StringUtils.isNotEmpty(routeVersionWeight)) {
                span.setTag(DiscoveryConstant.N_D_VERSION_WEIGHT, routeVersionWeight);
            }
            String routeRegionWeight = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION_WEIGHT);
            if (StringUtils.isNotEmpty(routeRegionWeight)) {
                span.setTag(DiscoveryConstant.N_D_REGION_WEIGHT, routeRegionWeight);
            }
        }

        LOG.debug("Trace chain information outputs to Opentracing...");
    }

    public void opentracingLocal(String className, String methodName, Map<String, String> customizationMap) {
        if (!traceOpentracingEnabled) {
            return;
        }

        Span span = getCurrentSpan();
        if (span == null) {
            LOG.error("Span not found in context to opentracing local");

            return;
        }

        if (MapUtils.isNotEmpty(customizationMap)) {
            for (Map.Entry<String, String> entry : customizationMap.entrySet()) {
                span.setTag(entry.getKey(), entry.getValue());
            }
        }

        if (traceOpentracingSeparateSpanEnabled) {
            span.setTag(Tags.COMPONENT.getKey(), DiscoveryConstant.TAG_COMPONENT_VALUE);
        }
        span.setTag(DiscoveryConstant.PLUGIN, DiscoveryConstant.PLUGIN_VALUE);
        span.setTag(DiscoveryConstant.CLASS, className);
        span.setTag(DiscoveryConstant.METHOD, methodName);
        span.setTag(DiscoveryConstant.TRACE_ID, span.context().toTraceId());
        span.setTag(DiscoveryConstant.SPAN_ID, span.context().toSpanId());
        span.setTag(DiscoveryConstant.N_D_SERVICE_GROUP, pluginAdapter.getGroup());
        span.setTag(DiscoveryConstant.N_D_SERVICE_TYPE, pluginAdapter.getServiceType());
        span.setTag(DiscoveryConstant.N_D_SERVICE_ID, pluginAdapter.getServiceId());
        span.setTag(DiscoveryConstant.N_D_SERVICE_ADDRESS, pluginAdapter.getHost() + ":" + pluginAdapter.getPort());
        span.setTag(DiscoveryConstant.N_D_SERVICE_VERSION, pluginAdapter.getVersion());
        span.setTag(DiscoveryConstant.N_D_SERVICE_REGION, pluginAdapter.getRegion());
        span.setTag(DiscoveryConstant.N_D_SERVICE_ENVIRONMENT, pluginAdapter.getEnvironment());

        if (traceOpentracingRuleOutputEnabled) {
            String routeVersion = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION);
            if (StringUtils.isNotEmpty(routeVersion)) {
                span.setTag(DiscoveryConstant.N_D_VERSION, routeVersion);
            }
            String routeRegion = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION);
            if (StringUtils.isNotEmpty(routeRegion)) {
                span.setTag(DiscoveryConstant.N_D_REGION, routeRegion);
            }
            String routeAddress = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ADDRESS);
            if (StringUtils.isNotEmpty(routeAddress)) {
                span.setTag(DiscoveryConstant.N_D_ADDRESS, routeAddress);
            }
            String routeVersionWeight = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION_WEIGHT);
            if (StringUtils.isNotEmpty(routeVersionWeight)) {
                span.setTag(DiscoveryConstant.N_D_VERSION_WEIGHT, routeVersionWeight);
            }
            String routeRegionWeight = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION_WEIGHT);
            if (StringUtils.isNotEmpty(routeRegionWeight)) {
                span.setTag(DiscoveryConstant.N_D_REGION_WEIGHT, routeRegionWeight);
            }
        }

        LOG.debug("Trace chain information outputs to Opentracing...");
    }

    public void opentracingError(String className, String methodName, Throwable e) {
        if (!traceOpentracingEnabled) {
            return;
        }

        if (!traceOpentracingSeparateSpanEnabled) {
            return;
        }

        Span span = getCurrentSpan();
        if (span == null) {
            LOG.error("Span not found in context to opentracing error");

            return;
        }

        span.log(new ImmutableMap.Builder<String, Object>()
                .put(DiscoveryConstant.CLASS, className)
                .put(DiscoveryConstant.METHOD, methodName)
                .put(DiscoveryConstant.EVENT, Tags.ERROR.getKey())
                .put(DiscoveryConstant.ERROR_OBJECT, e)
                .build());

        LOG.debug("Trace chain error outputs to Opentracing...");
    }

    public void opentracingClear() {
        if (!traceOpentracingEnabled) {
            return;
        }

        if (!traceOpentracingSeparateSpanEnabled) {
            return;
        }

        Span span = getCurrentSpan();
        if (span != null) {
            span.finish();
        } else {
            LOG.error("Span not found in context to opentracing clear");
        }
        StrategyOpentracingContext.clearCurrentContext();

        LOG.debug("Trace chain context of Opentracing cleared...");
    }

    public Span getCurrentSpan() {
        return traceOpentracingSeparateSpanEnabled ? StrategyOpentracingContext.getCurrentContext().getSpan() : tracer.activeSpan();
    }

    public String getTraceId() {
        if (!traceOpentracingEnabled) {
            return null;
        }

        Span span = tracer.activeSpan();
        if (span != null) {
            return span.context().toTraceId();
        }

        return null;
    }

    public String getSpanId() {
        if (!traceOpentracingEnabled) {
            return null;
        }

        Span span = tracer.activeSpan();
        if (span != null) {
            return span.context().toSpanId();
        }

        return null;
    }
}
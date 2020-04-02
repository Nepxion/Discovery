package com.nepxion.discovery.plugin.strategy.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;

public abstract class AbstractStrategyTracer<S> implements StrategyTracer {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractStrategyTracer.class);

    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected StrategyContextHolder strategyContextHolder;

    @Autowired(required = false)
    protected StrategyLogger strategyLogger;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACER_SEPARATE_SPAN_ENABLED + ":true}")
    protected Boolean tracerSeparateSpanEnabled;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACER_RULE_OUTPUT_ENABLED + ":true}")
    protected Boolean tracerRuleOutputEnabled;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACER_SPAN_VALUE + ":" + DiscoveryConstant.SPAN_VALUE + "}")
    protected String tracerSpanValue;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACER_SPAN_TAG_COMPONENT_VALUE + ":" + DiscoveryConstant.SPAN_TAG_COMPONENT_VALUE + "}")
    protected String tracerSpanComponentValue;

    @Override
    public void spanBuild() {
        if (!tracerSeparateSpanEnabled) {
            return;
        }

        S span = buildSpan();

        StrategyTracerContext.getCurrentContext().setSpan(span);
    }

    @Override
    public void spanOutput(Map<String, String> contextMap) {
        S span = getCurrentSpan();
        if (span == null) {
            LOG.error("Span not found in context to trace put");

            return;
        }

        if (MapUtils.isNotEmpty(contextMap)) {
            for (Map.Entry<String, String> entry : contextMap.entrySet()) {
                outputSpan(span, entry.getKey(), entry.getValue());
            }
        }

        Map<String, String> customizationMap = getCustomizationMap();
        if (MapUtils.isNotEmpty(customizationMap)) {
            for (Map.Entry<String, String> entry : customizationMap.entrySet()) {
                outputSpan(span, entry.getKey(), entry.getValue());
            }
        }

        if (tracerSeparateSpanEnabled) {
            outputSpan(span, DiscoveryConstant.SPAN_TAG_COMPONENT_NAME, tracerSpanComponentValue);
        }
        outputSpan(span, DiscoveryConstant.TRACE_ID, toTraceId(span));
        outputSpan(span, DiscoveryConstant.SPAN_ID, toSpanId(span));
        outputSpan(span, DiscoveryConstant.N_D_SERVICE_GROUP, pluginAdapter.getGroup());
        outputSpan(span, DiscoveryConstant.N_D_SERVICE_TYPE, pluginAdapter.getServiceType());
        String serviceAppId = pluginAdapter.getServiceAppId();
        if (StringUtils.isNotEmpty(serviceAppId)) {
            outputSpan(span, DiscoveryConstant.N_D_SERVICE_APP_ID, serviceAppId);
        }
        outputSpan(span, DiscoveryConstant.N_D_SERVICE_ID, pluginAdapter.getServiceId());
        outputSpan(span, DiscoveryConstant.N_D_SERVICE_ADDRESS, pluginAdapter.getHost() + ":" + pluginAdapter.getPort());
        outputSpan(span, DiscoveryConstant.N_D_SERVICE_VERSION, pluginAdapter.getVersion());
        outputSpan(span, DiscoveryConstant.N_D_SERVICE_REGION, pluginAdapter.getRegion());
        outputSpan(span, DiscoveryConstant.N_D_SERVICE_ENVIRONMENT, pluginAdapter.getEnvironment());

        if (tracerRuleOutputEnabled) {
            String routeVersion = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION);
            if (StringUtils.isNotEmpty(routeVersion)) {
                outputSpan(span, DiscoveryConstant.N_D_VERSION, routeVersion);
            }
            String routeRegion = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION);
            if (StringUtils.isNotEmpty(routeRegion)) {
                outputSpan(span, DiscoveryConstant.N_D_REGION, routeRegion);
            }
            String routeAddress = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ADDRESS);
            if (StringUtils.isNotEmpty(routeAddress)) {
                outputSpan(span, DiscoveryConstant.N_D_ADDRESS, routeAddress);
            }
            String routeEnvironment = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ENVIRONMENT);
            if (StringUtils.isNotEmpty(routeEnvironment)) {
                outputSpan(span, DiscoveryConstant.N_D_ENVIRONMENT, routeEnvironment);
            }
            String routeVersionWeight = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION_WEIGHT);
            if (StringUtils.isNotEmpty(routeVersionWeight)) {
                outputSpan(span, DiscoveryConstant.N_D_VERSION_WEIGHT, routeVersionWeight);
            }
            String routeRegionWeight = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION_WEIGHT);
            if (StringUtils.isNotEmpty(routeRegionWeight)) {
                outputSpan(span, DiscoveryConstant.N_D_REGION_WEIGHT, routeRegionWeight);
            }
        }
    }

    @Override
    public void spanError(Map<String, String> contextMap, Throwable e) {
        if (!tracerSeparateSpanEnabled) {
            return;
        }

        S span = getCurrentSpan();
        if (span == null) {
            LOG.error("Span not found in context to trace error");

            return;
        }

        errorSpan(span, contextMap, e);
    }

    @Override
    public void spanFinish() {
        if (!tracerSeparateSpanEnabled) {
            return;
        }

        S span = getCurrentSpan();
        if (span != null) {
            finishSpan(span);
        } else {
            LOG.error("Span not found in context to trace clear");
        }

        StrategyTracerContext.clearCurrentContext();
    }

    @SuppressWarnings("unchecked")
    private S getCurrentSpan() {
        return tracerSeparateSpanEnabled ? (S) StrategyTracerContext.getCurrentContext().getSpan() : getActiveSpan();
    }

    @Override
    public String getTraceId() {
        S span = getCurrentSpan();
        if (span != null) {
            return toTraceId(span);
        }

        return null;
    }

    public String getSpanId() {
        S span = getCurrentSpan();
        if (span != null) {
            return toSpanId(span);
        }

        return null;
    }

    public Map<String, String> getCustomizationMap() {
        return strategyLogger.getCustomizationMap();
    }

    protected abstract S buildSpan();

    protected abstract void outputSpan(S span, String key, String value);

    protected abstract void errorSpan(S span, Map<String, String> contextMap, Throwable e);

    protected abstract void finishSpan(S span);

    protected abstract S getActiveSpan();

    protected abstract String toTraceId(S span);

    protected abstract String toSpanId(S span);
}
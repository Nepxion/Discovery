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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;

public abstract class AbstractStrategyTracer<S> implements StrategyTracer {
    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected StrategyContextHolder strategyContextHolder;

    @Autowired
    protected StrategyMonitorContext strategyMonitorContext;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACER_ENABLED + ":false}")
    protected Boolean tracerEnabled;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACER_SEPARATE_SPAN_ENABLED + ":true}")
    protected Boolean tracerSeparateSpanEnabled;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACER_RULE_OUTPUT_ENABLED + ":true}")
    protected Boolean tracerRuleOutputEnabled;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACER_SPAN_VALUE + ":" + DiscoveryConstant.NEPXION_UPPERCASE + "}")
    protected String tracerSpanValue;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACER_SPAN_TAG_PLUGIN_VALUE + ":" + DiscoveryConstant.NEPXION_DISCOVERY + "}")
    protected String tracerSpanPluginValue;

    @Override
    public void spanBuild() {
        if (!tracerEnabled) {
            return;
        }

        if (!tracerSeparateSpanEnabled) {
            return;
        }

        S span = buildSpan();

        StrategyTracerContext.getCurrentContext().setSpan(span);
    }

    @Override
    public void spanOutput(Map<String, String> contextMap) {
        if (!tracerEnabled) {
            return;
        }

        S span = getCurrentSpan();
        if (span == null) {
            // LOG.error("Span not found in context to trace put");

            return;
        }

        if (MapUtils.isNotEmpty(contextMap)) {
            for (Map.Entry<String, String> entry : contextMap.entrySet()) {
                outputSpan(span, entry.getKey(), entry.getValue());
            }
        }

        Map<String, String> customizationMap = strategyMonitorContext.getCustomizationMap();
        if (MapUtils.isNotEmpty(customizationMap)) {
            for (Map.Entry<String, String> entry : customizationMap.entrySet()) {
                outputSpan(span, entry.getKey(), entry.getValue());
            }
        }

        if (tracerSeparateSpanEnabled) {
            outputSpan(span, DiscoveryConstant.SPAN_TAG_PLUGIN_NAME, tracerSpanPluginValue);
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
        String version = pluginAdapter.getVersion();
        if (StringUtils.isNotEmpty(version) && !StringUtils.equals(version, DiscoveryConstant.DEFAULT)) {
            outputSpan(span, DiscoveryConstant.N_D_SERVICE_VERSION, version);
        }
        String region = pluginAdapter.getRegion();
        if (StringUtils.isNotEmpty(region) && !StringUtils.equals(region, DiscoveryConstant.DEFAULT)) {
            outputSpan(span, DiscoveryConstant.N_D_SERVICE_REGION, region);
        }
        String environment = pluginAdapter.getEnvironment();
        if (StringUtils.isNotEmpty(environment) && !StringUtils.equals(environment, DiscoveryConstant.DEFAULT)) {
            outputSpan(span, DiscoveryConstant.N_D_SERVICE_ENVIRONMENT, environment);
        }
        String zone = pluginAdapter.getZone();
        if (StringUtils.isNotEmpty(zone) && !StringUtils.equals(zone, DiscoveryConstant.DEFAULT)) {
            outputSpan(span, DiscoveryConstant.N_D_SERVICE_ZONE, zone);
        }

        if (tracerRuleOutputEnabled) {
            String routeVersion = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION);
            if (StringUtils.isNotEmpty(routeVersion)) {
                outputSpan(span, DiscoveryConstant.N_D_VERSION, routeVersion);
            }
            String routeRegion = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION);
            if (StringUtils.isNotEmpty(routeRegion)) {
                outputSpan(span, DiscoveryConstant.N_D_REGION, routeRegion);
            }
            String routeEnvironment = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ENVIRONMENT);
            if (StringUtils.isNotEmpty(routeEnvironment)) {
                outputSpan(span, DiscoveryConstant.N_D_ENVIRONMENT, routeEnvironment);
            }
            String routeAddress = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ADDRESS);
            if (StringUtils.isNotEmpty(routeAddress)) {
                outputSpan(span, DiscoveryConstant.N_D_ADDRESS, routeAddress);
            }
            String routeVersionWeight = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION_WEIGHT);
            if (StringUtils.isNotEmpty(routeVersionWeight)) {
                outputSpan(span, DiscoveryConstant.N_D_VERSION_WEIGHT, routeVersionWeight);
            }
            String routeRegionWeight = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION_WEIGHT);
            if (StringUtils.isNotEmpty(routeRegionWeight)) {
                outputSpan(span, DiscoveryConstant.N_D_REGION_WEIGHT, routeRegionWeight);
            }
            String routeVersionPrefer = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION_PREFER);
            if (StringUtils.isNotEmpty(routeVersionPrefer)) {
                outputSpan(span, DiscoveryConstant.N_D_VERSION_PREFER, routeVersionPrefer);
            }
            String routeVersionFailover = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION_FAILOVER);
            if (StringUtils.isNotEmpty(routeVersionFailover)) {
                outputSpan(span, DiscoveryConstant.N_D_VERSION_FAILOVER, routeVersionFailover);
            }
            String routeRegionTransfer = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION_TRANSFER);
            if (StringUtils.isNotEmpty(routeRegionTransfer)) {
                outputSpan(span, DiscoveryConstant.N_D_REGION_TRANSFER, routeRegionTransfer);
            }
            String routeRegionFailover = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION_FAILOVER);
            if (StringUtils.isNotEmpty(routeRegionFailover)) {
                outputSpan(span, DiscoveryConstant.N_D_REGION_FAILOVER, routeRegionFailover);
            }
            String routeEnvironmentFailover = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ENVIRONMENT_FAILOVER);
            if (StringUtils.isNotEmpty(routeEnvironmentFailover)) {
                outputSpan(span, DiscoveryConstant.N_D_ENVIRONMENT_FAILOVER, routeEnvironmentFailover);
            }
            String routeZoneFailover = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ZONE_FAILOVER);
            if (StringUtils.isNotEmpty(routeZoneFailover)) {
                outputSpan(span, DiscoveryConstant.N_D_ZONE_FAILOVER, routeZoneFailover);
            }
            String routeAddressFailover = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ADDRESS_FAILOVER);
            if (StringUtils.isNotEmpty(routeAddressFailover)) {
                outputSpan(span, DiscoveryConstant.N_D_ADDRESS_FAILOVER, routeAddressFailover);
            }
            String routeIdBlacklist = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ID_BLACKLIST);
            if (StringUtils.isNotEmpty(routeIdBlacklist)) {
                outputSpan(span, DiscoveryConstant.N_D_ID_BLACKLIST, routeIdBlacklist);
            }
            String routeAddressBlacklist = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ADDRESS_BLACKLIST);
            if (StringUtils.isNotEmpty(routeAddressBlacklist)) {
                outputSpan(span, DiscoveryConstant.N_D_ADDRESS_BLACKLIST, routeAddressBlacklist);
            }
            String middlewareRequestType = strategyContextHolder.getHeader(DiscoveryConstant.N_DW_REQUEST_TYPE);
            if (StringUtils.isNotEmpty(middlewareRequestType)) {
                outputSpan(span, DiscoveryConstant.N_DW_REQUEST_TYPE, middlewareRequestType);
            }
        }
    }

    @Override
    public void spanError(Throwable e) {
        if (!tracerEnabled) {
            return;
        }

        if (!tracerSeparateSpanEnabled) {
            return;
        }

        S span = getCurrentSpan();
        if (span == null) {
            // LOG.error("Span not found in context to trace error");

            return;
        }

        errorSpan(span, e);
    }

    @Override
    public void spanFinish() {
        if (!tracerEnabled) {
            return;
        }

        if (!tracerSeparateSpanEnabled) {
            return;
        }

        S span = getCurrentSpan();
        if (span != null) {
            finishSpan(span);
        } else {
            // LOG.error("Span not found in context to trace clear");
        }

        StrategyTracerContext.clearCurrentContext();
    }

    @SuppressWarnings("unchecked")
    private S getCurrentSpan() {
        return tracerSeparateSpanEnabled ? (S) StrategyTracerContext.getCurrentContext().getSpan() : getActiveSpan();
    }

    @Override
    public String getTraceId() {
        if (!tracerEnabled) {
            return null;
        }

        S span = getCurrentSpan();
        if (span != null) {
            return toTraceId(span);
        }

        return null;
    }

    public String getSpanId() {
        if (!tracerEnabled) {
            return null;
        }

        S span = getCurrentSpan();
        if (span != null) {
            return toSpanId(span);
        }

        return null;
    }

    protected abstract S buildSpan();

    protected abstract void outputSpan(S span, String key, String value);

    protected abstract void errorSpan(S span, Throwable e);

    protected abstract void finishSpan(S span);

    protected abstract S getActiveSpan();

    protected abstract String toTraceId(S span);

    protected abstract String toSpanId(S span);
}
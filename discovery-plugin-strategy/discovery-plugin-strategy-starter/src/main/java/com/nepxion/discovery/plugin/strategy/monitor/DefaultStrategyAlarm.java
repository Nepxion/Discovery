package com.nepxion.discovery.plugin.strategy.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.event.PluginPublisher;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;
import com.nepxion.discovery.plugin.strategy.event.StrategyAlarmEvent;

public class DefaultStrategyAlarm implements StrategyAlarm {
    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected StrategyContextHolder strategyContextHolder;

    @Autowired
    protected StrategyMonitorContext strategyMonitorContext;

    @Autowired
    protected PluginPublisher pluginPublisher;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_ALARM_ENABLED + ":false}")
    protected Boolean alarmEnabled;

    @Override
    public void alarm(Map<String, String> contextMap) {
        if (!alarmEnabled) {
            return;
        }

        Map<String, String> alarmMap = new LinkedHashMap<String, String>();
        String traceId = strategyMonitorContext.getTraceId();
        String spanId = strategyMonitorContext.getSpanId();
        if (StringUtils.isNotEmpty(traceId)) {
            alarmMap.put(DiscoveryConstant.TRACE_ID, traceId);
        }
        if (StringUtils.isNotEmpty(spanId)) {
            alarmMap.put(DiscoveryConstant.SPAN_ID, spanId);
        }
        alarmMap.put(DiscoveryConstant.N_D_SERVICE_GROUP, pluginAdapter.getGroup());
        alarmMap.put(DiscoveryConstant.N_D_SERVICE_TYPE, pluginAdapter.getServiceType());
        String serviceAppId = pluginAdapter.getServiceAppId();
        if (StringUtils.isNotEmpty(serviceAppId)) {
            alarmMap.put(DiscoveryConstant.N_D_SERVICE_APP_ID, serviceAppId);
        }
        alarmMap.put(DiscoveryConstant.N_D_SERVICE_ID, pluginAdapter.getServiceId());
        alarmMap.put(DiscoveryConstant.N_D_SERVICE_ADDRESS, pluginAdapter.getHost() + ":" + pluginAdapter.getPort());
        String version = pluginAdapter.getVersion();
        if (StringUtils.isNotEmpty(version) && !StringUtils.equals(version, DiscoveryConstant.DEFAULT)) {
            alarmMap.put(DiscoveryConstant.N_D_SERVICE_VERSION, version);
        }
        String region = pluginAdapter.getRegion();
        if (StringUtils.isNotEmpty(region) && !StringUtils.equals(region, DiscoveryConstant.DEFAULT)) {
            alarmMap.put(DiscoveryConstant.N_D_SERVICE_REGION, region);
        }
        String environment = pluginAdapter.getEnvironment();
        if (StringUtils.isNotEmpty(environment) && !StringUtils.equals(environment, DiscoveryConstant.DEFAULT)) {
            alarmMap.put(DiscoveryConstant.N_D_SERVICE_ENVIRONMENT, environment);
        }
        String zone = pluginAdapter.getZone();
        if (StringUtils.isNotEmpty(zone) && !StringUtils.equals(zone, DiscoveryConstant.DEFAULT)) {
            alarmMap.put(DiscoveryConstant.N_D_SERVICE_ZONE, zone);
        }

        String routeVersion = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION);
        if (StringUtils.isNotEmpty(routeVersion)) {
            alarmMap.put(DiscoveryConstant.N_D_VERSION, routeVersion);
        }
        String routeRegion = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION);
        if (StringUtils.isNotEmpty(routeRegion)) {
            alarmMap.put(DiscoveryConstant.N_D_REGION, routeRegion);
        }
        String routeEnvironment = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ENVIRONMENT);
        if (StringUtils.isNotEmpty(routeEnvironment)) {
            alarmMap.put(DiscoveryConstant.N_D_ENVIRONMENT, routeEnvironment);
        }
        String routeAddress = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ADDRESS);
        if (StringUtils.isNotEmpty(routeAddress)) {
            alarmMap.put(DiscoveryConstant.N_D_ADDRESS, routeAddress);
        }
        String routeVersionWeight = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION_WEIGHT);
        if (StringUtils.isNotEmpty(routeVersionWeight)) {
            alarmMap.put(DiscoveryConstant.N_D_VERSION_WEIGHT, routeVersionWeight);
        }
        String routeRegionWeight = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION_WEIGHT);
        if (StringUtils.isNotEmpty(routeRegionWeight)) {
            alarmMap.put(DiscoveryConstant.N_D_REGION_WEIGHT, routeRegionWeight);
        }
        String routeVersionPrefer = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION_PREFER);
        if (StringUtils.isNotEmpty(routeVersionPrefer)) {
            alarmMap.put(DiscoveryConstant.N_D_VERSION_PREFER, routeVersionPrefer);
        }
        String routeVersionFailover = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION_FAILOVER);
        if (StringUtils.isNotEmpty(routeVersionFailover)) {
            alarmMap.put(DiscoveryConstant.N_D_VERSION_FAILOVER, routeVersionFailover);
        }
        String routeRegionTransfer = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION_TRANSFER);
        if (StringUtils.isNotEmpty(routeRegionTransfer)) {
            alarmMap.put(DiscoveryConstant.N_D_REGION_TRANSFER, routeRegionTransfer);
        }
        String routeRegionFailover = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION_FAILOVER);
        if (StringUtils.isNotEmpty(routeRegionFailover)) {
            alarmMap.put(DiscoveryConstant.N_D_REGION_FAILOVER, routeRegionFailover);
        }
        String routeEnvironmentFailover = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ENVIRONMENT_FAILOVER);
        if (StringUtils.isNotEmpty(routeEnvironmentFailover)) {
            alarmMap.put(DiscoveryConstant.N_D_ENVIRONMENT_FAILOVER, routeEnvironmentFailover);
        }
        String routeZoneFailover = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ZONE_FAILOVER);
        if (StringUtils.isNotEmpty(routeZoneFailover)) {
            alarmMap.put(DiscoveryConstant.N_D_ZONE_FAILOVER, routeZoneFailover);
        }
        String routeAddressFailover = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ADDRESS_FAILOVER);
        if (StringUtils.isNotEmpty(routeAddressFailover)) {
            alarmMap.put(DiscoveryConstant.N_D_ADDRESS_FAILOVER, routeAddressFailover);
        }
        String routeIdBlacklist = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ID_BLACKLIST);
        if (StringUtils.isNotEmpty(routeIdBlacklist)) {
            alarmMap.put(DiscoveryConstant.N_D_ID_BLACKLIST, routeIdBlacklist);
        }
        String routeAddressBlacklist = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ADDRESS_BLACKLIST);
        if (StringUtils.isNotEmpty(routeAddressBlacklist)) {
            alarmMap.put(DiscoveryConstant.N_D_ADDRESS_BLACKLIST, routeAddressBlacklist);
        }

        if (MapUtils.isNotEmpty(contextMap)) {
            for (Map.Entry<String, String> entry : contextMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (StringUtils.isNotEmpty(value)) {
                    alarmMap.put(key, value);
                }
            }
        }

        List<String> tracerInjectorHeaderNameList = strategyMonitorContext.getTracerInjectorHeaderNameList();
        if (CollectionUtils.isNotEmpty(tracerInjectorHeaderNameList)) {
            for (String tracerInjectorHeaderName : tracerInjectorHeaderNameList) {
                String tracerInjectorHeaderValue = strategyContextHolder.getHeader(tracerInjectorHeaderName);
                if (StringUtils.isNotEmpty(tracerInjectorHeaderValue)) {
                    alarmMap.put(tracerInjectorHeaderName, tracerInjectorHeaderValue);
                }
            }
        }

        Map<String, String> tracerCustomizationMap = strategyMonitorContext.getTracerCustomizationMap();
        if (MapUtils.isNotEmpty(tracerCustomizationMap)) {
            for (Map.Entry<String, String> entry : tracerCustomizationMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (StringUtils.isNotEmpty(value)) {
                    alarmMap.put(key, value);
                }
            }
        }

        onAlarm(alarmMap);
    }

    private void onAlarm(Map<String, String> alarmMap) {
        pluginPublisher.asyncPublish(new StrategyAlarmEvent(StrategyConstant.STRATEGY_CONTEXT_ALARM, alarmMap));
    }
}
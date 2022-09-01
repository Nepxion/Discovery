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
import java.util.Map;

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
    public void alarm() {
        if (!alarmEnabled) {
            return;
        }

        Map<String, String> contextMap = new LinkedHashMap<String, String>();
        String traceId = strategyMonitorContext.getTraceId();
        String spanId = strategyMonitorContext.getSpanId();
        contextMap.put(DiscoveryConstant.TRACE_ID, (StringUtils.isNotEmpty(traceId) ? traceId : StringUtils.EMPTY));
        contextMap.put(DiscoveryConstant.SPAN_ID, (StringUtils.isNotEmpty(spanId) ? spanId : StringUtils.EMPTY));
        contextMap.put(DiscoveryConstant.N_D_SERVICE_GROUP, pluginAdapter.getGroup());
        contextMap.put(DiscoveryConstant.N_D_SERVICE_TYPE, pluginAdapter.getServiceType());
        String serviceAppId = pluginAdapter.getServiceAppId();
        if (StringUtils.isNotEmpty(serviceAppId)) {
            contextMap.put(DiscoveryConstant.N_D_SERVICE_APP_ID, serviceAppId);
        }
        contextMap.put(DiscoveryConstant.N_D_SERVICE_ID, pluginAdapter.getServiceId());
        contextMap.put(DiscoveryConstant.N_D_SERVICE_ADDRESS, pluginAdapter.getHost() + ":" + pluginAdapter.getPort());
        contextMap.put(DiscoveryConstant.N_D_SERVICE_VERSION, pluginAdapter.getVersion());
        contextMap.put(DiscoveryConstant.N_D_SERVICE_REGION, pluginAdapter.getRegion());
        contextMap.put(DiscoveryConstant.N_D_SERVICE_ENVIRONMENT, pluginAdapter.getEnvironment());
        contextMap.put(DiscoveryConstant.N_D_SERVICE_ZONE, pluginAdapter.getZone());

        String routeVersion = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION);
        if (StringUtils.isNotEmpty(routeVersion)) {
            contextMap.put(DiscoveryConstant.N_D_VERSION, routeVersion);
        }
        String routeRegion = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION);
        if (StringUtils.isNotEmpty(routeRegion)) {
            contextMap.put(DiscoveryConstant.N_D_REGION, routeRegion);
        }
        String routeEnvironment = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ENVIRONMENT);
        if (StringUtils.isNotEmpty(routeEnvironment)) {
            contextMap.put(DiscoveryConstant.N_D_ENVIRONMENT, routeEnvironment);
        }
        String routeAddress = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ADDRESS);
        if (StringUtils.isNotEmpty(routeAddress)) {
            contextMap.put(DiscoveryConstant.N_D_ADDRESS, routeAddress);
        }
        String routeVersionWeight = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION_WEIGHT);
        if (StringUtils.isNotEmpty(routeVersionWeight)) {
            contextMap.put(DiscoveryConstant.N_D_VERSION_WEIGHT, routeVersionWeight);
        }
        String routeRegionWeight = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION_WEIGHT);
        if (StringUtils.isNotEmpty(routeRegionWeight)) {
            contextMap.put(DiscoveryConstant.N_D_REGION_WEIGHT, routeRegionWeight);
        }
        String routeVersionPrefer = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION_PREFER);
        if (StringUtils.isNotEmpty(routeVersionPrefer)) {
            contextMap.put(DiscoveryConstant.N_D_VERSION_PREFER, routeVersionPrefer);
        }
        String routeVersionFailover = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION_FAILOVER);
        if (StringUtils.isNotEmpty(routeVersionFailover)) {
            contextMap.put(DiscoveryConstant.N_D_VERSION_FAILOVER, routeVersionFailover);
        }
        String routeRegionTransfer = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION_TRANSFER);
        if (StringUtils.isNotEmpty(routeRegionTransfer)) {
            contextMap.put(DiscoveryConstant.N_D_REGION_TRANSFER, routeRegionTransfer);
        }
        String routeRegionFailover = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION_FAILOVER);
        if (StringUtils.isNotEmpty(routeRegionFailover)) {
            contextMap.put(DiscoveryConstant.N_D_REGION_FAILOVER, routeRegionFailover);
        }
        String routeEnvironmentFailover = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ENVIRONMENT_FAILOVER);
        if (StringUtils.isNotEmpty(routeEnvironmentFailover)) {
            contextMap.put(DiscoveryConstant.N_D_ENVIRONMENT_FAILOVER, routeEnvironmentFailover);
        }
        String routeZoneFailover = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ZONE_FAILOVER);
        if (StringUtils.isNotEmpty(routeZoneFailover)) {
            contextMap.put(DiscoveryConstant.N_D_ZONE_FAILOVER, routeZoneFailover);
        }
        String routeAddressFailover = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ADDRESS_FAILOVER);
        if (StringUtils.isNotEmpty(routeAddressFailover)) {
            contextMap.put(DiscoveryConstant.N_D_ADDRESS_FAILOVER, routeAddressFailover);
        }
        String routeIdBlacklist = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ID_BLACKLIST);
        if (StringUtils.isNotEmpty(routeIdBlacklist)) {
            contextMap.put(DiscoveryConstant.N_D_ID_BLACKLIST, routeIdBlacklist);
        }
        String routeAddressBlacklist = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ADDRESS_BLACKLIST);
        if (StringUtils.isNotEmpty(routeAddressBlacklist)) {
            contextMap.put(DiscoveryConstant.N_D_ADDRESS_BLACKLIST, routeAddressBlacklist);
        }

        Map<String, String> customizationMap = strategyMonitorContext.getCustomizationMap();
        if (MapUtils.isNotEmpty(customizationMap)) {
            contextMap.putAll(customizationMap);
        }

        onAlarm(contextMap);
    }

    private void onAlarm(Map<String, String> alarmMap) {
        pluginPublisher.asyncPublish(new StrategyAlarmEvent(StrategyConstant.STRATEGY_CONTEXT_ALARM, alarmMap));
    }
}
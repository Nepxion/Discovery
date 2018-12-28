package com.nepxion.discovery.plugin.example.service.feign;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.service.context.ServiceStrategyContextHolder;

public class AbstractFeignImpl {
    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private ServiceStrategyContextHolder serviceStrategyContextHolder;

    public String doInvoke(String value) {
        String serviceId = pluginAdapter.getServiceId();
        String host = pluginAdapter.getHost();
        int port = pluginAdapter.getPort();
        String version = pluginAdapter.getVersion();
        String region = pluginAdapter.getRegion();

        String traceId = null;
        ServletRequestAttributes attributes = serviceStrategyContextHolder.getRestAttributes();
        if (attributes != null) {
            traceId = attributes.getRequest().getHeader(DiscoveryConstant.TRACE_ID);
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(value + " -> " + serviceId);
        stringBuilder.append("[" + host + ":" + port + "]");
        if (StringUtils.isNotEmpty(version)) {
            stringBuilder.append("[V" + version + "]");
        }
        if (StringUtils.isNotEmpty(region)) {
            stringBuilder.append("[Region=" + region + "]");
        }
        if (StringUtils.isNotEmpty(traceId)) {
            stringBuilder.append("[TraceId=" + traceId + "]");
        }

        return stringBuilder.toString();
    }
}
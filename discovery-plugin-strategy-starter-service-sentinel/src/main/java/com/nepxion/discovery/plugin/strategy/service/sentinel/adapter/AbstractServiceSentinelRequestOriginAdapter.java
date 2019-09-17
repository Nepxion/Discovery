package com.nepxion.discovery.plugin.strategy.service.sentinel.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.service.sentinel.constant.ServiceSentinelStrategyConstant;

public abstract class AbstractServiceSentinelRequestOriginAdapter implements ServiceSentinelRequestOriginAdapter {
    @Value("${" + ServiceSentinelStrategyConstant.SPRING_APPLICATION_STRATEGY_SERVICE_SENTINEL_REQUEST_ORIGIN_KEY + ":" + DiscoveryConstant.N_D_SERVICE_ID + "}")
    protected String requestOriginKey;

    public String getRequestOriginKey() {
        return requestOriginKey;
    }
}
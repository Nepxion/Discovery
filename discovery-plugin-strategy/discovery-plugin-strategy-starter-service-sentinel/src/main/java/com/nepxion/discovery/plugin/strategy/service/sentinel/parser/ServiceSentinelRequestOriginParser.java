package com.nepxion.discovery.plugin.strategy.service.sentinel.parser;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Weihua Wang
 * @author Haojun Ren
 * @version 1.0
 */

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.service.sentinel.adapter.ServiceSentinelRequestOriginAdapter;
import com.nepxion.discovery.plugin.strategy.service.sentinel.constant.ServiceSentinelStrategyConstant;

public class ServiceSentinelRequestOriginParser implements RequestOriginParser {
    @Value("${" + ServiceSentinelStrategyConstant.SPRING_APPLICATION_STRATEGY_SERVICE_SENTINEL_REQUEST_ORIGIN_KEY + ":" + DiscoveryConstant.N_D_SERVICE_ID + "}")
    protected String requestOriginKey;

    @Autowired(required = false)
    protected ServiceSentinelRequestOriginAdapter serviceSentinelRequestOriginAdapter;

    public ServiceSentinelRequestOriginParser() {
        WebCallbackManager.setRequestOriginParser(this);
    }

    @Override
    public String parseOrigin(HttpServletRequest request) {
        if (serviceSentinelRequestOriginAdapter != null) {
            return serviceSentinelRequestOriginAdapter.parseOrigin(request);
        } else {
            String requestOriginValue = request.getHeader(requestOriginKey);

            return StringUtils.isNotEmpty(requestOriginValue) ? requestOriginValue : DiscoveryConstant.UNKNOWN;
        }
    }
}
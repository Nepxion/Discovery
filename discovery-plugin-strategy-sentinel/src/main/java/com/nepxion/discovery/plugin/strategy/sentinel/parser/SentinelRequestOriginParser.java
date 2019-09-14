package com.nepxion.discovery.plugin.strategy.sentinel.parser;

import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;

import javax.servlet.http.HttpServletRequest;

import static com.nepxion.discovery.common.constant.DiscoveryConstant.N_D_SERVICE_ID;

/**
 * @author Weihua
 * @since 5.3.9
 */
public class SentinelRequestOriginParser implements RequestOriginParser {

    @Override
    public String parseOrigin(HttpServletRequest request) {
        return request.getHeader(N_D_SERVICE_ID);
    }
}

package com.nepxion.discovery.plugin.strategy.gateway.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;

public class GatewayStrategyFilterResolver {
    @SuppressWarnings("deprecation")
    public static void setHeader(ServerHttpRequest.Builder requestBuilder, String headerName, String headerValue, Boolean gatewayHeaderPriority) {
        if (StringUtils.isEmpty(headerValue)) {
            return;
        }

        if (gatewayHeaderPriority) {
            // 需要把外界的Header清除
            requestBuilder.headers(headers -> headers.remove(headerName));
        }

        // 不管外界是否传递了Header，网关侧都加入Header
        // 当外界没传递了Header，由网关侧Header来替代
        // 当外界传递了Header，虽然网关侧也添加了Header，但传递到调用链的还是第一个Header。参考exchange.getRequest().getHeaders().getFirst(name)
        requestBuilder.header(headerName, headerValue);
    }

    public static void ignoreHeader(ServerHttpRequest.Builder requestBuilder, String headerName, Boolean gatewayHeaderPriority, Boolean gatewayOriginalHeaderIgnored) {
        if (gatewayHeaderPriority && gatewayOriginalHeaderIgnored) {
            // 需要把外界的Header清除
            requestBuilder.headers(headers -> headers.remove(headerName));
        }
    }
}
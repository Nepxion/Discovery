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
    public static void setHeader(ServerHttpRequest request, ServerHttpRequest.Builder requestBuilder, String headerName, String headerValue, Boolean gatewayHeaderPriority) {
        if (StringUtils.isEmpty(headerValue)) {
            return;
        }

        if (gatewayHeaderPriority) {
            // 需要把外界的Header清除
            requestBuilder.headers(headers -> headers.remove(headerName));

            // 需要把内置的Header加入
            requestBuilder.headers(headers -> headers.add(headerName, headerValue));
        } else {
            // 非网关优先条件下，判断外界请求是否含有Header
            // 如果含有，则不加入内置的Header
            if (!request.getHeaders().containsKey(headerName)) {
                requestBuilder.headers(headers -> headers.add(headerName, headerValue));
            }
        }
    }

    public static void ignoreHeader(ServerHttpRequest.Builder requestBuilder, String headerName, Boolean gatewayHeaderPriority, Boolean gatewayOriginalHeaderIgnored) {
        if (gatewayHeaderPriority && gatewayOriginalHeaderIgnored) {
            ignoreHeader(requestBuilder, headerName);
        }
    }

    public static void ignoreHeader(ServerHttpRequest.Builder requestBuilder, String headerName) {
        // 需要把外界的Header清除
        requestBuilder.headers(headers -> headers.remove(headerName));
    }
}
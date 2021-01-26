package com.nepxion.discovery.plugin.strategy.gateway.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.net.URI;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Set;

import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import com.nepxion.discovery.plugin.strategy.context.AbstractStrategyContextHolder;

public class GatewayStrategyContextHolder extends AbstractStrategyContextHolder {
    public ServerWebExchange getExchange() {
        return GatewayStrategyContext.getCurrentContext().getExchange();
    }

    public ServerHttpRequest getServerHttpRequest() {
        ServerWebExchange exchange = getExchange();
        if (exchange == null) {
            // LOG.warn("The ServerWebExchange object is lost for thread switched, or it is got before context filter probably");

            return null;
        }

        ServerHttpRequest request = exchange.getRequest();
        if (request == null) {
            // LOG.warn("The ServerHttpRequest object is lost for thread switched, or it is got before context filter probably");

            return null;
        }

        return request;
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        ServerHttpRequest request = getServerHttpRequest();
        if (request == null) {
            return null;
        }

        Set<String> headerNameSet = request.getHeaders().keySet();

        return Collections.enumeration(headerNameSet);
    }

    @Override
    public String getHeader(String name) {
        ServerHttpRequest request = getServerHttpRequest();
        if (request == null) {
            return null;
        }

        return request.getHeaders().getFirst(name);
    }

    @Override
    public String getParameter(String name) {
        ServerHttpRequest request = getServerHttpRequest();
        if (request == null) {
            return null;
        }

        return request.getQueryParams().getFirst(name);
    }

    public HttpCookie getHttpCookie(String name) {
        ServerHttpRequest request = getServerHttpRequest();
        if (request == null) {
            return null;
        }

        return request.getCookies().getFirst(name);
    }

    @Override
    public String getCookie(String name) {
        HttpCookie cookie = getHttpCookie(name);
        if (cookie != null) {
            return cookie.getValue();
        }

        return null;
    }

    public URI getURI() {
        ServerHttpRequest request = getServerHttpRequest();
        if (request == null) {
            return null;
        }

        return request.getURI();
    }
}
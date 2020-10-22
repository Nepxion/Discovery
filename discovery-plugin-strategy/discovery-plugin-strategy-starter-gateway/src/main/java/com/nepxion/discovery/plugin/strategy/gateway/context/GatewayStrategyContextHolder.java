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
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpCookie;
import org.springframework.web.server.ServerWebExchange;

import com.nepxion.discovery.plugin.strategy.context.AbstractStrategyContextHolder;

public class GatewayStrategyContextHolder extends AbstractStrategyContextHolder {
    private static final String REQUEST_BODY_KEY = "gateway_request_body";

    public ServerWebExchange getExchange() {
        return GatewayStrategyContext.getCurrentContext().getExchange();
    }

    @Override
    public String getHeader(String name) {
        ServerWebExchange exchange = getExchange();
        if (exchange == null) {
            // LOG.warn("The ServerWebExchange object is lost for thread switched, or it is got before context filter probably");

            return null;
        }

        return exchange.getRequest().getHeaders().getFirst(name);
    }

    @Override
    public String getParameter(String name) {
        ServerWebExchange exchange = getExchange();
        if (exchange == null) {
            // LOG.warn("The ServerWebExchange object is lost for thread switched, or it is got before context filter probably");

            return null;
        }

        return exchange.getRequest().getQueryParams().getFirst(name);
    }

    public HttpCookie getHttpCookie(String name) {
        ServerWebExchange exchange = getExchange();
        if (exchange == null) {
            // LOG.warn("The ServerWebExchange object is lost for thread switched, or it is got before context filter probably");

            return null;
        }

        return exchange.getRequest().getCookies().getFirst(name);
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
        ServerWebExchange exchange = getExchange();
        if (exchange == null) {
            // LOG.warn("The ServerWebExchange object is lost for thread switched, or it is got before context filter probably");

            return null;
        }

        return exchange.getRequest().getURI();
    }

    @Override
    public Map<String, String> getHeaders() {
        return getExchange().getRequest().getHeaders().toSingleValueMap();
    }

    @Override
    public Map<String, Object> getBody() {
        return (Map<String, Object>) getExchange().getAttributes().get(REQUEST_BODY_KEY);
    }

    @Override
    public Map<String, Object> getParam() {
        Map<String, Object> map = new HashMap<>(16);
        map.putAll(getExchange().getRequest().getQueryParams().toSingleValueMap());
        return map;
    }

    @Override
    public String getMethod() {
        return getExchange().getRequest().getMethodValue().toUpperCase();
    }
}
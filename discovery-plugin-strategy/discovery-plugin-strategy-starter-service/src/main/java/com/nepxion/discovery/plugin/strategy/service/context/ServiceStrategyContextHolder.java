package com.nepxion.discovery.plugin.strategy.service.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author Fan Yang
 * @version 1.0
 */

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.nepxion.discovery.plugin.strategy.service.utils.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nepxion.discovery.plugin.strategy.context.AbstractStrategyContextHolder;
import com.nepxion.discovery.plugin.strategy.service.filter.ServiceStrategyRouteFilter;

public class ServiceStrategyContextHolder extends AbstractStrategyContextHolder {
    public ServletRequestAttributes getRestAttributes() {
        RequestAttributes requestAttributes = RestStrategyContext.getCurrentContext().getRequestAttributes();
        if (requestAttributes == null) {
            requestAttributes = RequestContextHolder.getRequestAttributes();
        }

        return (ServletRequestAttributes) requestAttributes;
    }

    public Map<String, Object> getRpcAttributes() {
        return RpcStrategyContext.getCurrentContext().getAttributes();
    }

    @Override
    public String getHeader(String name) {
        ServletRequestAttributes attributes = getRestAttributes();
        if (attributes == null) {
            // LOG.warn("The ServletRequestAttributes object is lost for thread switched probably");

            return null;
        }

        return attributes.getRequest().getHeader(name);
    }

    @Override
    public String getParameter(String name) {
        ServletRequestAttributes attributes = getRestAttributes();
        if (attributes == null) {
            // LOG.warn("The ServletRequestAttributes object is lost for thread switched probably");

            return null;
        }

        return attributes.getRequest().getParameter(name);
    }

    public Cookie getHttpCookie(String name) {
        ServletRequestAttributes attributes = getRestAttributes();
        if (attributes == null) {
            // LOG.warn("The ServletRequestAttributes object is lost for thread switched probably");

            return null;
        }

        Cookie[] cookies = attributes.getRequest().getCookies();
        if (cookies == null) {
            return null;
        }

        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            String cookieName = cookie.getName();
            if (StringUtils.equals(cookieName, name)) {
                return cookie;
            }
        }

        return null;
    }

    @Override
    public String getCookie(String name) {
        Cookie cookie = getHttpCookie(name);
        if (cookie != null) {
            return cookie.getValue();
        }

        return null;
    }

    public String getRequestURL() {
        ServletRequestAttributes attributes = getRestAttributes();
        if (attributes == null) {
            // LOG.warn("The ServletRequestAttributes object is lost for thread switched probably");

            return null;
        }

        return attributes.getRequest().getRequestURL().toString();
    }

    public String getRequestURI() {
        ServletRequestAttributes attributes = getRestAttributes();
        if (attributes == null) {
            // LOG.warn("The ServletRequestAttributes object is lost for thread switched probably");

            return null;
        }

        return attributes.getRequest().getRequestURI();
    }

    // 如果配置了内置条件Header，强制使用内置条件Header的模式
    // 该模式只适用于服务层。不希望在服务层处理的这么复杂，且一般情况下，不会在服务层内置Header
    // 该模式不适用于网关层。网关层会在过滤器中预先塞入内置Header
    @Override
    protected boolean isInnerConditionHeaderForced() {
        return true;
    }

    @Autowired
    protected ServiceStrategyRouteFilter serviceStrategyRouteFilter;

    @Override
    public String getRouteVersion() {
        String routeVersion = serviceStrategyRouteFilter.getRouteVersion();
        if (StringUtils.isEmpty(routeVersion)) {
            routeVersion = super.getRouteVersion();
        }

        return routeVersion;
    }

    @Override
    public String getRouteRegion() {
        String routeRegion = serviceStrategyRouteFilter.getRouteRegion();
        if (StringUtils.isEmpty(routeRegion)) {
            routeRegion = super.getRouteRegion();
        }

        return routeRegion;
    }

    @Override
    public String getRouteEnvironment() {
        String routeEnvironment = serviceStrategyRouteFilter.getRouteEnvironment();
        if (StringUtils.isEmpty(routeEnvironment)) {
            routeEnvironment = super.getRouteEnvironment();
        }

        return routeEnvironment;
    }

    @Override
    public String getRouteAddress() {
        String routeAddress = serviceStrategyRouteFilter.getRouteAddress();
        if (StringUtils.isEmpty(routeAddress)) {
            routeAddress = super.getRouteAddress();
        }

        return routeAddress;
    }

    @Override
    public String getRouteVersionWeight() {
        String routeVersionWeight = serviceStrategyRouteFilter.getRouteVersionWeight();
        if (StringUtils.isEmpty(routeVersionWeight)) {
            routeVersionWeight = super.getRouteVersionWeight();
        }

        return routeVersionWeight;
    }

    @Override
    public String getRouteRegionWeight() {
        String routeRegionWeight = serviceStrategyRouteFilter.getRouteRegionWeight();
        if (StringUtils.isEmpty(routeRegionWeight)) {
            routeRegionWeight = super.getRouteRegionWeight();
        }

        return routeRegionWeight;
    }

    @Override
    public String getRouteIdBlacklist() {
        String routeIdBlacklist = serviceStrategyRouteFilter.getRouteIdBlacklist();
        if (StringUtils.isEmpty(routeIdBlacklist)) {
            routeIdBlacklist = super.getRouteIdBlacklist();
        }

        return routeIdBlacklist;
    }

    @Override
    public String getRouteAddressBlacklist() {
        String routeAddressBlacklist = serviceStrategyRouteFilter.getRouteAddressBlacklist();
        if (StringUtils.isEmpty(routeAddressBlacklist)) {
            routeAddressBlacklist = super.getRouteAddressBlacklist();
        }

        return routeAddressBlacklist;
    }

    @Override
    public Map<String, String> getHeaders() {
        Map<String, String> map = new HashMap<>(16);
        Enumeration<String> enumeration = getRestAttributes().getRequest().getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String headerName = enumeration.nextElement();
            map.put(headerName, getRestAttributes().getRequest().getHeader(headerName));
        }
        return map;
    }

    @Override
    public Map<String, Object> getBody() {
        RequestBodyContext requestBodyContext = RequestBodyContext.getContext();
        return requestBodyContext != null ? requestBodyContext.get() : null;
    }

    @Override
    public Map<String, Object> getParam() {
        HttpServletRequest request = getRestAttributes().getRequest();
        return RequestUtils.paramsToMap(request.getQueryString());
    }

    @Override
    public String getMethod() {
        return getRestAttributes().getRequest().getMethod().toUpperCase();
    }
}
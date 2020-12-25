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

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nepxion.discovery.plugin.strategy.context.AbstractStrategyContextHolder;

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

    public HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes attributes = getRestAttributes();
        if (attributes == null) {
            // LOG.warn("The ServletRequestAttributes object is lost for thread switched probably");

            return null;
        }

        HttpServletRequest request = attributes.getRequest();
        if (request == null) {
            // LOG.warn("The HttpServletRequest object is lost for thread switched probably");

            return null;
        }

        return request;
    }

    public String getWebMvcHeader(String name) {
        HttpServletRequest request = getHttpServletRequest();
        if (request == null) {
            return null;
        }

        return request.getHeader(name);
    }

    public String getWebMvcParameter(String name) {
        HttpServletRequest request = getHttpServletRequest();
        if (request == null) {
            return null;
        }

        return request.getParameter(name);
    }

    public Cookie getWebMvcHttpCookie(String name) {
        HttpServletRequest request = getHttpServletRequest();
        if (request == null) {
            return null;
        }

        Cookie[] cookies = request.getCookies();
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

    public String getWebMvcCookie(String name) {
        Cookie cookie = getWebMvcHttpCookie(name);
        if (cookie != null) {
            return cookie.getValue();
        }

        return null;
    }

    public String getWebMvcRequestURL() {
        HttpServletRequest request = getHttpServletRequest();
        if (request == null) {
            return null;
        }

        return request.getRequestURL().toString();
    }

    public String getWebMvcRequestURI() {
        HttpServletRequest request = getHttpServletRequest();
        if (request == null) {
            return null;
        }

        return request.getRequestURI();
    }

    @Override
    public String getHeader(String name) {
        return getWebMvcHeader(name);
    }

    @Override
    public String getParameter(String name) {
        return getWebMvcParameter(name);
    }

    @Override
    public String getCookie(String name) {
        return getWebMvcCookie(name);
    }

    public String getRequestURL() {
        return getWebMvcRequestURL();
    }

    public String getRequestURI() {
        return getWebMvcRequestURI();
    }
}
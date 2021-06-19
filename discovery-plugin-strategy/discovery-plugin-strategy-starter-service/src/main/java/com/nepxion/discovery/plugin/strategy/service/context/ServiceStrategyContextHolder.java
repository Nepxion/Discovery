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

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nepxion.discovery.plugin.strategy.context.AbstractStrategyContextHolder;
import com.nepxion.discovery.plugin.strategy.service.constant.ServiceStrategyConstant;

public class ServiceStrategyContextHolder extends AbstractStrategyContextHolder {
    // 如果外界也传了相同的Header，例如，从Postman传递过来的Header，当下面的变量为true，以服务设置为优先，否则以外界传值为优先
    @Value("${" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SERVICE_HEADER_PRIORITY + ":true}")
    protected Boolean serviceHeaderPriority;

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

    @Override
    public Enumeration<String> getHeaderNames() {
        HttpServletRequest request = getHttpServletRequest();
        if (request == null) {
            return null;
        }

        Enumeration<String> headerNames = request.getHeaderNames();

        Map<String, String> headerMap = strategyWrapper.getHeaderMap();
        if (MapUtils.isNotEmpty(headerMap)) {
            List<String> headerNameList = Collections.list(headerNames);

            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                String headerName = entry.getKey();
                if (!headerNameList.contains(headerName)) {
                    headerNameList.add(headerName);
                }
            }

            return Collections.enumeration(headerNameList);
        }

        return headerNames;
    }

    @Override
    public String getHeader(String name) {
        if (serviceHeaderPriority) {
            String header = strategyWrapper.getHeader(name);
            if (StringUtils.isEmpty(header)) {
                HttpServletRequest request = getHttpServletRequest();
                if (request != null) {
                    header = request.getHeader(name);
                }
            }

            return header;
        } else {
            String header = null;
            HttpServletRequest request = getHttpServletRequest();
            if (request != null) {
                header = request.getHeader(name);
            }

            if (StringUtils.isEmpty(header)) {
                header = strategyWrapper.getHeader(name);
            }

            return header;
        }
    }

    @Override
    public String getParameter(String name) {
        HttpServletRequest request = getHttpServletRequest();
        if (request == null) {
            return null;
        }

        return request.getParameter(name);
    }

    public Cookie getHttpCookie(String name) {
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

    @Override
    public String getCookie(String name) {
        Cookie cookie = getHttpCookie(name);
        if (cookie != null) {
            return cookie.getValue();
        }

        return null;
    }

    public String getRequestURL() {
        HttpServletRequest request = getHttpServletRequest();
        if (request == null) {
            return null;
        }

        return request.getRequestURL().toString();
    }

    public String getRequestURI() {
        HttpServletRequest request = getHttpServletRequest();
        if (request == null) {
            return null;
        }

        return request.getRequestURI();
    }
}
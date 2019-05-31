package com.nepxion.discovery.plugin.strategy.zuul.context;

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

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.context.RequestContext;

public class ZuulStrategyContextHolder {
    private static final Logger LOG = LoggerFactory.getLogger(ZuulStrategyContextHolder.class);

    public HttpServletRequest getRequest() {
        HttpServletRequest request = ZuulStrategyContext.getCurrentContext().getRequest();
        if (request == null) {
            request = RequestContext.getCurrentContext().getRequest();
        }

        return request;
    }

    public Map<String, String> getZuulRequestHeaders() {
        return RequestContext.getCurrentContext().getZuulRequestHeaders();
    }

    public String getHeader(String name) {
        HttpServletRequest request = getRequest();
        if (request == null) {
            LOG.warn("The HttpServletRequest object is null");

            return null;
        }

        // 来自于外界的Header，例如，从Postman传递过来的Header
        String header = request.getHeader(name);
        if (StringUtils.isEmpty(header)) {
            // 来自于Zuul Filter的Header
            header = getZuulRequestHeaders().get(name);
        }

        return header;
    }
}
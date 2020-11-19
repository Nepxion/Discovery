package com.nepxion.discovery.plugin.strategy.service.aop;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.util.StringUtil;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.service.constant.ServiceStrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.context.ServiceStrategyContextHolder;
import com.nepxion.discovery.plugin.strategy.util.StrategyUtil;

public abstract class AbstractStrategyInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractStrategyInterceptor.class);

    @Autowired
    protected ConfigurableEnvironment environment;

    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected ServiceStrategyContextHolder serviceStrategyContextHolder;

    @Value("${" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_REST_INTERCEPT_DEBUG_ENABLED + ":false}")
    protected Boolean interceptDebugEnabled;

    protected List<String> requestHeaderList = new ArrayList<String>();

    public AbstractStrategyInterceptor(String contextRequestHeaders, String businessRequestHeaders) {
        if (StringUtils.isNotEmpty(contextRequestHeaders)) {
            requestHeaderList.addAll(StringUtil.splitToList(contextRequestHeaders.toLowerCase()));
        }
        if (StringUtils.isNotEmpty(businessRequestHeaders)) {
            requestHeaderList.addAll(StringUtil.splitToList(businessRequestHeaders.toLowerCase()));
        }

        LOG.info("------- " + getInterceptorName() + " Intercept Information -------");
        LOG.info(getInterceptorName() + " desires to intercept customer headers are {}", requestHeaderList);
        LOG.info("--------------------------------------------------");
    }

    protected void interceptInputHeader() {
        if (!interceptDebugEnabled) {
            return;
        }

        ServletRequestAttributes attributes = serviceStrategyContextHolder.getRestAttributes();
        if (attributes == null) {
            return;
        }

        HttpServletRequest previousRequest = attributes.getRequest();
        Enumeration<String> headerNames = previousRequest.getHeaderNames();
        if (headerNames == null) {
            return;
        }

        System.out.println("------- " + getInterceptorName() + " Intercept Input Header Information -------");
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            boolean isHeaderContains = isHeaderContains(headerName.toLowerCase());
            if (isHeaderContains) {
                String headerValue = previousRequest.getHeader(headerName);

                System.out.println(headerName + "=" + headerValue);
            }
        }
        System.out.println("--------------------------------------------------");
    }

    protected boolean isHeaderContains(String headerName) {
        return headerName.startsWith(DiscoveryConstant.N_D_PREFIX) || requestHeaderList.contains(headerName);
    }

    protected boolean isHeaderContainsExcludeInner(String headerName) {
        return isHeaderContains(headerName) && !StrategyUtil.isInnerHeaderContains(headerName);

        // return isHeaderContains(headerName) && !headerName.startsWith(DiscoveryConstant.N_D_SERVICE_PREFIX);
    }

    protected abstract String getInterceptorName();
}
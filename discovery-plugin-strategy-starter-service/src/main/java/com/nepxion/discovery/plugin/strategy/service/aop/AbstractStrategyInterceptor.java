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
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.util.StringUtil;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.service.constant.ServiceStrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.context.ServiceStrategyContextHolder;

public abstract class AbstractStrategyInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractStrategyInterceptor.class);

    @Autowired
    protected ConfigurableEnvironment environment;

    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected ServiceStrategyContextHolder serviceStrategyContextHolder;

    protected List<String> requestHeaderList = new ArrayList<String>();

    public AbstractStrategyInterceptor(String requestHeaders) {
        if (StringUtils.isNotEmpty(requestHeaders)) {
            requestHeaderList.addAll(StringUtil.splitToList(requestHeaders.toLowerCase(), DiscoveryConstant.SEPARATE));
        }
        /*if (!requestHeaderList.contains(DiscoveryConstant.N_D_VERSION)) {
            requestHeaderList.add(DiscoveryConstant.N_D_VERSION);
        }
        if (!requestHeaderList.contains(DiscoveryConstant.N_D_REGION)) {
            requestHeaderList.add(DiscoveryConstant.N_D_REGION);
        }
        if (!requestHeaderList.contains(DiscoveryConstant.N_D_ADDRESS)) {
            requestHeaderList.add(DiscoveryConstant.N_D_ADDRESS);
        }
        if (!requestHeaderList.contains(DiscoveryConstant.N_D_VERSION_WEIGHT)) {
            requestHeaderList.add(DiscoveryConstant.N_D_VERSION_WEIGHT);
        }
        if (!requestHeaderList.contains(DiscoveryConstant.N_D_REGION_WEIGHT)) {
            requestHeaderList.add(DiscoveryConstant.N_D_REGION_WEIGHT);
        }*/
    }

    protected void printInputRouteHeader() {
        Boolean interceptLogPrint = environment.getProperty(ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_INTERCEPT_LOG_PRINT, Boolean.class, Boolean.FALSE);
        if (!interceptLogPrint) {
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

        LOG.info("--------- Input Route Header Information ---------");
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            boolean isHeaderContains = isHeaderContains(headerName.toLowerCase());
            if (isHeaderContains) {
                String headerValue = previousRequest.getHeader(headerName);

                LOG.info("{}={}", headerName, headerValue);
            }
        }
        LOG.info("--------------------------------------------------");
    }

    protected boolean isHeaderContains(String headerName) {
        return headerName.startsWith(DiscoveryConstant.N_D_SERVICE_PREFIX) || requestHeaderList.contains(headerName);
    }

    protected boolean isHeaderContainsExcludeInner(String headerName) {
        return isHeaderContains(headerName) &&
                !StringUtils.equals(headerName, DiscoveryConstant.N_D_SERVICE_TYPE) &&
                !StringUtils.equals(headerName, DiscoveryConstant.N_D_SERVICE_ID) &&
                !StringUtils.equals(headerName, DiscoveryConstant.N_D_GROUP);
    }
}
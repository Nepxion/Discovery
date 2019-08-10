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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.util.StringUtil;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.service.constant.ServiceStrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.context.ServiceStrategyContextHolder;

public abstract class AbstractStrategyInterceptor {
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
            requestHeaderList.addAll(StringUtil.splitToList(contextRequestHeaders.toLowerCase(), DiscoveryConstant.SEPARATE));
        }
        if (StringUtils.isNotEmpty(businessRequestHeaders)) {
            requestHeaderList.addAll(StringUtil.splitToList(businessRequestHeaders.toLowerCase(), DiscoveryConstant.SEPARATE));
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

        System.out.println("------- Intercept Input Header Information -------");
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
        return isHeaderContains(headerName) &&
                !StringUtils.equals(headerName, DiscoveryConstant.N_D_SERVICE_GROUP) &&
                !StringUtils.equals(headerName, DiscoveryConstant.N_D_SERVICE_TYPE) &&
                !StringUtils.equals(headerName, DiscoveryConstant.N_D_SERVICE_ID) &&
                !StringUtils.equals(headerName, DiscoveryConstant.N_D_SERVICE_ADDRESS) &&
                !StringUtils.equals(headerName, DiscoveryConstant.N_D_SERVICE_VERSION) &&
                !StringUtils.equals(headerName, DiscoveryConstant.N_D_SERVICE_REGION);
        // return isHeaderContains(headerName) && !headerName.startsWith(DiscoveryConstant.N_D_SERVICE_PREFIX);
    }
}
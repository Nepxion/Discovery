package com.nepxion.discovery.plugin.strategy.aop;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Enumeration;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.InterceptorType;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;
import com.nepxion.discovery.plugin.strategy.context.StrategyHeaderContext;
import com.nepxion.discovery.plugin.strategy.util.StrategyUtil;

public abstract class AbstractStrategyInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractStrategyInterceptor.class);

    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected StrategyContextHolder strategyContextHolder;

    @Autowired
    protected StrategyHeaderContext strategyHeaderContext;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_REST_INTERCEPT_DEBUG_ENABLED + ":false}")
    protected Boolean interceptDebugEnabled;

    protected List<String> requestHeaderNameList;

    @PostConstruct
    public void initialize() {
        requestHeaderNameList = strategyHeaderContext.getRequestHeaderNameList();

        InterceptorType interceptorType = getInterceptorType();

        LOG.info("--------- Strategy Intercept Information ---------");
        LOG.info("{} desires to intercept customer headers are {}", interceptorType, requestHeaderNameList);
        LOG.info("--------------------------------------------------");
    }

    protected void interceptInputHeader() {
        if (!interceptDebugEnabled) {
            return;
        }

        Enumeration<String> headerNames = strategyContextHolder.getHeaderNames();
        if (headerNames != null) {
            InterceptorType interceptorType = getInterceptorType();
            Logger log = getInterceptorLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n");
            switch (interceptorType) {
                case FEIGN:
                    stringBuilder.append("--------- Feign Intercept Input Header Information ---------").append("\n");
                    break;
                case REST_TEMPLATE:
                    stringBuilder.append("----- RestTemplate Intercept Input Header Information ------").append("\n");
                    break;
                case WEB_CLIENT:
                    stringBuilder.append("------- WebClient Intercept Input Header Information -------").append("\n");
                    break;
            }
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                boolean isHeaderContains = isHeaderContains(headerName.toLowerCase());
                if (isHeaderContains) {
                    String headerValue = strategyContextHolder.getHeader(headerName);

                    stringBuilder.append(headerName + "=" + headerValue).append("\n");
                }
            }
            stringBuilder.append("------------------------------------------------------------");
            log.info(stringBuilder.toString());
        }
    }

    protected boolean isHeaderContains(String headerName) {
        return headerName.startsWith(DiscoveryConstant.N_D_PREFIX) || requestHeaderNameList.contains(headerName);
    }

    protected boolean isHeaderContainsExcludeInner(String headerName) {
        return isHeaderContains(headerName) && !StrategyUtil.isInnerHeaderContains(headerName);

        // return isHeaderContains(headerName) && !headerName.startsWith(DiscoveryConstant.N_D_SERVICE_PREFIX);
    }

    protected abstract InterceptorType getInterceptorType();

    protected abstract Logger getInterceptorLogger();
}
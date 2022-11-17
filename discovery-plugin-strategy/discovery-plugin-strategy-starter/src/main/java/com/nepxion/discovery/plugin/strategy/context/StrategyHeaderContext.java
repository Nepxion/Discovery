package com.nepxion.discovery.plugin.strategy.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;

import com.nepxion.discovery.common.entity.HeadersInjectorType;
import com.nepxion.discovery.common.util.StringUtil;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.injector.StrategyHeadersInjector;
import com.nepxion.discovery.plugin.strategy.injector.StrategyHeadersResolver;

public class StrategyHeaderContext {
    @Autowired
    private ConfigurableEnvironment environment;

    @Autowired(required = false)
    private List<StrategyHeadersInjector> strategyHeadersInjectorList;

    private List<String> requestHeaderNameList;

    @PostConstruct
    public void initialize() {
        requestHeaderNameList = new ArrayList<String>();

        String contextRequestHeaders = environment.getProperty(StrategyConstant.SPRING_APPLICATION_STRATEGY_CONTEXT_REQUEST_HEADERS);
        String businessRequestHeaders = environment.getProperty(StrategyConstant.SPRING_APPLICATION_STRATEGY_BUSINESS_REQUEST_HEADERS);
        List<String> injectorRequestHeaders = StrategyHeadersResolver.getInjectedHeaders(strategyHeadersInjectorList, HeadersInjectorType.TRANSMISSION);

        if (StringUtils.isNotEmpty(contextRequestHeaders)) {
            requestHeaderNameList.addAll(StringUtil.splitToList(contextRequestHeaders.toLowerCase()));
        }
        if (StringUtils.isNotEmpty(businessRequestHeaders)) {
            requestHeaderNameList.addAll(StringUtil.splitToList(businessRequestHeaders.toLowerCase()));
        }
        if (CollectionUtils.isNotEmpty(injectorRequestHeaders)) {
            requestHeaderNameList.addAll(injectorRequestHeaders);
        }
    }

    public List<String> getRequestHeaderNameList() {
        return requestHeaderNameList;
    }
}
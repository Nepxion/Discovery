package com.nepxion.discovery.plugin.strategy.condition;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.StrategyConditionEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.plugin.strategy.wrapper.StrategyWrapper;

@Deprecated
public class HeaderExpressionStrategyCondition extends AbstractStrategyCondition {
    private Pattern pattern = Pattern.compile(DiscoveryConstant.EXPRESSION_REGEX);

    @Autowired
    private StrategyWrapper strategyWrapper;

    @Override
    public boolean isTriggered(StrategyConditionEntity strategyConditionEntity) {
        Map<String, String> headerMap = createHeaderMap(strategyConditionEntity);

        return isTriggered(strategyConditionEntity, headerMap);
    }

    private Map<String, String> createHeaderMap(StrategyConditionEntity strategyConditionEntity) {
        String conditionHeader = strategyConditionEntity.getConditionHeader();

        Map<String, String> headerMap = new HashMap<String, String>();

        Matcher matcher = pattern.matcher(conditionHeader);
        while (matcher.find()) {
            String group = matcher.group();
            String headerName = StringUtils.substringBetween(group, DiscoveryConstant.EXPRESSION_SUB_PREFIX, DiscoveryConstant.EXPRESSION_SUB_SUFFIX);
            String headerValue = null;

            // 从外置Header获取
            if (StringUtils.isBlank(headerValue)) {
                headerValue = strategyContextHolder.getHeader(headerName);
            }

            // 从内置Header获取
            if (StringUtils.isBlank(headerValue)) {
                headerValue = strategyWrapper.getHeader(headerName);
            }

            // 从外置Parameter获取
            if (StringUtils.isBlank(headerValue)) {
                headerValue = strategyContextHolder.getParameter(headerName);
            }

            // 从外置Cookie获取
            if (StringUtils.isBlank(headerValue)) {
                headerValue = strategyContextHolder.getCookie(headerName);
            }

            if (StringUtils.isNotBlank(headerValue)) {
                headerMap.put(headerName, headerValue);
            }
        }

        return headerMap;
    }

    @Override
    public boolean isTriggered(StrategyConditionEntity strategyConditionEntity, Map<String, String> headerMap) {
        if (headerMap == null) {
            throw new DiscoveryException("Header map can't be null");
        }

        String conditionHeader = strategyConditionEntity.getConditionHeader();

        return ExpressionStrategyResolver.eval(conditionHeader, DiscoveryConstant.EXPRESSION_PREFIX, headerMap, strategyTypeComparator);
    }
}
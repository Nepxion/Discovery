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

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.StrategyConditionEntity;

public class HeaderExpressionStrategyCondition extends AbstractStrategyCondition {
    private Pattern pattern = Pattern.compile(DiscoveryConstant.EXPRESSION_REGEX);

    @Override
    public boolean isTriggered(StrategyConditionEntity strategyConditionEntity) {
        String conditionHeader = strategyConditionEntity.getConditionHeader();
        Map<String, String> headerMap = createHeaderMap(conditionHeader);

        return ExpressionStrategyResolver.eval(conditionHeader, DiscoveryConstant.EXPRESSION_PREFIX, headerMap, strategyTypeComparator);
    }

    private Map<String, String> createHeaderMap(String conditionHeader) {
        Map<String, String> headerMap = new HashMap<String, String>();

        Matcher matcher = pattern.matcher(conditionHeader);
        while (matcher.find()) {
            String group = matcher.group();
            String headerName = StringUtils.substringBetween(group, DiscoveryConstant.EXPRESSION_SUB_PREFIX, DiscoveryConstant.EXPRESSION_SUB_SUFFIX);
            String headerValue = strategyContextHolder.getHeader(headerName);
            if (StringUtils.isNotBlank(headerValue)) {
                headerMap.put(headerName, headerValue);
            }
        }

        return headerMap;
    }

    @Override
    public boolean eval(String conditionHeader, Map<String, String> headerMap) {
        return ExpressionStrategyResolver.eval(conditionHeader, DiscoveryConstant.EXPRESSION_PREFIX, headerMap, strategyTypeComparator);
    }
}
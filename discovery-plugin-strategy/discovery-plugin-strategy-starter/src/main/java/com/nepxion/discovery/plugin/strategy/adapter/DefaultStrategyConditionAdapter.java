package com.nepxion.discovery.plugin.strategy.adapter;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.StrategyConditionEntity;
import com.nepxion.discovery.plugin.strategy.condition.AbstractStrategyCondition;
import com.nepxion.discovery.plugin.strategy.condition.BodyStrategyConditionPredicate;
import com.nepxion.discovery.plugin.strategy.condition.HeaderStrategyConditionPredicate;
import com.nepxion.discovery.plugin.strategy.condition.ParameterStrategyConditionPredicate;
import com.nepxion.discovery.plugin.strategy.condition.StrategyConditionCompositePredicate;
import com.nepxion.discovery.plugin.strategy.wrapper.StrategyWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zlliu
 * @date 2020/10/22 22:16
 */
public class DefaultStrategyConditionAdapter extends AbstractStrategyCondition {
    @Autowired
    private ParameterStrategyConditionPredicate parameterStrategyConditionPredicate;

    @Autowired
    private BodyStrategyConditionPredicate bodyStrategyConditionPredicate;

    @Autowired
    private HeaderStrategyConditionPredicate headerStrategyConditionPredicate;

    @Autowired
    private StrategyWrapper strategyWrapper;

    /**
     * header模式为: #H['a'] == '1'
     */
    private Pattern pattern = Pattern.compile(DiscoveryConstant.EXPRESSION_REGEX);

    @Override
    public boolean isTriggered(StrategyConditionEntity strategyConditionEntity) {
        Map<String, String> headerMap = createHeaderMap(strategyConditionEntity);
        return isTriggered(strategyConditionEntity, headerMap);
    }

    private Map<String, String> createHeaderMap(StrategyConditionEntity strategyConditionEntity) {
        String conditionHeader = strategyConditionEntity.getConditionHeader();

        Map<String, String> headerMap = new HashMap<>();

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
        String method = strategyContextHolder.getMethod();
        StrategyConditionCompositePredicate compositePredicate = null;
        //  GET则组合header/parameter断言
        if (HttpMethod.GET.toString().equals(method)) {
            compositePredicate =
                    new StrategyConditionCompositePredicate.Builder(
                            headerStrategyConditionPredicate,
                            parameterStrategyConditionPredicate)
                            .build();
            return compositePredicate.apply(strategyConditionEntity, headerMap);
        }
        //  POST、PUT则组合header/parameter/body断言
        else if (HttpMethod.POST.toString().equals(method) ||
                HttpMethod.PUT.toString().equals(method)) {
            compositePredicate =
                    new StrategyConditionCompositePredicate.Builder(
                            headerStrategyConditionPredicate,
                            parameterStrategyConditionPredicate,
                            bodyStrategyConditionPredicate)
                            .build();
            return compositePredicate.apply(strategyConditionEntity, headerMap);
        } else {
            compositePredicate =
                    new StrategyConditionCompositePredicate.Builder(
                            headerStrategyConditionPredicate)
                            .build();
            return compositePredicate.apply(strategyConditionEntity, headerMap);
        }
    }
}

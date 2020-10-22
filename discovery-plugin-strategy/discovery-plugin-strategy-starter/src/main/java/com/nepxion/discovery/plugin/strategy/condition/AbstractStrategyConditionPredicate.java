package com.nepxion.discovery.plugin.strategy.condition;

import com.nepxion.discovery.common.entity.StrategyConditionEntity;

import java.util.Map;

/**
 * <strategy-customization>的condition断言
 *
 * @author zlliu
 * @date 2020/10/22 22:16
 */
public abstract class AbstractStrategyConditionPredicate {

    /**
     * condition断言
     * @param input
     * @param headerMap
     * @return
     */
    public abstract boolean apply(StrategyConditionEntity input, Map<String, String> headerMap);

}

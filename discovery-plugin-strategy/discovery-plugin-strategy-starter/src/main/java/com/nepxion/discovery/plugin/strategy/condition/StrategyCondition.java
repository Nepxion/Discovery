package com.nepxion.discovery.plugin.strategy.condition;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import com.nepxion.discovery.common.entity.StrategyConditionEntity;

public interface StrategyCondition {
    boolean isTriggered(StrategyConditionEntity strategyConditionEntity);

    boolean isTriggered(StrategyConditionEntity strategyConditionEntity, Map<String, String> map);
}
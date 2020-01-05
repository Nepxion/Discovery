package com.nepxion.discovery.plugin.strategy.condition;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.common.entity.StrategyConditionEntity;

public class HeaderExpressionStrategyCondition extends AbstractStrategyCondition {
    @Override
    public boolean isTriggered(StrategyConditionEntity strategyConditionEntity) {
        // 此为包含若干个Header的表达式
        // String conditionHeader = strategyConditionEntity.getConditionHeader();

        // 待实现
        return true;
    }
}
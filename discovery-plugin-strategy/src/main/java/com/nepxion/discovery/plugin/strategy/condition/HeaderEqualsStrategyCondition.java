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

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.nepxion.discovery.common.entity.StrategyConditionEntity;

public class HeaderEqualsStrategyCondition extends AbstractStrategyCondition {
    @Override
    public boolean isTriggered(StrategyConditionEntity strategyConditionEntity) {
        Map<String, String> conditionHeaderMap = strategyConditionEntity.getConditionHeaderMap();
        if (MapUtils.isNotEmpty(conditionHeaderMap)) {
            for (Map.Entry<String, String> entry : conditionHeaderMap.entrySet()) {
                String headerName = entry.getKey();
                String headerValue = entry.getValue();

                String value = strategyContextHolder.getHeader(headerName);
                if (!StringUtils.equals(headerValue, value)) {
                    return false;
                }
            }
        }

        return true;
    }
}
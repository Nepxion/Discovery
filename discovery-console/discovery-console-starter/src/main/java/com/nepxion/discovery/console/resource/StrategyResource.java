package com.nepxion.discovery.console.resource;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.common.entity.ConditionStrategy;

public interface StrategyResource {
    String parseVersionRelease(String conditionStrategyYaml);

    String parseVersionRelease(ConditionStrategy conditionStrategy);

    String createVersionRelease(String group, String conditionStrategyYaml);

    String createVersionRelease(String group, ConditionStrategy conditionStrategy);

    String clearRelease(String group);

    String createVersionRelease(String group, String serviceId, String conditionStrategyYaml);

    String createVersionRelease(String group, String serviceId, ConditionStrategy conditionStrategy);

    String clearRelease(String group, String serviceId);

    boolean validateExpression(String expression, String validation);
}
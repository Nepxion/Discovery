package com.nepxion.discovery.console.resource;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.common.entity.ConditionRouteStrategy;
import com.nepxion.discovery.common.entity.ConditionStrategy;

public interface StrategyResource {
    // 全局订阅方式，获取Json格式的蓝绿灰度发布
    ConditionStrategy getVersionRelease(String group);

    // 全局订阅方式，根据Yaml格式，创建版本蓝绿灰度发布
    String createVersionRelease(String group, String conditionStrategyYaml);

    // 全局订阅方式，根据Json格式，创建版本蓝绿灰度发布
    String createVersionRelease(String group, ConditionStrategy conditionStrategy);

    // 全局订阅方式，根据Yaml格式，重新创建版本蓝绿灰度发布（创建链路智能编排，不创建条件表达式）
    String recreateVersionRelease(String group, String conditionRouteStrategyYaml);

    // 全局订阅方式，根据Json格式，重新创建版本蓝绿灰度发布（创建链路智能编排，不创建条件表达式）
    String recreateVersionRelease(String group, ConditionRouteStrategy conditionRouteStrategy);

    // 全局订阅方式，重置蓝绿灰度发布（清除链路智能编排，不清除条件表达式）
    String resetRelease(String group);

    // 全局订阅方式，清除蓝绿灰度发布
    String clearRelease(String group);

    // 局部订阅方式，获取Json格式的蓝绿灰度发布
    ConditionStrategy getVersionRelease(String group, String serviceId);

    // 局部订阅方式，根据Yaml格式，创建版本蓝绿灰度发布
    String createVersionRelease(String group, String serviceId, String conditionStrategyYaml);

    // 局部订阅方式，根据Json格式，创建版本蓝绿灰度发布
    String createVersionRelease(String group, String serviceId, ConditionStrategy conditionStrategy);

    // 局部订阅方式，根据Yaml格式，重新创建版本蓝绿灰度发布（创建链路智能编排，不创建条件表达式）
    String recreateVersionRelease(String group, String serviceId, String conditionRouteStrategyYaml);

    // 局部订阅方式，根据Json格式，重新创建版本蓝绿灰度发布（创建链路智能编排，不创建条件表达式）
    String recreateVersionRelease(String group, String serviceId, ConditionRouteStrategy conditionRouteStrategy);

    // 局部订阅方式，重置蓝绿灰度发布（清除链路智能编排，不清除条件表达式）
    String resetRelease(String group, String serviceId);

    // 局部订阅方式，清除蓝绿灰度发布
    String clearRelease(String group, String serviceId);

    // 根据Yaml格式，解析版本蓝绿灰度发布策略为Xml格式
    String parseVersionRelease(String conditionStrategyYaml);

    // 根据Json格式，解析版本蓝绿灰度发布策略为Xml格式
    String parseVersionRelease(ConditionStrategy conditionStrategy);

    // 根据Xml格式，反解析版本蓝绿灰度发布策略为Json格式
    ConditionStrategy deparseVersionReleaseXml(String ruleXml);

    // 根据Yaml格式，反解析版本蓝绿灰度发布策略为Json格式
    ConditionStrategy deparseVersionReleaseYaml(String conditionStrategyYaml);

    // 校验策略的条件表达式
    boolean validateExpression(String expression, String validation);
}
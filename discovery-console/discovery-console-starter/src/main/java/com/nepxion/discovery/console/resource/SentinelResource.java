package com.nepxion.discovery.console.resource;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import com.nepxion.discovery.common.entity.ResultEntity;
import com.nepxion.discovery.common.entity.SentinelRuleType;

public interface SentinelResource {
    boolean updateRemoteSentinel(SentinelRuleType ruleType, String group, String serviceId, String rule);

    boolean clearRemoteSentinel(SentinelRuleType ruleType, String group, String serviceId);

    String getRemoteSentinel(SentinelRuleType ruleType, String group, String serviceId);

    List<ResultEntity> updateSentinel(SentinelRuleType ruleType, String serviceId, String rule);

    List<ResultEntity> clearSentinel(SentinelRuleType ruleType, String serviceId);

    List<ResultEntity> viewSentinel(SentinelRuleType ruleType, String serviceId);
}
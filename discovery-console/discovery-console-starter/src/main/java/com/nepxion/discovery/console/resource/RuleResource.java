package com.nepxion.discovery.console.resource;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.common.entity.RuleEntity;

public interface RuleResource {
    void updateRemoteRuleEntity(String serviceId, RuleEntity ruleEntity) throws Exception;

    void updateRemoteRuleEntity(String group, String serviceId, RuleEntity ruleEntity) throws Exception;

    void clearRemoteRuleEntity(String serviceId) throws Exception;

    void clearRemoteRuleEntity(String group, String serviceId) throws Exception;

    RuleEntity getRemoteRuleEntity(String serviceId) throws Exception;

    RuleEntity getRemoteRuleEntity(String group, String serviceId) throws Exception;

    RuleEntity toRuleEntity(String config);

    String fromRuleEntity(RuleEntity ruleEntity);

    RuleEntity parse(String config);

    String deparse(RuleEntity ruleEntity);
}
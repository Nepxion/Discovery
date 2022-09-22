package com.nepxion.discovery.console.delegate;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.common.entity.RuleEntity;

public interface ConsoleResourceDelegate {
    RuleEntity getRemoteRuleEntity(String group, String serviceId);

    boolean updateRemoteRuleEntity(String group, String serviceId, RuleEntity ruleEntity);
}
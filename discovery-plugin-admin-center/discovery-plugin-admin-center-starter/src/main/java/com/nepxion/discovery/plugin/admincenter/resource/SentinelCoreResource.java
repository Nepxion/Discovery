package com.nepxion.discovery.plugin.admincenter.resource;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.system.SystemRule;

public interface SentinelCoreResource {
    void updateFlowRules(String rule);

    void clearFlowRules();

    public List<FlowRule> viewFlowRules();

    void updateDegradeRules(String rule);

    void clearDegradeRules();

    List<DegradeRule> viewDegradeRules();

    void updateAuthorityRules(String rule);

    void clearAuthorityRules();

    List<AuthorityRule> viewAuthorityRules();

    void updateSystemRules(String rule);

    void clearSystemRules();

    List<SystemRule> viewSystemRules();
}
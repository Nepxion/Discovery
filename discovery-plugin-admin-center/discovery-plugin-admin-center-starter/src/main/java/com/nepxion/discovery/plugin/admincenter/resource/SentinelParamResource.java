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

import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;

public interface SentinelParamResource {
    void updateParamFlowRules(String rule);

    void clearParamFlowRules();

    List<ParamFlowRule> viewParamFlowRules();
}
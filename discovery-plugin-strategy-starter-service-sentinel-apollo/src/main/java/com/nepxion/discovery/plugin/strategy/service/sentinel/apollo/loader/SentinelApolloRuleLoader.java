package com.nepxion.discovery.plugin.strategy.service.sentinel.apollo.loader;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Weihua Wang
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import javax.annotation.PostConstruct;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.apollo.ApolloDataSource;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.nepxion.discovery.common.apollo.configuration.ApolloAutoConfiguration;
import com.nepxion.discovery.plugin.strategy.service.sentinel.constant.SentinelStrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.sentinel.loader.SentinelDataSourceRuleLoader;

public class SentinelApolloRuleLoader extends SentinelDataSourceRuleLoader {
    private String namespace;

    @PostConstruct
    public void initialize() {
        namespace = ApolloAutoConfiguration.getNamespace(applicationContext.getEnvironment());
    }

    @Override
    public ReadableDataSource<String, List<FlowRule>> getFlowRuleDataSource() {
        return new ApolloDataSource<>(namespace, pluginAdapter.getGroup() + "-" + pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_FLOW_KEY, getRule(flowPath), sentinelFlowRuleParser);
    }

    @Override
    public ReadableDataSource<String, List<DegradeRule>> getDegradeRuleDataSource() {
        return new ApolloDataSource<>(namespace, pluginAdapter.getGroup() + "-" + pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_DEGRADE_KEY, getRule(degradePath), sentinelDegradeRuleParser);
    }

    @Override
    public ReadableDataSource<String, List<AuthorityRule>> getAuthorityRuleDataSource() {
        return new ApolloDataSource<>(namespace, pluginAdapter.getGroup() + "-" + pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_AUTHORITY_KEY, getRule(authorityPath), sentinelAuthorityRuleParser);
    }

    @Override
    public ReadableDataSource<String, List<SystemRule>> getSystemRuleDataSource() {
        return new ApolloDataSource<>(namespace, pluginAdapter.getGroup() + "-" + pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_SYSTEM_KEY, getRule(systemPath), sentinelSystemRuleParser);
    }

    @Override
    public ReadableDataSource<String, List<ParamFlowRule>> getParamFlowRuleDataSource() {
        return new ApolloDataSource<>(namespace, pluginAdapter.getGroup() + "-" + pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_PARAM_FLOW_KEY, getRule(paramFlowPath), sentinelParamFlowRuleParser);
    }

    @Override
    public boolean isFileManualLoaded() {
        return false;
    }
}
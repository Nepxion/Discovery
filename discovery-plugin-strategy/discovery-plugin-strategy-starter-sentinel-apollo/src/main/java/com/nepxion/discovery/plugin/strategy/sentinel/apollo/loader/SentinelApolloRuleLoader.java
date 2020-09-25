package com.nepxion.discovery.plugin.strategy.sentinel.apollo.loader;

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

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.apollo.ApolloDataSource;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.nepxion.discovery.common.apollo.configuration.ApolloAutoConfiguration;
import com.nepxion.discovery.common.apollo.constant.ApolloConstant;
import com.nepxion.discovery.plugin.strategy.sentinel.constant.SentinelStrategyConstant;
import com.nepxion.discovery.plugin.strategy.sentinel.loader.SentinelDataSourceRuleLoader;

public class SentinelApolloRuleLoader extends SentinelDataSourceRuleLoader {
    private String namespace;

    @Override
    public void initialize() {
        namespace = ApolloAutoConfiguration.getNamespace(applicationContext.getEnvironment());
    }

    @Override
    public ReadableDataSource<String, List<FlowRule>> getFlowRuleDataSource() {
        return new ApolloDataSource<>(namespace, pluginAdapter.getGroup() + "-" + pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_FLOW_KEY, null, sentinelFlowRuleParser);
    }

    @Override
    public ReadableDataSource<String, List<DegradeRule>> getDegradeRuleDataSource() {
        return new ApolloDataSource<>(namespace, pluginAdapter.getGroup() + "-" + pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_DEGRADE_KEY, null, sentinelDegradeRuleParser);
    }

    @Override
    public ReadableDataSource<String, List<AuthorityRule>> getAuthorityRuleDataSource() {
        return new ApolloDataSource<>(namespace, pluginAdapter.getGroup() + "-" + pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_AUTHORITY_KEY, null, sentinelAuthorityRuleParser);
    }

    @Override
    public ReadableDataSource<String, List<SystemRule>> getSystemRuleDataSource() {
        return new ApolloDataSource<>(namespace, pluginAdapter.getGroup() + "-" + pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_SYSTEM_KEY, null, sentinelSystemRuleParser);
    }

    @Override
    public ReadableDataSource<String, List<ParamFlowRule>> getParamFlowRuleDataSource() {
        return new ApolloDataSource<>(namespace, pluginAdapter.getGroup() + "-" + pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_PARAM_FLOW_KEY, null, sentinelParamFlowRuleParser);
    }

    @Override
    public String getConfigType() {
        return ApolloConstant.APOLLO_TYPE;
    }
}
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

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.apollo.ApolloDataSource;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.nepxion.discovery.common.apollo.configuration.ApolloAutoConfiguration;
import com.nepxion.discovery.plugin.strategy.service.sentinel.constant.SentinelStrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.sentinel.loader.AbstractSentinelRuleLoader;
import com.nepxion.discovery.plugin.strategy.service.sentinel.loader.SentinelRuleLoaderUtil;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.SentinelAuthorityRuleParser;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.SentinelDegradeRuleParser;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.SentinelFlowRuleParser;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.SentinelParamFlowRuleParser;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.SentinelSystemRuleParser;

public class SentinelApolloRuleLoader extends AbstractSentinelRuleLoader {
    @Override
    public void load() {
        String namespace = ApolloAutoConfiguration.getNamespace(applicationContext.getEnvironment());

        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new ApolloDataSource<>(namespace, pluginAdapter.getGroup() + "-" + pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_FLOW_KEY, SentinelRuleLoaderUtil.getRuleText(applicationContext, flowPath), new SentinelFlowRuleParser());
        ReadableDataSource<String, List<DegradeRule>> degradeRuleDataSource = new ApolloDataSource<>(namespace, pluginAdapter.getGroup() + "-" + pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_DEGRADE_KEY, SentinelRuleLoaderUtil.getRuleText(applicationContext, degradePath), new SentinelDegradeRuleParser());
        ReadableDataSource<String, List<AuthorityRule>> authorityRuleDataSource = new ApolloDataSource<>(namespace, pluginAdapter.getGroup() + "-" + pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_AUTHORITY_KEY, SentinelRuleLoaderUtil.getRuleText(applicationContext, authorityPath), new SentinelAuthorityRuleParser());
        ReadableDataSource<String, List<SystemRule>> systemRuleDataSource = new ApolloDataSource<>(namespace, pluginAdapter.getGroup() + "-" + pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_SYSTEM_KEY, SentinelRuleLoaderUtil.getRuleText(applicationContext, systemPath), new SentinelSystemRuleParser());
        ReadableDataSource<String, List<ParamFlowRule>> paramFlowRuleDataSource = new ApolloDataSource<>(namespace, pluginAdapter.getGroup() + "-" + pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_PARAM_FLOW_KEY, SentinelRuleLoaderUtil.getRuleText(applicationContext, paramFlowPath), new SentinelParamFlowRuleParser());

        loadRemote(flowRuleDataSource, degradeRuleDataSource, authorityRuleDataSource, systemRuleDataSource, paramFlowRuleDataSource);
        loadLog();
    }
}
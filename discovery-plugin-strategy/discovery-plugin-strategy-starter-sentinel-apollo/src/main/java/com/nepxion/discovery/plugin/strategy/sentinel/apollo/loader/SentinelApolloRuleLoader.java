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

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.apollo.ApolloDataSource;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.nepxion.discovery.common.apollo.constant.ApolloConstant;
import com.nepxion.discovery.plugin.strategy.sentinel.constant.SentinelStrategyConstant;
import com.nepxion.discovery.plugin.strategy.sentinel.loader.SentinelDataSourceRuleLoader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;

import java.util.List;

public class SentinelApolloRuleLoader extends SentinelDataSourceRuleLoader {
    private String namespace;

    @Override
    public void initialize() {
        namespace = getNamespace(applicationContext.getEnvironment());
    }

    /**
     * sentinel配置建议用单独配置，避免混乱
     * @param environment
     * @return
     */
    private String getNamespace(Environment environment) {
        String namespace = environment.getProperty(ApolloConstant.APOLLO_SENTINEL_NAMESPACE);
        if (StringUtils.isEmpty(namespace) || namespace.contains(ApolloConstant.SEPARATE)) {
            namespace = ApolloConstant.APOLLO_PLUGIN_NAMESPACE;
        }
        return namespace;
    }

    @Override
    public ReadableDataSource<String, List<FlowRule>> getFlowRuleDataSource() {
//        String oldRuleKey = pluginAdapter.getGroup() + "-" + pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_FLOW_KEY;

        //  sentinel dashboard和apollo同步时，不知道group，所以建议直接根据serviceId获取
        String ruleKey = pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_FLOW_KEY;
        return new ApolloDataSource<>(namespace, ruleKey, null, sentinelFlowRuleParser);
    }

    @Override
    public ReadableDataSource<String, List<DegradeRule>> getDegradeRuleDataSource() {
//        String oldRuleKey = pluginAdapter.getGroup() + "-" + pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_DEGRADE_KEY;

        //  sentinel dashboard和apollo同步时，不知道group，所以建议直接根据serviceId获取
        String ruleKey = pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_DEGRADE_KEY;
        return new ApolloDataSource<>(namespace, ruleKey, null, sentinelDegradeRuleParser);
    }

    @Override
    public ReadableDataSource<String, List<AuthorityRule>> getAuthorityRuleDataSource() {
//        String oldRuleKey = pluginAdapter.getGroup() + "-" + pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_AUTHORITY_KEY;

        //  sentinel dashboard和apollo同步时，不知道group，所以建议直接根据serviceId获取
        String ruleKey = pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_AUTHORITY_KEY;
        return new ApolloDataSource<>(namespace, ruleKey, null, sentinelAuthorityRuleParser);
    }

    @Override
    public ReadableDataSource<String, List<SystemRule>> getSystemRuleDataSource() {
//        String oldRuleKey = pluginAdapter.getGroup() + "-" + pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_SYSTEM_KEY;

        //  sentinel dashboard和apollo同步时，不知道group，所以建议直接根据serviceId获取
        String ruleKey = pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_SYSTEM_KEY;
        return new ApolloDataSource<>(namespace, ruleKey, null, sentinelSystemRuleParser);
    }

    @Override
    public ReadableDataSource<String, List<ParamFlowRule>> getParamFlowRuleDataSource() {
//        String oldRuleKey = pluginAdapter.getGroup() + "-" + pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_PARAM_FLOW_KEY;

        //  sentinel dashboard和apollo同步时，不知道group，所以建议直接根据serviceId获取
        String ruleKey = pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_PARAM_FLOW_KEY;
        return new ApolloDataSource<>(namespace, ruleKey, null, sentinelParamFlowRuleParser);
    }

    @Override
    public String getConfigType() {
        return ApolloConstant.APOLLO_TYPE;
    }
}
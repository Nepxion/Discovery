package com.nepxion.discovery.plugin.strategy.sentinel.nacos.loader;

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
import java.util.Properties;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.nepxion.discovery.common.nacos.configuration.NacosAutoConfiguration;
import com.nepxion.discovery.common.nacos.constant.NacosConstant;
import com.nepxion.discovery.plugin.strategy.sentinel.constant.SentinelStrategyConstant;
import com.nepxion.discovery.plugin.strategy.sentinel.loader.SentinelDataSourceRuleLoader;

public class SentinelNacosRuleLoader extends SentinelDataSourceRuleLoader {
    private Properties properties;

    @Override
    public void initialize() {
        properties = NacosAutoConfiguration.createNacosProperties(applicationContext.getEnvironment(), false);
    }

    @Override
    public ReadableDataSource<String, List<FlowRule>> getFlowRuleDataSource() {
        return new NacosDataSource<>(properties, pluginAdapter.getGroup(), pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_FLOW_KEY, sentinelFlowRuleParser);
    }

    @Override
    public ReadableDataSource<String, List<DegradeRule>> getDegradeRuleDataSource() {
        return new NacosDataSource<>(properties, pluginAdapter.getGroup(), pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_DEGRADE_KEY, sentinelDegradeRuleParser);
    }

    @Override
    public ReadableDataSource<String, List<AuthorityRule>> getAuthorityRuleDataSource() {
        return new NacosDataSource<>(properties, pluginAdapter.getGroup(), pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_AUTHORITY_KEY, sentinelAuthorityRuleParser);
    }

    @Override
    public ReadableDataSource<String, List<SystemRule>> getSystemRuleDataSource() {
        return new NacosDataSource<>(properties, pluginAdapter.getGroup(), pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_SYSTEM_KEY, sentinelSystemRuleParser);
    }

    @Override
    public ReadableDataSource<String, List<ParamFlowRule>> getParamFlowRuleDataSource() {
        return new NacosDataSource<>(properties, pluginAdapter.getGroup(), pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_PARAM_FLOW_KEY, sentinelParamFlowRuleParser);
    }

    @Override
    public String getConfigType() {
        return NacosConstant.NACOS_TYPE;
    }
}
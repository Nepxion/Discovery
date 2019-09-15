package com.nepxion.discovery.plugin.strategy.service.sentinel.nacos.loader;

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

import javax.annotation.PostConstruct;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.nepxion.discovery.common.nacos.configuration.NacosAutoConfiguration;
import com.nepxion.discovery.plugin.strategy.service.sentinel.constant.SentinelStrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.sentinel.loader.SentinelDatasourceRuleLoader;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.SentinelAuthorityRuleParser;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.SentinelDegradeRuleParser;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.SentinelFlowRuleParser;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.SentinelParamFlowRuleParser;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.SentinelSystemRuleParser;

public class SentinelNacosRuleLoader extends SentinelDatasourceRuleLoader {
    private Properties properties;

    @PostConstruct
    public void initialize() {
        properties = NacosAutoConfiguration.createNacosProperties(applicationContext.getEnvironment(), false);
    }

    @Override
    public ReadableDataSource<String, List<FlowRule>> getFlowRuleDataSource() {
        return new NacosDataSource<>(properties, pluginAdapter.getGroup(), pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_FLOW_KEY, new SentinelFlowRuleParser());
    }

    @Override
    public ReadableDataSource<String, List<DegradeRule>> getDegradeRuleDataSource() {
        return new NacosDataSource<>(properties, pluginAdapter.getGroup(), pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_DEGRADE_KEY, new SentinelDegradeRuleParser());
    }

    @Override
    public ReadableDataSource<String, List<AuthorityRule>> getAuthorityRuleDataSource() {
        return new NacosDataSource<>(properties, pluginAdapter.getGroup(), pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_AUTHORITY_KEY, new SentinelAuthorityRuleParser());
    }

    @Override
    public ReadableDataSource<String, List<SystemRule>> getSystemRuleDataSource() {
        return new NacosDataSource<>(properties, pluginAdapter.getGroup(), pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_SYSTEM_KEY, new SentinelSystemRuleParser());
    }

    @Override
    public ReadableDataSource<String, List<ParamFlowRule>> getParamFlowRuleDataSource() {
        return new NacosDataSource<>(properties, pluginAdapter.getGroup(), pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_PARAM_FLOW_KEY, new SentinelParamFlowRuleParser());
    }

    @Override
    public boolean isFileManualLoaded() {
        return true;
    }
}
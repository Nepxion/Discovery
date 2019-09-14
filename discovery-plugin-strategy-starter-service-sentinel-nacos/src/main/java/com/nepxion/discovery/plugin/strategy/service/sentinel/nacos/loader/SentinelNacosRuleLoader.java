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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.nepxion.discovery.common.nacos.configuration.NacosAutoConfiguration;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.service.sentinel.constant.SentinelStrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.sentinel.loader.AbstractSentinelRuleLoader;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.SentinelAuthorityRuleParser;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.SentinelDegradeRuleParser;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.SentinelFlowRuleParser;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.SentinelParamFlowRuleParser;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.SentinelSystemRuleParser;

public class SentinelNacosRuleLoader extends AbstractSentinelRuleLoader {
    private static final Logger LOG = LoggerFactory.getLogger(SentinelNacosRuleLoader.class);

    @Override
    public void load() {
        Properties properties = NacosAutoConfiguration.createNacosProperties(applicationContext.getEnvironment(), false);

        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(properties, pluginAdapter.getGroup(), pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_FLOW_KEY, new SentinelFlowRuleParser());
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
        LOG.info("{} flow rules loaded...", FlowRuleManager.getRules().size());

        ReadableDataSource<String, List<DegradeRule>> degradeRuleDataSource = new NacosDataSource<>(properties, pluginAdapter.getGroup(), pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_DEGRADE_KEY, new SentinelDegradeRuleParser());
        DegradeRuleManager.register2Property(degradeRuleDataSource.getProperty());
        LOG.info("{} degrade rules loaded...", DegradeRuleManager.getRules().size());

        ReadableDataSource<String, List<AuthorityRule>> authorityRuleDataSource = new NacosDataSource<>(properties, pluginAdapter.getGroup(), pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_AUTHORITY_KEY, new SentinelAuthorityRuleParser());
        AuthorityRuleManager.register2Property(authorityRuleDataSource.getProperty());
        LOG.info("{} authority rules loaded...", AuthorityRuleManager.getRules().size());

        ReadableDataSource<String, List<SystemRule>> systemRuleDataSource = new NacosDataSource<>(properties, pluginAdapter.getGroup(), pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_SYSTEM_KEY, new SentinelSystemRuleParser());
        SystemRuleManager.register2Property(systemRuleDataSource.getProperty());
        LOG.info("{} system rules loaded...", SystemRuleManager.getRules().size());

        ReadableDataSource<String, List<ParamFlowRule>> paramFlowRuleDataSource = new NacosDataSource<>(properties, pluginAdapter.getGroup(), pluginAdapter.getServiceId() + "-" + SentinelStrategyConstant.SENTINEL_PARAM_FLOW_KEY, new SentinelParamFlowRuleParser());
        ParamFlowRuleManager.register2Property(paramFlowRuleDataSource.getProperty());
        LOG.info("{} param flow rules loaded...", ParamFlowRuleManager.getRules().size());
    }
}
package com.nepxion.discovery.plugin.strategy.sentinel.loader;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
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
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;

public abstract class SentinelDataSourceRuleLoader extends SentinelFileRuleLoader {
    private static final Logger LOG = LoggerFactory.getLogger(SentinelDataSourceRuleLoader.class);

    @Autowired
    protected PluginAdapter pluginAdapter;

    @Override
    public void load() {
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = getFlowRuleDataSource();
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
        LOG.info("{} flow rules form datasource loaded...", FlowRuleManager.getRules().size());

        ReadableDataSource<String, List<DegradeRule>> degradeRuleDataSource = getDegradeRuleDataSource();
        DegradeRuleManager.register2Property(degradeRuleDataSource.getProperty());
        LOG.info("{} degrade rules form datasource loaded...", DegradeRuleManager.getRules().size());

        ReadableDataSource<String, List<AuthorityRule>> authorityRuleDataSource = getAuthorityRuleDataSource();
        AuthorityRuleManager.register2Property(authorityRuleDataSource.getProperty());
        LOG.info("{} authority rules form datasource loaded...", AuthorityRuleManager.getRules().size());

        ReadableDataSource<String, List<SystemRule>> systemRuleDataSource = getSystemRuleDataSource();
        SystemRuleManager.register2Property(systemRuleDataSource.getProperty());
        LOG.info("{} system rules form datasource loaded...", SystemRuleManager.getRules().size());

        ReadableDataSource<String, List<ParamFlowRule>> paramFlowRuleDataSource = getParamFlowRuleDataSource();
        ParamFlowRuleManager.register2Property(paramFlowRuleDataSource.getProperty());
        LOG.info("{} param flow rules datasource file loaded...", ParamFlowRuleManager.getRules().size());

        boolean isFileManualLoaded = isFileManualLoaded();
        if (isFileManualLoaded) {
            super.load();
        }
    }

    public PluginAdapter getPluginAdapter() {
        return pluginAdapter;
    }

    public abstract ReadableDataSource<String, List<FlowRule>> getFlowRuleDataSource();

    public abstract ReadableDataSource<String, List<DegradeRule>> getDegradeRuleDataSource();

    public abstract ReadableDataSource<String, List<AuthorityRule>> getAuthorityRuleDataSource();

    public abstract ReadableDataSource<String, List<SystemRule>> getSystemRuleDataSource();

    public abstract ReadableDataSource<String, List<ParamFlowRule>> getParamFlowRuleDataSource();

    public abstract boolean isFileManualLoaded();
}
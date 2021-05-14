package com.nepxion.discovery.plugin.strategy.sentinel.datasource.processor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.apollo.proccessor.ApolloProcessor;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.sentinel.datasource.entity.SentinelStrategyRuleType;
import com.nepxion.discovery.plugin.strategy.sentinel.datasource.loader.SentinelStrategyRuleLoader;

public class SentinelStrategyRuleApolloProcessor extends ApolloProcessor {
    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private SentinelStrategyRuleLoader sentinelStrategyRuleLoader;

    private SentinelStrategyRuleType sentinelStrategyRuleType;

    public SentinelStrategyRuleApolloProcessor(SentinelStrategyRuleType sentinelStrategyRuleType) {
        this.sentinelStrategyRuleType = sentinelStrategyRuleType;
    }

    @Override
    public void afterInitialization() {
        sentinelStrategyRuleLoader.loadFileRules(sentinelStrategyRuleType);
    }

    @Override
    public String getGroup() {
        return pluginAdapter.getGroup();
    }

    @Override
    public String getDataId() {
        return pluginAdapter.getServiceId() + "-" + sentinelStrategyRuleType.getValue();
    }

    @Override
    public String getDescription() {
        return sentinelStrategyRuleType.getDescription();
    }

    @Override
    public void callbackConfig(String config) {
        sentinelStrategyRuleLoader.loadRules(sentinelStrategyRuleType, config);
    }
}
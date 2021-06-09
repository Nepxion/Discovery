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
import com.nepxion.discovery.common.entity.SentinelRuleType;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.sentinel.datasource.loader.SentinelStrategyRuleLoader;

public class SentinelStrategyRuleApolloProcessor extends ApolloProcessor {
    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private SentinelStrategyRuleLoader sentinelStrategyRuleLoader;

    private SentinelRuleType sentinelRuleType;

    public SentinelStrategyRuleApolloProcessor(SentinelRuleType sentinelRuleType) {
        this.sentinelRuleType = sentinelRuleType;
    }

    @Override
    public void afterInitialization() {
        sentinelStrategyRuleLoader.loadFileRules(sentinelRuleType);
    }

    @Override
    public String getGroup() {
        return pluginAdapter.getGroup();
    }

    @Override
    public String getDataId() {
        return pluginAdapter.getServiceId() + "-" + sentinelRuleType.getKey();
    }

    @Override
    public String getDescription() {
        return sentinelRuleType.getDescription();
    }

    @Override
    public void callbackConfig(String config) {
        sentinelStrategyRuleLoader.loadRules(sentinelRuleType, config);
    }
}
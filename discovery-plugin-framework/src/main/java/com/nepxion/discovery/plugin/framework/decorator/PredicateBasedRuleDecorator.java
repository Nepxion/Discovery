package com.nepxion.discovery.plugin.framework.decorator;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.nepxion.discovery.common.entity.WeightFilterEntity;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.context.PluginContextHolder;
import com.nepxion.discovery.plugin.framework.loadbalance.weight.RuleMapWeightRandomLoadBalance;
import com.nepxion.discovery.plugin.framework.loadbalance.weight.StrategyMapWeightRandomLoadBalance;
import com.netflix.loadbalancer.PredicateBasedRule;
import com.netflix.loadbalancer.Server;

public abstract class PredicateBasedRuleDecorator extends PredicateBasedRule {
    private static final Logger LOG = LoggerFactory.getLogger(PredicateBasedRuleDecorator.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private PluginAdapter pluginAdapter;

    private StrategyMapWeightRandomLoadBalance strategyMapWeightRandomLoadBalance;
    private RuleMapWeightRandomLoadBalance ruleMapWeightRandomLoadBalance;

    @PostConstruct
    private void initialize() {
        PluginContextHolder pluginContextHolder = applicationContext.getBean(PluginContextHolder.class);
        strategyMapWeightRandomLoadBalance = new StrategyMapWeightRandomLoadBalance(pluginAdapter, pluginContextHolder);
        ruleMapWeightRandomLoadBalance = new RuleMapWeightRandomLoadBalance(pluginAdapter);
    }

    @Override
    public Server choose(Object key) {
        boolean isTriggered = false;

        WeightFilterEntity strategyWeightFilterEntity = strategyMapWeightRandomLoadBalance.getT();
        if (strategyWeightFilterEntity != null && strategyWeightFilterEntity.hasWeight()) {
            isTriggered = true;

            List<Server> eligibleServers = getPredicate().getEligibleServers(getLoadBalancer().getAllServers(), key);

            try {
                return strategyMapWeightRandomLoadBalance.choose(eligibleServers, strategyWeightFilterEntity);
            } catch (Exception e) {
                LOG.error("Exception causes for strategy weight-random-loadbalance, used default loadbalance", e);

                return super.choose(key);
            }
        }

        if (!isTriggered) {
            WeightFilterEntity weightFilterEntity = ruleMapWeightRandomLoadBalance.getT();
            if (weightFilterEntity != null && weightFilterEntity.hasWeight()) {
                List<Server> eligibleServers = getPredicate().getEligibleServers(getLoadBalancer().getAllServers(), key);

                try {
                    return ruleMapWeightRandomLoadBalance.choose(eligibleServers, weightFilterEntity);
                } catch (Exception e) {
                    LOG.error("Exception causes for rule weight-random-loadbalance, used default loadbalance", e);

                    return super.choose(key);
                }
            }
        }

        return super.choose(key);
    }
}
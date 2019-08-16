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
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;

public class ZoneAvoidanceRuleDecorator extends ZoneAvoidanceRule {
    private static final Logger LOG = LoggerFactory.getLogger(ZoneAvoidanceRuleDecorator.class);

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

            boolean isWeightChecked = strategyMapWeightRandomLoadBalance.checkWeight(eligibleServers, strategyWeightFilterEntity);
            if (isWeightChecked) {
                try {
                    return strategyMapWeightRandomLoadBalance.choose(eligibleServers, strategyWeightFilterEntity);
                } catch (Exception e) {
                    LOG.error("Exception causes for strategy weight-random-loadbalance, used default loadbalance", e);

                    return super.choose(key);
                }
            } else {
                return super.choose(key);
            }
        }

        if (!isTriggered) {
            WeightFilterEntity ruleWeightFilterEntity = ruleMapWeightRandomLoadBalance.getT();
            if (ruleWeightFilterEntity != null && ruleWeightFilterEntity.hasWeight()) {
                List<Server> eligibleServers = getPredicate().getEligibleServers(getLoadBalancer().getAllServers(), key);

                boolean isWeightChecked = ruleMapWeightRandomLoadBalance.checkWeight(eligibleServers, ruleWeightFilterEntity);
                if (isWeightChecked) {
                    try {
                        return ruleMapWeightRandomLoadBalance.choose(eligibleServers, ruleWeightFilterEntity);
                    } catch (Exception e) {
                        LOG.error("Exception causes for rule weight-random-loadbalance, used default loadbalance", e);

                        return super.choose(key);
                    }
                } else {
                    return super.choose(key);
                }
            }
        }

        return super.choose(key);
    }
}
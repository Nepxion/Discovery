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

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.entity.WeightFilterEntity;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.loadbalance.WeightRandomLoadBalance;
import com.netflix.loadbalancer.PredicateBasedRule;
import com.netflix.loadbalancer.Server;

public abstract class PredicateBasedRuleDecorator extends PredicateBasedRule {
    @Autowired
    private PluginAdapter pluginAdapter;

    private WeightRandomLoadBalance weightRandomLoadBalance;

    @PostConstruct
    private void initialize() {
        weightRandomLoadBalance = new WeightRandomLoadBalance();
        weightRandomLoadBalance.setPluginAdapter(pluginAdapter);
    }

    @Override
    public Server choose(Object key) {
        WeightFilterEntity weightFilterEntity = weightRandomLoadBalance.getWeightFilterEntity();
        if (weightFilterEntity == null) {
            return super.choose(key);
        }

        if (!weightFilterEntity.hasWeight()) {
            return super.choose(key);
        }

        List<Server> eligibleServers = getPredicate().getEligibleServers(getLoadBalancer().getAllServers(), key);

        try {
            return weightRandomLoadBalance.choose(eligibleServers, weightFilterEntity);
        } catch (Exception e) {
            return super.choose(key);
        }
    }
}
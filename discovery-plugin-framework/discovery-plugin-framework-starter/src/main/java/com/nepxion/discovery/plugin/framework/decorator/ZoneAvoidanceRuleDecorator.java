package com.nepxion.discovery.plugin.framework.decorator;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Optional;
import com.nepxion.discovery.common.entity.WeightFilterEntity;
import com.nepxion.discovery.plugin.framework.loadbalance.DiscoveryEnabledLoadBalance;
import com.nepxion.discovery.plugin.framework.loadbalance.weight.RuleWeightRandomLoadBalance;
import com.nepxion.discovery.plugin.framework.loadbalance.weight.StrategyWeightRandomLoadBalance;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;

public class ZoneAvoidanceRuleDecorator extends ZoneAvoidanceRule {
    @Autowired
    private StrategyWeightRandomLoadBalance<WeightFilterEntity> strategyWeightRandomLoadBalance;

    @Autowired
    private RuleWeightRandomLoadBalance<WeightFilterEntity> ruleWeightRandomLoadBalance;

    @Autowired(required = false)
    private DiscoveryEnabledLoadBalance discoveryEnabledLoadBalance;

    // 必须执行getEligibleServers，否则叠加执行权重规则和版本区域策略会失效
    private List<Server> getServerList(Object key) {
        return getPredicate().getEligibleServers(getLoadBalancer().getAllServers(), key);
    }

    @Override
    public Server choose(Object key) {
        boolean isTriggered = false;

        WeightFilterEntity strategyWeightFilterEntity = strategyWeightRandomLoadBalance.getT();
        if (strategyWeightFilterEntity != null && strategyWeightFilterEntity.hasWeight()) {
            isTriggered = true;

            List<Server> serverList = getServerList(key);
            boolean isWeightChecked = strategyWeightRandomLoadBalance.checkWeight(serverList, strategyWeightFilterEntity);
            if (isWeightChecked) {
                try {
                    return strategyWeightRandomLoadBalance.choose(serverList, strategyWeightFilterEntity);
                } catch (Exception e) {
                    return filterChoose(key);
                }
            } else {
                return filterChoose(key);
            }
        }

        if (!isTriggered) {
            WeightFilterEntity ruleWeightFilterEntity = ruleWeightRandomLoadBalance.getT();
            if (ruleWeightFilterEntity != null && ruleWeightFilterEntity.hasWeight()) {
                List<Server> serverList = getServerList(key);
                boolean isWeightChecked = ruleWeightRandomLoadBalance.checkWeight(serverList, ruleWeightFilterEntity);
                if (isWeightChecked) {
                    try {
                        return ruleWeightRandomLoadBalance.choose(serverList, ruleWeightFilterEntity);
                    } catch (Exception e) {
                        return filterChoose(key);
                    }
                } else {
                    return filterChoose(key);
                }
            }
        }

        return filterChoose(key);
    }

    public Server filterChoose(Object key) {
        ILoadBalancer lb = getLoadBalancer();

        List<Server> serverList = new ArrayList<Server>();
        serverList.addAll(lb.getAllServers());

        if (discoveryEnabledLoadBalance != null) {
            discoveryEnabledLoadBalance.filter(serverList);
        }

        Optional<Server> server = getPredicate().chooseRoundRobinAfterFiltering(serverList, key);
        if (server.isPresent()) {
            return server.get();
        } else {
            return null;
        }
    }
}
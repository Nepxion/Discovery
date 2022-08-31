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
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.google.common.base.Optional;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.WeightFilterEntity;
import com.nepxion.discovery.plugin.framework.loadbalance.DiscoveryEnabledLoadBalance;
import com.nepxion.discovery.plugin.framework.loadbalance.weight.RuleWeightRandomLoadBalance;
import com.nepxion.discovery.plugin.framework.loadbalance.weight.StrategyWeightRandomLoadBalance;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.PredicateBasedRule;
import com.netflix.loadbalancer.Server;

public abstract class PredicateBasedRuleDecorator extends PredicateBasedRule {
    private static final Logger LOG = LoggerFactory.getLogger(PredicateBasedRuleDecorator.class);

    @Value("${" + DiscoveryConstant.SPRING_APPLICATION_NO_SERVERS_RETRY_ENABLED + ":false}")
    private Boolean retryEnabled;

    @Value("${" + DiscoveryConstant.SPRING_APPLICATION_NO_SERVERS_RETRY_TIMES + ":5}")
    private Integer retryTimes;

    @Value("${" + DiscoveryConstant.SPRING_APPLICATION_NO_SERVERS_RETRY_AWAIT_TIME + ":2000}")
    private Integer retryAwaitTime;

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

    private List<Server> getRetryableServerList(Object key) {
        List<Server> serverList = getServerList(key);
        for (int i = 0; i < retryTimes; i++) {
            if (CollectionUtils.isNotEmpty(serverList)) {
                break;
            }

            try {
                TimeUnit.MILLISECONDS.sleep(retryAwaitTime);
            } catch (InterruptedException e) {

            }

            LOG.info("Retry to get server list for {} times...", i + 1);

            serverList = getServerList(key);
        }

        return serverList;
    }

    @Override
    public Server choose(Object key) {
        boolean isTriggered = false;

        WeightFilterEntity strategyWeightFilterEntity = strategyWeightRandomLoadBalance.getT();
        if (strategyWeightFilterEntity != null && strategyWeightFilterEntity.hasWeight()) {
            isTriggered = true;

            List<Server> serverList = retryEnabled ? getRetryableServerList(key) : getServerList(key);
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
                List<Server> serverList = retryEnabled ? getRetryableServerList(key) : getServerList(key);
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
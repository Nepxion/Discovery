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
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.WeightFilterEntity;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.context.PluginContextHolder;
import com.nepxion.discovery.plugin.framework.loadbalance.weight.RuleMapWeightRandomLoadBalance;
import com.nepxion.discovery.plugin.framework.loadbalance.weight.StrategyMapWeightRandomLoadBalance;
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
    private ApplicationContext applicationContext;

    @Autowired
    private PluginAdapter pluginAdapter;

    private StrategyMapWeightRandomLoadBalance strategyMapWeightRandomLoadBalance;
    private RuleMapWeightRandomLoadBalance ruleMapWeightRandomLoadBalance;

    @PostConstruct
    private void initialize() {
        PluginContextHolder pluginContextHolder = null;
        try {
            pluginContextHolder = applicationContext.getBean(PluginContextHolder.class);
        } catch (BeansException e) {
            LOG.warn("Disable Strategy-Weight-Random-LoadBalance for the dependency of discovery-plugin-strategy-starter-xyz not included");
        }
        strategyMapWeightRandomLoadBalance = new StrategyMapWeightRandomLoadBalance(pluginAdapter, pluginContextHolder);
        ruleMapWeightRandomLoadBalance = new RuleMapWeightRandomLoadBalance(pluginAdapter);
    }

    private List<Server> getEligibleServers(Object key) {
        return getPredicate().getEligibleServers(getLoadBalancer().getAllServers(), key);
    }

    private List<Server> getRetryableEligibleServers(Object key) {
        List<Server> eligibleServers = getEligibleServers(key);
        for (int i = 0; i < retryTimes; i++) {
            if (CollectionUtils.isNotEmpty(eligibleServers)) {
                break;
            }

            try {
                TimeUnit.MILLISECONDS.sleep(retryAwaitTime);
            } catch (InterruptedException e) {

            }

            LOG.info("Retry to get eligible servers for {} times...", i + 1);

            eligibleServers = getEligibleServers(key);
        }

        return eligibleServers;
    }

    @Override
    public Server choose(Object key) {
        List<Server> eligibleServers = retryEnabled ? getRetryableEligibleServers(key) : getEligibleServers(key);

        boolean isTriggered = false;

        WeightFilterEntity strategyWeightFilterEntity = strategyMapWeightRandomLoadBalance.getT();
        if (strategyWeightFilterEntity != null && strategyWeightFilterEntity.hasWeight()) {
            isTriggered = true;

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
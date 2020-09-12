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
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;

public class ZoneAvoidanceRuleDecorator extends ZoneAvoidanceRule {
    private static final Logger LOG = LoggerFactory.getLogger(ZoneAvoidanceRuleDecorator.class);

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

        }
        strategyMapWeightRandomLoadBalance = new StrategyMapWeightRandomLoadBalance(pluginAdapter, pluginContextHolder);
        ruleMapWeightRandomLoadBalance = new RuleMapWeightRandomLoadBalance(pluginAdapter);
    }

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

        WeightFilterEntity strategyWeightFilterEntity = strategyMapWeightRandomLoadBalance.getT();
        if (strategyWeightFilterEntity != null && strategyWeightFilterEntity.hasWeight()) {
            isTriggered = true;

            List<Server> serverList = retryEnabled ? getRetryableServerList(key) : getServerList(key);
            boolean isWeightChecked = strategyMapWeightRandomLoadBalance.checkWeight(serverList, strategyWeightFilterEntity);
            if (isWeightChecked) {
                try {
                    return strategyMapWeightRandomLoadBalance.choose(serverList, strategyWeightFilterEntity);
                } catch (Exception e) {
                    // LOG.error("Execute strategy weight-random-loadbalance failed, it will use default loadbalance", e);

                    return super.choose(key);
                }
            } else {
                return super.choose(key);
            }
        }

        if (!isTriggered) {
            WeightFilterEntity ruleWeightFilterEntity = ruleMapWeightRandomLoadBalance.getT();
            if (ruleWeightFilterEntity != null && ruleWeightFilterEntity.hasWeight()) {
                List<Server> serverList = retryEnabled ? getRetryableServerList(key) : getServerList(key);
                boolean isWeightChecked = ruleMapWeightRandomLoadBalance.checkWeight(serverList, ruleWeightFilterEntity);
                if (isWeightChecked) {
                    try {
                        return ruleMapWeightRandomLoadBalance.choose(serverList, ruleWeightFilterEntity);
                    } catch (Exception e) {
                        // LOG.error("Execute rule weight-random-loadbalance failed, it will use default loadbalance", e);

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
package com.nepxion.discovery.plugin.strategy.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.nepxion.discovery.plugin.strategy.filter.StrategyEnabledFilter;
import com.netflix.loadbalancer.Server;

public class DefaultDiscoveryEnabledAdapter implements DiscoveryEnabledAdapter {
    @Autowired
    protected List<StrategyEnabledFilter> strategyEnabledFilterList;

    @Autowired(required = false)
    protected List<DiscoveryEnabledStrategy> discoveryEnabledStrategyList;

    @Override
    public void filter(List<? extends Server> servers) {
        for (StrategyEnabledFilter strategyEnabledFilter : strategyEnabledFilterList) {
            strategyEnabledFilter.filter(servers);
        }
    }

    @Override
    public boolean apply(Server server) {
        if (CollectionUtils.isEmpty(discoveryEnabledStrategyList)) {
            return true;
        }

        for (DiscoveryEnabledStrategy discoveryEnabledStrategy : discoveryEnabledStrategyList) {
            boolean enabled = discoveryEnabledStrategy.apply(server);
            if (!enabled) {
                return false;
            }
        }

        return true;
    }
}
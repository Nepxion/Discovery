package com.nepxion.discovery.plugin.strategy.extension.enable;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import javax.annotation.Nullable;

import com.nepxion.discovery.plugin.strategy.extension.context.StrategyContext;
import com.nepxion.discovery.plugin.strategy.extension.context.StrategyContextHolder;
import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.PredicateKey;
import com.netflix.loadbalancer.Server;

public class DiscoveryEnabledPredicate extends AbstractServerPredicate {
    private DiscoveryEnabledAdapter discoveryEnabledAdapter;

    @Override
    public boolean apply(@Nullable PredicateKey input) {
        return input != null && apply(input.getServer());
    }

    protected boolean apply(Server server) {
        StrategyContext context = StrategyContextHolder.currentContext();

        return discoveryEnabledAdapter.apply(server, context);
    }

    public void setDiscoveryEnabledAdapter(DiscoveryEnabledAdapter discoveryEnabledAdapter) {
        this.discoveryEnabledAdapter = discoveryEnabledAdapter;
    }
}
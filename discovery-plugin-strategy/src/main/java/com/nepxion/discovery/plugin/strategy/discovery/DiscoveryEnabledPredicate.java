package com.nepxion.discovery.plugin.strategy.discovery;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.PredicateKey;
import com.netflix.loadbalancer.Server;

public class DiscoveryEnabledPredicate extends AbstractServerPredicate {
    private DiscoveryEnabledAdapter discoveryEnabledAdapter;

    @Override
    public boolean apply(PredicateKey input) {
        return input != null && apply(input.getServer());
    }

    protected boolean apply(Server server) {
        return discoveryEnabledAdapter.apply(server);
    }

    public void setDiscoveryEnabledAdapter(DiscoveryEnabledAdapter discoveryEnabledAdapter) {
        this.discoveryEnabledAdapter = discoveryEnabledAdapter;
    }
}
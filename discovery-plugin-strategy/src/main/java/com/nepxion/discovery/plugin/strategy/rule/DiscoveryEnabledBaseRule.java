package com.nepxion.discovery.plugin.strategy.rule;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.plugin.framework.decorator.PredicateBasedRuleDecorator;
import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.AvailabilityPredicate;
import com.netflix.loadbalancer.CompositePredicate;

public class DiscoveryEnabledBaseRule extends PredicateBasedRuleDecorator {
    private CompositePredicate compositePredicate;
    private DiscoveryEnabledBasePredicate discoveryEnabledPredicate;

    public DiscoveryEnabledBaseRule() {
        discoveryEnabledPredicate = new DiscoveryEnabledBasePredicate();
        AvailabilityPredicate availabilityPredicate = new AvailabilityPredicate(this, null);
        compositePredicate = createCompositePredicate(discoveryEnabledPredicate, availabilityPredicate);
    }

    private CompositePredicate createCompositePredicate(DiscoveryEnabledBasePredicate discoveryEnabledPredicate, AvailabilityPredicate availabilityPredicate) {
        return CompositePredicate.withPredicates(discoveryEnabledPredicate, availabilityPredicate)
                // .addFallbackPredicate(availabilityPredicate)
                // .addFallbackPredicate(AbstractServerPredicate.alwaysTrue())
                .build();
    }

    @Override
    public AbstractServerPredicate getPredicate() {
        return compositePredicate;
    }

    public DiscoveryEnabledBasePredicate getDiscoveryEnabledPredicate() {
        return discoveryEnabledPredicate;
    }
}
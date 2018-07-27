package com.nepxion.discovery.plugin.strategy.extension.enable;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.util.Assert;

import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.AvailabilityPredicate;
import com.netflix.loadbalancer.CompositePredicate;
import com.netflix.loadbalancer.PredicateBasedRule;

public class DiscoveryEnabledRule extends PredicateBasedRule {
    private final CompositePredicate predicate;
    private final DiscoveryEnabledPredicate discoveryEnabledPredicate;

    public DiscoveryEnabledRule() {
        this(new DiscoveryEnabledPredicate());
    }

    public DiscoveryEnabledRule(DiscoveryEnabledPredicate discoveryEnabledPredicate) {
        Assert.notNull(discoveryEnabledPredicate, "Parameter 'discoveryEnabledPredicate' can't be null");

        this.predicate = createCompositePredicate(discoveryEnabledPredicate, new AvailabilityPredicate(this, null));
        this.discoveryEnabledPredicate = discoveryEnabledPredicate;
    }

    @Override
    public AbstractServerPredicate getPredicate() {
        return predicate;
    }

    public DiscoveryEnabledPredicate getDiscoveryEnabledPredicate() {
        return discoveryEnabledPredicate;
    }

    private CompositePredicate createCompositePredicate(DiscoveryEnabledPredicate discoveryEnabledPredicate, AvailabilityPredicate availabilityPredicate) {
        return CompositePredicate.withPredicates(discoveryEnabledPredicate, availabilityPredicate).build();
    }
}
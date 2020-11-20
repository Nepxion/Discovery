package com.nepxion.discovery.plugin.framework.event;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.Serializable;

import com.nepxion.discovery.common.entity.SubscriptionType;

public class RuleUpdatedEvent implements Serializable {
    private static final long serialVersionUID = 2315578803987663866L;

    private SubscriptionType subscriptionType;
    private String rule;

    public RuleUpdatedEvent(SubscriptionType subscriptionType, String rule) {
        this.subscriptionType = subscriptionType;
        this.rule = rule;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public String getRule() {
        return rule;
    }
}
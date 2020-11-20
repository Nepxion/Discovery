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

public class RuleClearedEvent implements Serializable {
    private static final long serialVersionUID = -4942710381954711909L;

    private SubscriptionType subscriptionType;

    public RuleClearedEvent(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }
}
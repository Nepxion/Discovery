package com.nepxion.discovery.plugin.strategy.extension.enable;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.plugin.strategy.extension.context.StrategyContext;
import com.netflix.loadbalancer.Server;

public interface DiscoveryEnabledAdapter {
    boolean apply(Server server, StrategyContext context);
}
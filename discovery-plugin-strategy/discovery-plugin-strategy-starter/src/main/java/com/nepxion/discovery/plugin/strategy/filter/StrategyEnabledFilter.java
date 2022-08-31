package com.nepxion.discovery.plugin.strategy.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.springframework.core.Ordered;

import com.netflix.loadbalancer.Server;

public interface StrategyEnabledFilter extends Ordered {
    void filter(List<? extends Server> servers);

    boolean apply(List<? extends Server> servers, Server server);
}
package com.nepxion.discovery.plugin.strategy.service.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.core.Ordered;

import com.nepxion.discovery.plugin.strategy.filter.StrategyRouteFilter;

public abstract class ServiceStrategyRouteFilter extends ServiceStrategyFilter implements StrategyRouteFilter, Ordered {

}
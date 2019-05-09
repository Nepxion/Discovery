package com.nepxion.discovery.plugin.example.zuul.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.zuul.filter.ZuulStrategyFilterResolver;
import com.netflix.zuul.ZuulFilter;

public class MyZuulFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_VERSION, "{\"discovery-springcloud-example-a\":\"1.0\", \"discovery-springcloud-example-b\":\"1.0\", \"discovery-springcloud-example-c\":\"1.0;1.2\"}");

        return null;
    }
}
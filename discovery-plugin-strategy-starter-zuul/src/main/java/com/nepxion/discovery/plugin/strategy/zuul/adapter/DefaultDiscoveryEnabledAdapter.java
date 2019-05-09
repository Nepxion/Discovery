package com.nepxion.discovery.plugin.strategy.zuul.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.adapter.AbstractDiscoveryEnabledAdapter;
import com.nepxion.discovery.plugin.strategy.zuul.context.ZuulStrategyContextHolder;
import com.netflix.loadbalancer.Server;

public class DefaultDiscoveryEnabledAdapter extends AbstractDiscoveryEnabledAdapter {
    @Autowired
    private ZuulStrategyContextHolder zuulStrategyContextHolder;

    @Override
    protected String getVersionValue(Server server) {
        return zuulStrategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION);
    }

    @Override
    protected String getRegionValue(Server server) {
        return zuulStrategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION);
    }

    @Override
    protected String getAddressValue(Server server) {
        return zuulStrategyContextHolder.getHeader(DiscoveryConstant.N_D_ADDRESS);
    }
}
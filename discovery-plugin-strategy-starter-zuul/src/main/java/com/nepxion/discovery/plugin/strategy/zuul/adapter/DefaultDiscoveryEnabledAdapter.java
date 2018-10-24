package com.nepxion.discovery.plugin.strategy.zuul.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.adapter.AbstractDiscoveryEnabledAdapter;
import com.nepxion.discovery.plugin.strategy.zuul.configuration.ContextHolder;
import com.netflix.zuul.context.RequestContext;

public class DefaultDiscoveryEnabledAdapter extends AbstractDiscoveryEnabledAdapter {
    @Override
    protected String getVersionValue() {
        ContextHolder context = ContextHolder.getCurrentContext();

        return context.getRequest().getHeader(DiscoveryConstant.VERSION);
    }

    @Override
    protected String getRegionValue() {
        ContextHolder context = ContextHolder.getCurrentContext();

        return context.getRequest().getHeader(DiscoveryConstant.REGION);
    }
}
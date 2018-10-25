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
import com.nepxion.discovery.plugin.hystrix.context.HystrixContextHolder;
import com.nepxion.discovery.plugin.strategy.adapter.AbstractDiscoveryEnabledAdapter;

public class DefaultDiscoveryEnabledAdapter extends AbstractDiscoveryEnabledAdapter {
    @Override
    protected String getVersionValue() {
        HystrixContextHolder context = HystrixContextHolder.getCurrentContext();

        return context.getRequest().getHeader(DiscoveryConstant.VERSION);
    }

    @Override
    protected String getRegionValue() {
        HystrixContextHolder context = HystrixContextHolder.getCurrentContext();

        return context.getRequest().getHeader(DiscoveryConstant.REGION);
    }
}
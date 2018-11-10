package com.nepxion.discovery.plugin.strategy.service.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.adapter.AbstractDiscoveryEnabledAdapter;
import com.nepxion.discovery.plugin.strategy.service.context.ServiceStrategyContextHolder;
import com.netflix.loadbalancer.Server;

public class DefaultDiscoveryEnabledAdapter extends AbstractDiscoveryEnabledAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultDiscoveryEnabledAdapter.class);

    @Autowired
    private ServiceStrategyContextHolder serviceStrategyContextHolder;

    @Override
    protected String getVersionValue(Server server) {
        ServletRequestAttributes attributes = serviceStrategyContextHolder.getRestAttributes();
        if (attributes == null) {
            String serviceId = server.getMetaInfo().getAppName().toLowerCase();

            LOG.warn("The ServletRequestAttributes object is null, ignore to do version filter for service={}...", serviceId);

            return null;
        }

        return attributes.getRequest().getHeader(DiscoveryConstant.VERSION);
    }

    @Override
    protected String getRegionValue(Server server) {
        ServletRequestAttributes attributes = serviceStrategyContextHolder.getRestAttributes();
        if (attributes == null) {
            String serviceId = server.getMetaInfo().getAppName().toLowerCase();

            LOG.warn("The ServletRequestAttributes object is null, ignore to do region filter for service={}...", serviceId);

            return null;
        }

        return attributes.getRequest().getHeader(DiscoveryConstant.REGION);
    }
}
package com.nepxion.discovery.plugin.strategy.zuul.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.adapter.AbstractDiscoveryEnabledAdapter;
import com.nepxion.discovery.plugin.strategy.zuul.context.ZuulStrategyContextHolder;
import com.netflix.loadbalancer.Server;

public class DefaultDiscoveryEnabledAdapter extends AbstractDiscoveryEnabledAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultDiscoveryEnabledAdapter.class);

    @Autowired
    private ZuulStrategyContextHolder zuulStrategyContextHolder;

    @Override
    protected String getVersionValue(Server server) {
        HttpServletRequest request = zuulStrategyContextHolder.getRequest();
        if (request == null) {
            String serviceId = server.getMetaInfo().getAppName().toLowerCase();

            LOG.warn("The HttpServletRequest object is null, ignore to do version filter for service={}...", serviceId);

            return null;
        }

        return request.getHeader(DiscoveryConstant.VERSION);
    }

    @Override
    protected String getRegionValue(Server server) {
        HttpServletRequest request = zuulStrategyContextHolder.getRequest();
        if (request == null) {
            String serviceId = server.getMetaInfo().getAppName().toLowerCase();

            LOG.warn("The HttpServletRequest object is null, ignore to do region filter for service={}...", serviceId);

            return null;
        }

        return request.getHeader(DiscoveryConstant.REGION);
    }
}
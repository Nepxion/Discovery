package com.nepxion.discovery.plugin.example.zuul.extension;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.discovery.DiscoveryEnabledAdapter;
import com.netflix.loadbalancer.Server;
import com.netflix.zuul.context.RequestContext;

public class MyZuulDiscoveryEnabledAdapter implements DiscoveryEnabledAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(MyZuulDiscoveryEnabledAdapter.class);

    @Autowired
    protected PluginAdapter pluginAdapter;

    @Override
    public boolean apply(Server server) {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String token = request.getHeader("token");

        String serviceId = server.getMetaInfo().getAppName().toLowerCase();
        Map<String, String> metadata = pluginAdapter.getServerMetadata(server);

        LOG.info("Zuul端负载均衡用户定制触发：serviceId={}, host={}, metadata={}, context={}", serviceId, server.toString(), metadata, context);

        if (StringUtils.equals(token, "abc")) {
            return false;
        }

        return true;
    }
}
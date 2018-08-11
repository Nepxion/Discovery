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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nepxion.discovery.plugin.strategy.discovery.DiscoveryEnabledAdapter;
import com.netflix.loadbalancer.Server;
import com.netflix.zuul.context.RequestContext;

public class MyDiscoveryEnabledAdapter implements DiscoveryEnabledAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(MyDiscoveryEnabledAdapter.class);

    // 根据外部传来的Header参数（例如Token），选取执行调用请求的服务实例
    @Override
    public boolean apply(Server server, Map<String, String> metadata) {
        RequestContext context = RequestContext.getCurrentContext();
        String token = context.getRequest().getHeader("token");
        // String value = context.getRequest().getParameter("value");

        String serviceId = server.getMetaInfo().getAppName().toLowerCase();

        LOG.info("Zuul端负载均衡用户定制触发：serviceId={}, host={}, metadata={}, context={}", serviceId, server.toString(), metadata, context);

        String filterToken = "abc";
        if (StringUtils.isNotEmpty(token) && token.contains(filterToken)) {
            LOG.info("过滤条件：当Token含有'{}'的时候，不能被Ribbon负载均衡到", filterToken);

            return false;
        }

        return true;
    }
}
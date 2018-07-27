package com.nepxion.discovery.plugin.example.service.extension;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.discovery.DiscoveryEnabledAdapter;
import com.nepxion.discovery.plugin.strategy.extension.service.constant.ServiceStrategyConstant;
import com.nepxion.discovery.plugin.strategy.extension.service.context.ServiceStrategyContext;
import com.netflix.loadbalancer.Server;

public class MyDiscoveryEnabledAdapter implements DiscoveryEnabledAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(MyDiscoveryEnabledAdapter.class);

    @Autowired
    protected PluginAdapter pluginAdapter;

    @SuppressWarnings("unchecked")
    @Override
    public boolean apply(Server server) {
        ServiceStrategyContext context = ServiceStrategyContext.getCurrentContext();
        Map<String, Object> attributes = context.getAttributes();
        
        String serviceId = server.getMetaInfo().getAppName().toLowerCase();
        Map<String, String> metadata = pluginAdapter.getServerMetadata(server);

        LOG.info("Serivice端负载均衡用户定制触发：serviceId={}, host={}, metadata={}, context={}", serviceId, server.toString(), metadata, context);

        if (attributes.containsKey(ServiceStrategyConstant.PARAMETER_MAP)) {
            Map<String, Object> parameterMap = (Map<String, Object>) attributes.get(ServiceStrategyConstant.PARAMETER_MAP);
            String value = parameterMap.get("value").toString();
            if (value.contains("abc")) {
                LOG.info("过滤条件：当前端输入值包含'abc'的时候，不能被Ribbon负载均衡到");

                return false;
            }
        }

        /*String version = metadata.get(PluginConstant.VERSION);
        if (StringUtils.equals(serviceId, "discovery-springcloud-example-c") && StringUtils.equals(version, "1.0")) {
            LOG.info("过滤条件：当serviceId={}，version={}的时候，不能被Ribbon负载均衡到", serviceId, version);

            return false;
        }*/

        return true;
    }
}
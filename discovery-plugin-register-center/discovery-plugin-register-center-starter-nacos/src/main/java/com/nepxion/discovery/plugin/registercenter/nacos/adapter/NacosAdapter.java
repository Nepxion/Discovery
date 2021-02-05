package com.nepxion.discovery.plugin.registercenter.nacos.adapter;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.nepxion.discovery.plugin.framework.adapter.AbstractPluginAdapter;
import com.nepxion.discovery.plugin.registercenter.nacos.constant.NacosConstant;
import com.netflix.loadbalancer.Server;

public class NacosAdapter extends AbstractPluginAdapter {
    @Value("${" + NacosConstant.SPRING_APPLICATION_NACOS_SERVICE_ID_FILTER_ENABLED + ":true}")
    protected Boolean nacosServiceIdFilterEnabled;

    public static final String SEPARATE = "@@";

    @Override
    public Map<String, String> getServerMetadata(Server server) {
        if (server instanceof NacosServer) {
            NacosServer nacosServer = (NacosServer) server;

            return nacosServer.getMetadata();
        }

        return emptyMetadata;

        // throw new DiscoveryException("Server instance isn't the type of NacosServer");
    }

    @Override
    public String getServiceId() {
        String serviceId = super.getServiceId();

        return filterServiceId(serviceId);
    }

    @Override
    public String getServerServiceId(Server server) {
        String serverServiceId = super.getServerServiceId(server);

        return filterServiceId(serverServiceId);
    }

    @Override
    public String getInstanceServiceId(ServiceInstance instance) {
        String instanceServiceId = super.getInstanceServiceId(instance);

        return filterServiceId(instanceServiceId);
    }

    // 由于Nacos注册中心会自动把服务名处理成GROUP@@SERVICE_ID的格式，导致根据服务名去获取元数据的时候会找不到，通过如下方式过滤掉GROUP前缀
    private String filterServiceId(String serviceId) {
        if (nacosServiceIdFilterEnabled && StringUtils.contains(serviceId, SEPARATE)) {
            serviceId = serviceId.substring(serviceId.indexOf(SEPARATE) + SEPARATE.length(), serviceId.length());
        }

        return serviceId;
    }
}
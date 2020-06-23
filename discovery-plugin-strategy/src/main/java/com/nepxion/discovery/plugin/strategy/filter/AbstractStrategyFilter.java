package com.nepxion.discovery.plugin.strategy.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.netflix.loadbalancer.Server;

public abstract class AbstractStrategyFilter {
    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected DiscoveryClient discoveryClient;

    public boolean apply(Server server) {
        // 抽象获取版本号的Value
        String metadataValue = getMetadataValue(server);
        // 当服务未接入本框架或者版本号未设置（表现出来的值为DiscoveryConstant.DEFAULT），不过滤
        if (StringUtils.isEmpty(metadataValue) || StringUtils.equals(metadataValue, DiscoveryConstant.DEFAULT)) {
            return true;
        }

        // 获取对端的服务名
        String serviceId = pluginAdapter.getServerServiceId(server);
        // 从负载均衡缓存获取对端的服务列表
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        // 抽象获取版本号的Key
        String metadataKey = getMetadataKey();
        // 获取对端服务所有的版本号列表
        List<String> metadataValueList = getMetadataValueList(instances, metadataKey);
        // 如果没有版本号，不过滤；如果版本号只有一个，不过滤
        if (metadataValueList.size() <= 1) {
            return true;
        }

        // 通过暴露对外接口获取跨组调用下的老的稳定版的版本号列表，一般来说，老的稳定版的版本号只有一个，为了增加扩展性，支持多个
        // 给予全局版本号列表，过滤出老的稳定版的版本号列表
        List<String> filterMetadataValueList = filterMetadataValueList(metadataValueList);

        // 判断版本号是否在认可列表里
        return filterMetadataValueList.contains(metadataValue);
    }

    public List<String> getMetadataValueList(List<ServiceInstance> instances, String metadataKey) {
        List<String> metadataValueList = new ArrayList<String>();
        for (ServiceInstance instance : instances) {
            String metadataValue = pluginAdapter.getInstanceMetadata(instance).get(metadataKey);
            // 当服务未接入本框架或者版本号未设置（表现出来的值为DiscoveryConstant.DEFAULT），不添加到认可列表
            if (StringUtils.isNotEmpty(metadataValue) && !StringUtils.equals(metadataValue, DiscoveryConstant.DEFAULT) && !metadataValueList.contains(metadataValue)) {
                metadataValueList.add(metadataValue);
            }
        }

        // 排序有助于更好的过滤
        Collections.sort(metadataValueList);

        return metadataValueList;
    }

    public abstract String getMetadataKey();

    public abstract String getMetadataValue(Server server);

    public abstract List<String> filterMetadataValueList(List<String> metadataValueList);
}
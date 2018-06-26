package com.nepxion.discovery.plugin.framework.strategy.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;

import com.nepxion.discovery.plugin.framework.constant.PluginConstant;
import com.nepxion.discovery.plugin.framework.entity.DiscoveryEntity;
import com.nepxion.discovery.plugin.framework.entity.DiscoveryServiceEntity;
import com.nepxion.discovery.plugin.framework.entity.RuleEntity;
import com.nepxion.discovery.plugin.framework.entity.VersionEntity;
import com.nepxion.discovery.plugin.framework.strategy.AbstractDiscoveryStrategy;

public class VersionFilterDiscoveryStrategy extends AbstractDiscoveryStrategy {
    @Autowired
    private RuleEntity ruleEntity;

    @Override
    public void fireGetInstances(String serviceId, List<ServiceInstance> instances) {
        String consumerServiceId = environment.getProperty(PluginConstant.SPRING_APPLICATION_NAME);
        String consumerServiceVersion = environment.getProperty(PluginConstant.EUREKA_METADATA_VERSION);

        applyVersionFilter(consumerServiceId, consumerServiceVersion, serviceId, instances);
    }

    private void applyVersionFilter(String consumerServiceId, String consumerServiceVersion, String providerServiceId, List<ServiceInstance> instances) {
        // 如果消费端未配置版本号，那么它可以调用提供端所有服务，需要符合规范，极力避免该情况发生
        if (StringUtils.isEmpty(consumerServiceVersion)) {
            return;
        }

        DiscoveryEntity discoveryEntity = ruleEntity.getDiscoveryEntity();
        if (discoveryEntity == null) {
            return;
        }

        VersionEntity versionEntity = discoveryEntity.getVersionEntity();
        if (versionEntity == null) {
            return;
        }

        Map<String, List<DiscoveryServiceEntity>> serviceEntityMap = versionEntity.getServiceEntityMap();
        if (MapUtils.isEmpty(serviceEntityMap)) {
            return;
        }

        List<DiscoveryServiceEntity> serviceEntityList = serviceEntityMap.get(consumerServiceId);
        if (CollectionUtils.isEmpty(serviceEntityList)) {
            return;
        }

        // 当前版本的消费端所能调用提供端的版本号列表
        List<String> allFilterValueList = new ArrayList<String>();
        for (DiscoveryServiceEntity serviceEntity : serviceEntityList) {
            String providerServiceName = serviceEntity.getProviderServiceName();
            if (StringUtils.equals(providerServiceName, providerServiceId)) {
                List<String> consumerVersionValueList = serviceEntity.getConsumerVersionValueList();
                List<String> providerVersionValueList = serviceEntity.getProviderVersionValueList();

                // 判断consumer-version-value值是否包含当前消费端的版本号
                if (CollectionUtils.isNotEmpty(consumerVersionValueList) && consumerVersionValueList.contains(consumerServiceVersion)) {
                    if (CollectionUtils.isNotEmpty(providerVersionValueList)) {
                        allFilterValueList.addAll(providerVersionValueList);
                    }
                }
            }
        }

        // 未找到相应的版本定义或者未定义
        if (CollectionUtils.isEmpty(allFilterValueList)) {
            return;
        }

        Iterator<ServiceInstance> iterator = instances.iterator();
        while (iterator.hasNext()) {
            ServiceInstance serviceInstance = iterator.next();
            String metaDataVersion = serviceInstance.getMetadata().get(PluginConstant.VRESION);
            if (!allFilterValueList.contains(metaDataVersion)) {
                iterator.remove();
            }
        }
    }

    @Override
    public void fireGetServices(List<String> services) {

    }
}
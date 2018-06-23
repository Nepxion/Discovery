package com.nepxion.discovery.plugin.framework.strategy;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;

import com.nepxion.discovery.plugin.framework.cache.PluginCache;
import com.nepxion.discovery.plugin.framework.constant.PluginConstant;
import com.nepxion.discovery.plugin.framework.entity.DiscoveryEntity;
import com.nepxion.discovery.plugin.framework.entity.DiscoveryServiceEntity;
import com.nepxion.discovery.plugin.framework.entity.RuleEntity;

public class DiscoveryStrategy {
    @Autowired
    private RuleEntity pluginEntity;

    @Autowired
    private PluginCache pluginCache;

    @Autowired
    private ReentrantReadWriteLock reentrantReadWriteLock;

    public void apply(String consumerServiceId, String consumerServiceVersion, String providerServiceId, List<ServiceInstance> instances) {
        try {
            reentrantReadWriteLock.readLock().lock();

            applyIpAddressFilter(providerServiceId, instances);

            applyVersionFilter(consumerServiceId, consumerServiceVersion, providerServiceId, instances);
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }

    private void applyIpAddressFilter(String providerServiceId, List<ServiceInstance> instances) {
        String ipAddress = pluginCache.get(providerServiceId);
        if (StringUtils.isNotEmpty(ipAddress)) {
            String[] filterArray = StringUtils.split(ipAddress, PluginConstant.SEPARATE);
            List<String> filterList = Arrays.asList(filterArray);
            Iterator<ServiceInstance> iterator = instances.iterator();
            while (iterator.hasNext()) {
                ServiceInstance serviceInstance = iterator.next();
                String host = serviceInstance.getHost();
                if (filterList.contains(host)) {
                    iterator.remove();
                }
            }
        }
    }

    private void applyVersionFilter(String consumerServiceId, String consumerServiceVersion, String providerServiceId, List<ServiceInstance> instances) {
        DiscoveryEntity discoveryEntity = pluginEntity.getDiscoveryEntity();
        if (discoveryEntity == null) {
            return;
        }

        Map<String, List<DiscoveryServiceEntity>> serviceEntityMap = discoveryEntity.getServiceEntityMap();
        if (MapUtils.isEmpty(serviceEntityMap)) {
            return;
        }

        List<DiscoveryServiceEntity> serviceEntityList = serviceEntityMap.get(consumerServiceId);
        if (CollectionUtils.isEmpty(serviceEntityList)) {
            return;
        }

        // 当前版本的消费者所能调用提供者的版本列表
        List<String> allFilterVersions = new ArrayList<String>();
        for (DiscoveryServiceEntity serviceEntity : serviceEntityList) {
            String providerServiceName = serviceEntity.getProviderServiceName();
            if (StringUtils.equals(providerServiceName, providerServiceId)) {
                String consumerVersionValue = serviceEntity.getConsumerVersionValue();
                String providerVersionValue = serviceEntity.getProviderVersionValue();

                List<String> consumerVersionList = getVersionList(consumerVersionValue);
                List<String> providerVersionList = getVersionList(providerVersionValue);

                // 判断consumer-version-value值是否包含当前消费者的版本号
                if (CollectionUtils.isNotEmpty(consumerVersionList) && consumerVersionList.contains(consumerServiceVersion)) {
                    if (CollectionUtils.isNotEmpty(providerVersionList)) {
                        allFilterVersions.addAll(providerVersionList);
                    }
                }
            }
        }

        // 未找到相应的版本定义或者未定义
        if (CollectionUtils.isEmpty(allFilterVersions)) {
            return;
        }

        Iterator<ServiceInstance> iterator = instances.iterator();
        while (iterator.hasNext()) {
            ServiceInstance serviceInstance = iterator.next();
            String metaDataVersion = serviceInstance.getMetadata().get(PluginConstant.VRESION);
            if (!allFilterVersions.contains(metaDataVersion)) {
                iterator.remove();
            }
        }
    }

    private List<String> getVersionList(String versionValue) {
        if (StringUtils.isEmpty(versionValue)) {
            return null;
        }

        String[] versionArray = StringUtils.split(versionValue, PluginConstant.SEPARATE);

        return Arrays.asList(versionArray);
    }
}
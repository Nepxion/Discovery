package com.nepxion.discovery.plugin.strategy;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;

import com.nepxion.discovery.plugin.constant.DiscoveryPluginConstant;
import com.nepxion.discovery.plugin.entity.ConsumerEntity;
import com.nepxion.discovery.plugin.entity.DiscoveryEntity;
import com.nepxion.discovery.plugin.entity.VersionEntity;

public class VersionStrategy {
    @Autowired
    private DiscoveryEntity discoveryEntity;

    @Autowired
    private ReentrantLock reentrantLock;

    public void apply(String consumerServiceId, String providerServiceId, List<ServiceInstance> instances) {
        try {
            reentrantLock.lock();

            VersionEntity versionEntity = discoveryEntity.getVersionEntity();
            ConsumerEntity consumerEntity = getConsumerEntity(consumerServiceId, versionEntity);
            if (consumerEntity != null) {
                Map<String, String> providerMap = consumerEntity.getProviderMap();
                String version = providerMap.get(providerServiceId);
                String[] versionArray = StringUtils.split(version, DiscoveryPluginConstant.SEPARATE);
                List<String> versionList = Arrays.asList(versionArray);
                Iterator<ServiceInstance> iterator = instances.iterator();
                while (iterator.hasNext()) {
                    ServiceInstance serviceInstance = iterator.next();
                    String metaDataVersion = serviceInstance.getMetadata().get(DiscoveryPluginConstant.EUREKA_METADATA_VERSION);
                    if (!versionList.contains(metaDataVersion)) {
                        iterator.remove();
                    }
                }
            }
        } finally {
            reentrantLock.unlock();
        }
    }

    private ConsumerEntity getConsumerEntity(String consumerServiceId, VersionEntity versionEntity) {
        List<ConsumerEntity> consumerEntityList = versionEntity.getConsumerEntityList();
        for (ConsumerEntity consumerEntity : consumerEntityList) {
            String serviceName = consumerEntity.getServiceName();
            if (StringUtils.equals(consumerServiceId, serviceName)) {
                return consumerEntity;
            }
        }

        return null;
    }
}
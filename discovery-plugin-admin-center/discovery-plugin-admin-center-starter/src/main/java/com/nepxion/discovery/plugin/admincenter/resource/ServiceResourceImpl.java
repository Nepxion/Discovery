package com.nepxion.discovery.plugin.admincenter.resource;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.composite.CompositeDiscoveryClient;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.constant.DiscoveryMetaDataConstant;
import com.nepxion.discovery.common.entity.InstanceEntity;
import com.nepxion.discovery.common.entity.InstanceEntityWrapper;
import com.nepxion.discovery.common.entity.ServiceType;
import com.nepxion.discovery.plugin.admincenter.constant.AdminConstant;
import com.nepxion.discovery.plugin.framework.decorator.DiscoveryClientDecorator;

public class ServiceResourceImpl implements ServiceResource {
    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public String getDiscoveryType() {
        DiscoveryClient realDiscoveryClient = null;
        if (discoveryClient instanceof DiscoveryClientDecorator) {
            realDiscoveryClient = ((DiscoveryClientDecorator) discoveryClient).getRealDiscoveryClient();
        } else {
            realDiscoveryClient = discoveryClient;
        }

        if (realDiscoveryClient instanceof CompositeDiscoveryClient) {
            CompositeDiscoveryClient compositeDiscoveryClient = (CompositeDiscoveryClient) realDiscoveryClient;
            List<DiscoveryClient> discoveryClients = compositeDiscoveryClient.getDiscoveryClients();
            for (DiscoveryClient client : discoveryClients) {
                String discoveryDescription = client.description();
                for (int i = 0; i < AdminConstant.DISCOVERY_TYPES.length; i++) {
                    String discoveryType = AdminConstant.DISCOVERY_TYPES[i];
                    if (discoveryDescription.toLowerCase().contains(discoveryType.toLowerCase())) {
                        return discoveryType;
                    }
                }
            }
        } else {
            String discoveryDescription = realDiscoveryClient.description();
            for (int i = 0; i < AdminConstant.DISCOVERY_TYPES.length; i++) {
                String discoveryType = AdminConstant.DISCOVERY_TYPES[i];
                if (discoveryDescription.toLowerCase().contains(discoveryType.toLowerCase())) {
                    return discoveryType;
                }
            }
        }

        return DiscoveryConstant.UNKNOWN;
    }

    @Override
    public List<String> getGroups() {
        List<String> groups = new ArrayList<String>();

        List<String> services = getServices();
        for (String service : services) {
            List<InstanceEntity> instanceEntityList = getInstanceList(service);
            for (InstanceEntity instance : instanceEntityList) {
                String plugin = InstanceEntityWrapper.getPlugin(instance);
                String group = InstanceEntityWrapper.getGroup(instance);
                if (StringUtils.isNotEmpty(plugin) && !groups.contains(group)) {
                    groups.add(group);
                }
            }
        }

        return groups;
    }

    @Override
    public String getGroup(String serviceId) {
        List<InstanceEntity> instanceEntityList = getInstanceList(serviceId);
        for (InstanceEntity instance : instanceEntityList) {
            String group = InstanceEntityWrapper.getGroup(instance);
            if (StringUtils.isNotEmpty(group)) {
                return group;
            }
        }

        return null;
    }

    @Override
    public List<String> getServices() {
        return discoveryClient.getServices();
    }

    @Override
    public List<ServiceInstance> getInstances(String serviceId) {
        return discoveryClient.getInstances(serviceId);
    }

    @Override
    public List<String> getGateways() {
        List<String> gateways = new ArrayList<String>();
        List<String> services = getServices();
        for (String service : services) {
            List<ServiceInstance> instances = getInstances(service);
            for (ServiceInstance instance : instances) {
                Map<String, String> metadata = instance.getMetadata();
                String serviceId = instance.getServiceId().toLowerCase();
                String serviceType = metadata.get(DiscoveryMetaDataConstant.SPRING_APPLICATION_TYPE);
                if (StringUtils.equals(serviceType, ServiceType.GATEWAY.toString())) {
                    if (!gateways.contains(serviceId)) {
                        gateways.add(serviceId);
                    }
                }
            }
        }

        return gateways;
    }

    @Override
    public List<InstanceEntity> getInstanceList(String service) {
        List<ServiceInstance> instances = getInstances(service);
        List<InstanceEntity> instanceEntityList = new ArrayList<InstanceEntity>(instances.size());
        for (ServiceInstance instance : instances) {
            Map<String, String> metadata = instance.getMetadata();
            String serviceId = instance.getServiceId().toLowerCase();
            String serviceType = metadata.get(DiscoveryMetaDataConstant.SPRING_APPLICATION_TYPE);
            String version = metadata.get(DiscoveryConstant.VERSION);
            String region = metadata.get(DiscoveryConstant.REGION);
            String environment = metadata.get(DiscoveryConstant.ENVIRONMENT);
            String zone = metadata.get(DiscoveryConstant.ZONE);
            String host = instance.getHost();
            int port = instance.getPort();

            InstanceEntity instanceEntity = new InstanceEntity();
            instanceEntity.setServiceType(serviceType);
            instanceEntity.setServiceId(serviceId);
            instanceEntity.setVersion(version);
            instanceEntity.setRegion(region);
            instanceEntity.setEnvironment(environment);
            instanceEntity.setZone(zone);
            instanceEntity.setHost(host);
            instanceEntity.setPort(port);
            instanceEntity.setMetadata(metadata);

            instanceEntityList.add(instanceEntity);
        }

        return instanceEntityList;
    }

    @Override
    public Map<String, List<InstanceEntity>> getInstanceMap(List<String> groups) {
        List<String> services = getServices();
        Map<String, List<InstanceEntity>> instanceMap = new LinkedHashMap<String, List<InstanceEntity>>(services.size());
        for (String service : services) {
            List<InstanceEntity> instanceEntityList = getInstanceList(service);
            if (CollectionUtils.isNotEmpty(groups)) {
                for (InstanceEntity instance : instanceEntityList) {
                    String plugin = InstanceEntityWrapper.getPlugin(instance);
                    String group = InstanceEntityWrapper.getGroup(instance);
                    if (StringUtils.isNotEmpty(plugin) && groups.contains(group)) {
                        List<InstanceEntity> instanceList = instanceMap.get(service);
                        if (instanceList == null) {
                            instanceList = new ArrayList<InstanceEntity>();
                            instanceMap.put(service, instanceList);
                        }
                        instanceList.add(instance);
                    }
                }
            } else {
                instanceMap.put(service, instanceEntityList);
            }
        }

        return instanceMap;
    }
}
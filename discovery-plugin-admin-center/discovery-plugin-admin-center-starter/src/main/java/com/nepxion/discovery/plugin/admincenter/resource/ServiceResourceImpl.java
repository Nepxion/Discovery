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
import java.util.Comparator;
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
import com.nepxion.discovery.common.delegate.DiscoveryClientDelegate;
import com.nepxion.discovery.common.entity.DiscoveryType;
import com.nepxion.discovery.common.entity.GatewayType;
import com.nepxion.discovery.common.entity.InstanceEntity;
import com.nepxion.discovery.common.entity.InstanceEntityWrapper;
import com.nepxion.discovery.common.entity.ServiceType;

public class ServiceResourceImpl implements ServiceResource {
    @Autowired
    private DiscoveryClient discoveryClient;

    @SuppressWarnings("unchecked")
    @Override
    public DiscoveryType getDiscoveryType() {
        DiscoveryClient delegatedDiscoveryClient = null;
        if (discoveryClient instanceof DiscoveryClientDelegate) {
            delegatedDiscoveryClient = ((DiscoveryClientDelegate<DiscoveryClient>) discoveryClient).getDelegate();
        } else {
            delegatedDiscoveryClient = discoveryClient;
        }

        if (delegatedDiscoveryClient instanceof CompositeDiscoveryClient) {
            CompositeDiscoveryClient compositeDiscoveryClient = (CompositeDiscoveryClient) delegatedDiscoveryClient;
            List<DiscoveryClient> discoveryClients = compositeDiscoveryClient.getDiscoveryClients();
            for (DiscoveryClient client : discoveryClients) {
                String discoveryDescription = client.description();
                DiscoveryType[] discoveryTypes = DiscoveryType.values();
                for (int i = 0; i < discoveryTypes.length; i++) {
                    DiscoveryType discoveryType = discoveryTypes[i];
                    if (discoveryDescription.toLowerCase().contains(discoveryType.toString().toLowerCase())) {
                        return discoveryType;
                    }
                }
            }
        } else {
            String discoveryDescription = delegatedDiscoveryClient.description();
            DiscoveryType[] discoveryTypes = DiscoveryType.values();
            for (int i = 0; i < discoveryTypes.length; i++) {
                DiscoveryType discoveryType = discoveryTypes[i];
                if (discoveryDescription.toLowerCase().contains(discoveryType.toString().toLowerCase())) {
                    return discoveryType;
                }
            }
        }

        return null;
    }

    @Override
    public List<String> getGroups() {
        List<String> groupList = new ArrayList<String>();

        List<String> services = getServices();
        for (String service : services) {
            List<InstanceEntity> instanceEntityList = getInstanceList(service);
            for (InstanceEntity instance : instanceEntityList) {
                String plugin = InstanceEntityWrapper.getPlugin(instance);
                String group = InstanceEntityWrapper.getGroup(instance);
                if (StringUtils.isNotEmpty(plugin) && !groupList.contains(group)) {
                    groupList.add(group);
                }
            }
        }

        groupList.sort(String::compareTo);

        return groupList;
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
        List<String> serviceList = discoveryClient.getServices();

        serviceList.sort(String::compareTo);

        return serviceList;
    }

    @Override
    public List<String> getServiceList(List<ServiceType> types) {
        List<String> serviceList = new ArrayList<String>();
        List<String> services = getServices();
        for (String service : services) {
            List<ServiceInstance> instances = getInstances(service);
            for (ServiceInstance instance : instances) {
                Map<String, String> metadata = instance.getMetadata();
                String serviceId = instance.getServiceId().toLowerCase();
                String serviceType = metadata.get(DiscoveryMetaDataConstant.SPRING_APPLICATION_TYPE);
                for (ServiceType type : types) {
                    if (StringUtils.equals(serviceType, type.toString())) {
                        if (!serviceList.contains(serviceId)) {
                            serviceList.add(serviceId);
                        }
                    }
                }
            }
        }

        return serviceList;
    }

    @Override
    public List<String> getGateways() {
        List<String> gatewayList = new ArrayList<String>();
        List<String> services = getServices();
        for (String service : services) {
            List<ServiceInstance> instances = getInstances(service);
            for (ServiceInstance instance : instances) {
                Map<String, String> metadata = instance.getMetadata();
                String serviceId = instance.getServiceId().toLowerCase();
                String serviceType = metadata.get(DiscoveryMetaDataConstant.SPRING_APPLICATION_TYPE);
                if (StringUtils.equals(serviceType, ServiceType.GATEWAY.toString())) {
                    if (!gatewayList.contains(serviceId)) {
                        gatewayList.add(serviceId);
                    }
                }
            }
        }

        return gatewayList;
    }

    @Override
    public List<String> getGatewayList(List<GatewayType> types) {
        List<String> gatewayList = new ArrayList<String>();
        List<String> services = getServices();
        for (String service : services) {
            List<ServiceInstance> instances = getInstances(service);
            for (ServiceInstance instance : instances) {
                Map<String, String> metadata = instance.getMetadata();
                String serviceId = instance.getServiceId().toLowerCase();
                String serviceType = metadata.get(DiscoveryMetaDataConstant.SPRING_APPLICATION_TYPE);
                String gatewayType = metadata.get(DiscoveryMetaDataConstant.SPRING_APPLICATION_GATEWAY_TYPE);
                if (StringUtils.equals(serviceType, ServiceType.GATEWAY.toString())) {
                    for (GatewayType type : types) {
                        if (StringUtils.equals(gatewayType, type.toString())) {
                            if (!gatewayList.contains(serviceId)) {
                                gatewayList.add(serviceId);
                            }
                        }
                    }
                }
            }
        }

        return gatewayList;
    }

    @Override
    public List<ServiceInstance> getInstances(String serviceId) {
        List<ServiceInstance> instanceList = discoveryClient.getInstances(serviceId);

        // instanceList.sort(Comparator.comparing(ServiceInstance::getHost).thenComparing(ServiceInstance::getPort));
        instanceList.sort(Comparator.nullsLast(Comparator.comparing(ServiceInstance::getHost, Comparator.nullsLast(String::compareTo))).thenComparing(ServiceInstance::getPort));

        return instanceList;
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
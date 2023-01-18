package com.nepxion.discovery.plugin.admincenter.resource;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;
import java.util.Map;

import org.springframework.cloud.client.ServiceInstance;

import com.nepxion.discovery.common.entity.DiscoveryType;
import com.nepxion.discovery.common.entity.GatewayType;
import com.nepxion.discovery.common.entity.InstanceEntity;
import com.nepxion.discovery.common.entity.ServiceType;

public interface ServiceResource {
    DiscoveryType getDiscoveryType();

    List<String> getGroups();

    String getGroup(String serviceId);

    List<String> getServices();

    List<String> getServiceList(List<ServiceType> serviceTypes);

    List<String> getServiceList(String group, List<ServiceType> serviceTypes);

    List<String> getGateways();

    List<String> getGatewayList(List<GatewayType> gatewayTypes);

    List<ServiceInstance> getInstances(String serviceId);

    List<InstanceEntity> getInstanceList(String serviceId);

    Map<String, List<InstanceEntity>> getInstanceMap(List<String> groups);
}
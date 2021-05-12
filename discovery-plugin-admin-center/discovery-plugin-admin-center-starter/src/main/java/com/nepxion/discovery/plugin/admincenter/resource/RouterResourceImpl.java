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
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.DiscoveryEntity;
import com.nepxion.discovery.common.entity.RouterEntity;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.WeightEntityWrapper;
import com.nepxion.discovery.common.entity.WeightFilterEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;

public class RouterResourceImpl implements RouterResource {
    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private ServiceResource serviceResource;

    private RestTemplate routerRestTemplate;

    public RouterResourceImpl() {
        routerRestTemplate = new RestTemplate();
    }

    @Override
    public RouterEntity getRouterEntity() {
        String serviceType = pluginAdapter.getServiceType();
        String serviceId = pluginAdapter.getServiceId();
        String version = pluginAdapter.getVersion();
        String region = pluginAdapter.getRegion();
        String environment = pluginAdapter.getEnvironment();
        String zone = pluginAdapter.getZone();
        String host = pluginAdapter.getHost();
        int port = pluginAdapter.getPort();
        int weight = -1;
        String protocol = pluginAdapter.getProtocol();
        String contextPath = pluginAdapter.getFormatContextPath();

        RouterEntity routerEntity = new RouterEntity();
        routerEntity.setServiceType(serviceType);
        routerEntity.setServiceId(serviceId);
        routerEntity.setVersion(version);
        routerEntity.setRegion(region);
        routerEntity.setEnvironment(environment);
        routerEntity.setZone(zone);
        routerEntity.setHost(host);
        routerEntity.setPort(port);
        routerEntity.setWeight(weight);
        routerEntity.setProtocol(protocol);
        routerEntity.setContextPath(contextPath);

        return routerEntity;
    }

    @Override
    public List<RouterEntity> getRouterEntityList(String routeServiceId) {
        List<ServiceInstance> instanceList = null;

        try {
            instanceList = serviceResource.getInstances(routeServiceId);
        } catch (Exception e) {
            throw new DiscoveryException("Get instance list for route serviceId=" + routeServiceId + " failed", e);
        }

        if (CollectionUtils.isEmpty(instanceList)) {
            return null;
        }

        List<RouterEntity> routerEntityList = new ArrayList<RouterEntity>();
        for (ServiceInstance instance : instanceList) {
            String serviceId = pluginAdapter.getInstanceServiceId(instance);
            String serviceType = pluginAdapter.getInstanceServiceType(instance);
            String version = pluginAdapter.getInstanceVersion(instance);
            String region = pluginAdapter.getInstanceRegion(instance);
            String environment = pluginAdapter.getInstanceEnvironment(instance);
            String zone = pluginAdapter.getInstanceZone(instance);
            String host = instance.getHost();
            int port = instance.getPort();
            int weight = getWeight(routeServiceId, version, region);
            String protocol = pluginAdapter.getInstanceProtocol(instance);
            String contextPath = pluginAdapter.getInstanceFormatContextPath(instance);

            RouterEntity routerEntity = new RouterEntity();
            routerEntity.setServiceType(serviceType);
            routerEntity.setServiceId(serviceId);
            routerEntity.setVersion(version);
            routerEntity.setRegion(region);
            routerEntity.setEnvironment(environment);
            routerEntity.setZone(zone);
            routerEntity.setHost(host);
            routerEntity.setPort(port);
            routerEntity.setWeight(weight);
            routerEntity.setProtocol(protocol);
            routerEntity.setContextPath(contextPath);

            routerEntityList.add(routerEntity);
        }

        return routerEntityList;
    }

    @Override
    public List<RouterEntity> getRouterEntityList(String routeServiceId, String routeProtocol, String routeHost, int routePort, String routeContextPath) {
        String url = routeProtocol + "://" + routeHost + ":" + routePort + routeContextPath + "router/route/" + routeServiceId;

        String result = null;
        try {
            result = routerRestTemplate.getForEntity(url, String.class).getBody();
        } catch (RestClientException e) {
            throw new DiscoveryException("Failed to execute to route, serviceId=" + routeServiceId + ", url=" + url, e);
        }

        if (StringUtils.isEmpty(result)) {
            return null;
        }

        List<RouterEntity> routerEntityList = JsonUtil.fromJson(result, new TypeReference<List<RouterEntity>>() {
        });

        return routerEntityList;
    }

    @Override
    public RouterEntity routeTree(String routeServiceIds) {
        if (StringUtils.isEmpty(routeServiceIds)) {
            throw new DiscoveryException("Route serviceIds is empty");
        }

        String[] serviceIdArray = null;
        try {
            serviceIdArray = StringUtils.split(routeServiceIds, DiscoveryConstant.SEPARATE);
        } catch (Exception e) {
            throw new DiscoveryException("Route serviceIds must be separated with '" + DiscoveryConstant.SEPARATE + "'", e);
        }

        RouterEntity firstRouterEntity = getRouterEntity();

        // 路由深度为Key
        HashMap<Integer, List<RouterEntity>> routerEntityMap = new HashMap<Integer, List<RouterEntity>>();
        int routerDepth = 0;
        for (String serviceId : serviceIdArray) {
            serviceId = serviceId.toLowerCase().trim();
            if (routerDepth == 0) {
                routeFirst(firstRouterEntity, serviceId);

                retrieveRouterEntityList(routerEntityMap, routerDepth).addAll(firstRouterEntity.getNexts());
            } else {
                List<RouterEntity> routerEntityList = retrieveRouterEntityList(routerEntityMap, routerDepth - 1);
                for (RouterEntity routerEntity : routerEntityList) {
                    String routeHost = routerEntity.getHost();
                    int routePort = routerEntity.getPort();
                    String routeProtocol = routerEntity.getProtocol();
                    String routeContextPath = routerEntity.getContextPath();

                    route(routerEntity, serviceId, routeProtocol, routeHost, routePort, routeContextPath);

                    retrieveRouterEntityList(routerEntityMap, routerDepth).addAll(routerEntity.getNexts());
                }
            }

            routerDepth++;
        }

        return firstRouterEntity;
    }

    private void routeFirst(RouterEntity routerEntity, String routeServiceId) {
        List<RouterEntity> routerEntityList = getRouterEntityList(routeServiceId);
        if (CollectionUtils.isNotEmpty(routerEntityList)) {
            routerEntity.getNexts().addAll(routerEntityList);
        }
    }

    private void route(RouterEntity routerEntity, String routeServiceId, String routeProtocol, String routeHost, int routePort, String routeContextPath) {
        List<RouterEntity> routerEntityList = getRouterEntityList(routeServiceId, routeProtocol, routeHost, routePort, routeContextPath);
        if (CollectionUtils.isNotEmpty(routerEntityList)) {
            routerEntity.getNexts().addAll(routerEntityList);
        }
    }

    private List<RouterEntity> retrieveRouterEntityList(HashMap<Integer, List<RouterEntity>> routerEntityMap, int routerDepth) {
        List<RouterEntity> routerEntityList = routerEntityMap.get(routerDepth);
        if (routerEntityList == null) {
            routerEntityList = new ArrayList<RouterEntity>();
            routerEntityMap.put(routerDepth, routerEntityList);
        }

        return routerEntityList;
    }

    private int getWeight(String providerServiceId, String providerVersion, String providerRegion) {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity == null) {
            return -1;
        }

        DiscoveryEntity discoveryEntity = ruleEntity.getDiscoveryEntity();
        if (discoveryEntity == null) {
            return -1;
        }

        WeightFilterEntity weightFilterEntity = discoveryEntity.getWeightFilterEntity();
        if (weightFilterEntity == null || !weightFilterEntity.hasWeight()) {
            return -1;
        }

        String serviceId = pluginAdapter.getServiceId();

        return WeightEntityWrapper.getWeight(weightFilterEntity, providerServiceId, providerVersion, providerRegion, serviceId);
    }
}
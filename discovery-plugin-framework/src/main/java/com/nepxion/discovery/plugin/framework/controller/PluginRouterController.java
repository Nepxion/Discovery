package com.nepxion.discovery.plugin.framework.controller;

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
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.plugin.framework.constant.PluginConstant;
import com.nepxion.discovery.plugin.framework.context.PluginContainerInitializedHandler;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.entity.RouteEntity;
import com.nepxion.discovery.plugin.framework.exception.PluginException;
import com.nepxion.eventbus.util.HostUtil;

@RestController
public class PluginRouterController {
    @Autowired
    private PluginContainerInitializedHandler pluginContainerInitializedHandler;

    @Autowired
    private PluginContextAware pluginContextAware;

    @Autowired
    private RestTemplate pluginRestTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    // 获取本地节点可访问其他节点（根据服务名）的实例列表
    @RequestMapping(path = "/instances/{serviceId}", method = RequestMethod.GET)
    public List<ServiceInstance> instances(@PathVariable(value = "serviceId") String serviceId) {
        return getInstanceList(serviceId);
    }

    // 获取本地节点的路由信息
    @RequestMapping(path = "/info", method = RequestMethod.GET)
    public RouteEntity info() {
        return getRouteEntity();
    }

    // 获取本地节点可访问其他节点（根据服务名）的路由信息列表
    @RequestMapping(path = "/routes/{routeServiceId}", method = RequestMethod.GET)
    public List<RouteEntity> routes(@PathVariable(value = "routeServiceId") String routeServiceId) {
        return getRouteEntityList(routeServiceId);
    }

    // 获取指定节点（根据IP和端口）可访问其他节点（根据服务名）的路由信息列表
    @RequestMapping(path = "/routes/{routeServiceId}/{routeHost}/{routePort}", method = RequestMethod.GET)
    public List<RouteEntity> routes(@PathVariable(value = "routeServiceId") String routeServiceId, @PathVariable(value = "routeHost") String routeHost, @PathVariable(value = "routePort") int routePort) {
        return getRouteEntityList(routeServiceId, routeHost, routePort);
    }

    // 获取全路径的路由信息
    @RequestMapping(path = "/routeAll", method = RequestMethod.POST)
    public RouteEntity routeAll(@RequestBody String serviceIds) {
        return route(serviceIds);
    }

    public List<ServiceInstance> getInstanceList(String serviceId) {
        return discoveryClient.getInstances(serviceId);
    }

    public RouteEntity getRouteEntity() {
        String serviceId = pluginContextAware.getEnvironment().getProperty(PluginConstant.SPRING_APPLICATION_NAME);
        String version = pluginContextAware.getEnvironment().getProperty(PluginConstant.EUREKA_METADATA_VERSION);
        String host = HostUtil.getLocalhost();
        int port = pluginContainerInitializedHandler.getPort();

        RouteEntity routeEntity = new RouteEntity();
        routeEntity.setServiceId(serviceId);
        routeEntity.setVersion(version);
        routeEntity.setHost(host);
        routeEntity.setPort(port);

        return routeEntity;
    }

    public List<RouteEntity> getRouteEntityList(String routeServiceId) {
        List<ServiceInstance> instanceList = null;

        try {
            instanceList = getInstanceList(routeServiceId);
        } catch (Exception e) {
            throw new PluginException("Get instance list for serviceId=" + routeServiceId + " failed", e);
        }

        if (CollectionUtils.isEmpty(instanceList)) {
            return null;
        }

        List<RouteEntity> routeEntityList = new ArrayList<RouteEntity>();
        for (ServiceInstance instance : instanceList) {
            String serviceId = instance.getServiceId().toLowerCase();
            String version = instance.getMetadata().get(PluginConstant.VERSION);
            String host = instance.getHost();
            int port = instance.getPort();

            RouteEntity routeEntity = new RouteEntity();
            routeEntity.setServiceId(serviceId);
            routeEntity.setVersion(version);
            routeEntity.setHost(host);
            routeEntity.setPort(port);

            routeEntityList.add(routeEntity);
        }

        return routeEntityList;
    }

    @SuppressWarnings("unchecked")
    public List<RouteEntity> getRouteEntityList(String routeServiceId, String routeHost, int routePort) {
        String url = "http://" + routeHost + ":" + routePort + "/" + PluginConstant.INSTANCES + "/" + routeServiceId;

        List<Map<String, ?>> instanceList = null;
        try {
            instanceList = pluginRestTemplate.getForEntity(url, List.class).getBody();
        } catch (RestClientException e) {
            throw new PluginException("Get instance list for serviceId=" + routeServiceId + " with url=" + url + " failed", e);
        }

        if (CollectionUtils.isEmpty(instanceList)) {
            return null;
        }

        List<RouteEntity> routeEntityList = new ArrayList<RouteEntity>();
        for (Map<String, ?> instance : instanceList) {
            String serviceId = instance.get(PluginConstant.SERVICE_ID).toString().toLowerCase();
            String version = ((Map<String, String>) instance.get(PluginConstant.METADATA)).get(PluginConstant.VERSION);
            String host = instance.get(PluginConstant.HOST).toString();
            int port = (int) instance.get(PluginConstant.PORT);

            RouteEntity routeEntity = new RouteEntity();
            routeEntity.setServiceId(serviceId);
            routeEntity.setVersion(version);
            routeEntity.setHost(host);
            routeEntity.setPort(port);

            routeEntityList.add(routeEntity);
        }

        return routeEntityList;
    }

    public RouteEntity route(String serviceIds) {
        if (StringUtils.isEmpty(serviceIds)) {
            throw new PluginException("Service ids is empty");
        }

        String[] serviceIdArray = null;
        try {
            serviceIdArray = StringUtils.split(serviceIds, PluginConstant.SEPARATE);
        } catch (Exception e) {
            throw new PluginException("Service ids must be separated with '" + PluginConstant.SEPARATE + "'", e);
        }

        RouteEntity firstRouteEntity = getRouteEntity();

        HashMap<String, List<RouteEntity>> routeEntityMap = new HashMap<String, List<RouteEntity>>();
        String previousServiceId = null;
        for (String serviceId : serviceIdArray) {
            serviceId = serviceId.toLowerCase().trim();
            if (previousServiceId == null) {
                routeFirst(firstRouteEntity, serviceId);

                retrieveRouteEntityList(routeEntityMap, serviceId).addAll(firstRouteEntity.getChildRouteEntityList());
            } else {
                List<RouteEntity> routeEntityList = retrieveRouteEntityList(routeEntityMap, previousServiceId);
                for (RouteEntity routeEntity : routeEntityList) {
                    String routeHost = routeEntity.getHost();
                    int routePort = routeEntity.getPort();

                    route(routeEntity, serviceId, routeHost, routePort);

                    retrieveRouteEntityList(routeEntityMap, serviceId).addAll(routeEntity.getChildRouteEntityList());
                }
            }

            previousServiceId = serviceId;
        }

        return firstRouteEntity;
    }

    private void routeFirst(RouteEntity routeEntity, String routeServiceId) {
        List<RouteEntity> routeEntityList = getRouteEntityList(routeServiceId);
        if (CollectionUtils.isNotEmpty(routeEntityList)) {
            routeEntity.getChildRouteEntityList().addAll(routeEntityList);
        }
    }

    private void route(RouteEntity routeEntity, String routeServiceId, String routeHost, int routePort) {
        List<RouteEntity> routeEntityList = getRouteEntityList(routeServiceId, routeHost, routePort);
        if (CollectionUtils.isNotEmpty(routeEntityList)) {
            routeEntity.getChildRouteEntityList().addAll(routeEntityList);
        }
    }

    private List<RouteEntity> retrieveRouteEntityList(HashMap<String, List<RouteEntity>> routeEntityMap, String serviceId) {
        List<RouteEntity> routeEntityList = routeEntityMap.get(serviceId);
        if (routeEntityList == null) {
            routeEntityList = new ArrayList<RouteEntity>();
            routeEntityMap.put(serviceId, routeEntityList);
        }

        return routeEntityList;
    }
}
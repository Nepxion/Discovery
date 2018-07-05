package com.nepxion.discovery.plugin.routercenter.controller;

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
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.constant.PluginConstant;
import com.nepxion.discovery.plugin.framework.context.PluginContainerInitializedHandler;
import com.nepxion.discovery.plugin.framework.entity.RouterEntity;
import com.nepxion.discovery.plugin.framework.exception.PluginException;
import com.nepxion.eventbus.util.HostUtil;

@RestController
public class RouterController {
    @Autowired
    private PluginContainerInitializedHandler pluginContainerInitializedHandler;

    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private ConfigurableEnvironment environment;

    @Autowired
    private RestTemplate routerRestTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    // 获取本地节点可访问其他节点（根据服务名）的实例列表
    @RequestMapping(path = "/instances/{serviceId}", method = RequestMethod.GET)
    public List<ServiceInstance> instances(@PathVariable(value = "serviceId") String serviceId) {
        return getInstanceList(serviceId);
    }

    // 获取本地节点的路由信息（只显示当前节点的简单信息，不包含下级路由）
    @RequestMapping(path = "/info", method = RequestMethod.GET)
    public RouterEntity info() {
        return getRouterEntity();
    }

    // 获取本地节点可访问其他节点（根据服务名）的路由信息列表
    @RequestMapping(path = "/route/{routeServiceId}", method = RequestMethod.GET)
    public List<RouterEntity> route(@PathVariable(value = "routeServiceId") String routeServiceId) {
        return getRouterEntityList(routeServiceId);
    }

    // 获取指定节点（根据IP和端口）可访问其他节点（根据服务名）的路由信息列表
    @RequestMapping(path = "/route/{routeServiceId}/{routeHost}/{routePort}", method = RequestMethod.GET)
    public List<RouterEntity> route(@PathVariable(value = "routeServiceId") String routeServiceId, @PathVariable(value = "routeHost") String routeHost, @PathVariable(value = "routePort") int routePort) {
        return getRouterEntityList(routeServiceId, routeHost, routePort);
    }

    // 获取全路径的路由信息（routeServiceIds按调用服务名的前后次序排列，起始节点的服务名不能加上去。如果多个用“;”分隔，不允许出现空格）
    @RequestMapping(path = "/routes", method = RequestMethod.POST)
    public RouterEntity routes(@RequestBody String routeServiceIds) {
        return routeTree(routeServiceIds);
    }

    public List<ServiceInstance> getInstanceList(String serviceId) {
        return discoveryClient.getInstances(serviceId);
    }

    public RouterEntity getRouterEntity() {
        String serviceId = environment.getProperty(PluginConstant.SPRING_APPLICATION_NAME);
        String version = pluginAdapter.getVersion();
        String host = HostUtil.getLocalhost();
        int port = pluginContainerInitializedHandler.getPort();

        RouterEntity routerEntity = new RouterEntity();
        routerEntity.setServiceId(serviceId);
        routerEntity.setVersion(version);
        routerEntity.setHost(host);
        routerEntity.setPort(port);

        return routerEntity;
    }

    public List<RouterEntity> getRouterEntityList(String routeServiceId) {
        List<ServiceInstance> instanceList = null;

        try {
            instanceList = getInstanceList(routeServiceId);
        } catch (Exception e) {
            throw new PluginException("Get instance list for route serviceId=" + routeServiceId + " failed", e);
        }

        if (CollectionUtils.isEmpty(instanceList)) {
            return null;
        }

        List<RouterEntity> routerEntityList = new ArrayList<RouterEntity>();
        for (ServiceInstance instance : instanceList) {
            String serviceId = instance.getServiceId().toLowerCase();
            String version = instance.getMetadata().get(PluginConstant.VERSION);
            String host = instance.getHost();
            int port = instance.getPort();

            RouterEntity routerEntity = new RouterEntity();
            routerEntity.setServiceId(serviceId);
            routerEntity.setVersion(version);
            routerEntity.setHost(host);
            routerEntity.setPort(port);

            routerEntityList.add(routerEntity);
        }

        return routerEntityList;
    }

    @SuppressWarnings("unchecked")
    public List<RouterEntity> getRouterEntityList(String routeServiceId, String routeHost, int routePort) {
        String url = "http://" + routeHost + ":" + routePort + "/" + PluginConstant.INSTANCES + "/" + routeServiceId;

        List<Map<String, ?>> instanceList = null;
        try {
            instanceList = routerRestTemplate.getForEntity(url, List.class).getBody();
        } catch (RestClientException e) {
            throw new PluginException("Get instance list for route serviceId=" + routeServiceId + " with url=" + url + " failed", e);
        }

        if (CollectionUtils.isEmpty(instanceList)) {
            return null;
        }

        List<RouterEntity> routerEntityList = new ArrayList<RouterEntity>();
        for (Map<String, ?> instance : instanceList) {
            String serviceId = instance.get(PluginConstant.SERVICE_ID).toString().toLowerCase();
            String version = ((Map<String, String>) instance.get(PluginConstant.METADATA)).get(PluginConstant.VERSION);
            String host = instance.get(PluginConstant.HOST).toString();
            Integer port = (Integer) instance.get(PluginConstant.PORT);

            RouterEntity routerEntity = new RouterEntity();
            routerEntity.setServiceId(serviceId);
            routerEntity.setVersion(version);
            routerEntity.setHost(host);
            routerEntity.setPort(port);

            routerEntityList.add(routerEntity);
        }

        return routerEntityList;
    }

    public RouterEntity routeTree(String routeServiceIds) {
        if (StringUtils.isEmpty(routeServiceIds)) {
            throw new PluginException("Route serviceIds is empty");
        }

        String[] serviceIdArray = null;
        try {
            serviceIdArray = StringUtils.split(routeServiceIds, PluginConstant.SEPARATE);
        } catch (Exception e) {
            throw new PluginException("Route serviceIds must be separated with '" + PluginConstant.SEPARATE + "'", e);
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

                    route(routerEntity, serviceId, routeHost, routePort);

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

    private void route(RouterEntity routerEntity, String routeServiceId, String routeHost, int routePort) {
        List<RouterEntity> routerEntityList = getRouterEntityList(routeServiceId, routeHost, routePort);
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
}
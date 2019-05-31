package com.nepxion.discovery.plugin.admincenter.endpoint;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.CustomizationEntity;
import com.nepxion.discovery.common.entity.DiscoveryEntity;
import com.nepxion.discovery.common.entity.RegionWeightEntity;
import com.nepxion.discovery.common.entity.RouterEntity;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.WeightEntity;
import com.nepxion.discovery.common.entity.WeightFilterEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.common.util.UrlUtil;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;

@RestController
@RequestMapping(path = "/router")
@Api(tags = { "路由接口" })
@RestControllerEndpoint(id = "router")
@ManagedResource(description = "Router Endpoint")
public class RouterEndpoint {
    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private RestTemplate routerRestTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(path = "/services", method = RequestMethod.GET)
    @ApiOperation(value = "获取服务注册中心的服务列表", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    @ManagedOperation
    public List<String> services() {
        return getServices();
    }

    @RequestMapping(path = "/instances/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取本地节点可访问其他节点（根据服务名）的实例列表", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    @ManagedOperation
    public List<ServiceInstance> instances(@PathVariable(value = "serviceId") @ApiParam(value = "目标服务名", required = true) String serviceId) {
        return getInstanceList(serviceId);
    }

    @RequestMapping(path = "/info", method = RequestMethod.GET)
    @ApiOperation(value = "获取本地节点信息", notes = "获取当前节点的简单信息", response = RouterEntity.class, httpMethod = "GET")
    @ResponseBody
    @ManagedOperation
    public RouterEntity info() {
        return getRouterEntity();
    }

    @RequestMapping(path = "/route/{routeServiceId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取本地节点可访问其他节点（根据服务名）的路由信息列表", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    @ManagedOperation
    public List<RouterEntity> route(@PathVariable(value = "routeServiceId") @ApiParam(value = "目标服务名", required = true) String routeServiceId) {
        return getRouterEntityList(routeServiceId);
    }

    @RequestMapping(path = "/route/{routeServiceId}/{routeHost}/{routePort}/{routeContextPath}", method = RequestMethod.GET)
    @ApiOperation(value = "获取指定节点（根据IP和端口）可访问其他节点（根据服务名）的路由信息列表", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    @ManagedOperation
    public List<RouterEntity> route(@PathVariable(value = "routeServiceId") @ApiParam(value = "目标服务名", required = true) String routeServiceId, @PathVariable(value = "routeHost") @ApiParam(value = "目标服务所在机器的IP地址", required = true) String routeHost, @PathVariable(value = "routePort") @ApiParam(value = "目标服务所在机器的端口号", required = true) int routePort, @PathVariable(value = "routeContextPath") @ApiParam(value = "目标服务的调用路径前缀", required = true, defaultValue = "/") String routeContextPath) {
        return getRouterEntityList(routeServiceId, routeHost, routePort, routeContextPath);
    }

    @RequestMapping(path = "/routes", method = RequestMethod.POST)
    @ApiOperation(value = "获取全路径的路由信息树", notes = "参数按调用服务名的前后次序排列，起始节点的服务名不能加上去。如果多个用“;”分隔，不允许出现空格", response = RouterEntity.class, httpMethod = "POST")
    @ResponseBody
    @ManagedOperation
    public RouterEntity routes(@RequestBody @ApiParam(value = "例如：service-a;service-b", required = true) String routeServiceIds) {
        return routeTree(routeServiceIds);
    }

    public List<String> getServices() {
        return discoveryClient.getServices();
    }

    public List<ServiceInstance> getInstanceList(String serviceId) {
        return discoveryClient.getInstances(serviceId);
    }

    public RouterEntity getRouterEntity() {
        String serviceId = pluginAdapter.getServiceId();
        String version = pluginAdapter.getVersion();
        String region = pluginAdapter.getRegion();
        String host = pluginAdapter.getHost();
        int port = pluginAdapter.getPort();
        int weight = getWeight(serviceId, version, region);
        Map<String, String> customMap = getCustomMap(serviceId);
        String contextPath = pluginAdapter.getContextPath();

        RouterEntity routerEntity = new RouterEntity();
        routerEntity.setServiceId(serviceId);
        routerEntity.setVersion(version);
        routerEntity.setRegion(region);
        routerEntity.setHost(host);
        routerEntity.setPort(port);
        routerEntity.setWeight(weight);
        routerEntity.setCustomMap(customMap);
        routerEntity.setContextPath(contextPath);

        return routerEntity;
    }

    public List<RouterEntity> getRouterEntityList(String routeServiceId) {
        List<ServiceInstance> instanceList = null;

        try {
            instanceList = getInstanceList(routeServiceId);
        } catch (Exception e) {
            throw new DiscoveryException("Get instance list for route serviceId=" + routeServiceId + " failed", e);
        }

        if (CollectionUtils.isEmpty(instanceList)) {
            return null;
        }

        List<RouterEntity> routerEntityList = new ArrayList<RouterEntity>();
        for (ServiceInstance instance : instanceList) {
            Map<String, String> metadata = instance.getMetadata();
            String serviceId = instance.getServiceId().toLowerCase();
            String version = metadata.get(DiscoveryConstant.VERSION);
            String region = metadata.get(DiscoveryConstant.REGION);
            String host = instance.getHost();
            int port = instance.getPort();
            int weight = getWeight(routeServiceId, version, region);
            Map<String, String> customMap = getCustomMap(serviceId);
            String contextPath = metadata.get(DiscoveryConstant.SPRING_APPLICATION_CONTEXT_PATH);

            RouterEntity routerEntity = new RouterEntity();
            routerEntity.setServiceId(serviceId);
            routerEntity.setVersion(version);
            routerEntity.setRegion(region);
            routerEntity.setHost(host);
            routerEntity.setPort(port);
            routerEntity.setWeight(weight);
            routerEntity.setCustomMap(customMap);
            routerEntity.setContextPath(contextPath);

            routerEntityList.add(routerEntity);
        }

        return routerEntityList;
    }

    public List<RouterEntity> getRouterEntityList(String routeServiceId, String routeHost, int routePort, String routeContextPath) {
        String url = "http://" + routeHost + ":" + routePort + UrlUtil.formatContextPath(routeContextPath) + "router/route/" + routeServiceId;

        String result = null;
        try {
            result = routerRestTemplate.getForEntity(url, String.class).getBody();
        } catch (RestClientException e) {
            throw new DiscoveryException("Get instance list for route serviceId=" + routeServiceId + " with url=" + url + " failed", e);
        }

        if (StringUtils.isEmpty(result)) {
            return null;
        }

        List<RouterEntity> routerEntityList = JsonUtil.fromJson(result, new TypeReference<List<RouterEntity>>() {
        });

        return routerEntityList;
    }

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
                    String routeContextPath = routerEntity.getContextPath();

                    route(routerEntity, serviceId, routeHost, routePort, routeContextPath);

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

    private void route(RouterEntity routerEntity, String routeServiceId, String routeHost, int routePort, String routeContextPath) {
        List<RouterEntity> routerEntityList = getRouterEntityList(routeServiceId, routeHost, routePort, routeContextPath);
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
        if (weightFilterEntity == null) {
            return -1;
        }

        if (!weightFilterEntity.hasWeight()) {
            return -1;
        }

        Map<String, List<WeightEntity>> weightEntityMap = weightFilterEntity.getWeightEntityMap();
        RegionWeightEntity regionWeightEntity = weightFilterEntity.getRegionWeightEntity();

        String serviceId = pluginAdapter.getServiceId();
        // 取局部的权重配置
        int weight = getWeight(serviceId, providerServiceId, providerVersion, weightEntityMap);

        // 局部权重配置没找到，取全局的权重配置
        if (weight < 0) {
            weight = getWeight(StringUtils.EMPTY, providerServiceId, providerVersion, weightEntityMap);
        }

        // 全局的权重配置没找到，取区域的权重配置
        if (weight < 0) {
            weight = getWeight(providerRegion, regionWeightEntity);
        }

        return weight;
    }

    private int getWeight(String consumerServiceId, String providerServiceId, String providerVersion, Map<String, List<WeightEntity>> weightEntityMap) {
        if (MapUtils.isEmpty(weightEntityMap)) {
            return -1;
        }

        List<WeightEntity> weightEntityList = weightEntityMap.get(consumerServiceId);
        if (CollectionUtils.isEmpty(weightEntityList)) {
            return -1;
        }

        for (WeightEntity weightEntity : weightEntityList) {
            String providerServiceName = weightEntity.getProviderServiceName();
            if (StringUtils.equalsIgnoreCase(providerServiceName, providerServiceId)) {
                Map<String, Integer> weightMap = weightEntity.getWeightMap();
                if (MapUtils.isEmpty(weightMap)) {
                    return -1;
                }

                Integer weight = weightMap.get(providerVersion);
                if (weight != null) {
                    return weight;
                } else {
                    return -1;
                }
            }
        }

        return -1;
    }

    private int getWeight(String providerRegion, RegionWeightEntity regionWeightEntity) {
        if (regionWeightEntity == null) {
            return -1;
        }

        Map<String, Integer> weightMap = regionWeightEntity.getWeightMap();
        if (MapUtils.isEmpty(weightMap)) {
            return -1;
        }

        Integer weight = weightMap.get(providerRegion);
        if (weight != null) {
            return weight;
        } else {
            return -1;
        }
    }

    private Map<String, String> getCustomMap(String serviceId) {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity == null) {
            return null;
        }

        CustomizationEntity customizationEntity = ruleEntity.getCustomizationEntity();
        if (customizationEntity == null) {
            return null;
        }

        Map<String, Map<String, String>> customizationMap = customizationEntity.getCustomizationMap();
        if (MapUtils.isEmpty(customizationMap)) {
            return null;
        }

        return customizationMap.get(serviceId);
    }
}
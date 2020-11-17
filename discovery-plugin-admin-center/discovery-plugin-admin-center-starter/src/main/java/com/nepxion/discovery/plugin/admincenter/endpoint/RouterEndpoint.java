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

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
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
import com.nepxion.discovery.common.entity.DiscoveryEntity;
import com.nepxion.discovery.common.entity.RouterEntity;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.WeightEntityWrapper;
import com.nepxion.discovery.common.entity.WeightFilterEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.common.util.UrlUtil;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;

@RestController
@RequestMapping(path = "/router")
@Api(tags = { "路由接口" })
public class RouterEndpoint {
    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private DiscoveryClient discoveryClient;

    private RestTemplate routerRestTemplate;

    public RouterEndpoint() {
        routerRestTemplate = new RestTemplate();
    }

    @RequestMapping(path = "/services", method = RequestMethod.GET)
    @ApiOperation(value = "获取服务注册中心的服务列表", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    public List<String> services() {
        return getServices();
    }

    @RequestMapping(path = "/instances/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取本地节点可访问其他节点（根据服务名）的实例列表", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    public List<ServiceInstance> instances(@PathVariable(value = "serviceId") @ApiParam(value = "目标服务名", required = true) String serviceId) {
        return getInstanceList(serviceId);
    }

    @RequestMapping(path = "/info", method = RequestMethod.GET)
    @ApiOperation(value = "获取本地节点信息", notes = "获取当前节点的简单信息", response = RouterEntity.class, httpMethod = "GET")
    @ResponseBody
    public RouterEntity info() {
        return getRouterEntity();
    }

    @RequestMapping(path = "/route/{routeServiceId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取本地节点可访问其他节点（根据服务名）的路由信息列表", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    public List<RouterEntity> route(@PathVariable(value = "routeServiceId") @ApiParam(value = "目标服务名", required = true) String routeServiceId) {
        return getRouterEntityList(routeServiceId);
    }

    @RequestMapping(path = "/route/{routeServiceId}/{routeHost}/{routePort}/{routeContextPath}", method = RequestMethod.GET)
    @ApiOperation(value = "获取指定节点（根据IP地址和端口）可访问其他节点（根据服务名）的路由信息列表", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    public List<RouterEntity> route(@PathVariable(value = "routeServiceId") @ApiParam(value = "目标服务名", required = true) String routeServiceId, @PathVariable(value = "routeHost") @ApiParam(value = "目标服务所在机器的IP地址", required = true) String routeHost, @PathVariable(value = "routePort") @ApiParam(value = "目标服务所在机器的端口号", required = true) int routePort, @PathVariable(value = "routeContextPath") @ApiParam(value = "目标服务的调用路径前缀", required = true, defaultValue = "/") String routeContextPath) {
        return getRouterEntityList(routeServiceId, routeHost, routePort, routeContextPath);
    }

    @RequestMapping(path = "/routes", method = RequestMethod.POST)
    @ApiOperation(value = "获取全路径的路由信息树", notes = "参数按调用服务名的前后次序排列，起始节点的服务名不能加上去。如果多个用“;”分隔，不允许出现空格", response = RouterEntity.class, httpMethod = "POST")
    @ResponseBody
    public RouterEntity routes(@RequestBody @ApiParam(value = "格式示例：service-a;service-b", required = true) String routeServiceIds) {
        return routeTree(routeServiceIds);
    }

    public List<String> getServices() {
        return discoveryClient.getServices();
    }

    public List<ServiceInstance> getInstanceList(String serviceId) {
        return discoveryClient.getInstances(serviceId);
    }

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
        String contextPath = pluginAdapter.getContextPath();

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
            String serviceId = pluginAdapter.getInstanceServiceId(instance);
            String serviceType = pluginAdapter.getInstanceServiceType(instance);
            String version = pluginAdapter.getInstanceVersion(instance);
            String region = pluginAdapter.getInstanceRegion(instance);
            String environment = pluginAdapter.getInstanceEnvironment(instance);
            String zone = pluginAdapter.getInstanceZone(instance);
            String host = instance.getHost();
            int port = instance.getPort();
            int weight = getWeight(routeServiceId, version, region);
            String contextPath = pluginAdapter.getInstanceContextPath(instance);

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
            throw new DiscoveryException("Failed to execute to route, serviceId=" + routeServiceId + ", url=" + url, e);
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
        if (weightFilterEntity == null || !weightFilterEntity.hasWeight()) {
            return -1;
        }

        String serviceId = pluginAdapter.getServiceId();

        return WeightEntityWrapper.getWeight(weightFilterEntity, providerServiceId, providerVersion, providerRegion, serviceId);
    }
}
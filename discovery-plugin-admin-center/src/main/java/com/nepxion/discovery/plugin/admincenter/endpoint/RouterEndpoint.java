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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.actuate.endpoint.mvc.MvcEndpoint;
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

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.RouterEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.common.util.UrlUtil;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;

@RestController
@RequestMapping(path = "/router")
@Api(tags = { "路由接口" })
@ManagedResource(description = "Router Endpoint")
public class RouterEndpoint implements MvcEndpoint {
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
        String host = pluginAdapter.getHost();
        int port = pluginAdapter.getPort();
        String contextPath = pluginAdapter.getContextPath();

        RouterEntity routerEntity = new RouterEntity();
        routerEntity.setServiceId(serviceId);
        routerEntity.setVersion(version);
        routerEntity.setHost(host);
        routerEntity.setPort(port);
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
            String host = instance.getHost();
            int port = instance.getPort();
            String contextPath = metadata.get(DiscoveryConstant.SPRING_APPLICATION_CONTEXT_PATH);

            RouterEntity routerEntity = new RouterEntity();
            routerEntity.setServiceId(serviceId);
            routerEntity.setVersion(version);
            routerEntity.setHost(host);
            routerEntity.setPort(port);
            routerEntity.setContextPath(contextPath);

            routerEntityList.add(routerEntity);
        }

        return routerEntityList;
    }

    @SuppressWarnings("unchecked")
    public List<RouterEntity> getRouterEntityList(String routeServiceId, String routeHost, int routePort, String routeContextPath) {
        String url = "http://" + routeHost + ":" + routePort + UrlUtil.formatContextPath(routeContextPath) + "router/instances/" + routeServiceId;

        List<Map<String, ?>> instanceList = null;
        try {
            instanceList = routerRestTemplate.getForEntity(url, List.class).getBody();
        } catch (RestClientException e) {
            throw new DiscoveryException("Get instance list for route serviceId=" + routeServiceId + " with url=" + url + " failed", e);
        }

        if (CollectionUtils.isEmpty(instanceList)) {
            return null;
        }

        List<RouterEntity> routerEntityList = new ArrayList<RouterEntity>();
        for (Map<String, ?> instance : instanceList) {
            Map<String, String> metadata = (Map<String, String>) instance.get(DiscoveryConstant.METADATA);
            String serviceId = instance.get(DiscoveryConstant.SERVICE_ID).toString().toLowerCase();
            String version = metadata.get(DiscoveryConstant.VERSION);
            String host = instance.get(DiscoveryConstant.HOST).toString();
            Integer port = (Integer) instance.get(DiscoveryConstant.PORT);
            String contextPath = metadata.get(DiscoveryConstant.SPRING_APPLICATION_CONTEXT_PATH);

            RouterEntity routerEntity = new RouterEntity();
            routerEntity.setServiceId(serviceId);
            routerEntity.setVersion(version);
            routerEntity.setHost(host);
            routerEntity.setPort(port);
            routerEntity.setContextPath(contextPath);

            routerEntityList.add(routerEntity);
        }

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

    @Override
    public String getPath() {
        return "";
    }

    @Override
    public boolean isSensitive() {
        return true;
    }

    @Override
    public Class<? extends Endpoint<?>> getEndpointType() {
        return null;
    }
}
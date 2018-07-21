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
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.constant.PluginConstant;
import com.nepxion.discovery.plugin.framework.entity.RouterEntity;
import com.nepxion.discovery.plugin.framework.exception.PluginException;

@RestController
@Api(tags = { "路由接口" })
public class RouterEndpoint {
    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private RestTemplate routerRestTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(path = "/router/services", method = RequestMethod.GET)
    @ApiOperation(value = "获取服务注册中心的服务列表", notes = "", response = List.class, httpMethod = "GET")
    public List<String> services() {
        return getServices();
    }

    @RequestMapping(path = "/router/instances/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取本地节点可访问其他节点（根据服务名）的实例列表", notes = "", response = List.class, httpMethod = "GET")
    public List<ServiceInstance> instances(@PathVariable(value = "serviceId") @ApiParam(value = "目标服务名", required = true) String serviceId) {
        return getInstanceList(serviceId);
    }

    @RequestMapping(path = "/router/info", method = RequestMethod.GET)
    @ApiOperation(value = "获取本地节点信息", notes = "获取当前节点的简单信息", response = RouterEntity.class, httpMethod = "GET")
    public RouterEntity info() {
        return getRouterEntity();
    }

    @RequestMapping(path = "/router/route/{routeServiceId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取本地节点可访问其他节点（根据服务名）的路由信息列表", notes = "", response = List.class, httpMethod = "GET")
    public List<RouterEntity> route(@PathVariable(value = "routeServiceId") @ApiParam(value = "目标服务名", required = true) String routeServiceId) {
        return getRouterEntityList(routeServiceId);
    }

    @RequestMapping(path = "/router/route/{routeServiceId}/{routeHost}/{routePort}", method = RequestMethod.GET)
    @ApiOperation(value = "获取指定节点（根据IP和端口）可访问其他节点（根据服务名）的路由信息列表", notes = "", response = List.class, httpMethod = "GET")
    public List<RouterEntity> route(@PathVariable(value = "routeServiceId") @ApiParam(value = "目标服务名", required = true) String routeServiceId, @PathVariable(value = "routeHost") @ApiParam(value = "目标服务所在机器的IP地址", required = true) String routeHost, @PathVariable(value = "routePort") @ApiParam(value = "目标服务所在机器的端口号", required = true) int routePort) {
        return getRouterEntityList(routeServiceId, routeHost, routePort);
    }

    @RequestMapping(path = "/router/routes", method = RequestMethod.POST)
    @ApiOperation(value = "获取全路径的路由信息树", notes = "参数按调用服务名的前后次序排列，起始节点的服务名不能加上去。如果多个用“;”分隔，不允许出现空格", response = RouterEntity.class, httpMethod = "POST")
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
        String url = "http://" + routeHost + ":" + routePort + "/router/instances/" + routeServiceId;

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
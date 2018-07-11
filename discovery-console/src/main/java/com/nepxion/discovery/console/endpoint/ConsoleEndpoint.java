package com.nepxion.discovery.console.endpoint;

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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.actuate.endpoint.mvc.MvcEndpoint;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.console.entity.InstanceEntity;
import com.nepxion.discovery.console.handler.ConsoleErrorHandler;

@RestController
@Api(tags = { "控制台接口" })
@ManagedResource(description = "Console Endpoint")
public class ConsoleEndpoint implements MvcEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(ConsoleEndpoint.class);

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate consoleRestTemplate;

    @RequestMapping(path = "/console/services", method = RequestMethod.GET)
    @ApiOperation(value = "获取服务注册中心的服务列表", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    @ManagedOperation
    public List<String> services() {
        return getServices();
    }

    @RequestMapping(path = "/console/instances/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取服务注册中心服务的实例列表", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    @ManagedOperation
    public List<ServiceInstance> instances(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return getInstances(serviceId);
    }

    @RequestMapping(path = "/console/instance-list/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取服务注册中心服务的实例列表（精简数据）", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    @ManagedOperation
    public List<InstanceEntity> instanceList(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return getInstanceList(serviceId);
    }

    @RequestMapping(path = "/console/instance-map", method = RequestMethod.GET)
    @ApiOperation(value = "获取服务注册中心的服务和实例的Map（精简数据）", notes = "", response = Map.class, httpMethod = "GET")
    @ResponseBody
    @ManagedOperation
    public Map<String, List<InstanceEntity>> instanceMap() {
        return getInstanceMap();
    }

    @RequestMapping(path = "/console/config/update-async/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量异步推送更新规则配置信息", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<?> configUpdateAsync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "规则配置内容，XML格式", required = true) String config) {
        return executeConfigUpdate(serviceId, config, true);
    }

    @RequestMapping(path = "/console/config/update-sync/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量同步推送更新规则配置信息", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<?> configUpdateSync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "规则配置内容，XML格式", required = true) String config) {
        return executeConfigUpdate(serviceId, config, false);
    }

    @RequestMapping(path = "/version/update/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量更新服务的动态版本", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<?> versionUpdate(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "版本号", required = true) String version) {
        return executeVersionUpdate(serviceId, version);
    }

    @RequestMapping(path = "/version/clear/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "批量清除服务的动态版本", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<?> versionClear(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return executeVersionClear(serviceId);
    }

    public List<String> getServices() {
        return discoveryClient.getServices();
    }

    public List<ServiceInstance> getInstances(String serviceId) {
        return discoveryClient.getInstances(serviceId);
    }

    public List<InstanceEntity> getInstanceList(String service) {
        List<ServiceInstance> serviceInstances = getInstances(service);
        List<InstanceEntity> instanceEntityList = new ArrayList<InstanceEntity>(serviceInstances.size());
        for (ServiceInstance serviceInstance : serviceInstances) {
            String serviceId = serviceInstance.getServiceId().toLowerCase();
            String version = serviceInstance.getMetadata().get("version");
            String host = serviceInstance.getHost();
            int port = serviceInstance.getPort();

            InstanceEntity instanceEntity = new InstanceEntity();
            instanceEntity.setServiceId(serviceId);
            instanceEntity.setVersion(version);
            instanceEntity.setHost(host);
            instanceEntity.setPort(port);

            instanceEntityList.add(instanceEntity);
        }

        return instanceEntityList;
    }

    public Map<String, List<InstanceEntity>> getInstanceMap() {
        List<String> services = getServices();
        Map<String, List<InstanceEntity>> serviceMap = new LinkedHashMap<String, List<InstanceEntity>>(services.size());
        for (String service : services) {
            List<InstanceEntity> instanceEntityList = getInstanceList(service);
            serviceMap.put(service, instanceEntityList);
        }

        return serviceMap;
    }

    private ResponseEntity<?> executeConfigUpdate(String serviceId, String config, boolean async) {
        StringBuilder stringBuilder = new StringBuilder();

        List<ServiceInstance> serviceInstances = getInstances(serviceId);
        for (ServiceInstance serviceInstance : serviceInstances) {
            String host = serviceInstance.getHost();
            int port = serviceInstance.getPort();

            String url = "http://" + host + ":" + port + "/config/update-" + (async ? "async" : "sync");
            String result = consoleRestTemplate.postForEntity(url, config, String.class).getBody();

            if (!StringUtils.equals(result, "OK")) {
                ConsoleErrorHandler errorHandler = (ConsoleErrorHandler) consoleRestTemplate.getErrorHandler();
                result = errorHandler.getCause();
            }
            stringBuilder.append("Rule sent, serviceId=").append(serviceId).append(", url=").append(url).append(", result=").append(result).append("\n");
        }

        String result = stringBuilder.toString();
        result = result.substring(0, result.lastIndexOf("\n"));

        LOG.info("\n{}", result);

        return ResponseEntity.ok().body(result);
    }

    private ResponseEntity<?> executeVersionUpdate(String serviceId, String version) {
        StringBuilder stringBuilder = new StringBuilder();

        List<ServiceInstance> serviceInstances = getInstances(serviceId);
        for (ServiceInstance serviceInstance : serviceInstances) {
            String host = serviceInstance.getHost();
            int port = serviceInstance.getPort();

            String url = "http://" + host + ":" + port + "/version/update";
            String result = consoleRestTemplate.postForEntity(url, version, String.class).getBody();

            if (!StringUtils.equals(result, "OK")) {
                ConsoleErrorHandler errorHandler = (ConsoleErrorHandler) consoleRestTemplate.getErrorHandler();
                result = errorHandler.getCause();
            }
            stringBuilder.append("Version sent, serviceId=").append(serviceId).append(", url=").append(url).append(", result=").append(result).append("\n");
        }

        String result = stringBuilder.toString();
        result = result.substring(0, result.lastIndexOf("\n"));

        LOG.info("\n{}", result);

        return ResponseEntity.ok().body(result);
    }

    private ResponseEntity<?> executeVersionClear(String serviceId) {
        StringBuilder stringBuilder = new StringBuilder();

        List<ServiceInstance> serviceInstances = getInstances(serviceId);
        for (ServiceInstance serviceInstance : serviceInstances) {
            String host = serviceInstance.getHost();
            int port = serviceInstance.getPort();

            String url = "http://" + host + ":" + port + "/version/clear";
            String result = consoleRestTemplate.getForEntity(url, String.class).getBody();

            if (!StringUtils.equals(result, "OK")) {
                ConsoleErrorHandler errorHandler = (ConsoleErrorHandler) consoleRestTemplate.getErrorHandler();
                result = errorHandler.getCause();
            }
            stringBuilder.append("Version sent, serviceId=").append(serviceId).append(", url=").append(url).append(", result=").append(result).append("\n");
        }

        String result = stringBuilder.toString();
        result = result.substring(0, result.lastIndexOf("\n"));

        LOG.info("\n{}", result);

        return ResponseEntity.ok().body(result);
    }

    @Override
    public String getPath() {
        return "/";
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
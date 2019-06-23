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

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.actuate.endpoint.mvc.MvcEndpoint;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.composite.CompositeDiscoveryClient;
import org.springframework.http.HttpStatus;
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

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.InstanceEntity;
import com.nepxion.discovery.common.entity.InstanceEntityWrapper;
import com.nepxion.discovery.common.entity.UserEntity;
import com.nepxion.discovery.console.adapter.ConfigAdapter;
import com.nepxion.discovery.console.authentication.AuthenticationResource;
import com.nepxion.discovery.console.rest.ConfigClearRestInvoker;
import com.nepxion.discovery.console.rest.ConfigUpdateRestInvoker;
import com.nepxion.discovery.console.rest.VersionClearRestInvoker;
import com.nepxion.discovery.console.rest.VersionUpdateRestInvoker;

@RestController
@RequestMapping(path = "/console")
@Api(tags = { "控制台接口" })
@ManagedResource(description = "Console Endpoint")
public class ConsoleEndpoint implements MvcEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(ConsoleEndpoint.class);

    private static final String[] DISCOVERY_TYPES = { "Eureka", "Consul", "Zookeeper", "Nacos" };

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired(required = false)
    private ConfigAdapter configAdapter;

    @Autowired
    private RestTemplate consoleRestTemplate;

    @Autowired
    private AuthenticationResource authenticationResource;

    @RequestMapping(path = "/authenticate", method = RequestMethod.POST)
    @ApiOperation(value = "登录认证", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<?> authenticate(@RequestBody @ApiParam(value = "UserEntity实例", required = true) UserEntity userEntity) {
        return executeAuthenticate(userEntity);
    }

    @RequestMapping(path = "/discovery-type", method = RequestMethod.GET)
    @ApiOperation(value = "获取注册发现中心类型", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<?> discoveryType() {
        return getDiscoveryType();
    }

    @RequestMapping(path = "/config-type", method = RequestMethod.GET)
    @ApiOperation(value = "获取配置中心类型", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<?> configType() {
        return getConfigType();
    }

    @RequestMapping(path = "/groups", method = RequestMethod.GET)
    @ApiOperation(value = "获取服务注册中心的服务组名列表", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    @ManagedOperation
    public List<String> groups() {
        return getGroups();
    }

    @RequestMapping(path = "/services", method = RequestMethod.GET)
    @ApiOperation(value = "获取服务注册中心的服务名列表", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    @ManagedOperation
    public List<String> services() {
        return getServices();
    }

    @RequestMapping(path = "/instances/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取服务注册中心的服务实例列表", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    @ManagedOperation
    public List<ServiceInstance> instances(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return getInstances(serviceId);
    }

    @RequestMapping(path = "/instance-list/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取服务注册中心的服务实例列表（精简数据）", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    @ManagedOperation
    public List<InstanceEntity> instanceList(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return getInstanceList(serviceId);
    }

    @RequestMapping(path = "/instance-map", method = RequestMethod.POST)
    @ApiOperation(value = "获取服务注册中心的服务实例的Map（精简数据）", notes = "服务组名列表", response = Map.class, httpMethod = "POST")
    @ResponseBody
    @ManagedOperation
    public Map<String, List<InstanceEntity>> instanceMap(@RequestBody @ApiParam(value = "服务组名列表，传入空列则可以获取全部服务实例数据", required = true) List<String> groups) {
        return getInstanceMap(groups);
    }

    @RequestMapping(path = "/remote-config/update/{group}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "推送更新规则配置信息到远程配置中心", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<?> remoteConfigUpdate(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名。当全局推送模式下，服务名必须由组名来代替", required = true) String serviceId, @RequestBody @ApiParam(value = "规则配置内容，XML格式", required = true) String config) {
        return executeRemoteConfigUpdate(group, serviceId, config);
    }

    @RequestMapping(path = "/remote-config/clear/{group}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "清除规则配置信息到远程配置中心", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<?> remoteConfigClear(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名。当全局推送模式下，服务名必须由组名来代替", required = true) String serviceId) {
        return executeRemoteConfigClear(group, serviceId);
    }

    @RequestMapping(path = "/remote-config/view/{group}/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "查看远程配置中心的规则配置信息", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<?> remoteConfigView(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名。当全局推送模式下，服务名必须由组名来代替", required = true) String serviceId) {
        return executeRemoteConfigView(group, serviceId);
    }

    @RequestMapping(path = "/config/update-async/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量异步推送更新规则配置信息", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<?> configUpdateAsync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "规则配置内容，XML格式", required = true) String config) {
        return executeConfigUpdate(serviceId, config, true);
    }

    @RequestMapping(path = "/config/update-sync/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量同步推送更新规则配置信息", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<?> configUpdateSync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "规则配置内容，XML格式", required = true) String config) {
        return executeConfigUpdate(serviceId, config, false);
    }

    @RequestMapping(path = "/config/clear-async/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量异步清除更新的规则配置信息", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<?> configClearAsync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return executeConfigClear(serviceId, true);
    }

    @RequestMapping(path = "/config/clear-sync/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量同步清除更新的规则配置信息", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<?> configClearSync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return executeConfigClear(serviceId, false);
    }

    @RequestMapping(path = "/version/update-async/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量异步更新服务的动态版本", notes = "根据指定的localVersion更新服务的dynamicVersion。如果输入的localVersion不匹配服务的localVersion，则忽略；如果如果输入的localVersion为空，则直接更新服务的dynamicVersion", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<?> versionUpdateAsync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "版本号，格式为[dynamicVersion]或者[dynamicVersion];[localVersion]", required = true) String version) {
        return executeVersionUpdate(serviceId, version, true);
    }

    @RequestMapping(path = "/version/update-sync/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量同步更新服务的动态版本", notes = "根据指定的localVersion更新服务的dynamicVersion。如果输入的localVersion不匹配服务的localVersion，则忽略；如果如果输入的localVersion为空，则直接更新服务的dynamicVersion", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<?> versionUpdateSync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "版本号，格式为[dynamicVersion]或者[dynamicVersion];[localVersion]", required = true) String version) {
        return executeVersionUpdate(serviceId, version, false);
    }

    @RequestMapping(path = "/version/clear-async/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量异步清除服务的动态版本", notes = "根据指定的localVersion清除服务的dynamicVersion。如果输入的localVersion不匹配服务的localVersion，则忽略；如果如果输入的localVersion为空，则直接清除服务的dynamicVersion", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<?> versionClearAsync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody(required = false) @ApiParam(value = "版本号，指localVersion，可以为空") String version) {
        return executeVersionClear(serviceId, version, true);
    }

    @RequestMapping(path = "/version/clear-sync/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量同步清除服务的动态版本", notes = "根据指定的localVersion清除服务的dynamicVersion。如果输入的localVersion不匹配服务的localVersion，则忽略；如果如果输入的localVersion为空，则直接清除服务的dynamicVersion", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<?> versionClearSync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody(required = false) @ApiParam(value = "版本号，指localVersion，可以为空") String version) {
        return executeVersionClear(serviceId, version, false);
    }

    private ResponseEntity<?> executeAuthenticate(UserEntity userEntity) {
        try {
            boolean result = authenticationResource.authenticate(userEntity);

            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private ResponseEntity<?> getDiscoveryType() {
        if (discoveryClient instanceof CompositeDiscoveryClient) {
            CompositeDiscoveryClient compositeDiscoveryClient = (CompositeDiscoveryClient) discoveryClient;
            List<DiscoveryClient> discoveryClients = compositeDiscoveryClient.getDiscoveryClients();
            for (DiscoveryClient client : discoveryClients) {
                String discoveryDescription = client.description();
                for (int i = 0; i < DISCOVERY_TYPES.length; i++) {
                    String discoveryType = DISCOVERY_TYPES[i];
                    if (discoveryDescription.toLowerCase().contains(discoveryType.toLowerCase())) {
                        return ResponseEntity.ok().body(discoveryType);
                    }
                }
            }
        } else {
            String discoveryDescription = discoveryClient.description();
            for (int i = 0; i < DISCOVERY_TYPES.length; i++) {
                String discoveryType = DISCOVERY_TYPES[i];
                if (discoveryDescription.toLowerCase().contains(discoveryType.toLowerCase())) {
                    return ResponseEntity.ok().body(discoveryType);
                }
            }
        }

        return ResponseEntity.ok().body(DiscoveryConstant.UNKNOWN);
    }

    private ResponseEntity<?> getConfigType() {
        if (configAdapter != null) {
            String configType = configAdapter.getConfigType();

            return ResponseEntity.ok().body(configType);
        }

        return ResponseEntity.ok().body(DiscoveryConstant.UNKNOWN);
    }

    public List<String> getGroups() {
        List<String> groups = new ArrayList<String>();

        List<String> services = getServices();
        for (String service : services) {
            List<InstanceEntity> instanceEntityList = getInstanceList(service);
            for (InstanceEntity instance : instanceEntityList) {
                String plugin = InstanceEntityWrapper.getPlugin(instance);
                String group = InstanceEntityWrapper.getGroup(instance);
                if (StringUtils.isNotEmpty(plugin) && !groups.contains(group)) {
                    groups.add(group);
                }
            }
        }

        return groups;
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
            Map<String, String> metadata = serviceInstance.getMetadata();
            String serviceId = serviceInstance.getServiceId().toLowerCase();
            String version = metadata.get(DiscoveryConstant.VERSION);
            String region = metadata.get(DiscoveryConstant.REGION);
            String host = serviceInstance.getHost();
            int port = serviceInstance.getPort();

            InstanceEntity instanceEntity = new InstanceEntity();
            instanceEntity.setServiceId(serviceId);
            instanceEntity.setVersion(version);
            instanceEntity.setRegion(region);
            instanceEntity.setHost(host);
            instanceEntity.setPort(port);
            instanceEntity.setMetadata(metadata);

            instanceEntityList.add(instanceEntity);
        }

        return instanceEntityList;
    }

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

    private ResponseEntity<?> executeRemoteConfigUpdate(String group, String serviceId, String config) {
        if (configAdapter == null) {
            LOG.error("Remote config adapter isn't provided");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Remote config adapter isn't provided");
        }

        try {
            boolean result = configAdapter.updateConfig(group, serviceId, config);

            return ResponseEntity.ok().body(result ? DiscoveryConstant.OK : DiscoveryConstant.NO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private ResponseEntity<?> executeRemoteConfigClear(String group, String serviceId) {
        if (configAdapter == null) {
            LOG.error("Remote config adapter isn't provided");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Remote config adapter isn't provided");
        }

        try {
            boolean result = configAdapter.clearConfig(group, serviceId);

            return ResponseEntity.ok().body(result ? DiscoveryConstant.OK : DiscoveryConstant.NO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private ResponseEntity<?> executeRemoteConfigView(String group, String serviceId) {
        if (configAdapter == null) {
            LOG.error("Remote config adapter isn't provided");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Remote config adapter isn't provided");
        }

        try {
            String config = configAdapter.getConfig(group, serviceId);

            return ResponseEntity.ok().body(config);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private ResponseEntity<?> executeConfigUpdate(String serviceId, String config, boolean async) {
        List<ServiceInstance> serviceInstances = getInstances(serviceId);

        ConfigUpdateRestInvoker configUpdateRestInvoker = new ConfigUpdateRestInvoker(serviceInstances, consoleRestTemplate, config, async);

        return configUpdateRestInvoker.invoke();
    }

    private ResponseEntity<?> executeConfigClear(String serviceId, boolean async) {
        List<ServiceInstance> serviceInstances = getInstances(serviceId);

        ConfigClearRestInvoker configClearRestInvoker = new ConfigClearRestInvoker(serviceInstances, consoleRestTemplate, async);

        return configClearRestInvoker.invoke();
    }

    private ResponseEntity<?> executeVersionUpdate(String serviceId, String version, boolean async) {
        List<ServiceInstance> serviceInstances = getInstances(serviceId);

        VersionUpdateRestInvoker versionUpdateRestInvoker = new VersionUpdateRestInvoker(serviceInstances, consoleRestTemplate, version, async);

        return versionUpdateRestInvoker.invoke();
    }

    private ResponseEntity<?> executeVersionClear(String serviceId, String version, boolean async) {
        List<ServiceInstance> serviceInstances = getInstances(serviceId);

        VersionClearRestInvoker versionClearRestInvoker = new VersionClearRestInvoker(serviceInstances, consoleRestTemplate, version, async);

        return versionClearRestInvoker.invoke();
    }

    @Override
    public String getPath() {
        return StringUtils.EMPTY;
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
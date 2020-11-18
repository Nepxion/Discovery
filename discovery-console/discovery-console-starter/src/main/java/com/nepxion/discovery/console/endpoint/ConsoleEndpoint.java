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
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.composite.CompositeDiscoveryClient;
import org.springframework.expression.TypeComparator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.InstanceEntity;
import com.nepxion.discovery.common.entity.InstanceEntityWrapper;
import com.nepxion.discovery.common.entity.ServiceType;
import com.nepxion.discovery.common.entity.UserEntity;
import com.nepxion.discovery.common.expression.DiscoveryExpressionResolver;
import com.nepxion.discovery.common.expression.DiscoveryTypeComparor;
import com.nepxion.discovery.common.handler.DiscoveryResponseErrorHandler;
import com.nepxion.discovery.common.util.StringUtil;
import com.nepxion.discovery.console.adapter.ConfigAdapter;
import com.nepxion.discovery.console.authentication.AuthenticationResource;
import com.nepxion.discovery.console.rest.ConfigClearRestInvoker;
import com.nepxion.discovery.console.rest.ConfigUpdateRestInvoker;
import com.nepxion.discovery.console.rest.SentinelClearRestInvoker;
import com.nepxion.discovery.console.rest.SentinelUpdateRestInvoker;
import com.nepxion.discovery.console.rest.VersionClearRestInvoker;
import com.nepxion.discovery.console.rest.VersionUpdateRestInvoker;

@RestController
@RequestMapping(path = "/console")
@Api(tags = { "控制台接口" })
public class ConsoleEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(ConsoleEndpoint.class);

    private static final String[] DISCOVERY_TYPES = { "Eureka", "Consul", "Zookeeper", "Nacos" };

    private TypeComparator typeComparator = new DiscoveryTypeComparor();

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired(required = false)
    private ConfigAdapter configAdapter;

    @Autowired
    private AuthenticationResource authenticationResource;

    private RestTemplate consoleRestTemplate;

    public ConsoleEndpoint() {
        consoleRestTemplate = new RestTemplate();
        consoleRestTemplate.setErrorHandler(new DiscoveryResponseErrorHandler());
    }

    @RequestMapping(path = "/authenticate", method = RequestMethod.POST)
    @ApiOperation(value = "登录认证", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> authenticate(@RequestBody @ApiParam(value = "UserEntity实例", required = true) UserEntity userEntity) {
        return executeAuthenticate(userEntity);
    }

    @RequestMapping(path = "/discovery-type", method = RequestMethod.GET)
    @ApiOperation(value = "获取注册发现中心类型", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> discoveryType() {
        return getDiscoveryType();
    }

    @RequestMapping(path = "/config-type", method = RequestMethod.GET)
    @ApiOperation(value = "获取配置中心类型", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> configType() {
        return getConfigType();
    }

    @RequestMapping(path = "/groups", method = RequestMethod.GET)
    @ApiOperation(value = "获取服务注册中心的服务组名列表", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    public List<String> groups() {
        return getGroups();
    }

    @RequestMapping(path = "/services", method = RequestMethod.GET)
    @ApiOperation(value = "获取服务注册中心的服务名列表", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    public List<String> services() {
        return getServices();
    }

    @RequestMapping(path = "/gateways", method = RequestMethod.GET)
    @ApiOperation(value = "获取服务注册中心的网关名列表", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    public List<String> gateways() {
        return getGateways();
    }

    @RequestMapping(path = "/instances/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取服务注册中心的服务实例列表", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    public List<ServiceInstance> instances(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return getInstances(serviceId);
    }

    @RequestMapping(path = "/instance-list/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取服务注册中心的服务实例列表（精简数据）", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    public List<InstanceEntity> instanceList(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return getInstanceList(serviceId);
    }

    @RequestMapping(path = "/instance-map", method = RequestMethod.POST)
    @ApiOperation(value = "获取服务注册中心的服务实例的Map（精简数据）", notes = "服务组名列表", response = Map.class, httpMethod = "POST")
    @ResponseBody
    public Map<String, List<InstanceEntity>> instanceMap(@RequestBody @ApiParam(value = "服务组名列表，传入空列则可以获取全部服务实例数据", required = true) List<String> groups) {
        return getInstanceMap(groups);
    }

    @RequestMapping(path = "/remote-config/update/{group}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "推送更新规则配置信息到远程配置中心", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> remoteConfigUpdate(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名。当全局推送模式下，服务名必须由组名来代替", required = true) String serviceId, @RequestBody @ApiParam(value = "规则配置内容，XML格式", required = true) String config) {
        return executeRemoteConfigUpdate(group, serviceId, config);
    }

    @RequestMapping(path = "/remote-config/clear/{group}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "清除规则配置信息到远程配置中心", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> remoteConfigClear(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名。当全局推送模式下，服务名必须由组名来代替", required = true) String serviceId) {
        return executeRemoteConfigClear(group, serviceId);
    }

    @RequestMapping(path = "/remote-config/view/{group}/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "查看远程配置中心的规则配置信息", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> remoteConfigView(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名。当全局推送模式下，服务名必须由组名来代替", required = true) String serviceId) {
        return executeRemoteConfigView(group, serviceId);
    }

    @RequestMapping(path = "/config/update-async/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量异步推送更新规则配置信息", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> configUpdateAsync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "规则配置内容，XML格式", required = true) String config) {
        return executeConfigUpdate(serviceId, config, true);
    }

    @RequestMapping(path = "/config/update-sync/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量同步推送更新规则配置信息", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> configUpdateSync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "规则配置内容，XML格式", required = true) String config) {
        return executeConfigUpdate(serviceId, config, false);
    }

    @RequestMapping(path = "/config/clear-async/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量异步清除更新的规则配置信息", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> configClearAsync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return executeConfigClear(serviceId, true);
    }

    @RequestMapping(path = "/config/clear-sync/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量同步清除更新的规则配置信息", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> configClearSync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return executeConfigClear(serviceId, false);
    }

    @RequestMapping(path = "/version/update-async/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量异步更新服务的动态版本", notes = "根据指定的localVersion更新服务的dynamicVersion。如果输入的localVersion不匹配服务的localVersion，则忽略；如果如果输入的localVersion为空，则直接更新服务的dynamicVersion", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> versionUpdateAsync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "版本号，格式为[dynamicVersion]或者[dynamicVersion];[localVersion]", required = true) String version) {
        return executeVersionUpdate(serviceId, version, true);
    }

    @RequestMapping(path = "/version/update-sync/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量同步更新服务的动态版本", notes = "根据指定的localVersion更新服务的dynamicVersion。如果输入的localVersion不匹配服务的localVersion，则忽略；如果如果输入的localVersion为空，则直接更新服务的dynamicVersion", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> versionUpdateSync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "版本号，格式为[dynamicVersion]或者[dynamicVersion];[localVersion]", required = true) String version) {
        return executeVersionUpdate(serviceId, version, false);
    }

    @RequestMapping(path = "/version/clear-async/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量异步清除服务的动态版本", notes = "根据指定的localVersion清除服务的dynamicVersion。如果输入的localVersion不匹配服务的localVersion，则忽略；如果如果输入的localVersion为空，则直接清除服务的dynamicVersion", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> versionClearAsync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody(required = false) @ApiParam(value = "版本号，指localVersion，可以为空") String version) {
        return executeVersionClear(serviceId, version, true);
    }

    @RequestMapping(path = "/version/clear-sync/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量同步清除服务的动态版本", notes = "根据指定的localVersion清除服务的dynamicVersion。如果输入的localVersion不匹配服务的localVersion，则忽略；如果如果输入的localVersion为空，则直接清除服务的dynamicVersion", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> versionClearSync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody(required = false) @ApiParam(value = "版本号，指localVersion，可以为空") String version) {
        return executeVersionClear(serviceId, version, false);
    }

    @RequestMapping(path = "/sentinel/update/{serviceId}/{ruleType}", method = RequestMethod.POST)
    @ApiOperation(value = "更新哨兵规则列表", notes = "哨兵规则类型取值： flow | degrade | authority | system | param-flow", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> sentinelUpdate(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @PathVariable(value = "ruleType") @ApiParam(value = "哨兵规则类型", required = true) String ruleType, @RequestBody @ApiParam(value = "哨兵规则内容，JSON格式", required = true) String rule) {
        return executeSentinelUpdate(serviceId, ruleType, rule);
    }

    @RequestMapping(path = "/sentinel/clear/{serviceId}/{ruleType}", method = RequestMethod.POST)
    @ApiOperation(value = "清除哨兵规则列表", notes = "哨兵规则类型取值： flow | degrade | authority | system | param-flow", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> sentinelClear(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @PathVariable(value = "ruleType") @ApiParam(value = "哨兵规则类型", required = true) String ruleType) {
        return executeSentinelClear(serviceId, ruleType);
    }

    @RequestMapping(path = "/validate-expression", method = RequestMethod.GET)
    @ApiOperation(value = "校验策略的条件表达式", notes = "", response = Boolean.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> validateExpression(@RequestParam @ApiParam(value = "条件表达式，格式示例：#H['a'] == '1' && #H['b'] != '2'。注意，引号是否为中文格式", required = true) String condition, @RequestParam(required = false, defaultValue = "") @ApiParam(value = "校验参数，格式示例：a=1;b=1。如果多个用“;”分隔，不允许出现空格。允许为空", required = false, defaultValue = "") String validation) {
        Map<String, String> map = null;
        try {
            map = StringUtil.splitToMap(validation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid format for validation input");
        }

        boolean validated = DiscoveryExpressionResolver.eval(condition, DiscoveryConstant.EXPRESSION_PREFIX, map, typeComparator);

        return ResponseEntity.ok().body(validated);
    }

    private ResponseEntity<?> executeAuthenticate(UserEntity userEntity) {
        try {
            boolean result = authenticationResource.authenticate(userEntity);

            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionUtils.getStackTrace(e));
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

    public List<String> getGateways() {
        List<String> gateways = new ArrayList<String>();
        List<String> services = getServices();
        for (String service : services) {
            List<ServiceInstance> instances = getInstances(service);
            for (ServiceInstance instance : instances) {
                Map<String, String> metadata = instance.getMetadata();
                String serviceId = instance.getServiceId().toLowerCase();
                String serviceType = metadata.get(DiscoveryConstant.SPRING_APPLICATION_TYPE);
                if (StringUtils.equals(serviceType, ServiceType.GATEWAY.toString())) {
                    if (!gateways.contains(serviceId)) {
                        gateways.add(serviceId);
                    }
                }
            }
        }

        return gateways;
    }

    public List<InstanceEntity> getInstanceList(String service) {
        List<ServiceInstance> instances = getInstances(service);
        List<InstanceEntity> instanceEntityList = new ArrayList<InstanceEntity>(instances.size());
        for (ServiceInstance instance : instances) {
            Map<String, String> metadata = instance.getMetadata();
            String serviceId = instance.getServiceId().toLowerCase();
            String serviceType = metadata.get(DiscoveryConstant.SPRING_APPLICATION_TYPE);
            String version = metadata.get(DiscoveryConstant.VERSION);
            String region = metadata.get(DiscoveryConstant.REGION);
            String environment = metadata.get(DiscoveryConstant.ENVIRONMENT);
            String zone = metadata.get(DiscoveryConstant.ZONE);
            String host = instance.getHost();
            int port = instance.getPort();

            InstanceEntity instanceEntity = new InstanceEntity();
            instanceEntity.setServiceType(serviceType);
            instanceEntity.setServiceId(serviceId);
            instanceEntity.setVersion(version);
            instanceEntity.setRegion(region);
            instanceEntity.setEnvironment(environment);
            instanceEntity.setZone(zone);
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionUtils.getStackTrace(e));
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionUtils.getStackTrace(e));
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionUtils.getStackTrace(e));
        }
    }

    private ResponseEntity<?> executeConfigUpdate(String serviceId, String config, boolean async) {
        List<ServiceInstance> instances = getInstances(serviceId);

        ConfigUpdateRestInvoker configUpdateRestInvoker = new ConfigUpdateRestInvoker(instances, consoleRestTemplate, config, async);

        return configUpdateRestInvoker.invoke();
    }

    private ResponseEntity<?> executeConfigClear(String serviceId, boolean async) {
        List<ServiceInstance> instances = getInstances(serviceId);

        ConfigClearRestInvoker configClearRestInvoker = new ConfigClearRestInvoker(instances, consoleRestTemplate, async);

        return configClearRestInvoker.invoke();
    }

    private ResponseEntity<?> executeVersionUpdate(String serviceId, String version, boolean async) {
        List<ServiceInstance> instances = getInstances(serviceId);

        VersionUpdateRestInvoker versionUpdateRestInvoker = new VersionUpdateRestInvoker(instances, consoleRestTemplate, version, async);

        return versionUpdateRestInvoker.invoke();
    }

    private ResponseEntity<?> executeVersionClear(String serviceId, String version, boolean async) {
        List<ServiceInstance> instances = getInstances(serviceId);

        VersionClearRestInvoker versionClearRestInvoker = new VersionClearRestInvoker(instances, consoleRestTemplate, version, async);

        return versionClearRestInvoker.invoke();
    }

    private ResponseEntity<?> executeSentinelUpdate(String serviceId, String ruleType, String rule) {
        List<ServiceInstance> instances = getInstances(serviceId);

        SentinelUpdateRestInvoker sentinelUpdateRestInvoker = new SentinelUpdateRestInvoker(instances, consoleRestTemplate, ruleType, rule);

        return sentinelUpdateRestInvoker.invoke();
    }

    private ResponseEntity<?> executeSentinelClear(String serviceId, String ruleType) {
        List<ServiceInstance> instances = getInstances(serviceId);

        SentinelClearRestInvoker sentinelClearRestInvoker = new SentinelClearRestInvoker(instances, consoleRestTemplate, ruleType);

        return sentinelClearRestInvoker.invoke();
    }
}
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

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.common.entity.AuthenticationEntity;
import com.nepxion.discovery.common.entity.InstanceEntity;
import com.nepxion.discovery.common.entity.ResultEntity;
import com.nepxion.discovery.common.entity.UserEntity;
import com.nepxion.discovery.common.util.ResponseUtil;
import com.nepxion.discovery.console.entity.GatewayType;
import com.nepxion.discovery.console.entity.SentinelRuleType;
import com.nepxion.discovery.console.resource.AuthenticationResource;
import com.nepxion.discovery.console.resource.ConfigResource;
import com.nepxion.discovery.console.resource.RouteResource;
import com.nepxion.discovery.console.resource.SentinelResource;
import com.nepxion.discovery.console.resource.ServiceResource;
import com.nepxion.discovery.console.resource.StrategyResource;
import com.nepxion.discovery.console.resource.VersionResource;

@RestController
@RequestMapping(path = "/console")
@Api(tags = { "控制台接口" })
public class ConsoleEndpoint {
    @Autowired
    private AuthenticationResource authenticationResource;

    @Autowired
    private ServiceResource serviceResource;

    @Autowired
    private ConfigResource configResource;

    @Autowired
    private VersionResource versionResource;

    @Autowired
    private SentinelResource sentinelResource;

    @Autowired
    private RouteResource routeResource;

    @Autowired
    private StrategyResource strategyResource;

    @RequestMapping(path = "/authenticate", method = RequestMethod.POST)
    @ApiOperation(value = "登录认证", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> authenticate(@RequestBody @ApiParam(value = "UserEntity实例", required = true) UserEntity userEntity) {
        return doAuthenticate(userEntity);
    }

    @RequestMapping(path = "/discovery-type", method = RequestMethod.GET)
    @ApiOperation(value = "获取注册发现中心类型", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> discoveryType() {
        return doDiscoveryType();
    }

    @RequestMapping(path = "/config-type", method = RequestMethod.GET)
    @ApiOperation(value = "获取配置中心类型", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> configType() {
        return doConfigType();
    }

    @RequestMapping(path = "/groups", method = RequestMethod.GET)
    @ApiOperation(value = "获取注册中心的服务组名列表", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> groups() {
        return doGroups();
    }

    @RequestMapping(path = "/group/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取注册中心的服务组名", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> group(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return doGroup(serviceId);
    }

    @RequestMapping(path = "/services", method = RequestMethod.GET)
    @ApiOperation(value = "获取注册中心的服务名列表", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> services() {
        return doServices();
    }

    @RequestMapping(path = "/gateways", method = RequestMethod.GET)
    @ApiOperation(value = "获取注册中心的网关名列表", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> gateways() {
        return doGateways();
    }

    @RequestMapping(path = "/gateway-list/{gatewayType}", method = RequestMethod.GET)
    @ApiOperation(value = "获取服务注册中心的网关名列表（根据网关类型）", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> gatewayList(@PathVariable(value = "gatewayType") @ApiParam(value = "网关类型。取值： spring-cloud-gateway | zuul", defaultValue = "spring-cloud-gateway", required = true) String gatewayType) {
        return doGatewayList(gatewayType);
    }

    @RequestMapping(path = "/instances/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取注册中心的服务实例列表", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> instances(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return doInstances(serviceId);
    }

    @RequestMapping(path = "/instance-list/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取注册中心的服务实例列表（精简数据）", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> instanceList(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return doInstanceList(serviceId);
    }

    @RequestMapping(path = "/instance-map", method = RequestMethod.POST)
    @ApiOperation(value = "获取注册中心的服务实例的Map（精简数据）", notes = "服务组名列表", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> instanceMap(@RequestBody @ApiParam(value = "服务组名列表，传入空列则可以获取全部服务实例数据", required = true) List<String> groups) {
        return doInstanceMap(groups);
    }

    @RequestMapping(path = "/remote-config/update/{group}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "更新规则配置到远程配置中心", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> remoteConfigUpdate(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名。当全局推送模式下，服务名必须由组名来代替", required = true) String serviceId, @RequestBody @ApiParam(value = "规则配置内容，XML格式", required = true) String config) {
        return doRemoteConfigUpdate(group, serviceId, config);
    }

    @RequestMapping(path = "/remote-config/clear/{group}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "清除规则配置到远程配置中心", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> remoteConfigClear(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名。当全局推送模式下，服务名必须由组名来代替", required = true) String serviceId) {
        return doRemoteConfigClear(group, serviceId);
    }

    @RequestMapping(path = "/remote-config/view/{group}/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "查看远程配置中心的规则配置", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> remoteConfigView(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名。当全局推送模式下，服务名必须由组名来代替", required = true) String serviceId) {
        return doRemoteConfigView(group, serviceId);
    }

    @RequestMapping(path = "/config/update-async/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量异步更新规则配置", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> configUpdateAsync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "规则配置内容，XML格式", required = true) String config) {
        return doConfigUpdate(serviceId, config, true);
    }

    @RequestMapping(path = "/config/update-sync/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量同步更新规则配置", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> configUpdateSync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "规则配置内容，XML格式", required = true) String config) {
        return doConfigUpdate(serviceId, config, false);
    }

    @RequestMapping(path = "/config/clear-async/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量异步清除规则配置", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> configClearAsync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return doConfigClear(serviceId, true);
    }

    @RequestMapping(path = "/config/clear-sync/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量同步清除规则配置", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> configClearSync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return doConfigClear(serviceId, false);
    }

    @RequestMapping(path = "/config/view/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "批量查看规则配置", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> configView(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return doConfigView(serviceId);
    }

    @RequestMapping(path = "/version/update-async/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量异步更新动态版本", notes = "根据指定的localVersion更新服务的dynamicVersion。如果输入的localVersion不匹配服务的localVersion，则忽略；如果如果输入的localVersion为空，则直接更新服务的dynamicVersion", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> versionUpdateAsync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "版本号，格式为${dynamicVersion}或者${dynamicVersion};${localVersion}", required = true) String version) {
        return doVersionUpdate(serviceId, version, true);
    }

    @RequestMapping(path = "/version/update-sync/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量同步更新动态版本", notes = "根据指定的localVersion更新服务的dynamicVersion。如果输入的localVersion不匹配服务的localVersion，则忽略；如果如果输入的localVersion为空，则直接更新服务的dynamicVersion", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> versionUpdateSync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "版本号，格式为${dynamicVersion}或者${dynamicVersion};${localVersion}", required = true) String version) {
        return doVersionUpdate(serviceId, version, false);
    }

    @RequestMapping(path = "/version/clear-async/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量异步清除动态版本", notes = "根据指定的localVersion清除服务的dynamicVersion。如果输入的localVersion不匹配服务的localVersion，则忽略；如果如果输入的localVersion为空，则直接清除服务的dynamicVersion", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> versionClearAsync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody(required = false) @ApiParam(value = "版本号，指localVersion，可以为空") String version) {
        return doVersionClear(serviceId, version, true);
    }

    @RequestMapping(path = "/version/clear-sync/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量同步清除动态版本", notes = "根据指定的localVersion清除服务的dynamicVersion。如果输入的localVersion不匹配服务的localVersion，则忽略；如果如果输入的localVersion为空，则直接清除服务的dynamicVersion", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> versionClearSync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody(required = false) @ApiParam(value = "版本号，指localVersion，可以为空") String version) {
        return doVersionClear(serviceId, version, false);
    }

    @RequestMapping(path = "/version/view/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "批量查看版本", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> versionView(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return doVersionView(serviceId);
    }

    @RequestMapping(path = "/sentinel/update/{ruleType}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量更新哨兵规则列表", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> sentinelUpdate(@PathVariable(value = "ruleType") @ApiParam(value = "哨兵规则类型。取值： flow | degrade | authority | system | param-flow", defaultValue = "flow", required = true) String ruleType, @PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "哨兵规则内容，JSON格式", required = true) String rule) {
        return doSentinelUpdate(ruleType, serviceId, rule);
    }

    @RequestMapping(path = "/sentinel/clear/{ruleType}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量清除哨兵规则列表", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> sentinelClear(@PathVariable(value = "ruleType") @ApiParam(value = "哨兵规则类型。取值： flow | degrade | authority | system | param-flow", defaultValue = "flow", required = true) String ruleType, @PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return doSentinelClear(ruleType, serviceId);
    }

    @RequestMapping(path = "/sentinel/view/{ruleType}/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "批量查看哨兵规则列表", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> sentinelView(@PathVariable(value = "ruleType") @ApiParam(value = "哨兵规则类型。取值： flow | degrade | authority | system | param-flow", defaultValue = "flow", required = true) String ruleType, @PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return doSentinelView(ruleType, serviceId);
    }

    @RequestMapping(path = "/route/add/{gatewayType}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量增加网关路由", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> gatewayRouteAdd(@PathVariable(value = "gatewayType") @ApiParam(value = "网关类型。取值： spring-cloud-gateway | zuul。spring-cloud-gateway指Spring Cloud Gateway, zuul指Netflix Zuul", defaultValue = "spring-cloud-gateway", required = true) String gatewayType, @PathVariable(value = "serviceId") @ApiParam(value = "网关服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "网关路由对象内容，JSON格式") String route) {
        return doRouteAdd(gatewayType, serviceId, route);
    }

    @RequestMapping(path = "/route/modify/{gatewayType}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量修改网关路由", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> gatewayRouteModify(@PathVariable(value = "gatewayType") @ApiParam(value = "网关类型。取值： spring-cloud-gateway | zuul。spring-cloud-gateway指Spring Cloud Gateway, zuul指Netflix Zuul", defaultValue = "spring-cloud-gateway", required = true) String gatewayType, @PathVariable(value = "serviceId") @ApiParam(value = "网关服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "网关路由对象内容，JSON格式") String route) {
        return doRouteModify(gatewayType, serviceId, route);
    }

    @RequestMapping(path = "/route/delete/{gatewayType}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量删除网关路由", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> gatewayRouteDelete(@PathVariable(value = "gatewayType") @ApiParam(value = "网关类型。取值： spring-cloud-gateway | zuul。spring-cloud-gateway指Spring Cloud Gateway, zuul指Netflix Zuul", defaultValue = "spring-cloud-gateway", required = true) String gatewayType, @PathVariable(value = "serviceId") @ApiParam(value = "网关服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "路由Id") String routeId) {
        return doRouteDelete(gatewayType, serviceId, routeId);
    }

    @RequestMapping(path = "/route/update-all/{gatewayType}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量更新全部网关路由", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> gatewayRouteUpdateAll(@PathVariable(value = "gatewayType") @ApiParam(value = "网关类型。取值： spring-cloud-gateway | zuul。spring-cloud-gateway指Spring Cloud Gateway, zuul指Netflix Zuul", defaultValue = "spring-cloud-gateway", required = true) String gatewayType, @PathVariable(value = "serviceId") @ApiParam(value = "网关服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "网关路由对象列表内容，JSON格式") String route) {
        return doRouteUpdateAll(gatewayType, serviceId, route);
    }

    @RequestMapping(path = "/route/view-all/{gatewayType}/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "批量查看全部网关路由", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> gatewayRouteViewAll(@PathVariable(value = "gatewayType") @ApiParam(value = "网关类型。取值： spring-cloud-gateway | zuul。spring-cloud-gateway指Spring Cloud Gateway, zuul指Netflix Zuul", defaultValue = "spring-cloud-gateway", required = true) String gatewayType, @PathVariable(value = "serviceId") @ApiParam(value = "网关服务名", required = true) String serviceId) {
        return doRouteViewAll(gatewayType, serviceId);
    }

    @RequestMapping(path = "/validate-expression", method = RequestMethod.GET)
    @ApiOperation(value = "校验策略的条件表达式", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> validateExpression(@RequestParam @ApiParam(value = "条件表达式，格式示例：#H['a'] == '1' && #H['b'] != '2'。注意，引号是否为中文格式", defaultValue = "#H['a'] == '1' && #H['b'] != '2'", required = true) String expression, @RequestParam(defaultValue = "", required = false) @ApiParam(value = "校验参数，格式示例：a=1;b=1。如果多个用“;”分隔，不允许出现空格。允许为空", defaultValue = "a=1;b=1") String validation) {
        return doValidateExpression(expression, validation);
    }

    private ResponseEntity<?> doAuthenticate(UserEntity userEntity) {
        try {
            AuthenticationEntity authenticationEntity = authenticationResource.authenticate(userEntity);

            return ResponseUtil.getSuccessResponse(authenticationEntity);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doDiscoveryType() {
        try {
            String discoveryType = serviceResource.getDiscoveryType();

            return ResponseUtil.getSuccessResponse(discoveryType);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doConfigType() {
        try {
            String configType = configResource.getConfigType();

            return ResponseUtil.getSuccessResponse(configType);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doGroups() {
        try {
            List<String> groups = serviceResource.getGroups();

            return ResponseUtil.getSuccessResponse(groups);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doGroup(String serviceId) {
        try {
            String group = serviceResource.getGroup(serviceId);

            return ResponseUtil.getSuccessResponse(group);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doServices() {
        try {
            List<String> services = serviceResource.getServices();

            return ResponseUtil.getSuccessResponse(services);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doGateways() {
        try {
            List<String> gateways = serviceResource.getGateways();

            return ResponseUtil.getSuccessResponse(gateways);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doGatewayList(String gatewayType) {
        try {
            List<String> gatewayList = serviceResource.getGatewayList(GatewayType.fromString(gatewayType));

            return ResponseUtil.getSuccessResponse(gatewayList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doInstances(String serviceId) {
        try {
            List<ServiceInstance> instances = serviceResource.getInstances(serviceId);

            return ResponseUtil.getSuccessResponse(instances);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doInstanceList(String serviceId) {
        try {
            List<InstanceEntity> instanceList = serviceResource.getInstanceList(serviceId);

            return ResponseUtil.getSuccessResponse(instanceList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doInstanceMap(List<String> groups) {
        try {
            Map<String, List<InstanceEntity>> instanceMaps = serviceResource.getInstanceMap(groups);

            return ResponseUtil.getSuccessResponse(instanceMaps);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doRemoteConfigUpdate(String group, String serviceId, String config) {
        try {
            boolean result = configResource.updateRemoteConfig(group, serviceId, config);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doRemoteConfigClear(String group, String serviceId) {
        try {
            boolean result = configResource.clearRemoteConfig(group, serviceId);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doRemoteConfigView(String group, String serviceId) {
        try {
            String config = configResource.getRemoteConfig(group, serviceId);

            return ResponseUtil.getSuccessResponse(config);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doConfigUpdate(String serviceId, String config, boolean async) {
        try {
            List<ResultEntity> resultEntityList = configResource.updateConfig(serviceId, config, async);

            return ResponseUtil.getSuccessResponse(resultEntityList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doConfigClear(String serviceId, boolean async) {
        try {
            List<ResultEntity> resultEntityList = configResource.clearConfig(serviceId, async);

            return ResponseUtil.getSuccessResponse(resultEntityList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doConfigView(String serviceId) {
        try {
            List<ResultEntity> resultEntityList = configResource.viewConfig(serviceId);

            return ResponseUtil.getSuccessResponse(resultEntityList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doVersionUpdate(String serviceId, String version, boolean async) {
        try {
            List<ResultEntity> resultEntityList = versionResource.updateVersion(serviceId, version, async);

            return ResponseUtil.getSuccessResponse(resultEntityList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doVersionClear(String serviceId, String version, boolean async) {
        try {
            List<ResultEntity> resultEntityList = versionResource.clearVersion(serviceId, version, async);

            return ResponseUtil.getSuccessResponse(resultEntityList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doVersionView(String serviceId) {
        try {
            List<ResultEntity> resultEntityList = versionResource.viewVersion(serviceId);

            return ResponseUtil.getSuccessResponse(resultEntityList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doSentinelUpdate(String ruleType, String serviceId, String rule) {
        try {
            List<ResultEntity> resultEntityList = sentinelResource.updateSentinel(SentinelRuleType.fromString(ruleType), serviceId, rule);

            return ResponseUtil.getSuccessResponse(resultEntityList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doSentinelClear(String ruleType, String serviceId) {
        try {
            List<ResultEntity> resultEntityList = sentinelResource.clearSentinel(SentinelRuleType.fromString(ruleType), serviceId);

            return ResponseUtil.getSuccessResponse(resultEntityList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doSentinelView(String ruleType, String serviceId) {
        try {
            List<ResultEntity> resultEntityList = sentinelResource.viewSentinel(SentinelRuleType.fromString(ruleType), serviceId);

            return ResponseUtil.getSuccessResponse(resultEntityList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doRouteAdd(String gatewayType, String serviceId, String route) {
        try {
            List<ResultEntity> resultEntityList = routeResource.addRoute(GatewayType.fromString(gatewayType), serviceId, route);

            return ResponseUtil.getSuccessResponse(resultEntityList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doRouteModify(String gatewayType, String serviceId, String route) {
        try {
            List<ResultEntity> resultEntityList = routeResource.modifyRoute(GatewayType.fromString(gatewayType), serviceId, route);

            return ResponseUtil.getSuccessResponse(resultEntityList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doRouteDelete(String gatewayType, String serviceId, String routeId) {
        try {
            List<ResultEntity> resultEntityList = routeResource.deleteRoute(GatewayType.fromString(gatewayType), serviceId, routeId);

            return ResponseUtil.getSuccessResponse(resultEntityList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doRouteUpdateAll(String gatewayType, String serviceId, String route) {
        try {
            List<ResultEntity> resultEntityList = routeResource.updateAllRoute(GatewayType.fromString(gatewayType), serviceId, route);

            return ResponseUtil.getSuccessResponse(resultEntityList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doRouteViewAll(String gatewayType, String serviceId) {
        try {
            List<ResultEntity> resultEntityList = routeResource.viewAllRoute(GatewayType.fromString(gatewayType), serviceId);

            return ResponseUtil.getSuccessResponse(resultEntityList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doValidateExpression(String expression, String validation) {
        try {
            boolean result = strategyResource.validateExpression(expression, validation);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }
}
package com.nepxion.discovery.console.endpoint;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Ning Zhang
 * @author Haojun Ren
 * @version 1.0
 */

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.common.entity.GatewayType;
import com.nepxion.discovery.common.entity.ResultEntity;
import com.nepxion.discovery.common.util.ResponseUtil;
import com.nepxion.discovery.console.resource.RouteResource;

@RestController
@RequestMapping(path = "/route")
@Api(tags = { "网关动态路由接口" })
public class RouteEndpoint {
    @Autowired
    private RouteResource routeResource;

    @RequestMapping(path = "/remote/update/{gatewayType}/{group}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "更新网关路由到远程配置中心", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> remoteRouteUpdate(@PathVariable(value = "gatewayType") @ApiParam(value = "网关类型。取值： spring-cloud-gateway | zuul。spring-cloud-gateway指Spring Cloud Gateway, zuul指Netflix Zuul", defaultValue = "spring-cloud-gateway", required = true) String gatewayType, @PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "网关服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "网关路由对象内容，Json格式", required = true) String route) {
        return doRemoteRouteUpdate(gatewayType, group, serviceId, route);
    }

    @RequestMapping(path = "/remote/clear/{gatewayType}/{group}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "清除网关路由到远程配置中心", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> remoteRouteClear(@PathVariable(value = "gatewayType") @ApiParam(value = "网关类型。取值： spring-cloud-gateway | zuul。spring-cloud-gateway指Spring Cloud Gateway, zuul指Netflix Zuul", defaultValue = "spring-cloud-gateway", required = true) String gatewayType, @PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "网关服务名", required = true) String serviceId) {
        return doRemoteRouteClear(gatewayType, group, serviceId);
    }

    @RequestMapping(path = "/remote/view/{gatewayType}/{group}/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "查看远程配置中心的网关路由", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> remoteSentinelView(@PathVariable(value = "gatewayType") @ApiParam(value = "网关类型。取值： spring-cloud-gateway | zuul。spring-cloud-gateway指Spring Cloud Gateway, zuul指Netflix Zuul", defaultValue = "spring-cloud-gateway", required = true) String gatewayType, @PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "网关服务名", required = true) String serviceId) {
        return doRemoteRouteView(gatewayType, group, serviceId);
    }

    @RequestMapping(path = "/add/{gatewayType}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量增加网关路由", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> routeAdd(@PathVariable(value = "gatewayType") @ApiParam(value = "网关类型。取值： spring-cloud-gateway | zuul。spring-cloud-gateway指Spring Cloud Gateway, zuul指Netflix Zuul", defaultValue = "spring-cloud-gateway", required = true) String gatewayType, @PathVariable(value = "serviceId") @ApiParam(value = "网关服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "网关路由对象内容，Json格式", required = true) String route) {
        return doRouteAdd(gatewayType, serviceId, route);
    }

    @RequestMapping(path = "/modify/{gatewayType}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量修改网关路由", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> routeModify(@PathVariable(value = "gatewayType") @ApiParam(value = "网关类型。取值： spring-cloud-gateway | zuul。spring-cloud-gateway指Spring Cloud Gateway, zuul指Netflix Zuul", defaultValue = "spring-cloud-gateway", required = true) String gatewayType, @PathVariable(value = "serviceId") @ApiParam(value = "网关服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "网关路由对象内容，Json格式", required = true) String route) {
        return doRouteModify(gatewayType, serviceId, route);
    }

    @RequestMapping(path = "/delete/{gatewayType}/{serviceId}/{routeId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量删除网关路由", notes = "", response = ResponseEntity.class, httpMethod = "DELETE")
    @ResponseBody
    public ResponseEntity<?> routeDelete(@PathVariable(value = "gatewayType") @ApiParam(value = "网关类型。取值： spring-cloud-gateway | zuul。spring-cloud-gateway指Spring Cloud Gateway, zuul指Netflix Zuul", defaultValue = "spring-cloud-gateway", required = true) String gatewayType, @PathVariable(value = "serviceId") @ApiParam(value = "网关服务名", required = true) String serviceId, @PathVariable(value = "routeId") @ApiParam(value = "路由Id", required = true) String routeId) {
        return doRouteDelete(gatewayType, serviceId, routeId);
    }

    @RequestMapping(path = "/update-all/{gatewayType}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量更新全部网关路由", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> routeUpdateAll(@PathVariable(value = "gatewayType") @ApiParam(value = "网关类型。取值： spring-cloud-gateway | zuul。spring-cloud-gateway指Spring Cloud Gateway, zuul指Netflix Zuul", defaultValue = "spring-cloud-gateway", required = true) String gatewayType, @PathVariable(value = "serviceId") @ApiParam(value = "网关服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "网关路由对象列表内容，Json格式", required = true) String route) {
        return doRouteUpdateAll(gatewayType, serviceId, route);
    }

    @RequestMapping(path = "/view-all/{gatewayType}/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "批量查看全部网关路由", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> routeViewAll(@PathVariable(value = "gatewayType") @ApiParam(value = "网关类型。取值： spring-cloud-gateway | zuul。spring-cloud-gateway指Spring Cloud Gateway, zuul指Netflix Zuul", defaultValue = "spring-cloud-gateway", required = true) String gatewayType, @PathVariable(value = "serviceId") @ApiParam(value = "网关服务名", required = true) String serviceId) {
        return doRouteViewAll(gatewayType, serviceId);
    }

    private ResponseEntity<?> doRemoteRouteUpdate(String gatewayType, String group, String serviceId, String rule) {
        try {
            boolean result = routeResource.updateRemoteRoute(GatewayType.fromString(gatewayType), group, serviceId, rule);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doRemoteRouteClear(String gatewayType, String group, String serviceId) {
        try {
            boolean result = routeResource.clearRemoteRoute(GatewayType.fromString(gatewayType), group, serviceId);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doRemoteRouteView(String gatewayType, String group, String serviceId) {
        try {
            String result = routeResource.getRemoteRoute(GatewayType.fromString(gatewayType), group, serviceId);

            return ResponseUtil.getSuccessResponse(result);
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
}
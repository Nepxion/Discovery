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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.common.entity.RouterEntity;
import com.nepxion.discovery.common.util.ResponseUtil;
import com.nepxion.discovery.plugin.admincenter.resource.RouterResource;

@RestController
@RequestMapping(path = "/router")
@Api(tags = { "路由接口" })
public class RouterEndpoint {
    @Autowired
    private RouterResource routerResource;

    @RequestMapping(path = "/info", method = RequestMethod.GET)
    @ApiOperation(value = "获取本地节点信息", notes = "获取当前节点的简单信息", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> info() {
        return doInfo();
    }

    @RequestMapping(path = "/route/{routeServiceId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取本地节点可访问其他节点的路由信息列表", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> route(@PathVariable(value = "routeServiceId") @ApiParam(value = "目标服务名", required = true) String routeServiceId) {
        return doNativeRoute(routeServiceId);
    }

    @RequestMapping(path = "/route/{routeServiceId}/{routeProtocol}/{routeHost}/{routePort}/{routeContextPath}", method = RequestMethod.GET)
    @ApiOperation(value = "获取指定节点可访问其他节点的路由信息列表", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> route(@PathVariable(value = "routeServiceId") @ApiParam(value = "目标服务名", required = true) String routeServiceId, @PathVariable(value = "routeProtocol") @ApiParam(value = "目标服务采用的协议。取值： http | https", defaultValue = "http", required = true) String routeProtocol, @PathVariable(value = "routeHost") @ApiParam(value = "目标服务所在机器的IP地址", required = true) String routeHost, @PathVariable(value = "routePort") @ApiParam(value = "目标服务所在机器的端口号", required = true) int routePort, @PathVariable(value = "routeContextPath") @ApiParam(value = "目标服务的调用路径前缀", defaultValue = "/", required = true) String routeContextPath) {
        return doRemoteRoute(routeServiceId, routeProtocol, routeHost, routePort, routeContextPath);
    }

    @RequestMapping(path = "/routes", method = RequestMethod.POST)
    @ApiOperation(value = "获取全路径的路由信息树", notes = "参数按调用服务名的前后次序排列，起始节点的服务名不能加上去。如果多个用“;”分隔，不允许出现空格", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> routes(@RequestBody @ApiParam(value = "格式示例：service-a;service-b", required = true) String routeServiceIds) {
        return doRoutes(routeServiceIds);
    }

    private ResponseEntity<?> doInfo() {
        try {
            RouterEntity routerEntity = routerResource.getRouterEntity();

            return ResponseUtil.getSuccessResponse(routerEntity);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doNativeRoute(String routeServiceId) {
        try {
            List<RouterEntity> routerEntityList = routerResource.getRouterEntityList(routeServiceId);

            return ResponseUtil.getSuccessResponse(routerEntityList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doRemoteRoute(String routeServiceId, String routeProtocol, String routeHost, int routePort, String routeContextPath) {
        try {
            List<RouterEntity> routerEntityList = routerResource.getRouterEntityList(routeServiceId, routeProtocol, routeHost, routePort, routeContextPath);

            return ResponseUtil.getSuccessResponse(routerEntityList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doRoutes(String routeServiceIds) {
        try {
            RouterEntity routerEntity = routerResource.routeTree(routeServiceIds);

            return ResponseUtil.getSuccessResponse(routerEntity);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }
}
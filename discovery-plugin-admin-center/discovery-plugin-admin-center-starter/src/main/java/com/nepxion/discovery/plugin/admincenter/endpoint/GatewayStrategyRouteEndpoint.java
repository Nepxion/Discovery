package com.nepxion.discovery.plugin.admincenter.endpoint;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Ning Zhang
 * @version 1.0
 */

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.util.ExceptionUtil;
import com.nepxion.discovery.plugin.strategy.gateway.entity.GatewayStrategyRouteEntity;
import com.nepxion.discovery.plugin.strategy.gateway.route.GatewayStrategyRoute;

@RestController
@RequestMapping(path = "/gateway-route")
@Api(tags = { "网关动态路由接口" })
public class GatewayStrategyRouteEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(GatewayStrategyRouteEndpoint.class);

    @Autowired
    private GatewayStrategyRoute gatewayStrategyRoute;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "增加网关路由", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> add(@RequestBody @ApiParam(value = "网关路由对象", required = true) GatewayStrategyRouteEntity gatewayStrategyRouteEntity) {
        try {
            gatewayStrategyRoute.add(gatewayStrategyRouteEntity);
        } catch (Exception e) {
            LOG.error("Add Gateway dynamic route failed", e);

            return ExceptionUtil.getExceptionResponseEntity(e, false);
        }

        return ResponseEntity.ok().body(DiscoveryConstant.OK);
    }

    @RequestMapping(path = "/modify", method = RequestMethod.POST)
    @ApiOperation(value = "修改网关路由", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> modify(@RequestBody @ApiParam(value = "网关路由对象", required = true) GatewayStrategyRouteEntity gatewayStrategyRouteEntity) {
        try {
            gatewayStrategyRoute.modify(gatewayStrategyRouteEntity);
        } catch (Exception e) {
            LOG.error("Modify Gateway dynamic route failed", e);

            return ExceptionUtil.getExceptionResponseEntity(e, false);
        }

        return ResponseEntity.ok().body(DiscoveryConstant.OK);
    }

    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除网关路由", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> delete(@RequestBody @ApiParam(value = "路由ID", required = true) String routeId) {
        try {
            gatewayStrategyRoute.delete(routeId);
        } catch (Exception e) {
            LOG.error("Delete Gateway dynamic route by routeId failed", e);

            return ExceptionUtil.getExceptionResponseEntity(e, false);
        }

        return ResponseEntity.ok().body(DiscoveryConstant.OK);
    }

    @RequestMapping(path = "/update-all", method = RequestMethod.POST)
    @ApiOperation(value = "更新全部网关路由", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> updateAll(@RequestBody @ApiParam(value = "网关路由对象列表", required = true) List<GatewayStrategyRouteEntity> gatewayStrategyRouteEntityList) {
        try {
            gatewayStrategyRoute.updateAll(gatewayStrategyRouteEntityList);
        } catch (Exception e) {
            LOG.error("Update all Gateway dynamic routes failed", e);

            return ExceptionUtil.getExceptionResponseEntity(e, false);
        }

        return ResponseEntity.ok().body(DiscoveryConstant.OK);
    }

    @RequestMapping(path = "/view", method = RequestMethod.POST)
    @ApiOperation(value = "根据路由ID查看网关路由", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> view(@RequestBody @ApiParam(value = "路由ID", required = true) String routeId) {
        GatewayStrategyRouteEntity gatewayStrategyRouteEntity = null;
        try {
            gatewayStrategyRouteEntity = gatewayStrategyRoute.view(routeId);
        } catch (Exception e) {
            LOG.error("View Gateway dynamic routes by routeId failed", e);

            return ExceptionUtil.getExceptionResponseEntity(e, false);
        }

        return ResponseEntity.ok().body(gatewayStrategyRouteEntity);
    }

    @RequestMapping(path = "/view-all", method = RequestMethod.POST)
    @ApiOperation(value = "查看全部网关路由", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> viewAll() {
        List<GatewayStrategyRouteEntity> gatewayStrategyRouteEntityList = null;
        try {
            gatewayStrategyRouteEntityList = gatewayStrategyRoute.viewAll();
        } catch (Exception e) {
            LOG.error("View all Gateway dynamic routes failed", e);

            return ExceptionUtil.getExceptionResponseEntity(e, false);
        }

        return ResponseEntity.ok().body(gatewayStrategyRouteEntityList);
    }
}
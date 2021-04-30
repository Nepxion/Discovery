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

import com.nepxion.discovery.common.util.ExceptionUtil;
import com.nepxion.discovery.plugin.strategy.zuul.entity.ZuulStrategyRouteEntity;
import com.nepxion.discovery.plugin.strategy.zuul.route.ZuulStrategyRoute;

@RestController
@RequestMapping(path = "/zuul-route")
@Api(tags = { "网关动态路由接口" })
public class ZuulStrategyRouteEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(ZuulStrategyRouteEndpoint.class);

    @Autowired
    private ZuulStrategyRoute zuulStrategyRoute;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "增加单个网关路由", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> add(@RequestBody @ApiParam(value = "网关路由对象", required = true) ZuulStrategyRouteEntity zuulStrategyRouteEntity) {
        try {
            zuulStrategyRoute.add(zuulStrategyRouteEntity);
        } catch (Exception e) {
            LOG.error("Add Zuul dynamic route failed", e);

            return ExceptionUtil.getExceptionResponseEntity(e, false);
        }

        return ResponseEntity.ok(true);
    }

    @RequestMapping(path = "/modify", method = RequestMethod.POST)
    @ApiOperation(value = "修改单个网关路由", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> modify(@RequestBody @ApiParam(value = "网关路由对象列表，只允许包含2个动态路由对象，第一个为旧对象，第二个为新对象", required = true) List<ZuulStrategyRouteEntity> zuulStrategyRouteEntityList) {
        try {
            zuulStrategyRoute.modify(zuulStrategyRouteEntityList);
        } catch (Exception e) {
            LOG.error("Modify Zuul dynamic route failed", e);

            return ExceptionUtil.getExceptionResponseEntity(e, false);
        }

        return ResponseEntity.ok(true);
    }

    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除单个网关路由", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> delete(@RequestBody @ApiParam(value = "网关路由路径", required = true) String path) {
        try {
            zuulStrategyRoute.delete(path);
        } catch (Exception e) {
            LOG.error("Delete Zuul dynamic route failed", e);

            return ExceptionUtil.getExceptionResponseEntity(e, false);
        }

        return ResponseEntity.ok(true);
    }

    @RequestMapping(path = "/delete-all", method = RequestMethod.POST)
    @ApiOperation(value = "根据服务名删除全部网关路由", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> deleteAll(@RequestBody @ApiParam(value = "服务名", required = true) String serviceId) {
        try {
            zuulStrategyRoute.deleteAll(serviceId);
        } catch (Exception e) {
            LOG.error("Delete Zuul dynamic routes by serviceId failed", e);

            return ExceptionUtil.getExceptionResponseEntity(e, false);
        }

        return ResponseEntity.ok(true);
    }

    @RequestMapping(path = "/view", method = RequestMethod.POST)
    @ApiOperation(value = "根据服务名查看全部网关路由", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> view(@RequestBody @ApiParam(value = "服务名", required = true) String serviceId) {
        List<String> zuulStrategyRouteList = null;
        try {
            zuulStrategyRouteList = zuulStrategyRoute.view(serviceId);
        } catch (Exception e) {
            LOG.error("View Zuul dynamic routes by serviecId failed", e);

            return ExceptionUtil.getExceptionResponseEntity(e, false);
        }

        return ResponseEntity.ok(zuulStrategyRouteList);
    }

    @RequestMapping(path = "/view-all", method = RequestMethod.POST)
    @ApiOperation(value = "查看网关全部网关路由", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> viewAll() {
        List<String> zuulStrategyRouteList = null;
        try {
            zuulStrategyRouteList = zuulStrategyRoute.viewAll();
        } catch (Exception e) {
            LOG.error("View all Zuul dynamic routes failed", e);

            return ExceptionUtil.getExceptionResponseEntity(e, false);
        }

        return ResponseEntity.ok(zuulStrategyRouteList);
    }
}
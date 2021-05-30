package com.nepxion.discovery.plugin.admincenter.endpoint;

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

import com.nepxion.discovery.common.entity.ZuulStrategyRouteEntity;
import com.nepxion.discovery.common.util.ResponseUtil;
import com.nepxion.discovery.plugin.admincenter.resource.ZuulStrategyRouteResource;

@RestController
@RequestMapping(path = "/zuul-route")
@Api(tags = { "网关动态路由接口" })
public class ZuulStrategyRouteEndpoint {
    @Autowired
    private ZuulStrategyRouteResource zuulStrategyRouteResource;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "增加网关路由", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> add(@RequestBody @ApiParam(value = "网关路由对象", required = true) ZuulStrategyRouteEntity zuulStrategyRouteEntity) {
        return doAdd(zuulStrategyRouteEntity);
    }

    @RequestMapping(path = "/modify", method = RequestMethod.POST)
    @ApiOperation(value = "修改网关路由", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> modify(@RequestBody @ApiParam(value = "网关路由对象", required = true) ZuulStrategyRouteEntity zuulStrategyRouteEntity) {
        return doModify(zuulStrategyRouteEntity);
    }

    @RequestMapping(path = "/delete/{routeId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除网关路由", notes = "", response = ResponseEntity.class, httpMethod = "DELETE")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable(value = "routeId") @ApiParam(value = "路由Id", required = true) String routeId) {
        return doDelete(routeId);
    }

    @RequestMapping(path = "/update-all", method = RequestMethod.POST)
    @ApiOperation(value = "更新全部网关路由", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> updateAll(@RequestBody @ApiParam(value = "网关路由对象列表", required = true) List<ZuulStrategyRouteEntity> zuulStrategyRouteEntityList) {
        return doUpdateAll(zuulStrategyRouteEntityList);
    }

    @RequestMapping(path = "/view/{routeId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据路由Id查看网关路由", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> view(@PathVariable(value = "routeId") @ApiParam(value = "路由Id", required = true) String routeId) {
        return doView(routeId);
    }

    @RequestMapping(path = "/view-all", method = RequestMethod.GET)
    @ApiOperation(value = "查看全部网关路由", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> viewAll() {
        return doViewAll();
    }

    private ResponseEntity<?> doAdd(ZuulStrategyRouteEntity zuulStrategyRouteEntity) {
        try {
            zuulStrategyRouteResource.add(zuulStrategyRouteEntity);

            return ResponseUtil.getSuccessResponse(true);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doModify(ZuulStrategyRouteEntity zuulStrategyRouteEntity) {
        try {
            zuulStrategyRouteResource.modify(zuulStrategyRouteEntity);

            return ResponseUtil.getSuccessResponse(true);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doDelete(String routeId) {
        try {
            zuulStrategyRouteResource.delete(routeId);

            return ResponseUtil.getSuccessResponse(true);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doUpdateAll(List<ZuulStrategyRouteEntity> zuulStrategyRouteEntityList) {
        try {
            zuulStrategyRouteResource.updateAll(zuulStrategyRouteEntityList);

            return ResponseUtil.getSuccessResponse(true);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doView(String routeId) {
        try {
            ZuulStrategyRouteEntity zuulStrategyRouteEntity = zuulStrategyRouteResource.view(routeId);

            return ResponseUtil.getSuccessResponse(zuulStrategyRouteEntity);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doViewAll() {
        try {
            List<ZuulStrategyRouteEntity> zuulStrategyRouteEntityList = zuulStrategyRouteResource.viewAll();

            return ResponseUtil.getSuccessResponse(zuulStrategyRouteEntityList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }
}
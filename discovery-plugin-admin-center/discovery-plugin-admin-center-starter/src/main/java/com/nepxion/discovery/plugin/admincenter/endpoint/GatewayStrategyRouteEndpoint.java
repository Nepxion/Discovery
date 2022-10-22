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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.common.entity.GatewayStrategyRouteEntity;
import com.nepxion.discovery.common.util.ResponseUtil;
import com.nepxion.discovery.plugin.admincenter.resource.GatewayStrategyRouteResource;

@RestController
@RequestMapping(path = "/spring-cloud-gateway-route")
public class GatewayStrategyRouteEndpoint {
    @Autowired
    private GatewayStrategyRouteResource gatewayStrategyRouteResource;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> add(@RequestBody GatewayStrategyRouteEntity gatewayStrategyRouteEntity) {
        return doAdd(gatewayStrategyRouteEntity);
    }

    @RequestMapping(path = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> modify(@RequestBody GatewayStrategyRouteEntity gatewayStrategyRouteEntity) {
        return doModify(gatewayStrategyRouteEntity);
    }

    @RequestMapping(path = "/delete/{routeId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable(value = "routeId") String routeId) {
        return doDelete(routeId);
    }

    @RequestMapping(path = "/update-all", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> updateAll(@RequestBody List<GatewayStrategyRouteEntity> gatewayStrategyRouteEntityList) {
        return doUpdateAll(gatewayStrategyRouteEntityList);
    }

    @RequestMapping(path = "/view/{routeId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> view(@PathVariable(value = "routeId") String routeId) {
        return doView(routeId);
    }

    @RequestMapping(path = "/view-all", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> viewAll() {
        return doViewAll();
    }

    private ResponseEntity<?> doAdd(GatewayStrategyRouteEntity gatewayStrategyRouteEntity) {
        try {
            gatewayStrategyRouteResource.add(gatewayStrategyRouteEntity);

            return ResponseUtil.getSuccessResponse(true);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doModify(GatewayStrategyRouteEntity gatewayStrategyRouteEntity) {
        try {
            gatewayStrategyRouteResource.modify(gatewayStrategyRouteEntity);

            return ResponseUtil.getSuccessResponse(true);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doDelete(String routeId) {
        try {
            gatewayStrategyRouteResource.delete(routeId);

            return ResponseUtil.getSuccessResponse(true);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doUpdateAll(List<GatewayStrategyRouteEntity> gatewayStrategyRouteEntityList) {
        try {
            gatewayStrategyRouteResource.updateAll(gatewayStrategyRouteEntityList);

            return ResponseUtil.getSuccessResponse(true);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doView(String routeId) {
        try {
            GatewayStrategyRouteEntity gatewayStrategyRouteEntity = gatewayStrategyRouteResource.view(routeId);

            return ResponseUtil.getSuccessResponse(gatewayStrategyRouteEntity);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doViewAll() {
        try {
            List<GatewayStrategyRouteEntity> gatewayStrategyRouteEntityList = gatewayStrategyRouteResource.viewAll();

            return ResponseUtil.getSuccessResponse(gatewayStrategyRouteEntityList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }
}
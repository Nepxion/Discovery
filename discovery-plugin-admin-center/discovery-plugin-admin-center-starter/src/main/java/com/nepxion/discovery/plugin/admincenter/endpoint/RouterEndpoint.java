package com.nepxion.discovery.plugin.admincenter.endpoint;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
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

import com.nepxion.discovery.common.entity.RouterEntity;
import com.nepxion.discovery.common.util.ResponseUtil;
import com.nepxion.discovery.plugin.admincenter.resource.RouterResource;

@RestController
@RequestMapping(path = "/router")
public class RouterEndpoint {
    @Autowired
    private RouterResource routerResource;

    @RequestMapping(path = "/info", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> info() {
        return doInfo();
    }

    @RequestMapping(path = "/route/{routeServiceId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> route(@PathVariable(value = "routeServiceId") String routeServiceId) {
        return doNativeRoute(routeServiceId);
    }

    @RequestMapping(path = "/route/{routeServiceId}/{routeProtocol}/{routeHost}/{routePort}/{routeContextPath}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> route(@PathVariable(value = "routeServiceId") String routeServiceId, @PathVariable(value = "routeProtocol") String routeProtocol, @PathVariable(value = "routeHost") String routeHost, @PathVariable(value = "routePort") int routePort, @PathVariable(value = "routeContextPath") String routeContextPath) {
        return doRemoteRoute(routeServiceId, routeProtocol, routeHost, routePort, routeContextPath);
    }

    @RequestMapping(path = "/routes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> routes(@RequestBody String routeServiceIds) {
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
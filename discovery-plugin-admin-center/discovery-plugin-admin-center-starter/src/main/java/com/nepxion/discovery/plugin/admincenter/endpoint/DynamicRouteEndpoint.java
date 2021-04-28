package com.nepxion.discovery.plugin.admincenter.endpoint;

import com.nepxion.discovery.common.entity.RouteEntity;
import com.nepxion.discovery.plugin.framework.adapter.DynamicRouteAdapter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 *
 * @author Ning Zhang
 * @version 1.0
 */
@RestController
@RequestMapping(path = "/gateway-dynamic-route")
@Api(tags = {"网关动态路由接口"})
public class DynamicRouteEndpoint {
    @Autowired(required = false)
    private DynamicRouteAdapter dynamicRouteAdapter;

    @ApiOperation(value = "更新网关当前的路由", response = ResponseEntity.class, httpMethod = "POST")
    @PostMapping("/update")
    public synchronized ResponseEntity<Boolean> update(@RequestBody final ArrayList<RouteEntity> routeEntityList) {
        if (dynamicRouteAdapter == null) {
            return ResponseEntity.ok(false);
        }
        dynamicRouteAdapter.update(routeEntityList);
        return ResponseEntity.ok(true);
    }

    @ApiOperation(value = "获取网关当前已生效的路由信息", response = ResponseEntity.class, httpMethod = "POST")
    @PostMapping(value = "/view")
    public ResponseEntity<List<String>> view() {
        if (dynamicRouteAdapter == null) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(dynamicRouteAdapter.view());
    }
}
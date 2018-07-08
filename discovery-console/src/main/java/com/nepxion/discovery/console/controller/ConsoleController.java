package com.nepxion.discovery.console.controller;

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
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = { "控制台接口" })
public class ConsoleController {
    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(path = "/services", method = RequestMethod.GET)
    @ApiOperation(value = "获取服务注册中心所有服务列表", notes = "", response = List.class, httpMethod = "GET")
    public List<String> services() {
        return getServices();
    }

    @RequestMapping(path = "/instances/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取服务注册中心服务的实例列表", notes = "", response = List.class, httpMethod = "GET")
    public List<ServiceInstance> instances(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return getInstanceList(serviceId);
    }

    public List<String> getServices() {
        return discoveryClient.getServices();
    }

    public List<ServiceInstance> getInstanceList(String serviceId) {
        return discoveryClient.getInstances(serviceId);
    }
}
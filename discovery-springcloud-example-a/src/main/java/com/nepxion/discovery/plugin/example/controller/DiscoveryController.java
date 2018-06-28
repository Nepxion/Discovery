package com.nepxion.discovery.plugin.example.controller;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.plugin.framework.constant.PluginConstant;

@RestController
public class DiscoveryController {
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${" + PluginConstant.SPRING_APPLICATION_NAME + "}")
    private String serviceId;

    @Value("${" + PluginConstant.EUREKA_METADATA_VERSION + "}")
    private String eurekaVersion;

    @RequestMapping(path = "/instances", method = RequestMethod.GET)
    public List<ServiceInstance> instances() {
        return discoveryClient.getInstances("discovery-springcloud-example-b");
    }

    @RequestMapping(path = "/routes", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public List<String> routes() {
        List<String> routes = new ArrayList<String>();

        // 获取B服务的实例列表
        List<ServiceInstance> bInstances = instances();

        // 获取C服务的实例列表
        List<ServiceInstance> cInstances = (List<ServiceInstance>) restTemplate.getForEntity("http://discovery-springcloud-example-b/instances", List.class);

        for (ServiceInstance bInstance : bInstances) {
            String aInfo = serviceId + "[" + eurekaVersion + "]";
            String bInfo = bInstance.getServiceId() + "[" + bInstance.getMetadata().get(PluginConstant.EUREKA_METADATA_VERSION) + "]";
            for (ServiceInstance cInstance : cInstances) {
                String cInfo = cInstance.getServiceId() + "[" + cInstance.getMetadata().get(PluginConstant.EUREKA_METADATA_VERSION) + "]";
                StringBuilder stringBuilder = new StringBuilder();
                routes.add(stringBuilder.append(aInfo).append("->").append(bInfo).append("->").append(cInfo).toString());
            }
        }

        return routes;
    }
}
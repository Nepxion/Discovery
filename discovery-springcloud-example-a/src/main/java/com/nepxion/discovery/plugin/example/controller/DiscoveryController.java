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
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
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
    private String aServiceId;

    @Value("${" + PluginConstant.EUREKA_METADATA_VERSION + "}")
    private String aEurekaVersion;

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

        String aInfo = aServiceId.toLowerCase() + "[" + aEurekaVersion + "]";
        if (CollectionUtils.isNotEmpty(bInstances)) {
            for (ServiceInstance bInstance : bInstances) {
                String bServiceId = bInstance.getServiceId().toLowerCase();
                String bEurekaVersion = bInstance.getMetadata().get(PluginConstant.VERSION);
                String bInfo = bServiceId + "[" + bEurekaVersion + "]";
                String bHost = bInstance.getHost();
                int bPort = bInstance.getPort();

                // 获取C服务的实例列表
                List<Map<String, ?>> cInstances = restTemplate.getForEntity("http://" + bHost + ":" + bPort + "/instances", List.class).getBody();

                if (CollectionUtils.isNotEmpty(cInstances)) {
                    for (Map<String, ?> cInstance : cInstances) {
                        String cServiceId = cInstance.get("serviceId").toString().toLowerCase();
                        String cEurekaVersion = ((Map<String, String>) cInstance.get("metadata")).get(PluginConstant.VERSION);
                        String cInfo = cServiceId + "[" + cEurekaVersion + "]";

                        StringBuilder stringBuilder = new StringBuilder();
                        routes.add(stringBuilder.append(aInfo).append("->").append(bInfo).append("->").append(cInfo).toString());
                    }
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    routes.add(stringBuilder.append(aInfo).append("->").append(bInfo).toString());
                }
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            routes.add(stringBuilder.append(aInfo).toString());
        }

        return routes;
    }
}
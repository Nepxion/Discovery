package com.nepxion.discovery.console.desktop.controller;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nepxion.discovery.console.desktop.context.PropertiesContext;
import com.nepxion.discovery.console.desktop.entity.InstanceEntity;
import com.nepxion.discovery.console.desktop.entity.RouterEntity;
import com.nepxion.discovery.console.desktop.serializer.JacksonSerializer;

public class ServiceController {
    public static RestTemplate restTemplate;

    static {
        restTemplate = new RestTemplate();
    }

    public static Map<String, List<InstanceEntity>> getInstanceMap() {
        String url = getUrl() + "/console/instance-map";

        String json = restTemplate.getForEntity(url, String.class).getBody();

        return JacksonSerializer.fromJson(json, new TypeReference<Map<String, List<InstanceEntity>>>() {
        });
    }

    @SuppressWarnings("unchecked")
    public static List<String> getVersions(InstanceEntity instance) {
        String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/version/view";

        return restTemplate.getForEntity(url, List.class).getBody();
    }

    @SuppressWarnings("unchecked")
    public static List<String> getRules(InstanceEntity instance) {
        String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/config/view";

        return restTemplate.getForEntity(url, List.class).getBody();
    }

    public static RouterEntity routes(InstanceEntity instance, String routeServiceIds) {
        String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/router/routes";

        return restTemplate.postForEntity(url, routeServiceIds, RouterEntity.class).getBody();
    }

    public static String configUpdate(String serviceId, String config) {
        String url = getUrl() + "/console/config/update-sync/" + serviceId;

        return restTemplate.postForEntity(url, config, String.class).getBody();
    }

    public static String configClear(String serviceId) {
        String url = getUrl() + "/console/config/clear/" + serviceId;

        return restTemplate.postForEntity(url, null, String.class).getBody();
    }

    public static String getUrl() {
        String url = PropertiesContext.getProperties().getString("url");
        if (!url.endsWith("/")) {
            url += "/";
        }

        return url;
    }
}
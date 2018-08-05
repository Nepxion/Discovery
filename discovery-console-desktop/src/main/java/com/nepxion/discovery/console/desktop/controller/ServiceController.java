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

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nepxion.discovery.common.entity.ResultEntity;
import com.nepxion.discovery.common.entity.RouterEntity;
import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.common.util.UrlUtil;
import com.nepxion.discovery.console.desktop.context.PropertiesContext;
import com.nepxion.discovery.console.desktop.entity.Instance;

public class ServiceController {
    public static RestTemplate restTemplate;

    static {
        restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new ServiceErrorHandler());
    }

    public static Map<String, List<Instance>> getInstanceMap() {
        String url = getUrl() + "console/instance-map";

        String result = restTemplate.getForEntity(url, String.class).getBody();

        return convert(result, new TypeReference<Map<String, List<Instance>>>() {
        });
    }

    public static List<String> getVersions(Instance instance) {
        String url = getUrl(instance) + "version/view";

        String result = restTemplate.getForEntity(url, String.class).getBody();

        return convert(result, new TypeReference<List<String>>() {
        });
    }

    public static List<String> getRules(Instance instance) {
        String url = getUrl(instance) + "config/view";

        String result = restTemplate.getForEntity(url, String.class).getBody();

        return convert(result, new TypeReference<List<String>>() {
        });
    }

    public static RouterEntity routes(Instance instance, String routeServiceIds) {
        String url = getUrl(instance) + "router/routes";

        String result = restTemplate.postForEntity(url, routeServiceIds, String.class).getBody();

        return convert(result, new TypeReference<RouterEntity>() {
        });
    }

    public static List<ResultEntity> versionUpdate(String serviceId, String version) {
        String url = getUrl() + "console/version/update/" + serviceId;

        String result = restTemplate.postForEntity(url, version, String.class).getBody();

        return convert(result, new TypeReference<List<ResultEntity>>() {
        });
    }

    public static String versionUpdate(Instance instance, String version) {
        String url = getUrl(instance) + "version/update";

        String result = restTemplate.postForEntity(url, version, String.class).getBody();

        if (!StringUtils.equals(result, "OK")) {
            ServiceErrorHandler errorHandler = (ServiceErrorHandler) restTemplate.getErrorHandler();
            result = errorHandler.getCause();
        }

        return result;
    }

    public static List<ResultEntity> versionClear(String serviceId) {
        String url = getUrl() + "console/version/clear/" + serviceId;

        String result = restTemplate.postForEntity(url, null, String.class).getBody();

        return convert(result, new TypeReference<List<ResultEntity>>() {
        });
    }

    public static String versionClear(Instance instance) {
        String url = getUrl(instance) + "version/clear";

        String result = restTemplate.postForEntity(url, null, String.class).getBody();

        if (!StringUtils.equals(result, "OK")) {
            ServiceErrorHandler errorHandler = (ServiceErrorHandler) restTemplate.getErrorHandler();
            result = errorHandler.getCause();
        }

        return result;
    }

    public static String remoteConfigUpdate(String group, String serviceId, String config) {
        String url = getUrl() + "console/remote-config/update/" + group + "/" + serviceId;

        // 解决中文乱码
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(config, headers);

        String result = restTemplate.postForEntity(url, entity, String.class).getBody();

        if (!StringUtils.equals(result, "OK") && !StringUtils.equals(result, "NO")) {
            ServiceErrorHandler errorHandler = (ServiceErrorHandler) restTemplate.getErrorHandler();
            result = errorHandler.getCause();
        }

        return result;
    }

    public static List<ResultEntity> configUpdate(String serviceId, String config) {
        String url = getUrl() + "console/config/update-sync/" + serviceId;

        // 解决中文乱码
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(config, headers);

        String result = restTemplate.postForEntity(url, entity, String.class).getBody();

        return convert(result, new TypeReference<List<ResultEntity>>() {
        });
    }

    public static String configUpdate(Instance instance, String config) {
        String url = getUrl(instance) + "config/update-sync";

        // 解决中文乱码
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(config, headers);

        String result = restTemplate.postForEntity(url, entity, String.class).getBody();

        if (!StringUtils.equals(result, "OK")) {
            ServiceErrorHandler errorHandler = (ServiceErrorHandler) restTemplate.getErrorHandler();
            result = errorHandler.getCause();
        }

        return result;
    }

    public static String remoteConfigClear(String group, String serviceId) {
        String url = getUrl() + "console/remote-config/clear/" + group + "/" + serviceId;

        String result = restTemplate.postForEntity(url, null, String.class).getBody();

        if (!StringUtils.equals(result, "OK") && !StringUtils.equals(result, "NO")) {
            ServiceErrorHandler errorHandler = (ServiceErrorHandler) restTemplate.getErrorHandler();
            result = errorHandler.getCause();
        }

        return result;
    }

    public static List<ResultEntity> configClear(String serviceId) {
        String url = getUrl() + "console/config/clear/" + serviceId;

        String result = restTemplate.postForEntity(url, null, String.class).getBody();

        return convert(result, new TypeReference<List<ResultEntity>>() {
        });
    }

    public static String configClear(Instance instance) {
        String url = getUrl(instance) + "config/clear";

        String result = restTemplate.postForEntity(url, null, String.class).getBody();

        if (!StringUtils.equals(result, "OK")) {
            ServiceErrorHandler errorHandler = (ServiceErrorHandler) restTemplate.getErrorHandler();
            result = errorHandler.getCause();
        }

        return result;
    }

    public static String remoteConfigView(String group, String serviceId) {
        String url = getUrl() + "console/remote-config/view/" + group + "/" + serviceId;

        String result = restTemplate.getForEntity(url, String.class).getBody();

        return result;
    }

    private static String getUrl() {
        String url = PropertiesContext.getProperties().getString("url");
        if (!url.endsWith("/")) {
            url += "/";
        }

        return url;
    }

    private static String getUrl(Instance instance) {
        String url = "http://" + instance.getHost() + ":" + instance.getPort() + UrlUtil.formatContextPath(instance.getContextPath());

        return url;
    }

    private static <T> T convert(String result, TypeReference<T> typeReference) {
        try {
            return JsonUtil.fromJson(result, typeReference);
        } catch (Exception e) {
            ServiceErrorHandler errorHandler = (ServiceErrorHandler) restTemplate.getErrorHandler();
            result = errorHandler.getCause();

            throw new IllegalArgumentException(result);
        }
    }
}
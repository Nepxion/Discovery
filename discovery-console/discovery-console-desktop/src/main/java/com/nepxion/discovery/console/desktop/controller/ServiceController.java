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
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.InstanceEntityWrapper;
import com.nepxion.discovery.common.entity.ResultEntity;
import com.nepxion.discovery.common.entity.RouterEntity;
import com.nepxion.discovery.common.entity.UserEntity;
import com.nepxion.discovery.common.handler.RestErrorHandler;
import com.nepxion.discovery.common.util.RestUtil;
import com.nepxion.discovery.common.util.UrlUtil;
import com.nepxion.discovery.console.desktop.context.PropertiesContext;
import com.nepxion.discovery.console.desktop.entity.Instance;

public class ServiceController {
    public static RestTemplate restTemplate;

    private static String consoleUrl;

    static {
        restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new RestErrorHandler());
    }

    public static boolean authenticate(UserEntity userEntity) {
        String url = getUrl() + "console/authenticate";

        String result = restTemplate.postForEntity(url, userEntity, String.class).getBody();

        return Boolean.valueOf(result);
    }

    public static String getDiscoveryType() {
        String url = getUrl() + "console/discovery-type";

        String result = restTemplate.getForEntity(url, String.class).getBody();

        return result;
    }

    public static String getConfigType() {
        String url = getUrl() + "console/config-type";

        String result = restTemplate.getForEntity(url, String.class).getBody();

        return result;
    }

    public static List<String> getGroups() {
        String url = getUrl() + "console/groups";

        String result = restTemplate.getForEntity(url, String.class).getBody();

        return RestUtil.fromJson(restTemplate, result, new TypeReference<List<String>>() {
        });
    }

    public static Map<String, List<Instance>> getInstanceMap(List<String> groups) {
        String url = getUrl() + "console/instance-map";

        String result = restTemplate.postForEntity(url, groups, String.class).getBody();

        return RestUtil.fromJson(restTemplate, result, new TypeReference<Map<String, List<Instance>>>() {
        });
    }

    public static List<String> getVersions(Instance instance) {
        String url = getUrl(instance) + "version/view";

        String result = restTemplate.getForEntity(url, String.class).getBody();

        return RestUtil.fromJson(restTemplate, result, new TypeReference<List<String>>() {
        });
    }

    public static List<String> getRules(Instance instance) {
        String url = getUrl(instance) + "config/view";

        String result = restTemplate.getForEntity(url, String.class).getBody();

        return RestUtil.fromJson(restTemplate, result, new TypeReference<List<String>>() {
        });
    }

    public static List<String> getRules(RouterEntity routerEntity) {
        String url = getUrl(routerEntity) + "config/view";

        String result = restTemplate.getForEntity(url, String.class).getBody();

        return RestUtil.fromJson(restTemplate, result, new TypeReference<List<String>>() {
        });
    }

    public static RouterEntity routes(Instance instance, String routeServiceIds) {
        String url = getUrl(instance) + "router/routes";

        String result = restTemplate.postForEntity(url, routeServiceIds, String.class).getBody();

        return RestUtil.fromJson(restTemplate, result, new TypeReference<RouterEntity>() {
        });
    }

    public static String remoteConfigUpdate(String group, String serviceId, String config) {
        String url = getUrl() + "console/remote-config/update/" + group + "/" + serviceId;

        // 解决中文乱码
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(config, headers);

        String result = restTemplate.postForEntity(url, entity, String.class).getBody();

        if (!StringUtils.equals(result, DiscoveryConstant.OK) && !StringUtils.equals(result, DiscoveryConstant.NO)) {
            result = RestUtil.getCause(restTemplate);
        }

        return result;
    }

    public static String remoteConfigClear(String group, String serviceId) {
        String url = getUrl() + "console/remote-config/clear/" + group + "/" + serviceId;

        String result = restTemplate.postForEntity(url, null, String.class).getBody();

        if (!StringUtils.equals(result, DiscoveryConstant.OK) && !StringUtils.equals(result, DiscoveryConstant.NO)) {
            result = RestUtil.getCause(restTemplate);
        }

        return result;
    }

    public static String remoteConfigView(String group, String serviceId) {
        String url = getUrl() + "console/remote-config/view/" + group + "/" + serviceId;

        String result = restTemplate.getForEntity(url, String.class).getBody();

        return result;
    }

    public static List<ResultEntity> configUpdate(String serviceId, String config, boolean async) {
        String url = getUrl() + "console/config/update-" + getInvokeType(async) + "/" + serviceId;

        // 解决中文乱码
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(config, headers);

        String result = restTemplate.postForEntity(url, entity, String.class).getBody();

        return RestUtil.fromJson(restTemplate, result, new TypeReference<List<ResultEntity>>() {
        });
    }

    public static String configUpdate(Instance instance, String config, boolean async) {
        String url = getUrl(instance) + "config/update-" + getInvokeType(async);

        // 解决中文乱码
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(config, headers);

        String result = restTemplate.postForEntity(url, entity, String.class).getBody();

        if (!StringUtils.equals(result, DiscoveryConstant.OK)) {
            result = RestUtil.getCause(restTemplate);
        }

        return result;
    }

    public static List<ResultEntity> configClear(String serviceId, boolean async) {
        String url = getUrl() + "console/config/clear-" + getInvokeType(async) + "/" + serviceId;

        String result = restTemplate.postForEntity(url, null, String.class).getBody();

        return RestUtil.fromJson(restTemplate, result, new TypeReference<List<ResultEntity>>() {
        });
    }

    public static String configClear(Instance instance, boolean async) {
        String url = getUrl(instance) + "config/clear-" + getInvokeType(async);

        String result = restTemplate.postForEntity(url, null, String.class).getBody();

        if (!StringUtils.equals(result, DiscoveryConstant.OK)) {
            result = RestUtil.getCause(restTemplate);
        }

        return result;
    }

    public static List<ResultEntity> versionUpdate(String serviceId, String version, boolean async) {
        String url = getUrl() + "console/version/update-" + getInvokeType(async) + "/" + serviceId;

        String result = restTemplate.postForEntity(url, version, String.class).getBody();

        return RestUtil.fromJson(restTemplate, result, new TypeReference<List<ResultEntity>>() {
        });
    }

    public static String versionUpdate(Instance instance, String version, boolean async) {
        String url = getUrl(instance) + "version/update-" + getInvokeType(async);

        String result = restTemplate.postForEntity(url, version, String.class).getBody();

        if (!StringUtils.equals(result, DiscoveryConstant.OK)) {
            result = RestUtil.getCause(restTemplate);
        }

        return result;
    }

    public static List<ResultEntity> versionClear(String serviceId, boolean async) {
        String url = getUrl() + "console/version/clear-" + getInvokeType(async) + "/" + serviceId;

        String result = restTemplate.postForEntity(url, null, String.class).getBody();

        return RestUtil.fromJson(restTemplate, result, new TypeReference<List<ResultEntity>>() {
        });
    }

    public static String versionClear(Instance instance, boolean async) {
        String url = getUrl(instance) + "version/clear-" + getInvokeType(async);

        String result = restTemplate.postForEntity(url, null, String.class).getBody();

        if (!StringUtils.equals(result, DiscoveryConstant.OK)) {
            result = RestUtil.getCause(restTemplate);
        }

        return result;
    }

    public static String getUrl() {
        String url = null;
        if (StringUtils.isNotEmpty(consoleUrl)) {
            url = consoleUrl;
        } else {
            url = PropertiesContext.getProperties().getString("url");
        }

        return UrlUtil.formatUrl(url);
    }

    public static void setUrl(String url) {
        consoleUrl = url;
    }

    private static String getUrl(Instance instance) {
        String url = "http://" + instance.getHost() + ":" + instance.getPort() + UrlUtil.formatContextPath(InstanceEntityWrapper.getContextPath(instance));

        return url;
    }

    private static String getUrl(RouterEntity routerEntity) {
        String url = "http://" + routerEntity.getHost() + ":" + routerEntity.getPort() + UrlUtil.formatContextPath(routerEntity.getContextPath());

        return url;
    }

    private static String getInvokeType(boolean async) {
        return async ? DiscoveryConstant.ASYNC : DiscoveryConstant.SYNC;
    }
}
package com.nepxion.discovery.common.entity;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.constant.DiscoveryMetaDataConstant;
import com.nepxion.discovery.common.util.UrlUtil;

public class InstanceEntityWrapper {
    public static String getPlugin(InstanceEntity instanceEntity) {
        Map<String, String> metadata = instanceEntity.getMetadata();

        return getPlugin(metadata);
    }

    public static String getGroup(InstanceEntity instanceEntity) {
        Map<String, String> metadata = instanceEntity.getMetadata();

        return getGroup(metadata);
    }

    public static String getServiceType(InstanceEntity instanceEntity) {
        Map<String, String> metadata = instanceEntity.getMetadata();

        return getServiceType(metadata);
    }

    public static String getGatewayType(InstanceEntity instanceEntity) {
        Map<String, String> metadata = instanceEntity.getMetadata();

        return getGatewayType(metadata);
    }

    public static String getServiceAppId(InstanceEntity instanceEntity) {
        Map<String, String> metadata = instanceEntity.getMetadata();

        return getServiceAppId(metadata);
    }

    public static String getServiceUUId(InstanceEntity instanceEntity) {
        Map<String, String> metadata = instanceEntity.getMetadata();

        return getServiceUUId(metadata);
    }

    public static String getVersion(InstanceEntity instanceEntity) {
        Map<String, String> metadata = instanceEntity.getMetadata();

        return getVersion(metadata);
    }

    public static String getRegion(InstanceEntity instanceEntity) {
        Map<String, String> metadata = instanceEntity.getMetadata();

        return getRegion(metadata);
    }

    public static String getEnvironment(InstanceEntity instanceEntity) {
        Map<String, String> metadata = instanceEntity.getMetadata();

        return getEnvironment(metadata);
    }

    public static String getZone(InstanceEntity instanceEntity) {
        Map<String, String> metadata = instanceEntity.getMetadata();

        return getZone(metadata);
    }

    public static boolean isActive(InstanceEntity instanceEntity) {
        Map<String, String> metadata = instanceEntity.getMetadata();

        return isActive(metadata);
    }

    public static String getProtocol(InstanceEntity instanceEntity) {
        Map<String, String> metadata = instanceEntity.getMetadata();

        return getProtocol(metadata);
    }

    public static String getContextPath(InstanceEntity instanceEntity) {
        Map<String, String> metadata = instanceEntity.getMetadata();

        return getContextPath(metadata);
    }

    public static String getFormatContextPath(InstanceEntity instanceEntity) {
        Map<String, String> metadata = instanceEntity.getMetadata();

        return getFormatContextPath(metadata);
    }

    public static String getPlugin(Map<String, String> metadata) {
        String plugin = metadata.get(DiscoveryMetaDataConstant.SPRING_APPLICATION_DISCOVERY_PLUGIN);
        if (StringUtils.isEmpty(plugin)) {
            return StringUtils.EMPTY;
        }

        return plugin;
    }

    public static String getGroup(Map<String, String> metadata) {
        String groupKey = metadata.get(DiscoveryMetaDataConstant.SPRING_APPLICATION_GROUP_KEY);
        if (StringUtils.isEmpty(groupKey)) {
            groupKey = DiscoveryConstant.GROUP;
        }

        String group = metadata.get(groupKey);
        if (StringUtils.isEmpty(group)) {
            return StringUtils.EMPTY;
        }

        return group;
    }

    public static String getServiceType(Map<String, String> metadata) {
        return metadata.get(DiscoveryMetaDataConstant.SPRING_APPLICATION_TYPE);
    }

    public static String getGatewayType(Map<String, String> metadata) {
        return metadata.get(DiscoveryMetaDataConstant.SPRING_APPLICATION_GATEWAY_TYPE);
    }

    public static String getServiceAppId(Map<String, String> metadata) {
        return metadata.get(DiscoveryMetaDataConstant.SPRING_APPLICATION_APP_ID);
    }

    public static String getServiceUUId(Map<String, String> metadata) {
        return metadata.get(DiscoveryMetaDataConstant.SPRING_APPLICATION_UUID);
    }

    public static String getVersion(Map<String, String> metadata) {
        String version = metadata.get(DiscoveryConstant.VERSION);
        if (StringUtils.isEmpty(version)) {
            version = DiscoveryConstant.DEFAULT;
        }

        return version;
    }

    public static String getRegion(Map<String, String> metadata) {
        String region = metadata.get(DiscoveryConstant.REGION);
        if (StringUtils.isEmpty(region)) {
            region = DiscoveryConstant.DEFAULT;
        }

        return region;
    }

    public static String getEnvironment(Map<String, String> metadata) {
        String environment = metadata.get(DiscoveryConstant.ENVIRONMENT);
        if (StringUtils.isEmpty(environment)) {
            environment = DiscoveryConstant.DEFAULT;
        }

        return environment;
    }

    public static String getZone(Map<String, String> metadata) {
        String zone = metadata.get(DiscoveryConstant.ZONE);
        if (StringUtils.isEmpty(zone)) {
            zone = DiscoveryConstant.DEFAULT;
        }

        return zone;
    }

    public static boolean isActive(Map<String, String> metadata) {
        String active = metadata.get(DiscoveryConstant.ACTIVE);
        if (StringUtils.isEmpty(active)) {
            return false;
        }

        return Boolean.valueOf(active);
    }

    public static String getProtocol(Map<String, String> metadata) {
        String protocol = metadata.get(DiscoveryMetaDataConstant.SPRING_APPLICATION_PROTOCOL);
        if (StringUtils.isEmpty(protocol)) {
            return "http";
        }

        return protocol;
    }

    public static String getContextPath(Map<String, String> metadata) {
        String contextPath = metadata.get(DiscoveryMetaDataConstant.SPRING_APPLICATION_CONTEXT_PATH);
        if (StringUtils.isEmpty(contextPath)) {
            return "/";
        }

        return contextPath;
    }

    public static String getFormatContextPath(Map<String, String> metadata) {
        String contextPath = getContextPath(metadata);

        return UrlUtil.formatContextPath(contextPath);
    }
}
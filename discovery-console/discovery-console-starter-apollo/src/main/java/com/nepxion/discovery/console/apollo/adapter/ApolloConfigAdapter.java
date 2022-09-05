package com.nepxion.discovery.console.apollo.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
import com.ctrip.framework.apollo.openapi.dto.NamespaceReleaseDTO;
import com.ctrip.framework.apollo.openapi.dto.OpenItemDTO;
import com.nepxion.discovery.common.entity.ConfigType;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.console.adapter.ConfigAdapter;
import com.nepxion.discovery.console.apollo.constant.ApolloConstant;

public class ApolloConfigAdapter implements ConfigAdapter {
    @Autowired
    private Environment environment;

    @Autowired
    private ApolloOpenApiClient apolloOpenApiClient;

    @Override
    public boolean updateConfig(String group, String serviceId, String config) throws Exception {
        String appId = environment.getProperty(ApolloConstant.APOLLO_APP_ID);
        if (StringUtils.isEmpty(appId)) {
            throw new DiscoveryException(ApolloConstant.APOLLO_APP_ID + " can't be null or empty");
        }

        String env = environment.getProperty(ApolloConstant.APOLLO_ENV);
        if (StringUtils.isEmpty(env)) {
            throw new DiscoveryException(ApolloConstant.APOLLO_ENV + " can't be null or empty");
        }

        String operator = environment.getProperty(ApolloConstant.APOLLO_OPERATOR);
        if (StringUtils.isEmpty(operator)) {
            throw new DiscoveryException(ApolloConstant.APOLLO_OPERATOR + " can't be null or empty");
        }

        String cluster = environment.getProperty(ApolloConstant.APOLLO_CLUSTER, String.class, ApolloConstant.APOLLO_DEFAULT_CLUSTER);
        String namespace = environment.getProperty(ApolloConstant.APOLLO_NAMESPACE, String.class, ApolloConstant.APOLLO_DEFAULT_NAMESPACE);

        Date now = new Date();

        OpenItemDTO openItemDTO = new OpenItemDTO();
        openItemDTO.setKey(group + "-" + serviceId);
        openItemDTO.setValue(config);
        openItemDTO.setComment("Operated by Nepxion Discovery Console");
        openItemDTO.setDataChangeCreatedBy(operator);
        openItemDTO.setDataChangeLastModifiedBy(operator);
        openItemDTO.setDataChangeCreatedTime(now);
        openItemDTO.setDataChangeLastModifiedTime(now);

        apolloOpenApiClient.createOrUpdateItem(appId, env, cluster, namespace, openItemDTO);

        NamespaceReleaseDTO namespaceReleaseDTO = new NamespaceReleaseDTO();
        namespaceReleaseDTO.setReleaseTitle(new SimpleDateFormat("yyyyMMddHHmmss").format(now) + "-release");
        namespaceReleaseDTO.setReleasedBy(operator);
        namespaceReleaseDTO.setReleaseComment("Released by Nepxion Discovery Console");
        namespaceReleaseDTO.setEmergencyPublish(true);

        apolloOpenApiClient.publishNamespace(appId, env, cluster, namespace, namespaceReleaseDTO);

        return true;
    }

    @Override
    public boolean clearConfig(String group, String serviceId) throws Exception {
        String appId = environment.getProperty(ApolloConstant.APOLLO_APP_ID);
        if (StringUtils.isEmpty(appId)) {
            throw new DiscoveryException(ApolloConstant.APOLLO_APP_ID + " can't be null or empty");
        }

        String env = environment.getProperty(ApolloConstant.APOLLO_ENV);
        if (StringUtils.isEmpty(env)) {
            throw new DiscoveryException(ApolloConstant.APOLLO_ENV + " can't be null or empty");
        }

        String operator = environment.getProperty(ApolloConstant.APOLLO_OPERATOR);
        if (StringUtils.isEmpty(operator)) {
            throw new DiscoveryException(ApolloConstant.APOLLO_OPERATOR + " can't be null or empty");
        }

        String cluster = environment.getProperty(ApolloConstant.APOLLO_CLUSTER, String.class, ApolloConstant.APOLLO_DEFAULT_CLUSTER);
        String namespace = environment.getProperty(ApolloConstant.APOLLO_NAMESPACE, String.class, ApolloConstant.APOLLO_DEFAULT_NAMESPACE);

        apolloOpenApiClient.removeItem(appId, env, cluster, namespace, group + "-" + serviceId, operator);

        Date now = new Date();

        NamespaceReleaseDTO namespaceReleaseDTO = new NamespaceReleaseDTO();
        namespaceReleaseDTO.setReleaseTitle(new SimpleDateFormat("yyyyMMddHHmmss").format(now) + "-release");
        namespaceReleaseDTO.setReleasedBy(operator);
        namespaceReleaseDTO.setReleaseComment("Deleted by Nepxion Discovery Console");
        namespaceReleaseDTO.setEmergencyPublish(true);

        apolloOpenApiClient.publishNamespace(appId, env, cluster, namespace, namespaceReleaseDTO);

        return true;
    }

    @Override
    public String getConfig(String group, String serviceId) throws Exception {
        String appId = environment.getProperty(ApolloConstant.APOLLO_APP_ID);
        if (StringUtils.isEmpty(appId)) {
            throw new DiscoveryException(ApolloConstant.APOLLO_APP_ID + " can't be null or empty");
        }

        String env = environment.getProperty(ApolloConstant.APOLLO_ENV);
        if (StringUtils.isEmpty(env)) {
            throw new DiscoveryException(ApolloConstant.APOLLO_ENV + " can't be null or empty");
        }

        String cluster = environment.getProperty(ApolloConstant.APOLLO_CLUSTER, String.class, ApolloConstant.APOLLO_DEFAULT_CLUSTER);
        String namespace = environment.getProperty(ApolloConstant.APOLLO_NAMESPACE, String.class, ApolloConstant.APOLLO_DEFAULT_NAMESPACE);

        return apolloOpenApiClient.getLatestActiveRelease(appId, env, cluster, namespace).getConfigurations().get(group + "-" + serviceId);
    }

    @Override
    public ConfigType getConfigType() {
        return ConfigType.APOLLO;
    }
}
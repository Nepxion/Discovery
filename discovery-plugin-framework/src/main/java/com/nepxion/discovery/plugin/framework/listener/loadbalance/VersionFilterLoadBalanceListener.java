package com.nepxion.discovery.plugin.framework.listener.loadbalance;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.cache.RuleCache;
import com.nepxion.discovery.plugin.framework.constant.PluginConstant;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.entity.DiscoveryEntity;
import com.nepxion.discovery.plugin.framework.entity.DiscoveryServiceEntity;
import com.nepxion.discovery.plugin.framework.entity.RuleEntity;
import com.nepxion.discovery.plugin.framework.entity.VersionEntity;
import com.netflix.loadbalancer.Server;

public class VersionFilterLoadBalanceListener extends AbstractLoadBalanceListener {
    @Autowired
    private RuleCache ruleCache;

    @Autowired
    private PluginContextAware pluginContextAware;

    @Autowired
    private PluginAdapter pluginAdapter;

    @Override
    public void onGetServers(String serviceId, List<? extends Server> servers) {
        String consumerServiceId = pluginContextAware.getServiceId();
        String consumerServiceVersion = pluginAdapter.getVersion();

        applyVersionFilter(consumerServiceId, consumerServiceVersion, serviceId, servers);
    }

    private void applyVersionFilter(String consumerServiceId, String consumerServiceVersion, String providerServiceId, List<? extends Server> servers) {
        // 如果消费端未配置版本号，那么它可以调用提供端所有服务，需要符合规范，极力避免该情况发生
        if (StringUtils.isEmpty(consumerServiceVersion)) {
            return;
        }

        RuleEntity ruleEntity = ruleCache.get(PluginConstant.RULE);
        if (ruleEntity == null) {
            return;
        }

        DiscoveryEntity discoveryEntity = ruleEntity.getDiscoveryEntity();
        if (discoveryEntity == null) {
            return;
        }

        VersionEntity versionEntity = discoveryEntity.getVersionEntity();
        if (versionEntity == null) {
            return;
        }

        Map<String, List<DiscoveryServiceEntity>> serviceEntityMap = versionEntity.getServiceEntityMap();
        if (MapUtils.isEmpty(serviceEntityMap)) {
            return;
        }

        List<DiscoveryServiceEntity> serviceEntityList = serviceEntityMap.get(consumerServiceId);
        if (CollectionUtils.isEmpty(serviceEntityList)) {
            return;
        }

        // 当前版本的消费端所能调用提供端的版本号列表
        List<String> allNoFilterValueList = null;
        for (DiscoveryServiceEntity serviceEntity : serviceEntityList) {
            String providerServiceName = serviceEntity.getProviderServiceName();
            if (StringUtils.equals(providerServiceName, providerServiceId)) {
                List<String> consumerVersionValueList = serviceEntity.getConsumerVersionValueList();
                List<String> providerVersionValueList = serviceEntity.getProviderVersionValueList();

                // 判断consumer-version-value值是否包含当前消费端的版本号
                // 如果consumerVersionValueList为空，表示消费端版本列表未指定，那么任意消费端版本可以访问指定版本提供端版本
                if (CollectionUtils.isNotEmpty(consumerVersionValueList)) {
                    if (consumerVersionValueList.contains(consumerServiceVersion)) {
                        if (allNoFilterValueList == null) {
                            allNoFilterValueList = new ArrayList<String>();
                        }
                        if (CollectionUtils.isNotEmpty(providerVersionValueList)) {
                            allNoFilterValueList.addAll(providerVersionValueList);
                        }
                    }
                } else {
                    if (allNoFilterValueList == null) {
                        allNoFilterValueList = new ArrayList<String>();
                    }
                    if (CollectionUtils.isNotEmpty(providerVersionValueList)) {
                        allNoFilterValueList.addAll(providerVersionValueList);
                    }
                }
            }
        }

        if (allNoFilterValueList != null) {
            // 当allNoFilterValueList为空列表，意味着版本对应关系未做任何定义（即所有的providerVersionValueList为空），不需要执行过滤，直接返回
            if (allNoFilterValueList.isEmpty()) {
                return;
            } else {
                Iterator<? extends Server> iterator = servers.iterator();
                while (iterator.hasNext()) {
                    Server server = iterator.next();
                    String metaDataVersion = pluginAdapter.getServerVersion(server);
                    if (!allNoFilterValueList.contains(metaDataVersion)) {
                        iterator.remove();
                    }
                }
            }
        } else {
            // 当allNoFilterValueList为null, 意味着定义的版本关系都不匹配，直接清空所有实例
            servers.clear();
        }
    }
}
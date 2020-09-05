package com.nepxion.discovery.plugin.framework.listener.discovery;

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
import org.springframework.cloud.client.ServiceInstance;

import com.nepxion.discovery.common.entity.DiscoveryEntity;
import com.nepxion.discovery.common.entity.FilterType;
import com.nepxion.discovery.common.entity.HostFilterEntity;
import com.nepxion.discovery.common.entity.RuleEntity;

public class HostFilterDiscoveryListener extends AbstractDiscoveryListener {
    @Override
    public void onGetInstances(String serviceId, List<ServiceInstance> instances) {
        applyHostFilter(serviceId, instances);
    }

    private void applyHostFilter(String providerServiceId, List<ServiceInstance> instances) {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity == null) {
            return;
        }

        DiscoveryEntity discoveryEntity = ruleEntity.getDiscoveryEntity();
        if (discoveryEntity == null) {
            return;
        }

        HostFilterEntity hostFilterEntity = discoveryEntity.getHostFilterEntity();
        if (hostFilterEntity == null) {
            return;
        }

        FilterType filterType = hostFilterEntity.getFilterType();

        List<String> globalFilterValueList = hostFilterEntity.getFilterValueList();
        Map<String, List<String>> filterMap = hostFilterEntity.getFilterMap();
        List<String> filterValueList = filterMap.get(providerServiceId);

        if (CollectionUtils.isEmpty(globalFilterValueList) && CollectionUtils.isEmpty(filterValueList)) {
            return;
        }

        List<String> allFilterValueList = new ArrayList<String>();
        if (CollectionUtils.isNotEmpty(globalFilterValueList)) {
            allFilterValueList.addAll(globalFilterValueList);
        }

        if (CollectionUtils.isNotEmpty(filterValueList)) {
            allFilterValueList.addAll(filterValueList);
        }

        Iterator<ServiceInstance> iterator = instances.iterator();
        while (iterator.hasNext()) {
            ServiceInstance instance = iterator.next();
            String host = instance.getHost();
            switch (filterType) {
                case BLACKLIST:
                    if (validateBlacklist(allFilterValueList, host)) {
                        iterator.remove();
                    }
                    break;
                case WHITELIST:
                    if (validateWhitelist(allFilterValueList, host)) {
                        iterator.remove();
                    }
                    break;
            }
        }
    }

    private boolean validateBlacklist(List<String> allFilterValueList, String host) {
        for (String filterValue : allFilterValueList) {
            if (host.startsWith(filterValue)) {
                return true;
            }
        }

        return false;
    }

    private boolean validateWhitelist(List<String> allFilterValueList, String host) {
        boolean matched = true;
        for (String filterValue : allFilterValueList) {
            if (host.startsWith(filterValue)) {
                matched = false;
                break;
            }
        }

        return matched;
    }

    @Override
    public void onGetServices(List<String> services) {

    }

    @Override
    public int getOrder() {
        // Highest priority
        return HIGHEST_PRECEDENCE;
    }
}
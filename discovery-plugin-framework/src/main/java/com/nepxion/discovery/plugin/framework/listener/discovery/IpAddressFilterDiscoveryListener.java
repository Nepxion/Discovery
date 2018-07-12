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

import com.nepxion.discovery.plugin.framework.constant.PluginConstant;
import com.nepxion.discovery.plugin.framework.entity.DiscoveryEntity;
import com.nepxion.discovery.plugin.framework.entity.FilterType;
import com.nepxion.discovery.plugin.framework.entity.IpAddressFilterEntity;
import com.nepxion.discovery.plugin.framework.entity.RuleEntity;

public class IpAddressFilterDiscoveryListener extends AbstractDiscoveryListener {
    @Override
    public void onGetInstances(String serviceId, List<ServiceInstance> instances) {
        applyIpAddressFilter(serviceId, instances);
    }

    private void applyIpAddressFilter(String providerServiceId, List<ServiceInstance> instances) {
        RuleEntity ruleEntity = ruleCache.get(PluginConstant.RULE);
        if (ruleEntity == null) {
            return;
        }

        DiscoveryEntity discoveryEntity = ruleEntity.getDiscoveryEntity();
        if (discoveryEntity == null) {
            return;
        }

        IpAddressFilterEntity ipAddressFilterEntity = discoveryEntity.getIpAddressFilterEntity();
        if (ipAddressFilterEntity == null) {
            return;
        }

        FilterType filterType = ipAddressFilterEntity.getFilterType();

        List<String> globalFilterValueList = ipAddressFilterEntity.getFilterValueList();
        Map<String, List<String>> filterMap = ipAddressFilterEntity.getFilterMap();
        List<String> filterValueList = filterMap.get(providerServiceId);

        List<String> allFilterValueList = new ArrayList<String>();
        if (CollectionUtils.isNotEmpty(globalFilterValueList)) {
            allFilterValueList.addAll(globalFilterValueList);
        }

        if (CollectionUtils.isNotEmpty(filterValueList)) {
            allFilterValueList.addAll(filterValueList);
        }

        Iterator<ServiceInstance> iterator = instances.iterator();
        while (iterator.hasNext()) {
            ServiceInstance serviceInstance = iterator.next();
            String host = serviceInstance.getHost();
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

    private boolean validateBlacklist(List<String> allFilterValueList, String ipAddress) {
        for (String filterValue : allFilterValueList) {
            if (ipAddress.startsWith(filterValue)) {
                return true;
            }
        }

        return false;
    }

    private boolean validateWhitelist(List<String> allFilterValueList, String ipAddress) {
        boolean matched = true;
        for (String filterValue : allFilterValueList) {
            if (ipAddress.startsWith(filterValue)) {
                matched = false;
                break;
            }
        }

        return matched;
    }

    @Override
    public void onGetServices(List<String> services) {

    }
}
package com.nepxion.discovery.plugin.framework.listener.register;

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

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cloud.client.serviceregistry.Registration;

import com.nepxion.discovery.common.entity.FilterType;
import com.nepxion.discovery.common.entity.HostFilterEntity;
import com.nepxion.discovery.common.entity.RegisterEntity;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.plugin.framework.event.RegisterFailureEvent;

public class HostFilterRegisterListener extends AbstractRegisterListener {
    @Override
    public void onRegister(Registration registration) {
        String serviceId = pluginAdapter.getServiceId();
        String host = pluginAdapter.getHost();
        int port = pluginAdapter.getPort();

        applyHostFilter(serviceId, host, port);
    }

    private void applyHostFilter(String serviceId, String host, int port) {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity == null) {
            return;
        }

        RegisterEntity registerEntity = ruleEntity.getRegisterEntity();
        if (registerEntity == null) {
            return;
        }

        HostFilterEntity hostFilterEntity = registerEntity.getHostFilterEntity();
        if (hostFilterEntity == null) {
            return;
        }

        FilterType filterType = hostFilterEntity.getFilterType();

        List<String> globalFilterValueList = hostFilterEntity.getFilterValueList();
        Map<String, List<String>> filterMap = hostFilterEntity.getFilterMap();
        List<String> filterValueList = filterMap.get(serviceId);

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

        switch (filterType) {
            case BLACKLIST:
                validateBlacklist(filterType, allFilterValueList, serviceId, host, port);
                break;
            case WHITELIST:
                validateWhitelist(filterType, allFilterValueList, serviceId, host, port);
                break;
        }
    }

    private void validateBlacklist(FilterType filterType, List<String> allFilterValueList, String serviceId, String host, int port) {
        for (String filterValue : allFilterValueList) {
            if (host.startsWith(filterValue)) {
                onRegisterFailure(filterType, allFilterValueList, serviceId, host, port);
            }
        }
    }

    private void validateWhitelist(FilterType filterType, List<String> allFilterValueList, String serviceId, String host, int port) {
        boolean matched = true;
        for (String filterValue : allFilterValueList) {
            if (host.startsWith(filterValue)) {
                matched = false;
                break;
            }
        }

        if (matched) {
            onRegisterFailure(filterType, allFilterValueList, serviceId, host, port);
        }
    }

    private void onRegisterFailure(FilterType filterType, List<String> allFilterValueList, String serviceId, String host, int port) {
        String description = serviceId + " for " + host + ":" + port + " is rejected to register to Register server, not match host " + filterType + "=" + allFilterValueList;

        pluginEventWapper.fireRegisterFailure(new RegisterFailureEvent(filterType.toString(), description, serviceId, host, port));

        throw new DiscoveryException(description);
    }

    @Override
    public void onDeregister(Registration registration) {

    }

    @Override
    public void onSetStatus(Registration registration, String status) {

    }

    @Override
    public void onClose() {

    }

    @Override
    public int getOrder() {
        // After count filter
        return HIGHEST_PRECEDENCE + 1;
    }
}
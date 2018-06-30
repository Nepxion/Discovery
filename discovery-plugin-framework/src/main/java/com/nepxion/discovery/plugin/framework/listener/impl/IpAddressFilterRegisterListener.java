package com.nepxion.discovery.plugin.framework.listener.impl;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;

import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.constant.PluginConstant;
import com.nepxion.discovery.plugin.framework.entity.FilterEntity;
import com.nepxion.discovery.plugin.framework.entity.FilterType;
import com.nepxion.discovery.plugin.framework.entity.RegisterEntity;
import com.nepxion.discovery.plugin.framework.entity.RuleEntity;
import com.nepxion.discovery.plugin.framework.event.PluginPublisher;
import com.nepxion.discovery.plugin.framework.event.RegisterFaiureEvent;
import com.nepxion.discovery.plugin.framework.exception.PluginException;
import com.nepxion.discovery.plugin.framework.listener.AbstractRegisterListener;

public class IpAddressFilterRegisterListener extends AbstractRegisterListener {
    private static final Logger LOG = LoggerFactory.getLogger(IpAddressFilterRegisterListener.class);

    @Autowired
    private RuleEntity ruleEntity;

    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private PluginPublisher pluginPublisher;

    @Override
    public void onRegister(Registration registration) {
        String serviceId = registration.getServiceId();
        String ipAddress = pluginAdapter.getIpAddress(registration);
        int port = pluginAdapter.getPort(registration);

        applyIpAddressFilter(serviceId, ipAddress, port);
    }

    private void applyIpAddressFilter(String serviceId, String ipAddress, int port) {
        RegisterEntity registerEntity = ruleEntity.getRegisterEntity();
        if (registerEntity == null) {
            return;
        }

        FilterEntity filterEntity = registerEntity.getFilterEntity();
        if (filterEntity == null) {
            return;
        }

        FilterType filterType = filterEntity.getFilterType();

        List<String> globalFilterValueList = filterEntity.getFilterValueList();
        Map<String, List<String>> filterMap = filterEntity.getFilterMap();
        List<String> filterValueList = filterMap.get(serviceId);

        List<String> allFilterValueList = new ArrayList<String>();
        if (CollectionUtils.isNotEmpty(globalFilterValueList)) {
            allFilterValueList.addAll(globalFilterValueList);
        }

        if (CollectionUtils.isNotEmpty(filterValueList)) {
            allFilterValueList.addAll(filterValueList);
        }

        switch (filterType) {
            case BLACKLIST:
                validateBlacklist(filterType, allFilterValueList, serviceId, ipAddress, port);
                break;
            case WHITELIST:
                validateWhitelist(filterType, allFilterValueList, serviceId, ipAddress, port);
                break;
        }
    }

    private void validateBlacklist(FilterType filterType, List<String> allFilterValueList, String serviceId, String ipAddress, int port) {
        for (String filterValue : allFilterValueList) {
            if (ipAddress.startsWith(filterValue)) {
                onRegisterFaiure(filterType, allFilterValueList, serviceId, ipAddress, port);
            }
        }
    }

    private void validateWhitelist(FilterType filterType, List<String> allFilterValueList, String serviceId, String ipAddress, int port) {
        boolean matched = true;
        for (String filterValue : allFilterValueList) {
            if (ipAddress.startsWith(filterValue)) {
                matched = false;
                break;
            }
        }

        if (matched) {
            onRegisterFaiure(filterType, allFilterValueList, serviceId, ipAddress, port);
        }
    }

    private void onRegisterFaiure(FilterType filterType, List<String> allFilterValueList, String serviceId, String ipAddress, int port) {
        Boolean registerFailureEventEnabled = environment.getProperty(PluginConstant.SPRING_APPLICATION_REGISTER_FAILURE_EVENT_ENABLED, Boolean.class, Boolean.FALSE);
        if (registerFailureEventEnabled) {
            pluginPublisher.asyncPublish(new RegisterFaiureEvent(filterType, serviceId, ipAddress, port));
        }

        throw new PluginException(ipAddress + " isn't allowed to register to Register server, not match IP address " + filterType + "=" + allFilterValueList);
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
}
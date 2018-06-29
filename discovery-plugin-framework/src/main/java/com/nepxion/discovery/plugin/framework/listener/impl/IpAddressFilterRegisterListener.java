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
import com.nepxion.discovery.plugin.framework.entity.FilterEntity;
import com.nepxion.discovery.plugin.framework.entity.FilterType;
import com.nepxion.discovery.plugin.framework.entity.RegisterEntity;
import com.nepxion.discovery.plugin.framework.entity.RuleEntity;
import com.nepxion.discovery.plugin.framework.exception.PluginException;
import com.nepxion.discovery.plugin.framework.listener.AbstractRegisterListener;

public class IpAddressFilterRegisterListener extends AbstractRegisterListener {
    private static final Logger LOG = LoggerFactory.getLogger(IpAddressFilterRegisterListener.class);

    @Autowired
    private RuleEntity ruleEntity;

    @Autowired
    private PluginAdapter pluginAdapter;

    @Override
    public void onRegister(Registration registration) {
        String serviceId = registration.getServiceId();
        String ipAddress = pluginAdapter.getIpAddress(registration);

        applyIpAddressFilter(serviceId, ipAddress);
    }

    private void applyIpAddressFilter(String serviceId, String ipAddress) {
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
                validateBlacklist(allFilterValueList, ipAddress);
                break;
            case WHITELIST:
                validateWhitelist(allFilterValueList, ipAddress);
                break;
        }
    }

    private void validateBlacklist(List<String> allFilterValueList, String ipAddress) {
        LOG.info("********** IP address blacklist={}, current ip address={} **********", allFilterValueList, ipAddress);

        for (String filterValue : allFilterValueList) {
            if (ipAddress.startsWith(filterValue)) {
                throw new PluginException(ipAddress + " isn't allowed to register to Discovery server, because it is in blacklist");
            }
        }
    }

    private void validateWhitelist(List<String> allFilterValueList, String ipAddress) {
        LOG.info("********** IP address whitelist={}, current ip address={} **********", allFilterValueList, ipAddress);

        boolean matched = true;
        for (String filterValue : allFilterValueList) {
            if (ipAddress.startsWith(filterValue)) {
                matched = false;
                break;
            }
        }

        if (matched) {
            throw new PluginException(ipAddress + " isn't allowed to register to Discovery server, because it isn't in whitelist");
        }
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
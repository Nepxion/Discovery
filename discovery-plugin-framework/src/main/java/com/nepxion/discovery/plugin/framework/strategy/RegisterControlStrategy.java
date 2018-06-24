package com.nepxion.discovery.plugin.framework.strategy;

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
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.framework.entity.FilterEntity;
import com.nepxion.discovery.plugin.framework.entity.FilterType;
import com.nepxion.discovery.plugin.framework.entity.RegisterEntity;
import com.nepxion.discovery.plugin.framework.entity.RuleEntity;
import com.nepxion.discovery.plugin.framework.exception.PluginException;

public class RegisterControlStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(RegisterControlStrategy.class);

    @Autowired
    private RuleEntity ruleEntity;

    @Autowired
    private ReentrantReadWriteLock reentrantReadWriteLock;

    public void apply(String serviceId, String ipAddress) {
        try {
            reentrantReadWriteLock.readLock().lock();

            applyIpAddressFilter(serviceId, ipAddress);
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }
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
                throw new PluginException(ipAddress + " isn't allowed to register to Eureka server, because it is in blacklist");
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
            throw new PluginException(ipAddress + " isn't allowed to register to Eureka server, because it isn't in whitelist");
        }
    }
}
package com.nepxion.discovery.plugin.core.strategy;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.core.constant.PluginConstant;
import com.nepxion.discovery.plugin.core.entity.DiscoveryEntity;
import com.nepxion.discovery.plugin.core.entity.FilterEntity;
import com.nepxion.discovery.plugin.core.entity.FilterType;
import com.nepxion.discovery.plugin.core.exception.PluginException;

public class FilterStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(FilterStrategy.class);

    @Autowired
    private DiscoveryEntity discoveryEntity;

    @Autowired
    private ReentrantReadWriteLock reentrantReadWriteLock;

    public void apply(String serviceId, String ipAddress) {
        try {
            reentrantReadWriteLock.readLock().lock();

            FilterEntity filterEntity = discoveryEntity.getFilterEntity();
            FilterType filterType = filterEntity.getFilterType();

            String globalFilterValue = filterEntity.getFilterValue();

            Map<String, String> filterMap = filterEntity.getFilterMap();
            String filterValue = filterMap.get(serviceId);

            String allFilter = "";
            if (StringUtils.isNotEmpty(globalFilterValue)) {
                allFilter += globalFilterValue;
            }

            if (StringUtils.isNotEmpty(filterValue)) {
                allFilter += StringUtils.isEmpty(allFilter) ? filterValue : PluginConstant.SEPARATE + filterValue;
            }

            switch (filterType) {
                case BLACKLIST:
                    validateBlacklist(allFilter, ipAddress);
                    break;
                case WHITELIST:
                    validateWhitelist(allFilter, ipAddress);
                    break;
            }

        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }

    private void validateBlacklist(String filterValue, String ipAddress) {
        LOG.info("********** IP address blacklist={}, current ip address={} **********", filterValue, ipAddress);

        String[] filterArray = StringUtils.split(filterValue, PluginConstant.SEPARATE);
        for (String filter : filterArray) {
            if (ipAddress.startsWith(filter)) {
                throw new PluginException(ipAddress + " isn't allowed to register to Eureka server, because it is in blacklist");
            }
        }
    }

    private void validateWhitelist(String filterValue, String ipAddress) {
        LOG.info("********** IP address whitelist={}, current ip address={} **********", filterValue, ipAddress);

        boolean valid = false;
        String[] filterArray = StringUtils.split(filterValue, PluginConstant.SEPARATE);
        for (String filter : filterArray) {
            if (ipAddress.startsWith(filter)) {
                valid = true;
                break;
            }
        }

        if (!valid) {
            throw new PluginException(ipAddress + " isn't allowed to register to Eureka server, because it isn't in whitelist");
        }
    }
}
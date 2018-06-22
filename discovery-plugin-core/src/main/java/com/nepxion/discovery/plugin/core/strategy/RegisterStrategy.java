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
import com.nepxion.discovery.plugin.core.entity.RegisterFilterType;
import com.nepxion.discovery.plugin.core.entity.PluginEntity;
import com.nepxion.discovery.plugin.core.entity.RegisterEntity;
import com.nepxion.discovery.plugin.core.exception.PluginException;

public class RegisterStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(RegisterStrategy.class);

    @Autowired
    private PluginEntity pluginEntity;

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
        RegisterEntity registerEntity = pluginEntity.getRegisterEntity();
        RegisterFilterType filterType = registerEntity.getFilterType();

        String globalFilterValue = registerEntity.getFilterValue();

        Map<String, String> filterMap = registerEntity.getFilterMap();
        String filterValue = filterMap.get(serviceId);

        String allFilterValue = "";
        if (StringUtils.isNotEmpty(globalFilterValue)) {
            allFilterValue += globalFilterValue;
        }

        if (StringUtils.isNotEmpty(filterValue)) {
            allFilterValue += StringUtils.isEmpty(allFilterValue) ? filterValue : PluginConstant.SEPARATE + filterValue;
        }

        switch (filterType) {
            case BLACKLIST:
                validateBlacklist(allFilterValue, ipAddress);
                break;
            case WHITELIST:
                validateWhitelist(allFilterValue, ipAddress);
                break;
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
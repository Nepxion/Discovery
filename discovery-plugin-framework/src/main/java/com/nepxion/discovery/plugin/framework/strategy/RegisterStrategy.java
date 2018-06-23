package com.nepxion.discovery.plugin.framework.strategy;

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

import com.nepxion.discovery.plugin.framework.constant.PluginConstant;
import com.nepxion.discovery.plugin.framework.entity.PluginEntity;
import com.nepxion.discovery.plugin.framework.entity.RegisterEntity;
import com.nepxion.discovery.plugin.framework.entity.RegisterFilterType;
import com.nepxion.discovery.plugin.framework.exception.PluginException;

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
        if (registerEntity == null) {
            return;
        }

        RegisterFilterType filterType = registerEntity.getFilterType();

        String globalFilterValue = registerEntity.getFilterValue();

        Map<String, String> filterMap = registerEntity.getFilterMap();
        String filterValue = filterMap.get(serviceId);

        String allFilterIpAddress = "";
        if (StringUtils.isNotEmpty(globalFilterValue)) {
            allFilterIpAddress += globalFilterValue;
        }

        if (StringUtils.isNotEmpty(filterValue)) {
            allFilterIpAddress += StringUtils.isEmpty(allFilterIpAddress) ? filterValue : PluginConstant.SEPARATE + filterValue;
        }

        switch (filterType) {
            case BLACKLIST:
                validateBlacklist(allFilterIpAddress, ipAddress);
                break;
            case WHITELIST:
                validateWhitelist(allFilterIpAddress, ipAddress);
                break;
        }
    }

    private void validateBlacklist(String filterIpAddress, String ipAddress) {
        LOG.info("********** IP address blacklist={}, current ip address={} **********", filterIpAddress, ipAddress);

        String[] filterArray = StringUtils.split(filterIpAddress, PluginConstant.SEPARATE);
        for (String filter : filterArray) {
            if (ipAddress.startsWith(filter)) {
                throw new PluginException(ipAddress + " isn't allowed to register to Eureka server, because it is in blacklist");
            }
        }
    }

    private void validateWhitelist(String filterIpAddress, String ipAddress) {
        LOG.info("********** IP address whitelist={}, current ip address={} **********", filterIpAddress, ipAddress);

        boolean valid = false;
        String[] filterArray = StringUtils.split(filterIpAddress, PluginConstant.SEPARATE);
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
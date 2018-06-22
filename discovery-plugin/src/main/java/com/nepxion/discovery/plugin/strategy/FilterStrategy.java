package com.nepxion.discovery.plugin.strategy;

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
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.constant.DiscoveryPluginConstant;
import com.nepxion.discovery.plugin.entity.DiscoveryEntity;
import com.nepxion.discovery.plugin.entity.FilterEntity;
import com.nepxion.discovery.plugin.exception.DiscoveryPluginException;

public class FilterStrategy {
    @Autowired
    private DiscoveryEntity discoveryEntity;

    @Autowired
    private ReentrantReadWriteLock reentrantReadWriteLock;

    public void apply(String serviceId, String ipAddress) {
        try {
            reentrantReadWriteLock.readLock().lock();

            FilterEntity filterEntity = discoveryEntity.getFilterEntity();
            String globalFilterValue = filterEntity.getFilterValue();
            validate(globalFilterValue, ipAddress);

            Map<String, String> filterMap = filterEntity.getFilterMap();
            String filterValue = filterMap.get(serviceId);
            validate(filterValue, ipAddress);
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }

    private void validate(String filterValue, String ipAddress) {
        if (StringUtils.isEmpty(filterValue)) {
            return;
        }

        String[] filterArray = StringUtils.split(filterValue, DiscoveryPluginConstant.SEPARATE);
        for (String filter : filterArray) {
            if (ipAddress.startsWith(filter)) {
                throw new DiscoveryPluginException(ipAddress + " isn't allowed to register to Eureka server");
            }
        }
    }
}
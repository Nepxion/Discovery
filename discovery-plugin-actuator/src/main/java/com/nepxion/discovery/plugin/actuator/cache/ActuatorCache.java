package com.nepxion.discovery.plugin.actuator.cache;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class ActuatorCache {
    private static class LazyHolder {
        private static final ActuatorCache INSTANCE = new ActuatorCache();
    }

    private static final LoadingCache<String, String> EUREKA_RULE_CACHE = CacheBuilder.newBuilder()
            .concurrencyLevel(8)
            .expireAfterWrite(1, TimeUnit.DAYS)
            .initialCapacity(10)
            .maximumSize(100)
            .recordStats()
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) throws Exception {
                    return StringUtils.EMPTY;
                }
            });

    private ActuatorCache() {
    }

    public static final ActuatorCache getInstance() {
        return LazyHolder.INSTANCE;
    }

    public boolean put(String serviceId, String rule) {
        EUREKA_RULE_CACHE.put(serviceId, rule);

        return Boolean.TRUE;
    }

    public String get(String serviceId) {
        try {
            return EUREKA_RULE_CACHE.get(serviceId);
        } catch (ExecutionException e) {
            return StringUtils.EMPTY;
        }
    }

    public boolean clear(String serviceId) {
        EUREKA_RULE_CACHE.invalidate(serviceId);

        return Boolean.TRUE;
    }
}
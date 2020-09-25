package com.nepxion.discovery.plugin.framework.cache;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.nepxion.discovery.common.entity.RuleEntity;

public class RuleCache {
    private LoadingCache<String, RuleEntity> loadingCache;

    public RuleCache() {
        loadingCache = Caffeine.newBuilder()
                .expireAfterWrite(365 * 100, TimeUnit.DAYS)
                .initialCapacity(2)
                .maximumSize(10)
                .recordStats()
                .build(new CacheLoader<String, RuleEntity>() {
                    @Override
                    public RuleEntity load(String key) throws Exception {
                        return null;
                    }
                });
    }

    public boolean put(String key, RuleEntity ruleEntity) {
        loadingCache.put(key, ruleEntity);

        return Boolean.TRUE;
    }

    public RuleEntity get(String key) {
        try {
            return loadingCache.get(key);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean clear(String key) {
        loadingCache.invalidate(key);

        return Boolean.TRUE;
    }
}
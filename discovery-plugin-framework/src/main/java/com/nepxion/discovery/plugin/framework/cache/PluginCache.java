package com.nepxion.discovery.plugin.framework.cache;

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

public class PluginCache {
    private LoadingCache<String, String> loadingCache;

    public PluginCache() {
        loadingCache = CacheBuilder.newBuilder()
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
    }

    public boolean put(String key, String value) {
        loadingCache.put(key, value);

        return Boolean.TRUE;
    }

    public String get(String key) {
        try {
            return loadingCache.get(key);
        } catch (ExecutionException e) {
            return StringUtils.EMPTY;
        }
    }

    public boolean clear(String key) {
        loadingCache.invalidate(key);

        return Boolean.TRUE;
    }
}
package com.nepxion.discovery.console.redis.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author JiKai Sun
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.redis.constant.RedisConstant;
import com.nepxion.discovery.common.redis.operation.RedisOperation;
import com.nepxion.discovery.console.adapter.ConfigAdapter;

public class RedisConfigAdapter implements ConfigAdapter {
    @Autowired
    private RedisOperation redisOperation;

    @Override
    public boolean updateConfig(String group, String serviceId, String config) throws Exception {
        return redisOperation.publishConfig(group, serviceId, config);
    }

    @Override
    public boolean clearConfig(String group, String serviceId) throws Exception {
        return redisOperation.removeConfig(group, serviceId);
    }

    @Override
    public String getConfig(String group, String serviceId) throws Exception {
        return redisOperation.getConfig(group, serviceId);
    }

    @Override
    public String getConfigType() {
        return RedisConstant.REDIS_TYPE;
    }
}
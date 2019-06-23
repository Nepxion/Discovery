package com.nepxion.discovery.common.redis.operation;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author JiKai Sun
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisOperation {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private HashOperations<String, String, String> hashOperations;

    public String getConfig(String group, String serviceId) {
        return hashOperations.get(group, serviceId);
    }

    public boolean removeConfig(String group, String serviceId) {
        publishConfig(group, serviceId, StringUtils.EMPTY);

        return hashOperations.delete(group, serviceId) == 1;
    }

    public boolean publishConfig(String group, String serviceId, String config) {
        hashOperations.put(group, serviceId, config);
        stringRedisTemplate.convertAndSend(group + "-" + serviceId, config);

        return true;
    }

    public void subscribeConfig(String config, RedisSubscribeCallback redisSubscribeCallback) {
        redisSubscribeCallback.callback(config);
    }
}
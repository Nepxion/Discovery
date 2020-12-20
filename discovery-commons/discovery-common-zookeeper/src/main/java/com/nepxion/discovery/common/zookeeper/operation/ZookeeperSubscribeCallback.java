package com.nepxion.discovery.common.zookeeper.operation;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author rotten
 * @version 1.0
 */

public interface ZookeeperSubscribeCallback {
    void callback(String config);
}
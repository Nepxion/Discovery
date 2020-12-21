package com.nepxion.discovery.common.zookeeper.operation;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author rotten
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;

public class ZookeeperOperation {
    @Autowired
    private CuratorFramework curatorFramework;

    public String getConfig(String group, String serviceId) throws Exception {
        boolean hasPath = hasPath(group, serviceId);
        if (hasPath) {
            return StringUtils.EMPTY;
        }

        String path = getPath(group, serviceId);

        return convertConfig(path);
    }

    public boolean removeConfig(String group, String serviceId) throws Exception {
        boolean hasPath = hasPath(group, serviceId);
        if (hasPath) {
            return false;
        }

        String path = getPath(group, serviceId);

        curatorFramework.delete().forPath(path);

        return true;
    }

    public boolean publishConfig(String group, String serviceId, String config) throws Exception {
        String path = getPath(group, serviceId);
        byte[] bytes = config.getBytes();

        Stat stat = curatorFramework.checkExists().forPath(path);
        if (stat == null) {
            curatorFramework.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, bytes);
        } else {
            curatorFramework.setData().forPath(path, bytes);
        }

        return true;
    }

    public TreeCacheListener subscribeConfig(String group, String serviceId, ZookeeperSubscribeCallback zookeeperSubscribeCallback) throws Exception {
        String path = getPath(group, serviceId);

        TreeCacheListener configListener = new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent event) throws Exception {
                boolean hasPath = hasPath(group, serviceId);
                if (hasPath) {
                    return;
                }

                String config = convertConfig(curatorFramework, path);

                zookeeperSubscribeCallback.callback(config);
            }
        };

        TreeCache.newBuilder(curatorFramework, path).build().start().getListenable().addListener(configListener);

        return configListener;
    }

    public void unsubscribeConfig(String group, String serviceId, TreeCacheListener configListener) {
        String path = getPath(group, serviceId);

        TreeCache.newBuilder(curatorFramework, path).build().getListenable().removeListener(configListener);
    }

    public boolean hasPath(String group, String serviceId) throws Exception {
        String path = getPath(group, serviceId);

        return curatorFramework.checkExists().forPath(path) == null;
    }

    public String getPath(String group, String serviceId) {
        return String.format("/%s/%s", group, serviceId);
    }

    public String convertConfig(String path) throws Exception {
        return convertConfig(curatorFramework, path);
    }

    public String convertConfig(CuratorFramework curatorFramework, String path) throws Exception {
        byte[] bytes = curatorFramework.getData().forPath(path);

        return new String(bytes);
    }
}
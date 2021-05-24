package com.nepxion.discovery.common.zookeeper.operation;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author rotten
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;

public class ZookeeperOperation implements DisposableBean {
    private static final Logger LOG = LoggerFactory.getLogger(ZookeeperOperation.class);

    @Autowired
    private CuratorFramework curatorFramework;

    public String getConfig(String group, String serviceId) throws Exception {
        String path = getPath(group, serviceId);

        boolean hasPath = hasPath(path);
        if (!hasPath) {
            return null;
        }

        return convertConfig(path);
    }

    public boolean removeConfig(String group, String serviceId) throws Exception {
        String path = getPath(group, serviceId);

        boolean hasPath = hasPath(path);
        if (!hasPath) {
            return false;
        }

        curatorFramework.delete().forPath(path);

        return true;
    }

    public boolean publishConfig(String group, String serviceId, String config) throws Exception {
        byte[] bytes = config.getBytes();
        if (bytes == null) {
            return false;
        }

        String path = getPath(group, serviceId);
        Stat stat = curatorFramework.checkExists().forPath(path);
        if (stat == null) {
            curatorFramework.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, bytes);
        } else {
            curatorFramework.setData().forPath(path, bytes);
        }

        return true;
    }

    public ZookeeperListener subscribeConfig(String group, String serviceId, ZookeeperSubscribeCallback zookeeperSubscribeCallback) throws Exception {
        String path = getPath(group, serviceId);

        NodeCache nodeCache = new NodeCache(curatorFramework, path);
        nodeCache.start(true);

        NodeCacheListener nodeCacheListener = new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                String config = convertConfig(nodeCache);

                zookeeperSubscribeCallback.callback(config);
            }
        };

        ZookeeperListener zookeeperListener = new ZookeeperListener(nodeCache, nodeCacheListener);
        zookeeperListener.addListener();

        return zookeeperListener;
    }

    public void unsubscribeConfig(String group, String serviceId, ZookeeperListener zookeeperListener) throws Exception {
        zookeeperListener.removeListener();
    }

    public String getPath(String group, String serviceId) {
        return String.format("/%s/%s", group, serviceId);
    }

    public boolean hasPath(String path) throws Exception {
        return curatorFramework.checkExists().forPath(path) != null;
    }

    public boolean hasPath(String group, String serviceId) throws Exception {
        String path = getPath(group, serviceId);

        return hasPath(path);
    }

    public String convertConfig(String path) throws Exception {
        return convertConfig(curatorFramework, path);
    }

    public String convertConfig(CuratorFramework curatorFramework, String path) throws Exception {
        byte[] bytes = curatorFramework.getData().forPath(path);
        if (bytes == null) {
            return null;
        }

        return new String(bytes);
    }

    public String convertConfig(NodeCache nodeCache) throws Exception {
        ChildData childData = nodeCache.getCurrentData();
        if (childData == null) {
            return null;
        }

        byte[] bytes = childData.getData();
        if (bytes == null) {
            return null;
        }

        return new String(bytes);
    }

    @Override
    public void destroy() throws Exception {
        curatorFramework.close();

        LOG.info("Shutting down Zookeeper CuratorFramework...");
    }
}
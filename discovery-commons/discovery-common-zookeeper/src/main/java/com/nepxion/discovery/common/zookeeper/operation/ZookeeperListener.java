package com.nepxion.discovery.common.zookeeper.operation;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.IOException;

import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;

public class ZookeeperListener {
    private NodeCache nodeCache;
    private NodeCacheListener nodeCacheListener;

    public ZookeeperListener(NodeCache nodeCache, NodeCacheListener nodeCacheListener) {
        this.nodeCache = nodeCache;
        this.nodeCacheListener = nodeCacheListener;
    }

    public NodeCache getNodeCache() {
        return nodeCache;
    }

    public NodeCacheListener getNodeCacheListener() {
        return nodeCacheListener;
    }

    public void addListener() {
        nodeCache.getListenable().addListener(nodeCacheListener);
    }

    public void removeListener() throws IOException {
        nodeCache.getListenable().removeListener(nodeCacheListener);
        nodeCache.close();
    }
}
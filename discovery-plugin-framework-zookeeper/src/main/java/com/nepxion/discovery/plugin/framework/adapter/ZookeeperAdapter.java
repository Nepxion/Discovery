package com.nepxion.discovery.plugin.framework.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import org.springframework.cloud.zookeeper.discovery.ZookeeperServer;

import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.plugin.framework.constant.ZookeeperConstant;
import com.netflix.loadbalancer.Server;

public class ZookeeperAdapter extends AbstractPluginAdapter {
    // Zookeeper比较特殊，父类中getMetadata().get(groupKey)方法不行，执行该方法的时候Metadata还没初始化
    @Override
    protected String getGroup(String groupKey) {
        return pluginContextAware.getEnvironment().getProperty(ZookeeperConstant.META_DATA + "." + groupKey);
    }

    @Override
    public Map<String, String> getServerMetadata(Server server) {
        if (server instanceof ZookeeperServer) {
            ZookeeperServer zookeeperServer = (ZookeeperServer) server;

            return zookeeperServer.getInstance().getPayload().getMetadata();
        }

        throw new DiscoveryException("Server instance isn't the type of ZookeeperServer");
    }
}
package com.nepxion.discovery.plugin.strategy.isolation;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.listener.loadbalance.AbstractLoadBalanceListener;
import com.netflix.loadbalancer.Server;

// 当目标服务的元数据中的Group和本服务不相等，禁止被本服务负载均衡（只用于DiscoveryClient.getInstances接口方法用）
public class ConsumerIsolationLoadBalanceStrategy extends AbstractLoadBalanceListener {
    @Autowired
    private PluginAdapter pluginAdapter;
    
    @Override
    public void onGetServers(String serviceId, List<? extends Server> servers) {
        Iterator<? extends Server> iterator = servers.iterator();
        while (iterator.hasNext()) {
            Server server = iterator.next();
            String serverGroup = pluginAdapter.getServerMetadata(server).get(DiscoveryConstant.GROUP);
            String group = pluginAdapter.getGroup();
            if (!StringUtils.equals(serverGroup, group)) {
                iterator.remove();
            }
        }
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE - 2;
    }
}
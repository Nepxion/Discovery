package com.nepxion.discovery.plugin.example.service.impl;

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

// 当元数据中的group为mygroup，禁止被负载均衡到
public class MyLoadBalanceListener extends AbstractLoadBalanceListener {
    @Autowired
    private PluginAdapter pluginAdapter;

    @Override
    public void onGetServers(String serviceId, List<? extends Server> servers) {
        Iterator<? extends Server> iterator = servers.iterator();
        while (iterator.hasNext()) {
            Server server = iterator.next();
            String group = pluginAdapter.getServerMetadata(server).get(DiscoveryConstant.GROUP);
            if (StringUtils.equals(group, "mygroup2")) {
                iterator.remove();

                System.out.println("********** 服务名=" + serviceId + "，组名=" + group + "的服务不允许被其它服务负载均衡到");
            }
        }
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE - 1;
    }
}
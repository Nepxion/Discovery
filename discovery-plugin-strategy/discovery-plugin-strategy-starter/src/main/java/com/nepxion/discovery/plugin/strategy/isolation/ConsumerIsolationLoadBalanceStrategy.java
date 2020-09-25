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

import com.nepxion.discovery.common.entity.ServiceType;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.listener.loadbalance.AbstractLoadBalanceListener;
import com.netflix.loadbalancer.Server;

// 当目标服务的元数据中的Group和本服务不相等，禁止被本服务负载均衡。如果是网关，则不做处理
public class ConsumerIsolationLoadBalanceStrategy extends AbstractLoadBalanceListener {
    @Autowired
    protected PluginAdapter pluginAdapter;

    @Override
    public void onGetServers(String serviceId, List<? extends Server> servers) {
        Iterator<? extends Server> iterator = servers.iterator();
        while (iterator.hasNext()) {
            Server server = iterator.next();

            String serverServiceType = pluginAdapter.getServerServiceType(server);
            if (StringUtils.equals(serverServiceType, ServiceType.GATEWAY.toString())) {
                continue;
            }

            String serverGroup = pluginAdapter.getServerGroup(server);
            String group = pluginAdapter.getGroup();
            if (!StringUtils.equals(serverGroup, group)) {
                iterator.remove();
            }
        }
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
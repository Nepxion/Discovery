package com.nepxion.discovery.plugin.strategy.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.entity.ServiceType;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.netflix.loadbalancer.Server;

public class StrategyGroupEnabledFilter extends AbstractStrategyEnabledFilter {
    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_CONSUMER_ISOLATION_ENABLED + ":false}")
    protected Boolean consumerIsolationEnabled;

    @Override
    public boolean apply(List<? extends Server> servers, Server server) {
        if (!consumerIsolationEnabled) {
            return true;
        }

        String serverServiceType = pluginAdapter.getServerServiceType(server);
        if (StringUtils.equals(serverServiceType, ServiceType.GATEWAY.toString())) {
            return true;
        }

        String serverGroup = pluginAdapter.getServerGroup(server);
        String group = pluginAdapter.getGroup();

        return StringUtils.equals(serverGroup, group);
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 2;
    }
}
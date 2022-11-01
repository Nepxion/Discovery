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

import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.netflix.loadbalancer.Server;

public class StrategyAddressEnabledFilter extends AbstractStrategyEnabledFilter {
    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_ADDRESS_FAILOVER_ENABLED + ":false}")
    protected Boolean addressFailoverEnabled;

    @Override
    public boolean apply(List<? extends Server> servers, Server server) {
        String serviceId = pluginAdapter.getServerServiceId(server);

        String addresses = JsonUtil.fromJsonMap(pluginContextHolder.getContextRouteAddress(), serviceId);
        if (StringUtils.isEmpty(addresses)) {
            return true;
        }

        if (addressFailoverEnabled) {
            boolean matched = matchByAddress(servers, addresses);
            if (!matched) {
                String addressFailovers = JsonUtil.fromJsonMap(pluginContextHolder.getContextRouteAddressFailover(), serviceId);
                if (StringUtils.isEmpty(addressFailovers)) {
                    return true;
                } else {
                    return discoveryMatcher.matchAddress(addressFailovers, server.getHost(), server.getPort(), true);
                }
            }
        }

        return discoveryMatcher.matchAddress(addresses, server.getHost(), server.getPort(), true);
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 5;
    }
}
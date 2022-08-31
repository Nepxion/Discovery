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

import com.nepxion.discovery.common.util.JsonUtil;
import com.netflix.loadbalancer.Server;

public class StrategyIdBlacklistEnabledFilter extends AbstractStrategyEnabledFilter {
    @Override
    public boolean apply(List<? extends Server> servers, Server server) {
        String serviceId = pluginAdapter.getServerServiceId(server);

        String ids = JsonUtil.fromJsonMap(pluginContextHolder.getContextRouteIdBlacklist(), serviceId);
        if (StringUtils.isEmpty(ids)) {
            return true;
        }

        String id = pluginAdapter.getServerServiceUUId(server);

        return discoveryMatcher.match(ids, id, false);
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
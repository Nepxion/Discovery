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

public class StrategyEnvironmentEnabledFilter extends AbstractStrategyEnabledFilter {
    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_ENVIRONMENT_FAILOVER_ENABLED + ":false}")
    protected Boolean environmentFailoverEnabled;

    @Override
    public boolean apply(List<? extends Server> servers, Server server) {
        String environment = pluginContextHolder.getContextRouteEnvironment();
        if (StringUtils.isEmpty(environment)) {
            return true;
        }

        String serverEnvironment = pluginAdapter.getServerEnvironment(server);

        boolean found = findByEnvironment(servers, environment);
        if (found) {
            // 匹配到传递过来的环境Header的服务实例，返回匹配的环境的服务实例
            return StringUtils.equals(serverEnvironment, environment);
        } else {
            if (environmentFailoverEnabled) {
                String serviceId = pluginAdapter.getServerServiceId(server);

                // 没有匹配上，则寻址Common环境，返回Common环境的服务实例
                String environmentFailovers = JsonUtil.fromJsonMap(pluginContextHolder.getContextRouteEnvironmentFailover(), serviceId);
                if (StringUtils.isEmpty(environmentFailovers)) {
                    environmentFailovers = StrategyConstant.SPRING_APPLICATION_STRATEGY_ENVIRONMENT_FAILOVER_VALUE;
                }

                return discoveryMatcher.match(environmentFailovers, serverEnvironment, true);
            }
        }

        return StringUtils.equals(serverEnvironment, environment);
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 3;
    }
}
package com.nepxion.discovery.plugin.framework.listener.loadbalance;

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
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.adapter.EnvironmentRouteAdapter;
import com.nepxion.discovery.plugin.framework.context.PluginContextHolder;
import com.netflix.loadbalancer.Server;

public class EnvironmentFilterLoadBalanceListener extends AbstractLoadBalanceListener {
    @Autowired(required = false)
    private PluginContextHolder pluginContextHolder;

    @Autowired(required = false)
    private EnvironmentRouteAdapter environmentRouteAdapter;

    @Value("${" + DiscoveryConstant.SPRING_APPLICATION_ENVIRONMENT_ROUTE + ":" + DiscoveryConstant.SPRING_APPLICATION_ENVIRONMENT_ROUTE_VALUE + "}")
    private String environmentRoute;

    @Override
    public void onGetServers(String serviceId, List<? extends Server> servers) {
        applyEnvironmentFilter(serviceId, servers);
    }

    private void applyEnvironmentFilter(String providerServiceId, List<? extends Server> servers) {
        String environment = pluginAdapter.getEnvironment();
        if (StringUtils.equals(environment, DiscoveryConstant.DEFAULT)) {
            return;
        }

        // 当前服务是Common环境，按照Header传递的环境号匹配
        if (StringUtils.equals(environment, environmentRoute)) {
            String headerEnvironment = pluginContextHolder.getContext(DiscoveryConstant.N_D_ENVIRONMENT);
            // Header环境号未传递进来，则返回，负载均衡执行轮询的提供端实例列表方式
            if (StringUtils.isEmpty(headerEnvironment)) {
                return;
            } else {
                // 匹配Header环境号和提供端实例的环境号是否相同
                // 判断是否有匹配的环境
                boolean validated = validate(servers, headerEnvironment);
                Iterator<? extends Server> iterator = servers.iterator();
                while (iterator.hasNext()) {
                    Server server = iterator.next();
                    String serverEnvironment = pluginAdapter.getServerEnvironment(server);
                    // 如果存在Header环境号匹配的提供端实例，执行环境隔离
                    if (validated) {
                        if (!StringUtils.equals(serverEnvironment, headerEnvironment)) {
                            iterator.remove();
                        }
                        // 如果不存在Header环境号匹配的提供端实例，但也存在Common环境的提供端实例（即Common环境调用Common环境），则取Common环境
                    } else {
                        if (!StringUtils.equals(serverEnvironment, environmentRoute)) {
                            iterator.remove();
                        }
                    }
                }
            }
        } else {
            // 当前服务是Env环境，按照环境号匹配
            // 判断是否有匹配的提供端实例
            boolean validated = validate(servers, environment);
            Iterator<? extends Server> iterator = servers.iterator();
            while (iterator.hasNext()) {
                Server server = iterator.next();
                String serverEnvironment = pluginAdapter.getServerEnvironment(server);
                // 如果存在匹配的提供端实例，执行环境隔离
                if (validated) {
                    // 环境隔离：调用端实例和提供端实例的元数据Metadata环境配置值相等才能调用
                    if (!StringUtils.equals(serverEnvironment, environment)) {
                        iterator.remove();
                    }
                    // 如果不存在匹配的提供端实例，执行环境路由
                } else {
                    // 环境路由：环境隔离下，调用端实例找不到符合条件的提供端实例，把流量路由到一个通用或者备份环境，例如：元数据Metadata环境配置值为common（该值可配置，但不允许为保留值default）
                    if (environmentRouteAdapter != null && environmentRouteAdapter.isRouteEnabled()) {
                        if (!StringUtils.equals(serverEnvironment, environmentRouteAdapter.getEnvironmentRoute())) {
                            iterator.remove();
                        }
                    } else {
                        // 环境路由未开启，移除所有不匹配的实例
                        iterator.remove();
                    }
                }
            }
        }
    }

    // 判断环境是否要路由。只要调用端实例和至少一个提供端实例的元数据Metadata环境配置值相等，就不需要路由
    private boolean validate(List<? extends Server> servers, String environment) {
        for (Server server : servers) {
            String serverEnvironment = pluginAdapter.getServerEnvironment(server);
            if (StringUtils.equals(serverEnvironment, environment)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int getOrder() {
        // After region filter
        return HIGHEST_PRECEDENCE + 3;
    }
}
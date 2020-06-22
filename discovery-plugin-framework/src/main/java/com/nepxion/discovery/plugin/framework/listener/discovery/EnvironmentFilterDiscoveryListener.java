package com.nepxion.discovery.plugin.framework.listener.discovery;

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
import org.springframework.cloud.client.ServiceInstance;

import com.nepxion.discovery.plugin.framework.adapter.EnvironmentRouteAdapter;

public class EnvironmentFilterDiscoveryListener extends AbstractDiscoveryListener {
    @Autowired(required = false)
    private EnvironmentRouteAdapter environmentRouteAdapter;

    @Override
    public void onGetInstances(String serviceId, List<ServiceInstance> instances) {
        applyEnvironmentFilter(serviceId, instances);
    }

    private void applyEnvironmentFilter(String providerServiceId, List<ServiceInstance> instances) {
        String environment = pluginAdapter.getEnvironment();
        boolean validated = validate(instances, environment);
        Iterator<ServiceInstance> iterator = instances.iterator();
        while (iterator.hasNext()) {
            ServiceInstance serviceInstance = iterator.next();
            String instanceEnvironment = pluginAdapter.getInstanceEnvironment(serviceInstance);
            if (validated) {
                // 环境隔离：调用端实例和提供端实例的元数据Metadata环境配置值相等才能调用
                if (!StringUtils.equals(instanceEnvironment, environment)) {
                    iterator.remove();
                }
            } else {
                // 环境路由：环境隔离下，调用端实例找不到符合条件的提供端实例，把流量路由到一个通用或者备份环境，例如：元数据Metadata环境配置值为common（该值可配置，但不允许为保留值default）
                if (environmentRouteAdapter != null && environmentRouteAdapter.isRouteEnabled()) {
                    if (!StringUtils.equals(instanceEnvironment, environmentRouteAdapter.getEnvironmentRoute())) {
                        iterator.remove();
                    }
                } else {
                    // 环境路由未开启，移除所有不匹配的实例
                    iterator.remove();
                }
            }
        }
    }

    // 判断环境是否要路由。只要调用端实例和至少一个提供端实例的元数据Metadata环境配置值相等，就不需要路由
    private boolean validate(List<ServiceInstance> instances, String environment) {
        for (ServiceInstance serviceInstance : instances) {
            String instanceEnvironment = pluginAdapter.getInstanceEnvironment(serviceInstance);
            if (StringUtils.equals(instanceEnvironment, environment)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void onGetServices(List<String> services) {

    }

    @Override
    public int getOrder() {
        // After region filter
        return HIGHEST_PRECEDENCE + 3;
    }
}
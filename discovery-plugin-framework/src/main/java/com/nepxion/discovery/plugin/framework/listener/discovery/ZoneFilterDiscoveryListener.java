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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;

import com.nepxion.discovery.common.constant.DiscoveryConstant;

public class ZoneFilterDiscoveryListener extends AbstractDiscoveryListener {
    @Value("${" + DiscoveryConstant.SPRING_APPLICATION_ZONE_ROUTE_ENABLED + ":true}")
    protected Boolean zoneRouteEnabled;

    @Override
    public void onGetInstances(String serviceId, List<ServiceInstance> instances) {
        applyZoneFilter(serviceId, instances);
    }

    private void applyZoneFilter(String providerServiceId, List<ServiceInstance> instances) {
        String zone = pluginAdapter.getZone();
        if (StringUtils.equals(zone, DiscoveryConstant.DEFAULT)) {
            return;
        }

        boolean validated = validate(instances, zone);
        Iterator<ServiceInstance> iterator = instances.iterator();
        while (iterator.hasNext()) {
            ServiceInstance instance = iterator.next();
            String instanceZone = pluginAdapter.getInstanceZone(instance);
            if (validated) {
                // 可用区存在：执行可用区亲和性，即调用端实例和提供端实例的元数据Metadata的zone配置值相等才能调用
                if (!StringUtils.equals(instanceZone, zone)) {
                    iterator.remove();
                }
            } else {
                // 可用区不存在：路由开关打开，可路由到其它可用区；路由开关关闭，不可路由到其它可用区或者不归属任何可用区，即删除所有zone配置值不相等的提供端实例
                if (!zoneRouteEnabled) {
                    if (!StringUtils.equals(instanceZone, zone)) {
                        iterator.remove();
                    }
                }
            }
        }
    }

    // 判断可用区是否存在。只要调用端实例和至少一个提供端实例的元数据Metadata的zone配置值相等，就表示可用区存在
    private boolean validate(List<ServiceInstance> instances, String zone) {
        for (ServiceInstance instance : instances) {
            String instanceZone = pluginAdapter.getInstanceZone(instance);
            if (StringUtils.equals(instanceZone, zone)) {
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
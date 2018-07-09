package com.nepxion.discovery.plugin.framework.listener.register;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;

import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.cache.RuleCache;
import com.nepxion.discovery.plugin.framework.constant.PluginConstant;
import com.nepxion.discovery.plugin.framework.decorator.DiscoveryClientDecorator;
import com.nepxion.discovery.plugin.framework.entity.CountEntity;
import com.nepxion.discovery.plugin.framework.entity.RegisterEntity;
import com.nepxion.discovery.plugin.framework.entity.RuleEntity;
import com.nepxion.discovery.plugin.framework.event.PluginPublisher;
import com.nepxion.discovery.plugin.framework.event.RegisterFailureEvent;
import com.nepxion.discovery.plugin.framework.exception.PluginException;

public class CountFilterRegisterListener extends AbstractRegisterListener {
    @Autowired
    private RuleCache ruleCache;

    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private PluginPublisher pluginPublisher;

    @Autowired
    private DiscoveryClientDecorator discoveryClient;

    @Override
    public void onRegister(Registration registration) {
        String serviceId = registration.getServiceId();
        String ipAddress = pluginAdapter.getIpAddress(registration);
        int port = pluginAdapter.getPort(registration);

        applyCountFilter(serviceId, ipAddress, port);
    }

    private void applyCountFilter(String serviceId, String ipAddress, int port) {
        RuleEntity ruleEntity = ruleCache.get(PluginConstant.RULE);
        if (ruleEntity == null) {
            return;
        }

        RegisterEntity registerEntity = ruleEntity.getRegisterEntity();
        if (registerEntity == null) {
            return;
        }

        CountEntity countEntity = registerEntity.getCountEntity();
        if (countEntity == null) {
            return;
        }

        Integer globalFilterValue = countEntity.getFilterValue();
        Map<String, Integer> filterMap = countEntity.getFilterMap();
        Integer filterValue = filterMap.get(serviceId);

        // 如果局部值存在，就取局部值，否则取全局值
        Integer maxCount = null;
        if (filterValue != null) {
            maxCount = filterValue;
        } else {
            maxCount = globalFilterValue;
        }

        if (maxCount == null) {
            return;
        }

        int count = discoveryClient.getRealInstances(serviceId).size();
        if (count >= maxCount) {
            onRegisterFailure(maxCount, serviceId, ipAddress, port);
        }
    }

    private void onRegisterFailure(int maxCount, String serviceId, String ipAddress, int port) {
        String description = ipAddress + " isn't allowed to register to Register server, reach max limited count=" + maxCount;

        Boolean registerFailureEventEnabled = pluginContextAware.getEnvironment().getProperty(PluginConstant.SPRING_APPLICATION_REGISTER_FAILURE_EVENT_ENABLED, Boolean.class, Boolean.FALSE);
        if (registerFailureEventEnabled) {
            pluginPublisher.asyncPublish(new RegisterFailureEvent(PluginConstant.REACH_MAX_LIMITED_COUNT, description, serviceId, ipAddress, port));
        }

        throw new PluginException(description);
    }

    @Override
    public void onDeregister(Registration registration) {

    }

    @Override
    public void onSetStatus(Registration registration, String status) {

    }

    @Override
    public void onClose() {

    }
}
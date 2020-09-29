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
import org.springframework.context.annotation.Lazy;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.CountFilterEntity;
import com.nepxion.discovery.common.entity.RegisterEntity;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.plugin.framework.decorator.DiscoveryClientDecorator;
import com.nepxion.discovery.plugin.framework.event.RegisterFailureEvent;

public class CountFilterRegisterListener extends AbstractRegisterListener {
    @Autowired
    @Lazy
    protected DiscoveryClientDecorator discoveryClient;

    @Override
    public void onRegister(Registration registration) {
        String serviceId = pluginAdapter.getServiceId();
        String host = pluginAdapter.getHost();
        int port = pluginAdapter.getPort();

        applyCountFilter(serviceId, host, port);
    }

    private void applyCountFilter(String serviceId, String host, int port) {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity == null) {
            return;
        }

        RegisterEntity registerEntity = ruleEntity.getRegisterEntity();
        if (registerEntity == null) {
            return;
        }

        CountFilterEntity countFilterEntity = registerEntity.getCountFilterEntity();
        if (countFilterEntity == null) {
            return;
        }

        Integer globalFilterValue = countFilterEntity.getFilterValue();
        Map<String, Integer> filterMap = countFilterEntity.getFilterMap();
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
            onRegisterFailure(maxCount, serviceId, host, port);
        }
    }

    private void onRegisterFailure(int maxCount, String serviceId, String host, int port) {
        String description = serviceId + " for " + host + ":" + port + " is rejected to register to Register server, reach max limited count=" + maxCount;

        pluginEventWapper.fireRegisterFailure(new RegisterFailureEvent(DiscoveryConstant.REACH_MAX_LIMITED_COUNT, description, serviceId, host, port));

        throw new DiscoveryException(description);
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

    @Override
    public int getOrder() {
        // 由于通过服务数来判断是否注册满，需要第一优先级执行，否则服务列表已经被其它监听器过滤过了，其数目就不准确了
        // Highest priority
        return HIGHEST_PRECEDENCE;
    }
}
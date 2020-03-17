package com.nepxion.discovery.plugin.example.service.impl;

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

import com.google.common.eventbus.Subscribe;
import com.nepxion.discovery.common.entity.ParameterEntity;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.event.ParameterChangedEvent;
import com.nepxion.discovery.plugin.framework.event.RegisterFailureEvent;
import com.nepxion.eventbus.annotation.EventBus;

@EventBus
public class MySubscriber {
    @Autowired
    private PluginAdapter pluginAdapter;

    @Subscribe
    public void onParameterChanged(ParameterChangedEvent parameterChangedEvent) {
        ParameterEntity parameterEntity = parameterChangedEvent.getParameterEntity();
        String serviceId = pluginAdapter.getServiceId();
        Map<String, String> parameter = null;
        if (parameterEntity != null) {
            Map<String, Map<String, String>> parameterMap = parameterEntity.getParameterMap();
            parameter = parameterMap.get(serviceId);
        }
        System.out.println("========== 获取动态参数, serviceId=" + serviceId + ", parameter=" + parameter);
    }

    @Subscribe
    public void onRegisterFailure(RegisterFailureEvent registerFailureEvent) {
        System.out.println("========== 注册失败, eventType=" + registerFailureEvent.getEventType() + ", eventDescription=" + registerFailureEvent.getEventDescription() + ", serviceId=" + registerFailureEvent.getServiceId() + ", host=" + registerFailureEvent.getHost() + ", port=" + registerFailureEvent.getPort());
    }
}
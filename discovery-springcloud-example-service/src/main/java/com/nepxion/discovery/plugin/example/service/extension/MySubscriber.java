package com.nepxion.discovery.plugin.example.service.extension;

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
import com.nepxion.discovery.common.entity.CustomizationEntity;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.event.CustomizationEvent;
import com.nepxion.discovery.plugin.framework.event.RegisterFailureEvent;
import com.nepxion.eventbus.annotation.EventBus;

@EventBus
public class MySubscriber {
    @Autowired
    private PluginAdapter pluginAdapter;

    @Subscribe
    public void onCustomization(CustomizationEvent customizationEvent) {
        CustomizationEntity customizationEntity = customizationEvent.getCustomizationEntity();
        String serviceId = pluginAdapter.getServiceId();
        if (customizationEntity != null) {
            Map<String, Map<String, String>> customizationMap = customizationEntity.getCustomizationMap();
            Map<String, String> customizationParameter = customizationMap.get(serviceId);
            System.out.println("========== 获取客户化对象, serviceId=" + serviceId + ", customizationParameter=" + customizationParameter);
        } else {
            System.out.println("========== 获取客户化对象, serviceId=" + serviceId + ", customizationEntity=" + customizationEntity);
        }
    }

    @Subscribe
    public void onRegisterFailure(RegisterFailureEvent registerFailureEvent) {
        System.out.println("========== 注册失败, eventType=" + registerFailureEvent.getEventType() + ", eventDescription=" + registerFailureEvent.getEventDescription() + ", serviceId=" + registerFailureEvent.getServiceId() + ", host=" + registerFailureEvent.getHost() + ", port=" + registerFailureEvent.getPort());
    }
}
package com.nepxion.discovery.plugin.example.extension;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.google.common.eventbus.Subscribe;
import com.nepxion.discovery.plugin.framework.event.RegisterFaiureEvent;
import com.nepxion.eventbus.annotation.EventBus;

@EventBus
public class MySubscriber {
    @Subscribe
    public void subscribeRegisterFaiure(RegisterFaiureEvent registerFaiureEvent) {
        System.out.println("========== 注册失败：filterType=" + registerFaiureEvent.getFilterType() + ", serviceId=" + registerFaiureEvent.getServiceId() + ", ipAddress=" + registerFaiureEvent.getIpAddress() + ", port=" + registerFaiureEvent.getPort());
    }
}
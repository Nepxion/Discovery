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
import com.nepxion.discovery.plugin.framework.event.RegisterFailureEvent;
import com.nepxion.eventbus.annotation.EventBus;

@EventBus
public class MySubscriber {
    @Subscribe
    public void onRegisterFailure(RegisterFailureEvent registerFailureEvent) {
        System.out.println("========== 注册失败, eventType=" + registerFailureEvent.getEventType() + ", eventDescription=" + registerFailureEvent.getEventDescription() + ", serviceId=" + registerFailureEvent.getServiceId() + ", host=" + registerFailureEvent.getHost() + ", port=" + registerFailureEvent.getPort());
    }
}
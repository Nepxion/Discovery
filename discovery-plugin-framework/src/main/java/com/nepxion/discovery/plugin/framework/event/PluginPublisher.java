package com.nepxion.discovery.plugin.framework.event;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.eventbus.core.EventControllerFactory;

public class PluginPublisher {
    @Autowired
    private EventControllerFactory eventControllerFactory;

    public void asyncPublish(Object object) {
        eventControllerFactory.getAsyncController().post(object);
    }

    public void syncPublish(Object object) {
        eventControllerFactory.getSyncController().post(object);
    }
}
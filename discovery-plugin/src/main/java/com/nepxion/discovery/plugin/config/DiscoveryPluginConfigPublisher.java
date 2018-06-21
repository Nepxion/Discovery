package com.nepxion.discovery.plugin.config;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.eventbus.core.Event;
import com.nepxion.eventbus.core.EventControllerFactory;

public class DiscoveryPluginConfigPublisher {
    @Autowired
    private EventControllerFactory eventControllerFactory;

    public void publish(InputStream inputStream) {
        eventControllerFactory.getAsyncController().post(new Event(inputStream));
    }
}
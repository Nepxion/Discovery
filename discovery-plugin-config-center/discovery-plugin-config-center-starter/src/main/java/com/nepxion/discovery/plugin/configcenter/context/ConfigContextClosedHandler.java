package com.nepxion.discovery.plugin.configcenter.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import com.nepxion.discovery.plugin.configcenter.loader.RemoteConfigLoader;

public class ConfigContextClosedHandler implements ApplicationListener<ContextClosedEvent> {
    @Autowired(required = false)
    private RemoteConfigLoader remoteConfigLoader;

    private AtomicBoolean flag = new AtomicBoolean(false);

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        if (flag.compareAndSet(false, true)) {
            if (remoteConfigLoader != null) {
                remoteConfigLoader.unsubscribeConfig();
            }
        }
    }
}
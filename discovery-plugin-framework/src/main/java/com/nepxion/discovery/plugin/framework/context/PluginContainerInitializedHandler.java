package com.nepxion.discovery.plugin.framework.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;

public class PluginContainerInitializedHandler implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {
    private int serverPort;

    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        serverPort = event.getEmbeddedServletContainer().getPort();
    }

    public int getPort() {
        return serverPort;
    }
}
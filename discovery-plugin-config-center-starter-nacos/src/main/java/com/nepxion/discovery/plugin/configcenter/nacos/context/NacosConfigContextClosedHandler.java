package com.nepxion.discovery.plugin.configcenter.nacos.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import com.nepxion.discovery.plugin.configcenter.nacos.adapter.NacosConfigAdapter;

public class NacosConfigContextClosedHandler implements ApplicationListener<ContextClosedEvent> {
    @Autowired
    private NacosConfigAdapter nacosConfigAdapter;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        nacosConfigAdapter.close();
    }
}
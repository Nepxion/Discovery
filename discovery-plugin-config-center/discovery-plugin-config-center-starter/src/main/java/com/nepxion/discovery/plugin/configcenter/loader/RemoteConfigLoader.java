package com.nepxion.discovery.plugin.configcenter.loader;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.DisposableBean;

public abstract class RemoteConfigLoader implements ConfigLoader, DisposableBean {
    public abstract void subscribeConfig();

    public abstract void unsubscribeConfig();

    @Override
    public void destroy() throws Exception {
        unsubscribeConfig();
    }
}
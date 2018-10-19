package com.nepxion.discovery.common.scconfig.operation;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author Ankeway
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class SCConfigOperation {
    @Autowired
    private Environment environment;

    public String getConfig(String group, String serviceId) {
        return environment.getProperty(group + "-" + serviceId);
    }

    public boolean removeConfig(String group, String serviceId) {
        throw new UnsupportedOperationException("Unsupported operation for removing config");
    }

    public boolean publishConfig(String group, String serviceId, String config) {
        throw new UnsupportedOperationException("Unsupported operation for publish config");
    }

    public void subscribeConfig(String group, String serviceId, SCConfigSubscribeCallback subscribeCallback) {
        String config = getConfig(group, serviceId);

        subscribeCallback.callback(config);
    }
}
package com.nepxion.discovery.plugin.framework.event;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.nepxion.discovery.common.exception.DiscoveryException;

public class VersionUpdatedEvent implements Serializable {
    private static final long serialVersionUID = 7749946311426379329L;

    private String dynamicVersion;
    private String localVersion;

    public VersionUpdatedEvent(String dynamicVersion) {
        this(dynamicVersion, null);
    }

    public VersionUpdatedEvent(String dynamicVersion, String localVersion) {
        if (StringUtils.isNotEmpty(dynamicVersion)) {
            this.dynamicVersion = dynamicVersion.trim();
        }

        if (StringUtils.isEmpty(this.dynamicVersion)) {
            throw new DiscoveryException("Dynamic version can't be null or empty while updating");
        }

        if (StringUtils.isNotEmpty(localVersion)) {
            this.localVersion = localVersion.trim();
        }
    }

    public String getDynamicVersion() {
        return dynamicVersion;
    }

    public String getLocalVersion() {
        return localVersion;
    }
}
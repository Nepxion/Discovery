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

public class VersionClearedEvent implements Serializable {
    private static final long serialVersionUID = 5079797986381461496L;

    private String localVersion;

    public VersionClearedEvent() {
        this(null);
    }

    public VersionClearedEvent(String localVersion) {
        if (StringUtils.isNotEmpty(localVersion)) {
            this.localVersion = localVersion.trim();
        }
    }

    public String getLocalVersion() {
        return localVersion;
    }
}
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

import com.nepxion.discovery.plugin.framework.exception.PluginException;

public class VersionChangedEvent implements Serializable {
    private static final long serialVersionUID = 5079797986381461496L;

    public enum EventType {
        VERSION_UPDATE,
        VERSION_CLEAR;
    }

    private EventType eventType;
    private String version;

    public VersionChangedEvent(EventType eventType) {
        this(eventType, null);
    }

    public VersionChangedEvent(EventType eventType, String version) {
        if (eventType == EventType.VERSION_UPDATE && StringUtils.isEmpty(version)) {
            throw new PluginException("Version value can't be null or empty while updating");
        }

        this.eventType = eventType;
        if (StringUtils.isNotEmpty(version)) {
            this.version = version.trim();
        }
    }

    public EventType getEventType() {
        return eventType;
    }

    public String getVersion() {
        return version;
    }
}
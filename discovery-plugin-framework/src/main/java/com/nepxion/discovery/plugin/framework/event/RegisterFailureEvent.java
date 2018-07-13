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

public class RegisterFailureEvent implements Serializable {
    private static final long serialVersionUID = -1343084923958294246L;

    private String eventType;
    private String eventDescription;

    private String serviceId;
    private String host;
    private int port;

    public RegisterFailureEvent(String eventType, String eventDescription, String serviceId, String host, int port) {
        this.eventType = eventType;
        this.eventDescription = eventDescription;
        this.serviceId = serviceId;
        this.host = host;
        this.port = port;
    }

    public String getEventType() {
        return eventType;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
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
    private String serviceId;
    private String ipAddress;
    private int port;

    public RegisterFailureEvent(String eventType, String serviceId, String ipAddress, int port) {
        this.eventType = eventType;
        this.serviceId = serviceId;
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public String getEventType() {
        return eventType;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }
}
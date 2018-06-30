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

import com.nepxion.discovery.plugin.framework.entity.FilterType;

public class RegisterFaiureEvent implements Serializable {
    private static final long serialVersionUID = -1343084923958294246L;

    private FilterType filterType;
    private String serviceId;
    private String ipAddress;
    private int port;

    public RegisterFaiureEvent(FilterType filterType, String serviceId, String ipAddress, int port) {
        this.filterType = filterType;
        this.serviceId = serviceId;
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getServiceId() {
        return serviceId;
    }

    public int getPort() {
        return port;
    }

    public FilterType getFilterType() {
        return filterType;
    }
}
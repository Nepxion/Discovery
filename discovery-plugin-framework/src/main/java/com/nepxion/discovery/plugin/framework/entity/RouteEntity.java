package com.nepxion.discovery.plugin.framework.entity;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.nepxion.discovery.plugin.framework.constant.PluginConstant;

public class RouteEntity implements Serializable {
    private static final long serialVersionUID = -4480475963615166799L;

    private String serviceId;
    private String version;
    private String host;
    private int port;

    private List<RouteEntity> nexts = new ArrayList<RouteEntity>();

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<RouteEntity> getNexts() {
        return nexts;
    }

    public void setNexts(List<RouteEntity> nexts) {
        this.nexts = nexts;
    }

    public String toInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[")
                .append(PluginConstant.SERVICE_ID).append("=").append(serviceId).append(", ")
                .append(PluginConstant.VERSION).append("=").append(version).append(", ")
                .append(PluginConstant.HOST).append("=").append(host).append(", ")
                .append(PluginConstant.PORT).append("=").append(port)
                .append("]");

        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object object) {
        return EqualsBuilder.reflectionEquals(this, object);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
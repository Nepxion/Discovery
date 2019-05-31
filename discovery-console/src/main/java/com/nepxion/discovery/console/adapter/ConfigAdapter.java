package com.nepxion.discovery.console.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

public interface ConfigAdapter {
    boolean updateConfig(String group, String serviceId, String config) throws Exception;

    boolean clearConfig(String group, String serviceId) throws Exception;

    String getConfig(String group, String serviceId) throws Exception;

    String getConfigType();
}
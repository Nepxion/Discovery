package com.nepxion.discovery.console.remote;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

public interface ConfigAdapter {
    boolean configUpdate(String group, String serviceId, String config) throws Exception;

    boolean configClear(String group, String serviceId) throws Exception;
}
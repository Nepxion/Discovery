package com.nepxion.discovery.console.resource;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

public interface BlacklistResource {
    String addBlacklist(String serviceId, String host, int port);

    String addBlacklist(String group, String serviceId, String host, int port);

    boolean deleteBlacklist(String serviceId, String serviceUUId);

    boolean deleteBlacklist(String group, String serviceId, String serviceUUId);
}
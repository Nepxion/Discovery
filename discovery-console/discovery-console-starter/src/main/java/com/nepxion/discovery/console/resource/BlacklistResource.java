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
    String addBlacklist(String group, String targetServiceId, String targetHost, int targetPort);

    String addBlacklist(String group, String targetServiceId, String targetServiceUUId);

    boolean deleteBlacklist(String group, String targetServiceId, String targetServiceUUId);

    boolean clearBlacklist(String group);

    String addBlacklist(String group, String serviceId, String targetServiceId, String targetHost, int targetPort);

    String addBlacklist(String group, String serviceId, String targetServiceId, String targetServiceUUId);

    boolean deleteBlacklist(String group, String serviceId, String targetServiceId, String targetServiceUUId);

    boolean clearBlacklist(String group, String serviceId);
}
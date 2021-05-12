package com.nepxion.discovery.console.resource;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import com.nepxion.discovery.common.entity.ResultEntity;

public interface ConfigResource {
    String getConfigType();

    boolean updateRemoteConfig(String group, String serviceId, String config) throws Exception;

    boolean clearRemoteConfig(String group, String serviceId) throws Exception;

    String getRemoteConfig(String group, String serviceId) throws Exception;

    List<ResultEntity> updateConfig(String serviceId, String config, boolean async);

    List<ResultEntity> clearConfig(String serviceId, boolean async);

    List<ResultEntity> viewConfig(String serviceId);
}
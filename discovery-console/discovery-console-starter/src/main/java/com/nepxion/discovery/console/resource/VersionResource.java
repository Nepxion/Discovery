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

public interface VersionResource {
    List<ResultEntity> updateVersion(String serviceId, String version, boolean async);

    List<ResultEntity> clearVersion(String serviceId, String version, boolean async);

    List<ResultEntity> viewVersion(String serviceId);
}
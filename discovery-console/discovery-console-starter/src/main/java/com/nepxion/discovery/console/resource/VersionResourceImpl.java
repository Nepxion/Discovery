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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.common.entity.ResultEntity;
import com.nepxion.discovery.console.rest.VersionClearRestInvoker;
import com.nepxion.discovery.console.rest.VersionUpdateRestInvoker;
import com.nepxion.discovery.console.rest.VersionViewRestInvoker;

public class VersionResourceImpl implements VersionResource {
    @Autowired
    private ServiceResource serviceResource;

    @Autowired
    private RestTemplate consoleRestTemplate;

    @Override
    public List<ResultEntity> updateVersion(String serviceId, String version, boolean async) {
        VersionUpdateRestInvoker versionUpdateRestInvoker = new VersionUpdateRestInvoker(serviceResource, serviceId, consoleRestTemplate, async, version);

        return versionUpdateRestInvoker.invoke();
    }

    @Override
    public List<ResultEntity> clearVersion(String serviceId, String version, boolean async) {
        VersionClearRestInvoker versionClearRestInvoker = new VersionClearRestInvoker(serviceResource, serviceId, consoleRestTemplate, async, version);

        return versionClearRestInvoker.invoke();
    }

    @Override
    public List<ResultEntity> viewVersion(String serviceId) {
        VersionViewRestInvoker versionViewRestInvoker = new VersionViewRestInvoker(serviceResource, serviceId, consoleRestTemplate);

        return versionViewRestInvoker.invoke();
    }
}
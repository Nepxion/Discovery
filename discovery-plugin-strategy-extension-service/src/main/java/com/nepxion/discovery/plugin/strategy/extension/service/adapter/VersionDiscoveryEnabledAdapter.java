package com.nepxion.discovery.plugin.strategy.extension.service.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.adapter.AbstractVersionDiscoveryEnabledAdapter;

public class VersionDiscoveryEnabledAdapter extends AbstractVersionDiscoveryEnabledAdapter {
    @Override
    protected String getVersionJson() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }

        return request.getHeader(DiscoveryConstant.VERSION);
    }

    public HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }

        return attributes.getRequest();
    }
}
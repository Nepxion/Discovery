package com.nepxion.discovery.plugin.strategy.extension.zuul.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import javax.servlet.http.HttpServletRequest;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.adapter.AbstractVersionDiscoveryEnabledAdapter;
import com.netflix.zuul.context.RequestContext;

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
        RequestContext context = RequestContext.getCurrentContext();

        return context.getRequest();
    }
}
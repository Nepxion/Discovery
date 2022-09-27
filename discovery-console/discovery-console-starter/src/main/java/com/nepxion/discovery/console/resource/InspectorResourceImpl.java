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

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.InspectorEntity;
import com.nepxion.discovery.common.util.StringUtil;
import com.nepxion.discovery.common.util.UrlUtil;

public class InspectorResourceImpl implements InspectorResource {
    @Autowired
    private RestTemplate loadBalancedRestTemplate;

    @Override
    public String inspect(String protocol, String portalId, String contextPath, String services) {
        List<String> serviceList = StringUtil.splitToList(services);

        String url = protocol + "://" + portalId + UrlUtil.formatContextPath(contextPath) + DiscoveryConstant.INSPECTOR_ENDPOINT_URL;

        InspectorEntity inspectorEntity = new InspectorEntity();
        inspectorEntity.setServiceIdList(serviceList);

        return loadBalancedRestTemplate.postForEntity(url, inspectorEntity, InspectorEntity.class).getBody().getResult();
    }
}
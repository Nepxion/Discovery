package com.nepxion.discovery.console.resource;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.InspectorEntity;
import com.nepxion.discovery.common.util.PluginInfoUtil;
import com.nepxion.discovery.common.util.UrlUtil;

public class InspectorResourceImpl implements InspectorResource {
    @Autowired
    private RestTemplate loadBalancedRestTemplate;

    @Override
    public String inspect(String protocol, String portal, String path, List<String> service, Map<String, String> header, List<String> filter) {
        String url = protocol + "://" + portal + UrlUtil.formatContextPath(path) + DiscoveryConstant.INSPECTOR_ENDPOINT_URL;

        InspectorEntity inspectorEntity = new InspectorEntity();
        inspectorEntity.setServiceIdList(service);

        if (MapUtils.isNotEmpty(header)) {
            HttpHeaders headers = new HttpHeaders();
            for (Map.Entry<String, String> entry : header.entrySet()) {
                headers.add(entry.getKey(), entry.getValue());
            }

            HttpEntity<InspectorEntity> requestEntity = new HttpEntity<InspectorEntity>(inspectorEntity, headers);

            String result = loadBalancedRestTemplate.exchange(url, HttpMethod.POST, requestEntity, InspectorEntity.class, new HashMap<String, String>()).getBody().getResult();

            return PluginInfoUtil.extractAll(result, filter);
        }

        String result = loadBalancedRestTemplate.postForEntity(url, inspectorEntity, InspectorEntity.class).getBody().getResult();

        return PluginInfoUtil.extractAll(result, filter);
    }

    @Override
    public List<Map<String, String>> inspectToList(String protocol, String portal, String path, List<String> service, Map<String, String> header, List<String> filter) {
        String result = inspect(protocol, portal, path, service, header, filter);

        return PluginInfoUtil.assembleAll(result, filter);
    }
}
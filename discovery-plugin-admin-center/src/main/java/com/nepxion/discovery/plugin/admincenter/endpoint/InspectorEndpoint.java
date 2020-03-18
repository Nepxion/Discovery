package com.nepxion.discovery.plugin.admincenter.endpoint;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.common.entity.InspectorEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;

@RestController
@RequestMapping(path = "/inspector")
@Api(tags = { "全链路侦测接口" })
public class InspectorEndpoint {
    @Autowired
    private RestTemplate pluginRestTemplate;

    @Autowired
    private PluginAdapter pluginAdapter;

    @RequestMapping(path = "/inspect", method = RequestMethod.POST)
    @ApiOperation(value = "侦测全链路路由", notes = "", response = InspectorEntity.class, httpMethod = "POST")
    @ResponseBody
    public InspectorEntity inspect(@RequestBody @ApiParam(value = "侦测对象", required = true) InspectorEntity inspectorEntity) {
        List<String> serviceIdList = inspectorEntity.getServiceIdList();
        String result = inspectorEntity.getResult();
        inspectorEntity.setResult(pluginAdapter.getPluginInfo(result));

        if (CollectionUtils.isNotEmpty(serviceIdList)) {
            String serviceId = serviceIdList.get(0);
            String url = "http://" + serviceId + "/inspector/inspect";

            serviceIdList.remove(0);

            try {
                return pluginRestTemplate.postForEntity(url, inspectorEntity, InspectorEntity.class).getBody();
            } catch (RestClientException e) {
                throw new DiscoveryException("Failed to execute to inspect, serviceId=" + serviceId + ", url=" + url, e);
            }
        } else {
            return inspectorEntity;
        }
    }
}
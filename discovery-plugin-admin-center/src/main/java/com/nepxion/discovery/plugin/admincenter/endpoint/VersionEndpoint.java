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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.actuate.endpoint.mvc.MvcEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.event.PluginEventWapper;
import com.nepxion.discovery.plugin.framework.event.VersionChangedEvent;

@RestController
@Api(tags = { "版本接口" })
@ManagedResource(description = "Version Endpoint")
public class VersionEndpoint implements MvcEndpoint {
    @Autowired
    private PluginContextAware pluginContextAware;

    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private PluginEventWapper pluginEventWapper;

    @RequestMapping(path = "/version/send", method = RequestMethod.POST)
    @ApiOperation(value = "设置服务的动态版本", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<?> send(@RequestBody @ApiParam(value = "版本号", required = true) String version) {
        Boolean discoveryControlEnabled = pluginContextAware.isDiscoveryControlEnabled();
        if (!discoveryControlEnabled) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Discovery control is disabled");
        }

        pluginEventWapper.fireVersionChanged(new VersionChangedEvent(VersionChangedEvent.EventType.VERSION_UPDATE, version), true);

        return ResponseEntity.ok().body("OK");
    }

    @RequestMapping(path = "/version/clear", method = RequestMethod.GET)
    @ApiOperation(value = "清除服务的动态版本", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<?> clear() {
        Boolean discoveryControlEnabled = pluginContextAware.isDiscoveryControlEnabled();
        if (!discoveryControlEnabled) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Discovery control is disabled");
        }

        pluginEventWapper.fireVersionChanged(new VersionChangedEvent(VersionChangedEvent.EventType.VERSION_CLEAR), true);

        return ResponseEntity.ok().body("OK");
    }

    @RequestMapping(path = "/version/view", method = RequestMethod.GET)
    @ApiOperation(value = "查看服务的本地版本和动态版本", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<List<String>> view() {
        List<String> versionList = new ArrayList<String>(2);

        String localVersion = pluginAdapter.getLocalVersion();
        String dynamicVersion = pluginAdapter.getDynamicVersion();

        versionList.add(StringUtils.isNotEmpty(localVersion) ? localVersion : StringUtils.EMPTY);
        versionList.add(StringUtils.isNotEmpty(dynamicVersion) ? dynamicVersion : StringUtils.EMPTY);

        return ResponseEntity.ok().body(versionList);
    }

    @Override
    public String getPath() {
        return "/";
    }

    @Override
    public boolean isSensitive() {
        return true;
    }

    @Override
    public Class<? extends Endpoint<?>> getEndpointType() {
        return null;
    }
}
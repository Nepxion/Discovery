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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.event.PluginEventWapper;
import com.nepxion.discovery.plugin.framework.event.VersionClearedEvent;
import com.nepxion.discovery.plugin.framework.event.VersionUpdatedEvent;

@RestController
@RequestMapping(path = "/version")
@Api(tags = { "版本接口" })
public class VersionEndpoint {
    @Autowired
    private PluginContextAware pluginContextAware;

    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private PluginEventWapper pluginEventWapper;

    @RequestMapping(path = "/update-async", method = RequestMethod.POST)
    @ApiOperation(value = "异步更新服务的动态版本", notes = "根据指定的localVersion更新服务的dynamicVersion。如果输入的localVersion不匹配服务的localVersion，则忽略；如果如果输入的localVersion为空，则直接更新服务的dynamicVersion", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> updateAsync(@RequestBody @ApiParam(value = "版本号，格式为[dynamicVersion]或者[dynamicVersion];[localVersion]", required = true) String version) {
        return update(version, true);
    }

    @RequestMapping(path = "/update-sync", method = RequestMethod.POST)
    @ApiOperation(value = "同步更新服务的动态版本", notes = "根据指定的localVersion更新服务的dynamicVersion。如果输入的localVersion不匹配服务的localVersion，则忽略；如果如果输入的localVersion为空，则直接更新服务的dynamicVersion", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> updateSync(@RequestBody @ApiParam(value = "版本号，格式为[dynamicVersion]或者[dynamicVersion];[localVersion]", required = true) String version) {
        return update(version, false);
    }

    @RequestMapping(path = "/clear-async", method = RequestMethod.POST)
    @ApiOperation(value = "异步清除服务的动态版本", notes = "根据指定的localVersion清除服务的dynamicVersion。如果输入的localVersion不匹配服务的localVersion，则忽略；如果如果输入的localVersion为空，则直接清除服务的dynamicVersion", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> clearAsync(@RequestBody(required = false) @ApiParam(value = "版本号，指localVersion，可以为空") String version) {
        return clear(version, true);
    }

    @RequestMapping(path = "/clear-sync", method = RequestMethod.POST)
    @ApiOperation(value = "同步清除服务的动态版本", notes = "根据指定的localVersion清除服务的dynamicVersion。如果输入的localVersion不匹配服务的localVersion，则忽略；如果如果输入的localVersion为空，则直接清除服务的dynamicVersion", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> clearSync(@RequestBody(required = false) @ApiParam(value = "版本号，指localVersion，可以为空") String version) {
        return clear(version, false);
    }

    @RequestMapping(path = "/view", method = RequestMethod.GET)
    @ApiOperation(value = "查看服务的本地版本和动态版本", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<List<String>> view() {
        return view(false);
    }

    private ResponseEntity<?> update(String version, boolean async) {
        Boolean discoveryControlEnabled = pluginContextAware.isDiscoveryControlEnabled();
        if (!discoveryControlEnabled) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Discovery control is disabled");
        }

        Boolean isConfigRestControlEnabled = pluginContextAware.isConfigRestControlEnabled();
        if (!isConfigRestControlEnabled) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Config rest control is disabled");
        }

        if (StringUtils.isEmpty(version)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Version can't be null or empty");
        }

        String dynamicVersion = null;
        String localVersion = null;
        String[] versionArray = StringUtils.split(version, DiscoveryConstant.SEPARATE);
        if (versionArray.length == 2) {
            dynamicVersion = versionArray[0];
            localVersion = versionArray[1];
        } else if (versionArray.length == 1) {
            dynamicVersion = versionArray[0];
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid version format, it must be '[dynamicVersion]' or '[dynamicVersion];[localVersion]'");
        }

        pluginEventWapper.fireVersionUpdated(new VersionUpdatedEvent(dynamicVersion, localVersion), async);

        return ResponseEntity.ok().body(DiscoveryConstant.OK);
    }

    private ResponseEntity<?> clear(String version, boolean async) {
        Boolean discoveryControlEnabled = pluginContextAware.isDiscoveryControlEnabled();
        if (!discoveryControlEnabled) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Discovery control is disabled");
        }

        Boolean isConfigRestControlEnabled = pluginContextAware.isConfigRestControlEnabled();
        if (!isConfigRestControlEnabled) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Config rest control is disabled");
        }

        // 修复Swagger的一个Bug，当在Swagger界面不输入版本号的时候，传到后端变成了“{}”
        if (StringUtils.isNotEmpty(version) && StringUtils.equals(version.trim(), "{}")) {
            version = null;
        }

        pluginEventWapper.fireVersionCleared(new VersionClearedEvent(version), async);

        return ResponseEntity.ok().body(DiscoveryConstant.OK);
    }

    private ResponseEntity<List<String>> view(boolean async) {
        List<String> versionList = new ArrayList<String>(2);

        String localVersion = pluginAdapter.getLocalVersion();
        String dynamicVersion = pluginAdapter.getDynamicVersion();

        versionList.add(StringUtils.isNotEmpty(localVersion) ? localVersion : StringUtils.EMPTY);
        versionList.add(StringUtils.isNotEmpty(dynamicVersion) ? dynamicVersion : StringUtils.EMPTY);

        return ResponseEntity.ok().body(versionList);
    }
}
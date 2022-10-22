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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.common.util.ResponseUtil;
import com.nepxion.discovery.plugin.admincenter.resource.VersionResource;

@RestController
@RequestMapping(path = "/version")
@Api(tags = { "版本接口" })
public class VersionEndpoint {
    @Autowired
    private VersionResource versionResource;

    @RequestMapping(path = "/update-async", method = RequestMethod.POST)
    @ApiOperation(value = "异步更新动态版本", notes = "根据指定的localVersion更新服务的dynamicVersion。如果输入的localVersion不匹配服务的localVersion，则忽略；如果如果输入的localVersion为空，则直接更新服务的dynamicVersion", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> updateAsync(@RequestBody @ApiParam(value = "版本号，格式为${dynamicVersion}或者${dynamicVersion};${localVersion}", required = true) String version) {
        return doUpdate(version, true);
    }

    @RequestMapping(path = "/update-sync", method = RequestMethod.POST)
    @ApiOperation(value = "同步更新动态版本", notes = "根据指定的localVersion更新服务的dynamicVersion。如果输入的localVersion不匹配服务的localVersion，则忽略；如果如果输入的localVersion为空，则直接更新服务的dynamicVersion", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> updateSync(@RequestBody @ApiParam(value = "版本号，格式为${dynamicVersion}或者${dynamicVersion};${localVersion}", required = true) String version) {
        return doUpdate(version, false);
    }

    @RequestMapping(path = "/clear-async", method = RequestMethod.POST)
    @ApiOperation(value = "异步清除动态版本", notes = "根据指定的localVersion清除服务的dynamicVersion。如果输入的localVersion不匹配服务的localVersion，则忽略；如果如果输入的localVersion为空，则直接清除服务的dynamicVersion", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> clearAsync(@RequestBody(required = false) @ApiParam(value = "版本号，指localVersion，可以为空") String version) {
        return doClear(version, true);
    }

    @RequestMapping(path = "/clear-sync", method = RequestMethod.POST)
    @ApiOperation(value = "同步清除动态版本", notes = "根据指定的localVersion清除服务的dynamicVersion。如果输入的localVersion不匹配服务的localVersion，则忽略；如果如果输入的localVersion为空，则直接清除服务的dynamicVersion", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> clearSync(@RequestBody(required = false) @ApiParam(value = "版本号，指localVersion，可以为空") String version) {
        return doClear(version, false);
    }

    @RequestMapping(path = "/view", method = RequestMethod.GET)
    @ApiOperation(value = "查看本地版本和动态版本", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> view() {
        return doView();
    }

    private ResponseEntity<?> doUpdate(String version, boolean async) {
        try {
            versionResource.update(version, async);

            return ResponseUtil.getSuccessResponse(true);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doClear(String version, boolean async) {
        try {
            versionResource.clear(version, async);

            return ResponseUtil.getSuccessResponse(true);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doView() {
        try {
            List<String> versionList = versionResource.view();

            return ResponseUtil.getSuccessResponse(versionList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }
}
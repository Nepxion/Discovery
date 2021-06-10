package com.nepxion.discovery.console.endpoint;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.common.entity.ResultEntity;
import com.nepxion.discovery.common.util.ResponseUtil;
import com.nepxion.discovery.console.resource.VersionResource;

@RestController
@RequestMapping(path = "/version")
@Api(tags = { "版本接口" })
public class VersionEndpoint {
    @Autowired
    private VersionResource versionResource;

    @RequestMapping(path = "/update-async/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量异步更新动态版本", notes = "根据指定的localVersion更新服务的dynamicVersion。如果输入的localVersion不匹配服务的localVersion，则忽略；如果如果输入的localVersion为空，则直接更新服务的dynamicVersion", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> versionUpdateAsync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "版本号，格式为${dynamicVersion}或者${dynamicVersion};${localVersion}", required = true) String version) {
        return doVersionUpdate(serviceId, version, true);
    }

    @RequestMapping(path = "/update-sync/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量同步更新动态版本", notes = "根据指定的localVersion更新服务的dynamicVersion。如果输入的localVersion不匹配服务的localVersion，则忽略；如果如果输入的localVersion为空，则直接更新服务的dynamicVersion", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> versionUpdateSync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "版本号，格式为${dynamicVersion}或者${dynamicVersion};${localVersion}", required = true) String version) {
        return doVersionUpdate(serviceId, version, false);
    }

    @RequestMapping(path = "/clear-async/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量异步清除动态版本", notes = "根据指定的localVersion清除服务的dynamicVersion。如果输入的localVersion不匹配服务的localVersion，则忽略；如果如果输入的localVersion为空，则直接清除服务的dynamicVersion", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> versionClearAsync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody(required = false) @ApiParam(value = "版本号，指localVersion，可以为空") String version) {
        return doVersionClear(serviceId, version, true);
    }

    @RequestMapping(path = "/clear-sync/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量同步清除动态版本", notes = "根据指定的localVersion清除服务的dynamicVersion。如果输入的localVersion不匹配服务的localVersion，则忽略；如果如果输入的localVersion为空，则直接清除服务的dynamicVersion", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> versionClearSync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody(required = false) @ApiParam(value = "版本号，指localVersion，可以为空") String version) {
        return doVersionClear(serviceId, version, false);
    }

    @RequestMapping(path = "/view/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "批量查看版本", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> versionView(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return doVersionView(serviceId);
    }

    private ResponseEntity<?> doVersionUpdate(String serviceId, String version, boolean async) {
        try {
            List<ResultEntity> resultEntityList = versionResource.updateVersion(serviceId, version, async);

            return ResponseUtil.getSuccessResponse(resultEntityList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doVersionClear(String serviceId, String version, boolean async) {
        try {
            List<ResultEntity> resultEntityList = versionResource.clearVersion(serviceId, version, async);

            return ResponseUtil.getSuccessResponse(resultEntityList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doVersionView(String serviceId) {
        try {
            List<ResultEntity> resultEntityList = versionResource.viewVersion(serviceId);

            return ResponseUtil.getSuccessResponse(resultEntityList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }
}
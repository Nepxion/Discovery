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
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.common.entity.InspectorDebugEntity;
import com.nepxion.discovery.common.util.ResponseUtil;
import com.nepxion.discovery.console.resource.InspectorResource;

@RestController
@RequestMapping(path = "/inspector")
@Api(tags = { "侦测接口" })
public class InspectorEndpoint {
    @Autowired
    private InspectorResource inspectorResource;

    @RequestMapping(path = "/inspect", method = RequestMethod.POST)
    @ApiOperation(value = "侦测调试全链路路由，返回字符串格式", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> inspect(@RequestBody @ApiParam(value = "侦测调试对象", required = true) InspectorDebugEntity inspectorDebugEntity) {
        return doInspect(inspectorDebugEntity);
    }

    @RequestMapping(path = "/inspect-to-list", method = RequestMethod.POST)
    @ApiOperation(value = "侦测调试全链路路由，返回结构化格式", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> inspectToList(@RequestBody @ApiParam(value = "侦测调试对象", required = true) InspectorDebugEntity inspectorDebugEntity) {
        return doInspectToList(inspectorDebugEntity);
    }

    private ResponseEntity<?> doInspect(InspectorDebugEntity inspectorDebugEntity) {
        try {
            String result = inspectorResource.inspect(inspectorDebugEntity.getProtocol(), inspectorDebugEntity.getPortal(), inspectorDebugEntity.getPath(), inspectorDebugEntity.getService(), inspectorDebugEntity.getHeader(), inspectorDebugEntity.getFilter());

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doInspectToList(InspectorDebugEntity inspectorDebugEntity) {
        try {
            List<Map<String, String>> result = inspectorResource.inspectToList(inspectorDebugEntity.getProtocol(), inspectorDebugEntity.getPortal(), inspectorDebugEntity.getPath(), inspectorDebugEntity.getService(), inspectorDebugEntity.getHeader(), inspectorDebugEntity.getFilter());

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }
}
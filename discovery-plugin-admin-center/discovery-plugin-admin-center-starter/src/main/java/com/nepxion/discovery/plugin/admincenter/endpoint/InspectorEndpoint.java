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

import com.nepxion.discovery.common.entity.InspectorEntity;
import com.nepxion.discovery.common.util.ResponseUtil;
import com.nepxion.discovery.plugin.admincenter.resource.InspectorResource;

@RestController
@RequestMapping(path = "/inspector")
@Api(tags = { "侦测接口" })
public class InspectorEndpoint {
    @Autowired
    private InspectorResource inspectorResource;

    @RequestMapping(path = "/inspect", method = RequestMethod.POST)
    @ApiOperation(value = "侦测全链路路由", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> inspect(@RequestBody @ApiParam(value = "侦测对象", required = true) InspectorEntity inspectorEntity) {
        return doInspect(inspectorEntity);
    }

    @RequestMapping(path = "/inspect-service", method = RequestMethod.POST)
    @ApiOperation(value = "侦测全链路路由", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> inspect(@RequestBody @ApiParam(value = "侦测服务列表", required = true) List<String> service) {
        return doInspect(service);
    }

    private ResponseEntity<?> doInspect(InspectorEntity inspectorEntity) {
        try {
            InspectorEntity result = inspectorResource.inspect(inspectorEntity);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doInspect(List<String> service) {
        try {
            String result = inspectorResource.inspect(service);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }
}
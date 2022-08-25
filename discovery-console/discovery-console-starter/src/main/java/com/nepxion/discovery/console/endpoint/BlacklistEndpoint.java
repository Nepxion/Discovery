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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.common.entity.AddressEntity;
import com.nepxion.discovery.common.util.ResponseUtil;
import com.nepxion.discovery.console.resource.BlacklistResource;

@RestController
@RequestMapping(path = "/blacklist")
@Api(tags = { "下线黑名单接口" })
public class BlacklistEndpoint {
    @Autowired
    private BlacklistResource blacklistResource;

    @RequestMapping(path = "/add/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "根据服务实例IP地址和端口，添加下线的服务实例UUId到黑名单", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> addBlacklist(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "IP地址和端口对象", required = true) AddressEntity addressEntity) {
        return doAddBlacklist(serviceId, addressEntity);
    }

    @RequestMapping(path = "/add/{group}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "根据服务实例IP地址和端口，添加下线的服务实例UUId到黑名单", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> addBlacklist(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "IP地址和端口对象", required = true) AddressEntity addressEntity) {
        return doAddBlacklist(group, serviceId, addressEntity);
    }

    @RequestMapping(path = "/delete/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "根据服务实例UUId，从黑名单删除过期的服务实例", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> deleteBlacklist(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "服务实例UUId", required = true) String serviceUUId) {
        return doDeleteBlacklist(serviceId, serviceUUId);
    }

    @RequestMapping(path = "/delete/{group}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "根据服务实例UUId，从黑名单删除过期的服务实例", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> deleteBlacklist(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "服务实例UUId", required = true) String serviceUUId) {
        return doDeleteBlacklist(group, serviceId, serviceUUId);
    }

    private ResponseEntity<?> doAddBlacklist(String serviceId, AddressEntity addressEntity) {
        try {
            String result = blacklistResource.addBlacklist(serviceId, addressEntity.getHost(), addressEntity.getPort());

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doAddBlacklist(String group, String serviceId, AddressEntity addressEntity) {
        try {
            String result = blacklistResource.addBlacklist(group, serviceId, addressEntity.getHost(), addressEntity.getPort());

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doDeleteBlacklist(String serviceId, String serviceUUId) {
        try {
            boolean result = blacklistResource.deleteBlacklist(serviceId, serviceUUId);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doDeleteBlacklist(String group, String serviceId, String serviceUUId) {
        try {
            boolean result = blacklistResource.deleteBlacklist(group, serviceId, serviceUUId);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }
}
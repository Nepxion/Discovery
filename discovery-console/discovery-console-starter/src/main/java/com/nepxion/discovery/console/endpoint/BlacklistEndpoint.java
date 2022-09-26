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
@Api(tags = { "无损下线黑名单接口" })
public class BlacklistEndpoint {
    @Autowired
    private BlacklistResource blacklistResource;

    @RequestMapping(path = "/add-address/{group}/{targetServiceId}", method = RequestMethod.POST)
    @ApiOperation(value = "全局订阅方式，根据服务实例IP地址和端口，添加下线的服务实例到黑名单", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> addBlacklist(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "targetServiceId") @ApiParam(value = "待下线实例的服务名", required = true) String targetServiceId, @RequestBody @ApiParam(value = "待下线实例的IP地址和端口", required = true) AddressEntity targetAddressEntity) {
        return doAddBlacklist(group, targetServiceId, targetAddressEntity);
    }

    @RequestMapping(path = "/add-uuid/{group}/{targetServiceId}", method = RequestMethod.POST)
    @ApiOperation(value = "全局订阅方式，根据服务实例UUId，添加下线的服务实例到黑名单", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> addBlacklist(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "targetServiceId") @ApiParam(value = "待下线实例的服务名", required = true) String targetServiceId, @RequestBody @ApiParam(value = "待下线实例的UUId", required = true) String targetServiceUUId) {
        return doAddBlacklist(group, targetServiceId, targetServiceUUId);
    }

    @RequestMapping(path = "/delete/{group}/{targetServiceId}/{targetServiceUUId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "全局订阅方式，根据服务实例UUId，从黑名单删除过期的服务实例", notes = "", response = ResponseEntity.class, httpMethod = "DELETE")
    @ResponseBody
    public ResponseEntity<?> deleteBlacklist(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "targetServiceId") @ApiParam(value = "已下线实例的服务名", required = true) String targetServiceId, @PathVariable(value = "targetServiceUUId") @ApiParam(value = "已下线实例的UUId", required = true) String targetServiceUUId) {
        return doDeleteBlacklist(group, targetServiceId, targetServiceUUId);
    }

    @RequestMapping(path = "/clear/{group}", method = RequestMethod.POST)
    @ApiOperation(value = "全局订阅方式，从黑名单清除所有过期的服务实例", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> clearBlacklist(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group) {
        return doClearBlacklist(group);
    }

    @RequestMapping(path = "/add-address/{group}/{serviceId}/{targetServiceId}", method = RequestMethod.POST)
    @ApiOperation(value = "局部订阅方式，根据服务实例IP地址和端口，添加下线的服务实例到黑名单", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> addBlacklist(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @PathVariable(value = "targetServiceId") @ApiParam(value = "待下线实例的服务名", required = true) String targetServiceId, @RequestBody @ApiParam(value = "待下线实例的IP地址和端口", required = true) AddressEntity targetAddressEntity) {
        return doAddBlacklist(group, serviceId, targetServiceId, targetAddressEntity);
    }

    @RequestMapping(path = "/add-uuid/{group}/{serviceId}/{targetServiceId}", method = RequestMethod.POST)
    @ApiOperation(value = "局部订阅方式，根据服务实例UUId，添加下线的服务实例到黑名单", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> addBlacklist(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @PathVariable(value = "targetServiceId") @ApiParam(value = "待下线实例的服务名", required = true) String targetServiceId, @RequestBody @ApiParam(value = "待下线实例的UUId", required = true) String targetServiceUUId) {
        return doAddBlacklist(group, serviceId, targetServiceId, targetServiceUUId);
    }

    @RequestMapping(path = "/delete/{group}/{serviceId}/{targetServiceId}/{targetServiceUUId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "局部订阅方式，根据服务实例UUId，从黑名单删除过期的服务实例", notes = "", response = ResponseEntity.class, httpMethod = "DELETE")
    @ResponseBody
    public ResponseEntity<?> deleteBlacklist(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @PathVariable(value = "targetServiceId") @ApiParam(value = "已下线实例的服务名", required = true) String targetServiceId, @PathVariable(value = "targetServiceUUId") @ApiParam(value = "已下线实例的UUId", required = true) String targetServiceUUId) {
        return doDeleteBlacklist(group, serviceId, targetServiceId, targetServiceUUId);
    }

    @RequestMapping(path = "/clear/{group}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "局部订阅方式，从黑名单清除所有过期的服务实例", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> clearBlacklist(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return doClearBlacklist(group, serviceId);
    }

    private ResponseEntity<?> doAddBlacklist(String group, String targetServiceId, AddressEntity targetAddressEntity) {
        try {
            String result = blacklistResource.addBlacklist(group, targetServiceId, targetAddressEntity.getHost(), targetAddressEntity.getPort());

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doAddBlacklist(String group, String targetServiceId, String targetServiceUUId) {
        try {
            String result = blacklistResource.addBlacklist(group, targetServiceId, targetServiceUUId);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doDeleteBlacklist(String group, String targetServiceId, String targetServiceUUId) {
        try {
            boolean result = blacklistResource.deleteBlacklist(group, targetServiceId, targetServiceUUId);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doClearBlacklist(String group) {
        try {
            boolean result = blacklistResource.clearBlacklist(group);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doAddBlacklist(String group, String serviceId, String targetServiceId, AddressEntity targetAddressEntity) {
        try {
            String result = blacklistResource.addBlacklist(group, serviceId, targetServiceId, targetAddressEntity.getHost(), targetAddressEntity.getPort());

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doAddBlacklist(String group, String serviceId, String targetServiceId, String targetServiceUUId) {
        try {
            String result = blacklistResource.addBlacklist(group, serviceId, targetServiceId, targetServiceUUId);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doDeleteBlacklist(String group, String serviceId, String targetServiceId, String targetServiceUUId) {
        try {
            boolean result = blacklistResource.deleteBlacklist(group, serviceId, targetServiceId, targetServiceUUId);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doClearBlacklist(String group, String serviceId) {
        try {
            boolean result = blacklistResource.clearBlacklist(group, serviceId);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }
}
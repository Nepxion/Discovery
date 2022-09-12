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

    @RequestMapping(path = "/add/{group}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "全局组订阅方式，根据服务实例IP地址和端口，添加下线的服务实例UUId到黑名单", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> addBlacklist(@PathVariable(value = "group") @ApiParam(value = "订阅的组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "待下线实例的服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "待下线实例的IP地址和端口", required = true) AddressEntity addressEntity) {
        return doAddBlacklist(group, serviceId, addressEntity);
    }

    @RequestMapping(path = "/add/{group}/{gatewayId}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "局部网关订阅方式，根据服务实例IP地址和端口，添加下线的服务实例UUId到黑名单", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> addBlacklist(@PathVariable(value = "group") @ApiParam(value = "订阅的组名", required = true) String group, @PathVariable(value = "gatewayId") @ApiParam(value = "订阅的网关名", required = true) String gatewayId, @PathVariable(value = "serviceId") @ApiParam(value = "待下线实例的服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "待下线实例的IP地址和端口", required = true) AddressEntity addressEntity) {
        return doAddBlacklist(group, gatewayId, serviceId, addressEntity);
    }

    @RequestMapping(path = "/delete/{group}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "全局组订阅方式，根据服务实例UUId，从黑名单删除过期的服务实例", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> deleteBlacklist(@PathVariable(value = "group") @ApiParam(value = "订阅的组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "已下线实例的服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "已下线实例的UUId", required = true) String serviceUUId) {
        return doDeleteBlacklist(group, serviceId, serviceUUId);
    }

    @RequestMapping(path = "/delete/{group}/{gatewayId}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "局部网关订阅方式，根据服务实例UUId，从黑名单删除过期的服务实例", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> deleteBlacklist(@PathVariable(value = "group") @ApiParam(value = "订阅的组名", required = true) String group, @PathVariable(value = "gatewayId") @ApiParam(value = "订阅的网关名", required = true) String gatewayId, @PathVariable(value = "serviceId") @ApiParam(value = "已下线实例的服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "已下线实例的UUId", required = true) String serviceUUId) {
        return doDeleteBlacklist(group, gatewayId, serviceId, serviceUUId);
    }

    private ResponseEntity<?> doAddBlacklist(String group, String serviceId, AddressEntity addressEntity) {
        try {
            String result = blacklistResource.addBlacklist(group, serviceId, addressEntity.getHost(), addressEntity.getPort());

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doAddBlacklist(String group, String gatewayId, String serviceId, AddressEntity addressEntity) {
        try {
            String result = blacklistResource.addBlacklist(group, gatewayId, serviceId, addressEntity.getHost(), addressEntity.getPort());

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

    private ResponseEntity<?> doDeleteBlacklist(String group, String gatewayId, String serviceId, String serviceUUId) {
        try {
            boolean result = blacklistResource.deleteBlacklist(group, gatewayId, serviceId, serviceUUId);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }
}
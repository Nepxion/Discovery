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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.common.entity.AuthenticationEntity;
import com.nepxion.discovery.common.entity.UserEntity;
import com.nepxion.discovery.common.util.ResponseUtil;
import com.nepxion.discovery.console.resource.AuthenticationResource;

@RestController
@RequestMapping(path = "/authentication")
@Api(tags = { "认证接口" })
public class AuthenticationEndpoint {
    @Autowired
    private AuthenticationResource authenticationResource;

    @RequestMapping(path = "/authenticate", method = RequestMethod.POST)
    @ApiOperation(value = "登录认证", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> authenticate(@RequestBody @ApiParam(value = "UserEntity实例", required = true) UserEntity userEntity) {
        return doAuthenticate(userEntity);
    }

    private ResponseEntity<?> doAuthenticate(UserEntity userEntity) {
        try {
            AuthenticationEntity authenticationEntity = authenticationResource.authenticate(userEntity);

            return ResponseUtil.getSuccessResponse(authenticationEntity);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }
}
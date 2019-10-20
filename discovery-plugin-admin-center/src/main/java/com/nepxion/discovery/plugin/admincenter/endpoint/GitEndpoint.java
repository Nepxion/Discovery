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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.actuate.endpoint.mvc.MvcEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.plugin.framework.generator.GitGenerator;

@RestController
@RequestMapping(path = "/git")
@Api(tags = { "Git信息接口" })
@ManagedResource(description = "Git Endpoint")
public class GitEndpoint implements MvcEndpoint {
    @Autowired(required = false)
    private GitGenerator gitGenerator;

    @RequestMapping(path = "/map", method = RequestMethod.GET)
    @ApiOperation(value = "获取Git信息的Map格式", notes = "", response = Map.class, httpMethod = "GET")
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<Map<String, String>> map() {
        Map<String, String> map = gitGenerator != null ? gitGenerator.getMap() : new HashMap<String, String>();

        return ResponseEntity.ok().body(map);
    }

    @RequestMapping(path = "/text", method = RequestMethod.GET)
    @ApiOperation(value = "获取Git信息的文本格式", notes = "", response = String.class, httpMethod = "GET")
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<String> text() {
        String text = gitGenerator != null ? gitGenerator.getText() : StringUtils.EMPTY;

        return ResponseEntity.ok().body(text);
    }

    @Override
    public String getPath() {
        return StringUtils.EMPTY;
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
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.plugin.framework.generator.GitGenerator;

@RestController
@RequestMapping(path = "/git")
@Api(tags = { "Git信息接口" })
public class GitEndpoint {
    @Autowired(required = false)
    private GitGenerator gitGenerator;

    @RequestMapping(path = "/map", method = RequestMethod.GET)
    @ApiOperation(value = "获取Git信息的Map格式", notes = "", response = Map.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<Map<String, String>> map() {
        Map<String, String> map = gitGenerator != null ? gitGenerator.getMap() : new HashMap<String, String>();

        return ResponseEntity.ok().body(map);
    }

    @RequestMapping(path = "/text", method = RequestMethod.GET)
    @ApiOperation(value = "获取Git信息的文本格式", notes = "", response = String.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<String> text() {
        String text = gitGenerator != null ? gitGenerator.getText() : StringUtils.EMPTY;

        return ResponseEntity.ok().body(text);
    }
}
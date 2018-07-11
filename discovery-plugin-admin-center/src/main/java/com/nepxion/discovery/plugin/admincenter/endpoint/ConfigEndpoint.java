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

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.actuate.endpoint.mvc.MvcEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.plugin.framework.cache.RuleCache;
import com.nepxion.discovery.plugin.framework.constant.PluginConstant;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.entity.RuleEntity;
import com.nepxion.discovery.plugin.framework.event.PluginEventWapper;
import com.nepxion.discovery.plugin.framework.event.RuleChangedEvent;

@RestController
@Api(tags = { "配置接口" })
// 用法参照ServiceRegistryEndpoint和ServiceRegistryAutoConfiguration
@ManagedResource(description = "Config Endpoint")
public class ConfigEndpoint implements MvcEndpoint {
    @Autowired
    private PluginContextAware pluginContextAware;

    @Autowired
    private PluginEventWapper pluginEventWapper;

    @Autowired
    private RuleCache ruleCache;

    @RequestMapping(path = "/config/update-async", method = RequestMethod.POST)
    @ApiOperation(value = "异步推送更新规则配置信息", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<?> updateAsync(@RequestBody @ApiParam(value = "规则配置内容，XML格式", required = true) String config) {
        return update(config, true);
    }

    @RequestMapping(path = "/config/update-sync", method = RequestMethod.POST)
    @ApiOperation(value = "同步推送更新规则配置信息", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<?> updateSync(@RequestBody @ApiParam(value = "规则配置内容，XML格式", required = true) String config) {
        return update(config, false);
    }

    @RequestMapping(path = "/config/view", method = RequestMethod.GET)
    @ApiOperation(value = "查看当前生效的规则配置信息", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<?> view() {
        RuleEntity ruleEntity = ruleCache.get(PluginConstant.RULE);
        if (ruleEntity == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No config to view");
        }

        String content = ruleEntity.getContent();

        return ResponseEntity.ok().body(content);
    }

    private ResponseEntity<?> update(String config, boolean async) {
        Boolean discoveryControlEnabled = pluginContextAware.isDiscoveryControlEnabled();
        if (!discoveryControlEnabled) {
            // return new ResponseEntity<>(Collections.singletonMap("Message", "Discovery control is disabled"), HttpStatus.NOT_FOUND);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Discovery control is disabled");
        }

        try {
            InputStream inputStream = IOUtils.toInputStream(config, PluginConstant.ENCODING_UTF_8);
            pluginEventWapper.fireRuleChanged(new RuleChangedEvent(inputStream), async);
        } catch (IOException e) {
            return toExceptionResponseEntity(e, true);
        }

        // return ResponseEntity.ok().build();

        return ResponseEntity.ok().body("OK");
    }

    private ResponseEntity<String> toExceptionResponseEntity(Exception e, boolean showDetail) {
        String message = null;
        if (showDetail) {
            message = ExceptionUtils.getStackTrace(e);
        } else {
            message = e.getMessage();
        }

        message = "An internal error occurred while processing your request\n" + message;

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }

    @Override
    public String getPath() {
        return "/";
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
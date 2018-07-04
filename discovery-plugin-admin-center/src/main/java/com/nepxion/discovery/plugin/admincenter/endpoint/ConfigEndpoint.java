package com.nepxion.discovery.plugin.admincenter.endpoint;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.nepxion.discovery.plugin.framework.constant.PluginConstant;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.entity.RuleEntity;
import com.nepxion.discovery.plugin.framework.event.PluginPublisher;
import com.nepxion.discovery.plugin.framework.event.RuleChangedEvent;

// 用法参照ServiceRegistryEndpoint和ServiceRegistryAutoConfiguration
@ManagedResource(description = "Config Endpoint")
public class ConfigEndpoint implements MvcEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigEndpoint.class);

    @Autowired
    private PluginContextAware pluginContextAware;

    @Autowired
    private PluginPublisher pluginPublisher;

    @Autowired
    private RuleEntity ruleEntity;

    // 推送规则配置信息
    @RequestMapping(path = "send", method = RequestMethod.POST)
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<?> send(@RequestBody String config) {
        Boolean discoveryControlEnabled = pluginContextAware.isDiscoveryControlEnabled();
        if (!discoveryControlEnabled) {
            return new ResponseEntity<>(Collections.singletonMap("Message", "Discovery control is disabled"), HttpStatus.NOT_FOUND);
        }

        try {
            InputStream inputStream = IOUtils.toInputStream(config, PluginConstant.ENCODING_UTF_8);
            pluginPublisher.asyncPublish(new RuleChangedEvent(inputStream));
        } catch (IOException e) {
            LOG.error("Publish config failed", e);

            return new ResponseEntity<>(Collections.singletonMap("Message", "Send config failed"), HttpStatus.OK);
        }

        // return ResponseEntity.ok().build();

        return ResponseEntity.ok().body("OK");
    }

    // 查看当前生效的规则配置信息
    @RequestMapping(path = "view", method = RequestMethod.GET)
    @ResponseBody
    @ManagedOperation
    public ResponseEntity<String> view() {
        String content = ruleEntity.getContent();

        return ResponseEntity.ok().body(content);
    }

    @Override
    public String getPath() {
        return "/config";
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
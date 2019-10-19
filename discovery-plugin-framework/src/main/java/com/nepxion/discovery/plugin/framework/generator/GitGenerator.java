package com.nepxion.discovery.plugin.framework.generator;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.property.DiscoveryProperties;
import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.util.FileContextUtil;

public class GitGenerator {
    @Autowired
    private ApplicationContext applicationContext;

    private String gitGeneratorPath;
    private String gitVersionKey;

    private String text;
    private Map<String, String> map;

    @PostConstruct
    public void initialize() {
        gitGeneratorPath = PluginContextAware.getGitGeneratorPath(applicationContext.getEnvironment());
        gitVersionKey = PluginContextAware.getGitVersionKey(applicationContext.getEnvironment());

        initializeText();
        initializeJsonMap();
        initializePropertiesMap();
    }

    private void initializeText() {
        text = FileContextUtil.getText(applicationContext, gitGeneratorPath);
    }

    private void initializeJsonMap() {
        if (StringUtils.isEmpty(text)) {
            return;
        }

        if (MapUtils.isNotEmpty(map)) {
            return;
        }

        try {
            map = JsonUtil.fromJson(text, new TypeReference<Map<String, String>>() {
            });
        } catch (Exception e) {

        }
    }

    private void initializePropertiesMap() {
        if (StringUtils.isEmpty(text)) {
            return;
        }

        if (MapUtils.isNotEmpty(map)) {
            return;
        }

        try {
            DiscoveryProperties properties = new DiscoveryProperties(text, DiscoveryConstant.ENCODING_UTF_8);
            map = properties.getMap();
        } catch (Exception e) {

        }
    }

    public String getGitVersionKey() {
        return gitVersionKey;
    }

    public String getGitGeneratorPath() {
        return gitGeneratorPath;
    }

    public String getText() {
        return text;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public String getVersion() {
        if (MapUtils.isEmpty(map)) {
            return null;
        }

        return map.get(gitVersionKey);
    }
}
package com.nepxion.discovery.plugin.framework.generator;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author Yong Chen
 * @version 1.0
 */

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.property.DiscoveryProperties;
import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.util.FileContextUtil;

public class GitGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(GitGenerator.class);

    @Autowired
    private ApplicationContext applicationContext;

    private String generatorPath;
    private String versionKey;

    private String text;
    private Map<String, String> map;

    @PostConstruct
    public void initialize() {
        generatorPath = PluginContextAware.getGitGeneratorPath(applicationContext.getEnvironment());
        versionKey = PluginContextAware.getGitVersionKey(applicationContext.getEnvironment());

        initializeText();
        initializeJsonMap();
        initializePropertiesMap();

        String version = getVersion();
        LOG.info("--------------------------------------------------");
        if (StringUtils.isNotEmpty(version)) {
            LOG.info("Use {}={} as metadata version", versionKey, version);
        } else {
            LOG.error("Not found value of {}, use default metadata version setting", versionKey);
        }
        LOG.info("--------------------------------------------------");
    }

    private void initializeText() {
        text = FileContextUtil.getText(applicationContext, generatorPath);
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

    public String getVersionKey() {
        return versionKey;
    }

    public String getGeneratorPath() {
        return generatorPath;
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

        return match(versionKey, map);
        // return map.get(versionKey);
    }

    private String match(String versionKey, Map<String, String> map) {
        String value = new String(versionKey);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String regex = "\\{" + entry.getKey() + "\\}";
            // String regex = "\\$\\{" + entry.getKey() + "\\}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(value);
            value = matcher.replaceAll(entry.getValue());
        }

        return value;
    }
}
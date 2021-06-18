package com.nepxion.discovery.plugin.test.automation.operation;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.FormatType;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.common.property.DiscoveryContent;
import com.nepxion.discovery.common.util.UrlUtil;
import com.nepxion.discovery.plugin.test.automation.constant.TestConstant;

public class TestOperation {
    private static final Logger LOG = LoggerFactory.getLogger(TestOperation.class);

    @Value("${" + TestConstant.SPRING_APPLICATION_TEST_CONSOLE_URL + "}")
    private String consoleUrl;

    @Value("${" + TestConstant.SPRING_APPLICATION_TEST_CONFIG_PRINT_ENABLED + ":true}")
    private Boolean configPrintEnabled;

    @Autowired
    private TestRestTemplate testRestTemplate;

    public String update(String group, String serviceId, FormatType formatType, String path) {
        String content = null;
        try {
            DiscoveryContent discoveryContent = new DiscoveryContent(path, DiscoveryConstant.ENCODING_UTF_8);
            content = discoveryContent.getContent();
        } catch (IOException e) {
            throw new DiscoveryException(e);
        }

        if (configPrintEnabled) {
            LOG.info("Update config, group={}, serviceId={}, formatType={}, path={}, content=\n{}", group, serviceId, formatType, path, content);
        }

        String url = consoleUrl + UrlUtil.formatContextPath(TestConstant.REMOTE_UPDATE_URL) + group + "/" + serviceId + "/" + formatType;

        return testRestTemplate.postForEntity(url, content, String.class).getBody();
    }

    public String clear(String group, String serviceId) {
        if (configPrintEnabled) {
            LOG.info("Clear config, group={}, serviceId={}", group, serviceId);
        }

        String url = consoleUrl + UrlUtil.formatContextPath(TestConstant.REMOTE_CLEAR_URL) + group + "/" + serviceId;

        return testRestTemplate.postForEntity(url, null, String.class).getBody();
    }
}
package com.nepxion.discovery.plugin.example.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

import com.nepxion.discovery.plugin.configcenter.ConfigAdapter;

// 模拟主动从本地或远程配置中心获取规则
// 模拟订阅远程配置中心的规则更新
public class DiscoveryConfigAdapter extends ConfigAdapter {
    // 从本地获取规则
    // 通过application.properties里的spring.application.discovery.remote.config.enabled=true，来决定主动从本地，还是远程配置中心获取规则
    @Override
    protected String getLocalContextPath() {
        // 规则文件放在resources目录下
        return "classpath:rule.xml";

        // 规则文件放在工程根目录下
        // return "file:rule.xml";
    }

    // 从远程配置中心获取规则
    @Override
    public InputStream getRemoteInputStream() {
        try {
            return FileUtils.openInputStream(new File("src/main/resources/rule.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 订阅远程配置中心的规则更新（推送策略自己决定，可以所有服务都只对应一个规则信息，也可以根据服务名获取对应的规则信息）
    /*@PostConstruct
    public void publish() {
        try {
            InputStream inputStream = FileUtils.openInputStream(new File("src/main/resources/rule.xml"));
            publish(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
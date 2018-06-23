package com.nepxion.discovery.plugin.example.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.nepxion.discovery.plugin.configcenter.loader.AbstractConfigLoader;

// 模拟从本地配置或远程配置中心获取配置
public class DiscoveryConfigLoader extends AbstractConfigLoader {
    @Override
    public InputStream getRemoteInputStream() {
        // 本地文件模拟代替远程文件
        return getInputStream("src/main/resources/rule1.xml");
    }

    @Override
    protected String getLocalContextPath() {
        // 配置文件放在resources目录下
        return "classpath:rule1.xml";

        // 配置文件放在工程根目录下
        // return "file:rule1.xml";
    }

    private InputStream getInputStream(String fileName) {
        try {
            return new FileInputStream(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
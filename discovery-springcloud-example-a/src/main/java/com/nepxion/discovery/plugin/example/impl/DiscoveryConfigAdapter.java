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
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;

import com.nepxion.discovery.plugin.configcenter.ConfigAdapter;

// 模拟从本地配置或远程配置中心获取配置，订阅配置更新
public class DiscoveryConfigAdapter extends ConfigAdapter {
    @Override
    public InputStream getRemoteInputStream() {
        // 本地文件模拟代替远程文件
        try {
            return FileUtils.openInputStream(new File("src/main/resources/rule1.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected String getLocalContextPath() {
        // 配置文件放在resources目录下
        return "classpath:rule1.xml";

        // 配置文件放在工程根目录下
        // return "file:rule1.xml";
    }

    @PostConstruct
    public void initialize() {
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

        // 模拟每隔15秒通过EventBus接收远程配置中心推送过来的配置更新
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                // 本地文件模拟代替远程文件，随机读取
                int index = threadLocalRandom.nextInt(5) + 1;
                System.out.println("-------------------- rule" + index + ".xml is loaded --------------------");
                try {
                    InputStream inputStream = FileUtils.openInputStream(new File("src/main/resources/rule" + index + ".xml"));
                    publish(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, 10000L, 15000L);
    }
}
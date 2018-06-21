package com.nepxion.discovery.plugin.example;

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
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.nepxion.discovery.plugin.loader.AbstractFileLoader;
import com.nepxion.discovery.plugin.loader.FileLoader;
import com.nepxion.eventbus.core.Event;
import com.nepxion.eventbus.core.EventControllerFactory;

@Component
public class DiscoveryPluginSimulator {
    // 模拟从本地配置和远程配置获取discovery.xml
    @Bean
    public FileLoader fileLoader() {
        return new AbstractFileLoader() {
            @Override
            public InputStream getRemoteInputStream() throws IOException {
                // 本地文件模拟代替远程文件
                return createInputStream("src/main/resources/discovery1.xml");
            }

            @Override
            protected String getLocalContextPath() {
                // 配置文件放在resources目录下
                return "classpath:discovery1.xml";

                // 配置文件放在工程根目录下
                // return "file:discovery.xml";
            }
        };
    }

    // 模拟每隔15秒通过事件总线接收远程配置中心推送过来的配置更新
    @Autowired
    private EventControllerFactory eventControllerFactory;

    @PostConstruct
    public void initialize() {
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                // 本地文件模拟代替远程文件，随机读取
                int index = threadLocalRandom.nextInt(3) + 1;
                InputStream inputStream = createInputStream("src/main/resources/discovery" + index + ".xml");
                eventControllerFactory.getAsyncController().post(new Event(inputStream));
            }
        }, 0L, 15000L);
    }

    private InputStream createInputStream(String fileName) {
        File file = new File(fileName);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return inputStream;
    }
}
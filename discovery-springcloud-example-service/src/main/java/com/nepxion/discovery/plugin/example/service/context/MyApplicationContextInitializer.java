package com.nepxion.discovery.plugin.example.service.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// 通过如下方式，可以代替在配置文件里面配置元数据，格式为ext.xxx。例如，如果version希望通过git插件获取到最后一次提交编号，可以动态在这里进行解析并放入
public class MyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        if (applicationContext instanceof AnnotationConfigApplicationContext) {
            return;
        }

        // System.setProperty("ext.group", "myGroup");
        // System.setProperty("ext.version", "8888");
        // System.setProperty("ext.region", "myRegion");
    }
}
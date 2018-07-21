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
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;

import com.nepxion.discovery.plugin.configcenter.ConfigAdapter;
import com.nepxion.discovery.plugin.framework.event.RuleUpdatedEvent;

// 模拟主动从本地或远程配置中心获取规则
// 模拟订阅远程配置中心的规则更新
public class MyConfigAdapter extends ConfigAdapter {
    // 从远程配置中心获取规则
    @Override
    public InputStream getInputStream() throws Exception {
        return FileUtils.openInputStream(new File("src/main/resources/rule.xml"));
    }

    // 订阅远程配置中心的规则更新（推送策略自己决定，可以所有服务都只对应一个规则信息，也可以根据服务名获取对应的规则信息）
    @PostConstruct
    public void update() throws Exception {
        InputStream inputStream = FileUtils.openInputStream(new File("src/main/resources/rule.xml"));
        fireRuleUpdated(new RuleUpdatedEvent(inputStream), true);
    }
}
package com.nepxion.discovery.plugin.example.zuul.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.client.serviceregistry.Registration;

import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.plugin.framework.listener.register.AbstractRegisterListener;

// 当本服务的元数据中的Group为mygroup1，禁止被注册到注册中心
public class MyRegisterListener extends AbstractRegisterListener {
    @Override
    public void onRegister(Registration registration) {
        String serviceId = pluginAdapter.getServiceId();
        String group = pluginAdapter.getGroup();
        if (StringUtils.equals(group, "mygroup1")) {
            throw new DiscoveryException("服务名=" + serviceId + "，组名=" + group + "的服务禁止被注册到注册中心");
        }
    }

    @Override
    public void onDeregister(Registration registration) {

    }

    @Override
    public void onSetStatus(Registration registration, String status) {

    }

    @Override
    public void onClose() {

    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE - 500;
    }
}
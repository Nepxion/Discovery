package com.nepxion.discovery.plugin.example.extension;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.cloud.client.serviceregistry.Registration;

import com.nepxion.discovery.plugin.framework.listener.AbstractRegisterListener;

public class MyRegisterListener extends AbstractRegisterListener {
    @Override
    public void onRegister(Registration registration) {
        System.out.println("========== register() 被触发：serviceId=" + registration.getServiceId());
    }

    @Override
    public void onDeregister(Registration registration) {
        System.out.println("========== deregister() 被触发：serviceId=" + registration.getServiceId());
    }

    @Override
    public void onSetStatus(Registration registration, String status) {
        System.out.println("========== setStatus() 被触发：serviceId=" + registration.getServiceId() + ", status=" + status);
    }

    @Override
    public void onClose() {
        System.out.println("========== close() 被触发 ==========");
    }
}
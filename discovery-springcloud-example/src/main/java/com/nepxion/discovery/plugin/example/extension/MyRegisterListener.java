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

import com.nepxion.discovery.plugin.framework.listener.register.AbstractRegisterListener;

public class MyRegisterListener extends AbstractRegisterListener {
    @Override
    public void onRegister(Registration registration) {
        // System.out.println("========== Register Listener :: register()被触发, serviceId=" + registration.getServiceId());
    }

    @Override
    public void onDeregister(Registration registration) {
        // System.out.println("========== Register Listener :: deregister()被触发, serviceId=" + registration.getServiceId());
    }

    @Override
    public void onSetStatus(Registration registration, String status) {
        // System.out.println("========== Register Listener :: setStatus()被触发, serviceId=" + registration.getServiceId() + ", status=" + status);
    }

    @Override
    public void onClose() {
        // System.out.println("========== Register Listener :: close()被触发 ==========");
    }
}
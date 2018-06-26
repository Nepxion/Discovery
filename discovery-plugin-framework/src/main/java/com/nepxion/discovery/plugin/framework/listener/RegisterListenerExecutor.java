package com.nepxion.discovery.plugin.framework.listener;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;

import com.nepxion.discovery.plugin.framework.listener.impl.IpAddressFilterRegisterListener;

// 因为内置监听触发的时候，会抛异常处理，所以逆序执行
public class RegisterListenerExecutor {
    @Autowired
    private IpAddressFilterRegisterListener ipAddressFilterRegisterListener;

    @Autowired
    private List<RegisterListener> registerListenerList;

    @Autowired
    private ReentrantReadWriteLock reentrantReadWriteLock;

    public void fireRegister(Registration registration) {
        try {
            reentrantReadWriteLock.readLock().lock();

            for (RegisterListener registerListener : registerListenerList) {
                if (registerListener != ipAddressFilterRegisterListener) {
                    registerListener.fireRegister(registration);
                }
            }

            ipAddressFilterRegisterListener.fireRegister(registration);
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }

    public void fireDeregister(Registration registration) {
        for (RegisterListener registerListener : registerListenerList) {
            if (registerListener != ipAddressFilterRegisterListener) {
                registerListener.fireDeregister(registration);
            }
        }

        ipAddressFilterRegisterListener.fireDeregister(registration);
    }

    public void fireSetStatus(Registration registration, String status) {
        for (RegisterListener registerListener : registerListenerList) {
            if (registerListener != ipAddressFilterRegisterListener) {
                registerListener.fireSetStatus(registration, status);
            }
        }

        ipAddressFilterRegisterListener.fireSetStatus(registration, status);
    }

    public void fireClose() {
        for (RegisterListener registerListener : registerListenerList) {
            if (registerListener != ipAddressFilterRegisterListener) {
                registerListener.fireClose();
            }
        }

        ipAddressFilterRegisterListener.fireClose();
    }
}
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

import com.nepxion.discovery.plugin.framework.listener.impl.CountFilterRegisterListener;
import com.nepxion.discovery.plugin.framework.listener.impl.IpAddressFilterRegisterListener;

// 因为内置监听触发的时候，会抛异常处理，所以逆序执行
public class RegisterListenerExecutor {
    @Autowired
    private CountFilterRegisterListener countFilterRegisterListener;

    @Autowired
    private IpAddressFilterRegisterListener ipAddressFilterRegisterListener;

    @Autowired
    private List<RegisterListener> registerListenerList;

    @Autowired
    private ReentrantReadWriteLock reentrantReadWriteLock;

    public void onRegister(Registration registration) {
        try {
            reentrantReadWriteLock.readLock().lock();

            for (RegisterListener registerListener : registerListenerList) {
                if (registerListener != countFilterRegisterListener && registerListener != ipAddressFilterRegisterListener) {
                    registerListener.onRegister(registration);
                }
            }

            countFilterRegisterListener.onRegister(registration);
            ipAddressFilterRegisterListener.onRegister(registration);
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }

    public void onDeregister(Registration registration) {
        for (RegisterListener registerListener : registerListenerList) {
            if (registerListener != countFilterRegisterListener && registerListener != ipAddressFilterRegisterListener) {
                registerListener.onDeregister(registration);
            }
        }

        countFilterRegisterListener.onDeregister(registration);
        ipAddressFilterRegisterListener.onDeregister(registration);
    }

    public void onSetStatus(Registration registration, String status) {
        for (RegisterListener registerListener : registerListenerList) {
            if (registerListener != countFilterRegisterListener && registerListener != ipAddressFilterRegisterListener) {
                registerListener.onSetStatus(registration, status);
            }
        }

        countFilterRegisterListener.onSetStatus(registration, status);
        ipAddressFilterRegisterListener.onSetStatus(registration, status);
    }

    public void onClose() {
        for (RegisterListener registerListener : registerListenerList) {
            if (registerListener != countFilterRegisterListener && registerListener != ipAddressFilterRegisterListener) {
                registerListener.onClose();
            }
        }

        countFilterRegisterListener.onClose();
        ipAddressFilterRegisterListener.onClose();
    }
}
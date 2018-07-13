package com.nepxion.discovery.plugin.framework.listener.register;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;

// 因为内置监听触发的时候，会抛异常处理，所以逆序执行
public class RegisterListenerExecutor {
    @Autowired
    private CountFilterRegisterListener countFilterRegisterListener;

    @Autowired
    private HostFilterRegisterListener hostFilterRegisterListener;

    @Autowired
    private List<RegisterListener> registerListenerList;

    public void onRegister(Registration registration) {
        for (RegisterListener registerListener : registerListenerList) {
            if (registerListener != countFilterRegisterListener && registerListener != hostFilterRegisterListener) {
                registerListener.onRegister(registration);
            }
        }

        countFilterRegisterListener.onRegister(registration);
        hostFilterRegisterListener.onRegister(registration);
    }

    public void onDeregister(Registration registration) {
        for (RegisterListener registerListener : registerListenerList) {
            if (registerListener != countFilterRegisterListener && registerListener != hostFilterRegisterListener) {
                registerListener.onDeregister(registration);
            }
        }

        countFilterRegisterListener.onDeregister(registration);
        hostFilterRegisterListener.onDeregister(registration);
    }

    public void onSetStatus(Registration registration, String status) {
        for (RegisterListener registerListener : registerListenerList) {
            if (registerListener != countFilterRegisterListener && registerListener != hostFilterRegisterListener) {
                registerListener.onSetStatus(registration, status);
            }
        }

        countFilterRegisterListener.onSetStatus(registration, status);
        hostFilterRegisterListener.onSetStatus(registration, status);
    }

    public void onClose() {
        for (RegisterListener registerListener : registerListenerList) {
            if (registerListener != countFilterRegisterListener && registerListener != hostFilterRegisterListener) {
                registerListener.onClose();
            }
        }

        countFilterRegisterListener.onClose();
        hostFilterRegisterListener.onClose();
    }
}
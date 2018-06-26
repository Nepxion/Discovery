package com.nepxion.discovery.plugin.framework.strategy;

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

import com.nepxion.discovery.plugin.framework.strategy.impl.IpAddressFilterRegisterStrategy;

// 因为内置监听触发的时候，会抛异常处理，所以逆序执行
public class RegisterStrategyExecutor {
    @Autowired
    private IpAddressFilterRegisterStrategy ipAddressFilterRegisterStrategy;

    @Autowired
    private List<RegisterStrategy> registerStrategyList;

    @Autowired
    private ReentrantReadWriteLock reentrantReadWriteLock;

    public void fireRegister(Registration registration) {
        try {
            reentrantReadWriteLock.readLock().lock();

            for (RegisterStrategy registerStrategy : registerStrategyList) {
                if (registerStrategy != ipAddressFilterRegisterStrategy) {
                    registerStrategy.fireRegister(registration);
                }
            }

            ipAddressFilterRegisterStrategy.fireRegister(registration);
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }

    public void fireDeregister(Registration registration) {
        for (RegisterStrategy registerStrategy : registerStrategyList) {
            if (registerStrategy != ipAddressFilterRegisterStrategy) {
                registerStrategy.fireDeregister(registration);
            }
        }

        ipAddressFilterRegisterStrategy.fireDeregister(registration);
    }

    public void fireSetStatus(Registration registration, String status) {
        for (RegisterStrategy registerStrategy : registerStrategyList) {
            if (registerStrategy != ipAddressFilterRegisterStrategy) {
                registerStrategy.fireSetStatus(registration, status);
            }
        }

        ipAddressFilterRegisterStrategy.fireSetStatus(registration, status);
    }

    public void fireClose() {
        for (RegisterStrategy registerStrategy : registerStrategyList) {
            if (registerStrategy != ipAddressFilterRegisterStrategy) {
                registerStrategy.fireClose();
            }
        }

        ipAddressFilterRegisterStrategy.fireClose();
    }
}
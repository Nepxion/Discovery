package com.nepxion.discovery.plugin.registercenter.zookeeper.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.zookeeper.discovery.ZookeeperServer;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperRegistration;
import org.springframework.core.env.Environment;

import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.plugin.framework.adapter.AbstractPluginAdapter;
import com.nepxion.discovery.plugin.registercenter.zookeeper.constant.ZookeeperConstant;
import com.netflix.loadbalancer.Server;

public class ZookeeperAdapter extends AbstractPluginAdapter {
    @Autowired
    private Environment environment;

    private String group;

    /*private AtomicBoolean flag = new AtomicBoolean(false);*/
    private boolean flag = false;

    // Zookeeper比较特殊，getServiceInstance是Lazy Initialize模式
    @PostConstruct
    private void initialize() {
        if (registration instanceof ZookeeperRegistration) {
            ZookeeperRegistration zookeeperRegistration = (ZookeeperRegistration) registration;

            zookeeperRegistration.getServiceInstance();

            return;
        }

        throw new DiscoveryException("Registration isn't the type of ZookeeperRegistration");
    }

    // Zookeeper比较特殊，父类中getMetadata().get(groupKey)方法不行，执行该方法的时候Metadata还没初始化
    @Override
    protected String getGroup(String groupKey) {
        /*if (flag.compareAndSet(false, true)) {
            group = environment.getProperty(ZookeeperConstant.ZOOKEEPER_METADATA + "." + groupKey);
        }*/

        if (!flag) {
            group = environment.getProperty(ZookeeperConstant.ZOOKEEPER_METADATA + "." + groupKey);

            flag = true;
        }

        return group;
    }

    @Override
    public Map<String, String> getServerMetadata(Server server) {
        if (server instanceof ZookeeperServer) {
            ZookeeperServer zookeeperServer = (ZookeeperServer) server;

            return zookeeperServer.getInstance().getPayload().getMetadata();
        }

        return emptyMetadata;

        // throw new DiscoveryException("Server instance isn't the type of ZookeeperServer");
    }
}
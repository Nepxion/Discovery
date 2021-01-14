package com.nepxion.discovery.common.consul.operation;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Congwei Xu
 * @version 1.0
 */

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.kv.model.GetValue;
import com.nepxion.discovery.common.consul.constant.ConsulConstant;

public class ConsulOperation {
    @Autowired
    private ConsulClient consulClient;

    @Autowired
    private Environment environment;

    public String getConfig(String group, String serviceId) {
        Response<GetValue> response = consulClient.getKVValue(group + "-" + serviceId);
        if (response == null) {
            return null;
        }

        GetValue getValue = response.getValue();
        if (getValue == null) {
            return null;
        }

        return getValue.getDecodedValue(StandardCharsets.UTF_8);
    }

    public boolean removeConfig(String group, String serviceId) {
        consulClient.deleteKVValue(group + "-" + serviceId);

        return true;
    }

    public boolean publishConfig(String group, String serviceId, String config) {
        consulClient.setKVValue(group + "-" + serviceId, config);

        return true;
    }

    public ConsulListener subscribeConfig(String group, String serviceId, ExecutorService executorService, ConsulSubscribeCallback consulSubscribeCallback) throws Exception {
        int timeout = environment.getProperty(ConsulConstant.CONSUL_TIMEOUT, Integer.class, ConsulConstant.CONSUL_DEFAULT_TIMEOUT);

        ConsulListener consulListener = new ConsulListener(group, serviceId, timeout, consulClient, consulSubscribeCallback);
        executorService.submit(consulListener);

        return consulListener;
    }

    public void unsubscribeConfig(String group, String serviceId, ConsulListener consulListener) throws Exception {
        consulListener.stop();
    }
}
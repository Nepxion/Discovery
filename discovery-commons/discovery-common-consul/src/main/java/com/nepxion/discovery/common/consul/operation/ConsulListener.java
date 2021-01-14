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
import java.util.concurrent.TimeUnit;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.kv.model.GetValue;

public class ConsulListener implements Runnable {
    private String group;
    private String serviceId;

    private int timeout;
    private String token;

    private ConsulClient consulClient;
    private ConsulSubscribeCallback consulSubscribeCallback;

    private volatile long lastIndex;
    private volatile boolean running = true;

    public ConsulListener(String group, String serviceId, int timeout, String token, ConsulClient consulClient, ConsulSubscribeCallback consulSubscribeCallback) {
        this.group = group;
        this.serviceId = serviceId;
        this.timeout = timeout;
        this.token = token;
        this.consulClient = consulClient;
        this.consulSubscribeCallback = consulSubscribeCallback;

        initialize();
    }

    private void initialize() {
        Response<GetValue> response = consulClient.getKVValue(group + "-" + serviceId, token);
        if (response != null) {
            lastIndex = response.getConsulIndex();
        }
    }

    @Override
    public void run() {
        while (running) {
            Response<GetValue> response = consulClient.getKVValue(group + "-" + serviceId, token, new QueryParams(timeout, lastIndex));
            if (response == null) {
                try {
                    TimeUnit.MILLISECONDS.sleep(timeout * 1000);
                } catch (InterruptedException e) {
                }

                continue;
            }

            Long currentIndex = response.getConsulIndex();
            if (currentIndex == null || currentIndex <= lastIndex) {
                continue;
            }
            lastIndex = currentIndex;

            GetValue getValue = response.getValue();
            if (getValue != null) {
                String value = getValue.getDecodedValue(StandardCharsets.UTF_8);

                consulSubscribeCallback.callback(value);
            }
        }
    }

    public void stop() {
        running = false;
    }
}
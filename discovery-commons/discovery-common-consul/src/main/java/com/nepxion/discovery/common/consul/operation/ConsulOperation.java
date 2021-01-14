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
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.kv.model.GetValue;
import com.nepxion.discovery.common.thread.DiscoveryNamedThreadFactory;

public class ConsulOperation {
    private static final Logger LOG = LoggerFactory.getLogger(ConsulOperation.class);

    // Request of query will hang until timeout (in second) or get updated value.
    private final int watchTimeout = 1;

    // Record the data's index in Consul to watch the change.
    // If lastIndex is smaller than the index of next query, it means that data has updated.
    private volatile long lastIndex;

    private volatile ConsulKVWatcher globalWatcher = null;
    private volatile ConsulKVWatcher partialWatcher = null;

    private final ExecutorService watcherService = Executors.newSingleThreadExecutor(
            new DiscoveryNamedThreadFactory("nepxion-consul-config-watcher", true));

    @Autowired
    private ConsulClient client;

    public String getConfig(String group, String serviceId) {
        Response<GetValue> keyValueResponse = client.getKVValue(group + "-" + serviceId);
        if (keyValueResponse != null) {
            if (keyValueResponse.getValue() != null) {
                return keyValueResponse.getValue().getDecodedValue(StandardCharsets.UTF_8);
            }
        }

        return null;
    }

    public boolean removeConfig(String group, String serviceId) {
        client.deleteKVValue(group + "-" + serviceId);

        return true;
    }

    public boolean publishConfig(String group, String serviceId, String config) {
        client.setKVValue(group + "-" + serviceId, config);

        return true;
    }

    public void subscribeConfig(String group, String serviceId, boolean globalConfig, ConsulSubscribeCallback consulSubscribeCallback) throws Exception {
        loadInitialConfig(group, serviceId, consulSubscribeCallback);
        startKVWatcher(group, serviceId, globalConfig, consulSubscribeCallback);
    }

    public void unsubscribeConfig(String group, String serviceId, boolean globalConfig) throws Exception {
        if (globalConfig) {
            globalWatcher.stop();
        } else {
            partialWatcher.stop();
        }
        watcherService.shutdown();
    }

    private void startKVWatcher(String group, String serviceId, boolean globalConfig, ConsulSubscribeCallback consulSubscribeCallback) {
        if (globalConfig) {
            globalWatcher = new ConsulKVWatcher(group, serviceId, consulSubscribeCallback);
            watcherService.submit(globalWatcher);
        } else {
            partialWatcher = new ConsulKVWatcher(group, serviceId, consulSubscribeCallback);
            watcherService.submit(partialWatcher);
        }
    }

    private void loadInitialConfig(String group, String serviceId, ConsulSubscribeCallback consulSubscribeCallback) {
        try {
            String newValue = null;

            Response<GetValue> response = getValueImmediately(group + "-" + serviceId);
            if (response != null) {
                GetValue value = response.getValue();
                lastIndex = response.getConsulIndex();
                if (value != null) {
                    newValue = value.getDecodedValue();
                }
            }

            if (newValue == null) {
                LOG.warn(
                        "[ConsulOperation] WARN: initial config is null, you may have to check your consul config");
            }
            consulSubscribeCallback.callback(newValue);
        } catch (Exception ex) {
            LOG.warn("[ConsulOperation] Error when loading initial config", ex);
        }
    }

    private class ConsulKVWatcher implements Runnable {
        private volatile boolean running = true;
        private String group;
        private String serviceId;
        private ConsulSubscribeCallback consulSubscribeCallback;

        public ConsulKVWatcher(String group, String serviceId, ConsulSubscribeCallback consulSubscribeCallback) {
            this.group = group;
            this.serviceId = serviceId;
            this.consulSubscribeCallback = consulSubscribeCallback;
        }

        @Override
        public void run() {
            while (running) {
                // It will be blocked until watchTimeout(s) if data has no update
                Response<GetValue> response = getValue(group + "-" + serviceId, lastIndex, watchTimeout);
                if (response == null) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(watchTimeout * 1000);
                    } catch (InterruptedException e) {
                    }
                    continue;
                }
                GetValue getValue = response.getValue();
                Long currentIndex = response.getConsulIndex();
                if (currentIndex == null || currentIndex <= lastIndex) {
                    continue;
                }
                lastIndex = currentIndex;
                if (getValue != null) {
                    String newValue = getValue.getDecodedValue();

                    consulSubscribeCallback.callback(newValue);
                }
            }
        }

        private void stop() {
            running = false;
        }
    }

    /**
     * Get data from Consul immediately.
     * @param key data key in Consul
     * @return the value associated to the key, or null if error occurs
     */
    private Response<GetValue> getValueImmediately(String key) {
        return getValue(key, -1, -1);
    }

    /**
     * Get data from Consul (blocking).
     * @param key data key in Consul
     * @param index the index of data in Consul.
     * @param waitTime time(second) for waiting get updated value.
     * @return the value associated to the key, or null if error occurs
     */
    private Response<GetValue> getValue(String key, long index, long waitTime) {
        try {
            return client.getKVValue(key, new QueryParams(waitTime, index));
        } catch (Throwable t) {
            LOG.warn("[ConsulOperation] Failed to get value for key: " + key, t);
        }
        return null;
    }
}
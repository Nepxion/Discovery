package com.nepxion.discovery.common.etcd.operation;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Congwei Xu
 * @version 1.0
 */

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.Watch;
import io.etcd.jetcd.Watch.Listener;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.watch.WatchEvent;
import io.etcd.jetcd.watch.WatchEvent.EventType;
import io.etcd.jetcd.watch.WatchResponse;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;

public class EtcdOperation implements DisposableBean {
    private static final Logger LOG = LoggerFactory.getLogger(EtcdOperation.class);

    @Autowired
    private Client client;

    public String getConfig(String group, String serviceId) throws ExecutionException, InterruptedException {
        ByteSequence byteSequence = ByteSequence.from(group + "-" + serviceId, StandardCharsets.UTF_8);

        KV kvClient = client.getKVClient();
        GetResponse getResponse = kvClient.get(byteSequence).get();
        if (getResponse.getKvs().size() > 0) {
            KeyValue keyValue = getResponse.getKvs().get(0);

            return keyValue.getValue().toString(StandardCharsets.UTF_8);
        }

        return null;
    }

    public boolean removeConfig(String group, String serviceId) throws ExecutionException, InterruptedException {
        ByteSequence byteSequence = ByteSequence.from(group + "-" + serviceId, StandardCharsets.UTF_8);

        KV kvClient = client.getKVClient();
        kvClient.delete(byteSequence);

        return true;
    }

    public boolean publishConfig(String group, String serviceId, String config) throws ExecutionException, InterruptedException {
        ByteSequence keyByteSequence = ByteSequence.from(group + "-" + serviceId, StandardCharsets.UTF_8);
        ByteSequence valueByteSequence = ByteSequence.from(config, StandardCharsets.UTF_8);

        KV kvClient = client.getKVClient();
        kvClient.put(keyByteSequence, valueByteSequence);

        return true;
    }

    public Watch subscribeConfig(String group, String serviceId, EtcdSubscribeCallback etcdSubscribeCallback) throws Exception {
        ByteSequence byteSequence = ByteSequence.from(group + "-" + serviceId, StandardCharsets.UTF_8);

        Watch watchClient = client.getWatchClient();
        Listener listener = new Listener() {
            @Override
            public void onNext(WatchResponse response) {
                for (WatchEvent event : response.getEvents()) {
                    EventType eventType = event.getEventType();
                    if (eventType == EventType.PUT) {
                        KeyValue keyValue = event.getKeyValue();
                        if (keyValue != null) {
                            String config = keyValue.getValue().toString(StandardCharsets.UTF_8);

                            etcdSubscribeCallback.callback(config);
                        }
                    } else if (eventType == EventType.DELETE) {
                        etcdSubscribeCallback.callback(null);
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {

            }
        };
        watchClient.watch(byteSequence, listener);

        return watchClient;
    }

    public void unsubscribeConfig(String group, String serviceId, Watch watchClient) {
        watchClient.close();
    }

    @Override
    public void destroy() throws Exception {
        client.close();

        LOG.info("Shutting down JEtcd client...");
    }
}
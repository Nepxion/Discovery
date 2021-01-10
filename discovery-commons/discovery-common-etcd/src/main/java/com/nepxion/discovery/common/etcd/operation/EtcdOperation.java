package com.nepxion.discovery.common.etcd.operation;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Congwei Xu
 * @version 1.0
 */

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.Watch;
import io.etcd.jetcd.Watch.Listener;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.watch.WatchEvent;
import io.etcd.jetcd.watch.WatchResponse;

public class EtcdOperation {
    @Autowired
    private Client client;

    public String getConfig(String group, String serviceId) throws ExecutionException, InterruptedException {
        String value = null;
        KV kvClient = client.getKVClient();
        ByteSequence byteSequence = ByteSequence.from(group + "-" + serviceId, StandardCharsets.UTF_8);
        GetResponse getResponse = kvClient.get(byteSequence).get();
        if (getResponse.getKvs().size() > 0) {
            KeyValue keyValue = getResponse.getKvs().get(0);
            value = keyValue.getValue().toString(StandardCharsets.UTF_8);
        }
        return value;
    }

    public boolean deleteConfig(String group, String serviceId) throws ExecutionException, InterruptedException {
        KV kvClient = client.getKVClient();
        ByteSequence byteSequence = ByteSequence.from(group + "-" + serviceId, StandardCharsets.UTF_8);
        kvClient.delete(byteSequence);
        return true;
    }

    public boolean putConfig(String group, String serviceId, String config) throws ExecutionException, InterruptedException {
        KV kvClient = client.getKVClient();
        ByteSequence keyByteSequence = ByteSequence.from(group + "-" + serviceId, StandardCharsets.UTF_8);
        ByteSequence valueByteSequence = ByteSequence.from(config, StandardCharsets.UTF_8);
        kvClient.put(keyByteSequence, valueByteSequence);
        return true;
    }

    public Listener subscribeConfig(String group, String serviceId, EtcdSubscribeCallback etcdSubscribeCallback) throws Exception {
        Watch watchClient = client.getWatchClient();
        ByteSequence byteSequence = ByteSequence.from(group + "-" + serviceId, StandardCharsets.UTF_8);
        Listener listener = new Listener() {
            @Override
            public void onNext(WatchResponse response) {
                for (WatchEvent event : response.getEvents()) {
                    KeyValue keyValue = event.getKeyValue();
                    if (keyValue != null) {
                        String config = keyValue.getValue().toString(StandardCharsets.UTF_8);
                        etcdSubscribeCallback.callback(config);
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
        return listener;
    }

    public void unsubscribeConfig(String group, String serviceId, Listener listener) throws Exception {
        if (client != null) {
            client.close();
        }
    }
}
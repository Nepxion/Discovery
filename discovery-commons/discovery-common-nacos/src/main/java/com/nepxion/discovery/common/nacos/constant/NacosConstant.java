package com.nepxion.discovery.common.nacos.constant;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.alibaba.nacos.api.PropertyKeyConst;

public class NacosConstant extends PropertyKeyConst {
    public static final String NACOS_TYPE = "Nacos";
    public static final String NACOS_SERVER_ADDR = "nacos.server-addr";
    public static final String NACOS_ACCESS_KEY = "nacos.access-key";
    public static final String NACOS_SECRET_KEY = "nacos.secret-key";
    public static final String NACOS_USERNAME = "nacos.username";
    public static final String NACOS_PASSWORD = "nacos.password";
    public static final String NACOS_PLUGIN_NAMESPACE = "nacos.plugin.namespace";
    public static final String NACOS_PLUGIN_CLUSTER_NAME = "nacos.plugin.cluster-name";
    public static final String NACOS_PLUGIN_CONTEXT_PATH = "nacos.plugin.context-path";
    public static final String NACOS_PLUGIN_CONFIG_LONG_POLL_TIMEOUT = "nacos.plugin.config-long-poll-timeout";
    public static final String NACOS_PLUGIN_CONFIG_RETRY_TIME = "nacos.plugin.config-retry-time";
    public static final String NACOS_PLUGIN_MAX_RETRY = "nacos.plugin.max-retry";
    public static final String NACOS_PLUGIN_ENDPOINT = "nacos.plugin.endpoint";
    public static final String NACOS_PLUGIN_ENDPOINT_PORT = "nacos.plugin.endpoint-port";
    public static final String NACOS_PLUGIN_IS_USE_ENDPOINT_PARSING_RULE = "nacos.plugin.is-use-endpoint-parsing-rule";
    public static final String NACOS_PLUGIN_IS_USE_CLOUD_NAMESPACE_PARSING = "nacos.plugin.is-use-cloud-namespace-parsing";
    public static final String NACOS_PLUGIN_ENCODE = "nacos.plugin.encode";
    public static final String NACOS_PLUGIN_NAMING_LOAD_CACHE_AT_START = "nacos.plugin.naming-load-cache-at-start";
    public static final String NACOS_PLUGIN_NAMING_CLIENT_BEAT_THREAD_COUNT = "nacos.plugin.naming-client-beat-thread-count";
    public static final String NACOS_PLUGIN_NAMING_POLLING_THREAD_COUNT = "nacos.plugin.naming-polling-thread-count";
    public static final String NACOS_PLUGIN_NAMING_REQUEST_DOMAIN_RETRY_COUNT = "nacos.plugin.naming-request-domain-max-retry-count";
    public static final String NACOS_PLUGIN_NAMING_PUSH_EMPTY_PROTECTION = "nacos.plugin.naming-push-empty-protection";
    public static final String NACOS_PLUGIN_RAM_ROLE_NAME = "nacos.plugin.ram-role-name";
    public static final String NACOS_PLUGIN_ENABLE_REMOTE_SYNC_CONFIG = "nacos.plugin.enable-remote-sync-config";
    public static final String NACOS_PLUGIN_TIMEOUT = "nacos.plugin.timout";
    public static final long NACOS_PLUGIN_DEFAULT_TIMEOUT = 30000;
}
package com.nepxion.discovery.sentinel.starter.apollo;

import com.alibaba.csp.sentinel.datasource.AbstractDataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Weihua
 * @since 5.3.9
 */
public class ApolloDataSource<T> extends AbstractDataSource<String, T> {

    private static final Logger LOG = LoggerFactory.getLogger(ApolloDataSource.class);

    private final String ruleKey;
    private final Config config;
    private final String defaultRuleJson;

    public ApolloDataSource(String namespace, String ruleKey, String defaultRuleJson) {
        super(source -> JSON.parseObject(source, new TypeReference<>()));
        this.ruleKey = ruleKey;
        this.defaultRuleJson = defaultRuleJson;
        this.config = ConfigService.getConfig(namespace);
        ConfigChangeListener configChangeListener = event -> {
            ConfigChange change = event.getChange(ruleKey);
            if (change != null) {
                LOG.info("Rule of key: " + ruleKey + " changes from Apollo: " + change.toString());
            }
            loadRules();
        };
        config.addChangeListener(configChangeListener, Sets.newHashSet(ruleKey));
    }

    private void loadRules() {
        try {
            T newValue = loadConfig();
            if (newValue == null) {
                LOG.warn("Failed to load config, please check your apollo data source.");
            }
            getProperty().updateValue(newValue);
        } catch (Throwable throwable) {
            LOG.error("Failed to load config for rule key:" + ruleKey, throwable);
        }
    }

    @Override
    public String readSource() {
        return config.getProperty(ruleKey, defaultRuleJson);
    }

    @Override
    public void close() {

    }
}

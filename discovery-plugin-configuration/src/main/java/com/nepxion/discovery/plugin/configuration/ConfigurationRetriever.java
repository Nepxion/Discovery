package com.nepxion.discovery.plugin.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.google.common.eventbus.Subscribe;
import com.nepxion.discovery.plugin.configuration.constant.ConfigurationConstant;
import com.nepxion.discovery.plugin.configuration.loader.ConfigurationLoader;
import com.nepxion.discovery.plugin.core.exception.PluginException;
import com.nepxion.eventbus.annotation.EventBus;
import com.nepxion.eventbus.core.Event;

@EventBus
public class ConfigurationRetriever {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationRetriever.class);

    @Value("${" + ConfigurationConstant.SPRING_APPLICATION_DISCOVERY_REMOTE_CONFIG_ENABLED + ":false}")
    private Boolean remoteConfigEnabled;

    @Autowired
    private ConfigurationLoader configurationLoader;

    @Autowired
    private ConfigurationParser configurationParser;

    public void initialize() throws PluginException {
        LOG.info("********** {} config starts to initialize **********", remoteConfigEnabled ? "Remote" : "Local");

        InputStream inputStream = null;
        try {
            if (remoteConfigEnabled) {
                inputStream = configurationLoader.getRemoteInputStream();
            } else {
                inputStream = configurationLoader.getLocalInputStream();
            }
            parse(inputStream);
        } catch (IOException e) {
            throw new PluginException(e);
        } catch (DocumentException e) {
            throw new PluginException(e);
        } finally {
            if (inputStream != null) {
                IOUtils.closeQuietly(inputStream);
            }
        }
    }

    @Subscribe
    public void subscribe(Event event) {
        if (!remoteConfigEnabled) {
            return;
        }

        LOG.info("********** Remote config change has been retrieved **********");

        InputStream inputStream = (InputStream) event.getSource();
        try {
            parse(inputStream);
        } catch (IOException e) {
            throw new PluginException(e);
        } catch (DocumentException e) {
            throw new PluginException(e);
        } finally {
            if (inputStream != null) {
                IOUtils.closeQuietly(inputStream);
            }
        }
    }

    private void parse(InputStream inputStream) throws DocumentException, IOException {
        if (inputStream == null) {
            throw new PluginException("Failed to load " + (remoteConfigEnabled ? "remote" : "local") + " config, no input stream returns");
        }

        configurationParser.parse(inputStream);
    }
}
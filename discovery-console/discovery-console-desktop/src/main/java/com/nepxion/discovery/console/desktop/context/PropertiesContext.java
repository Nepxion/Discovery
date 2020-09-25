package com.nepxion.discovery.console.desktop.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.File;
import java.net.URL;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class PropertiesContext extends PropertiesConfiguration {
    private static final String PATH = "config/console.properties";
    private static PropertiesContext propertiesContext;

    public static void initialize() {
        try {
            propertiesContext = new PropertiesContext(PATH);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static PropertiesContext getProperties() {
        return propertiesContext;
    }

    public PropertiesContext(String path) throws ConfigurationException {
        super(path);
    }

    public PropertiesContext(File file) throws ConfigurationException {
        super(file);
    }

    public PropertiesContext(URL url) throws ConfigurationException {
        super(url);
    }
}
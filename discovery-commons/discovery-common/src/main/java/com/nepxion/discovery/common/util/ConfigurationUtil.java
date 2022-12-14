package com.nepxion.discovery.common.util;

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
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.YAMLConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.io.IOUtils;

import com.nepxion.discovery.common.constant.DiscoveryConstant;

public class ConfigurationUtil {
    private static YAMLConfiguration yamlConfiguration = new YAMLConfiguration();
    private static PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();

    public static Map<String, String> parseYaml(String content) throws IOException, ConfigurationException {
        Map<String, String> map = new HashMap<String, String>();

        InputStream inputStream = null;
        try {
            inputStream = IOUtils.toInputStream(content, DiscoveryConstant.ENCODING_UTF_8);

            yamlConfiguration.read(inputStream);

            Iterator<String> keys = yamlConfiguration.getKeys();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = yamlConfiguration.getString(key);

                map.put(key, value);
            }
        } finally {
            if (inputStream != null) {
                IOUtils.closeQuietly(inputStream);
            }
        }

        return map;
    }

    public static Map<String, String> parseProperties(String content) throws IOException, ConfigurationException {
        Map<String, String> map = new HashMap<String, String>();

        InputStream inputStream = null;
        Reader reader = null;
        try {
            inputStream = IOUtils.toInputStream(content, DiscoveryConstant.ENCODING_UTF_8);
            reader = new InputStreamReader(inputStream, DiscoveryConstant.ENCODING_UTF_8);

            propertiesConfiguration.read(reader);

            Iterator<String> keys = propertiesConfiguration.getKeys();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = propertiesConfiguration.getString(key);

                map.put(key, value);
            }
        } finally {
            if (reader != null) {
                IOUtils.closeQuietly(reader);
            }

            if (inputStream != null) {
                IOUtils.closeQuietly(inputStream);
            }
        }

        return map;
    }
}
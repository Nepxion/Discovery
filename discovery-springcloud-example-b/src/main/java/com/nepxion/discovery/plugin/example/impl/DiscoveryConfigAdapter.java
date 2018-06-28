package com.nepxion.discovery.plugin.example.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

import com.nepxion.discovery.plugin.configcenter.ConfigAdapter;

public class DiscoveryConfigAdapter extends ConfigAdapter {
    @Override
    protected String getLocalContextPath() {
        return "classpath:rule.xml";
    }

    @Override
    public InputStream getRemoteInputStream() {
        try {
            return FileUtils.openInputStream(new File("src/main/resources/rule.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
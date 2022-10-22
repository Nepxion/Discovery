package com.nepxion.discovery.plugin.admincenter.resource;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.framework.generator.GitGenerator;

public class GitResourceImpl implements GitResource {
    @Autowired
    private GitGenerator gitGenerator;

    @Override
    public Map<String, String> map() {
        return gitGenerator.getMap();
    }

    @Override
    public String text() {
        return gitGenerator.getText();
    }
}
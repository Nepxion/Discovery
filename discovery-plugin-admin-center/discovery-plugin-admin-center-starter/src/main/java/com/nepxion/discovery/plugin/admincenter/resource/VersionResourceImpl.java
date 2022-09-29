package com.nepxion.discovery.plugin.admincenter.resource;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.event.PluginEventWapper;
import com.nepxion.discovery.plugin.framework.event.VersionClearedEvent;
import com.nepxion.discovery.plugin.framework.event.VersionUpdatedEvent;

public class VersionResourceImpl implements VersionResource {
    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private PluginEventWapper pluginEventWapper;

    @Override
    public void update(String version, boolean async) {
        if (StringUtils.isEmpty(version)) {
            throw new DiscoveryException("Version can't be null or empty");
        }

        String dynamicVersion = null;
        String localVersion = null;
        String[] versionArray = StringUtils.split(version, DiscoveryConstant.SEPARATE);
        if (versionArray.length == 2) {
            dynamicVersion = versionArray[0];
            localVersion = versionArray[1];
        } else if (versionArray.length == 1) {
            dynamicVersion = versionArray[0];
        } else {
            throw new DiscoveryException("Invalid version format, it must be '${dynamicVersion}' or '${dynamicVersion};${localVersion}'");
        }

        pluginEventWapper.fireVersionUpdated(new VersionUpdatedEvent(dynamicVersion, localVersion), async);
    }

    @Override
    public void clear(String version, boolean async) {
        // 修复Swagger的一个Bug，当在Swagger界面不输入版本号的时候，传到后端变成了“{}”
        if (StringUtils.isNotEmpty(version) && StringUtils.equals(version.trim(), "{}")) {
            version = null;
        }

        pluginEventWapper.fireVersionCleared(new VersionClearedEvent(version), async);
    }

    @Override
    public List<String> view() {
        List<String> versionList = new ArrayList<String>(2);

        String localVersion = pluginAdapter.getLocalVersion();
        String dynamicVersion = pluginAdapter.getDynamicVersion();

        versionList.add(StringUtils.isNotEmpty(localVersion) ? localVersion : StringUtils.EMPTY);
        versionList.add(StringUtils.isNotEmpty(dynamicVersion) ? dynamicVersion : StringUtils.EMPTY);

        return versionList;
    }
}
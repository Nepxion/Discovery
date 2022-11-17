package com.nepxion.discovery.common.util;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.InstanceEntity;
import com.nepxion.discovery.common.entity.VersionSortEntity;
import com.nepxion.discovery.common.entity.VersionSortType;

public class VersionSortUtil {
    public static List<String> assembleVersionList(List<InstanceEntity> instanceEntityList, VersionSortType versionSortType) {
        if (CollectionUtils.isEmpty(instanceEntityList)) {
            return null;
        }

        List<VersionSortEntity> versionSortEntityList = new ArrayList<VersionSortEntity>();
        for (InstanceEntity instanceEntity : instanceEntityList) {
            String version = instanceEntity.getVersion();
            String serviceUUId = instanceEntity.getServiceUUId();

            VersionSortEntity versionSortEntity = new VersionSortEntity();
            versionSortEntity.setVersion(version);
            versionSortEntity.setServiceUUId(serviceUUId);

            versionSortEntityList.add(versionSortEntity);
        }

        List<String> versionList = VersionSortUtil.getVersionList(versionSortEntityList, versionSortType);

        // 当服务未接入本框架或者版本号未设置（表现出来的值为DiscoveryConstant.DEFAULT），被认为是老版本
        String defaultVersion = DiscoveryConstant.DEFAULT;
        if (versionList.contains(defaultVersion)) {
            versionList.remove(defaultVersion);
            versionList.add(0, defaultVersion);
        }

        return versionList;
    }

    public static List<String> getVersionList(List<VersionSortEntity> versionSortEntityList, VersionSortType versionSortType) {
        List<String> versionList = new ArrayList<String>();

        Collections.sort(versionSortEntityList, new Comparator<VersionSortEntity>() {
            public int compare(VersionSortEntity versionSortEntity1, VersionSortEntity versionSortEntity2) {
                if (versionSortType == VersionSortType.VERSION) {
                    String version1 = versionSortEntity1.getVersion();
                    String version2 = versionSortEntity2.getVersion();

                    return version1.compareTo(version2);
                } else {
                    String serviceUUId1 = versionSortEntity1.getServiceUUId();
                    String serviceUUId2 = versionSortEntity2.getServiceUUId();

                    return serviceUUId1.compareTo(serviceUUId2);
                }
            }
        });

        for (VersionSortEntity versionSortEntity : versionSortEntityList) {
            String version = versionSortEntity.getVersion();
            if (!versionList.contains(version)) {
                versionList.add(version);
            }
        }

        return versionList;
    }
}
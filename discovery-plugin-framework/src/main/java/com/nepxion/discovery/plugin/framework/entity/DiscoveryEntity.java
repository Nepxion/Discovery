package com.nepxion.discovery.plugin.framework.entity;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

public class DiscoveryEntity extends FilterHolderEntity {
    private static final long serialVersionUID = -7417362859952278987L;

    private VersionEntity versionEntity;

    public DiscoveryEntity() {

    }

    public VersionEntity getVersionEntity() {
        return versionEntity;
    }

    public void setVersionEntity(VersionEntity versionEntity) {
        this.versionEntity = versionEntity;
    }
}
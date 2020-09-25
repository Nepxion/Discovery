package com.nepxion.discovery.common.entity;

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

    private VersionFilterEntity versionFilterEntity;
    private RegionFilterEntity regionFilterEntity;
    private WeightFilterEntity weightFilterEntity;

    public VersionFilterEntity getVersionFilterEntity() {
        return versionFilterEntity;
    }

    public void setVersionFilterEntity(VersionFilterEntity versionFilterEntity) {
        this.versionFilterEntity = versionFilterEntity;
    }

    public RegionFilterEntity getRegionFilterEntity() {
        return regionFilterEntity;
    }

    public void setRegionFilterEntity(RegionFilterEntity regionFilterEntity) {
        this.regionFilterEntity = regionFilterEntity;
    }

    public WeightFilterEntity getWeightFilterEntity() {
        return weightFilterEntity;
    }

    public void setWeightFilterEntity(WeightFilterEntity weightFilterEntity) {
        this.weightFilterEntity = weightFilterEntity;
    }
}
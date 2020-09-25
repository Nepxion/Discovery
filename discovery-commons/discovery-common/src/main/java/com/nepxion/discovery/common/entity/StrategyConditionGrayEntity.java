package com.nepxion.discovery.common.entity;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

public class StrategyConditionGrayEntity extends StrategyConditionEntity {
    private static final long serialVersionUID = 4992751531082022953L;

    private VersionWeightEntity versionWeightEntity;
    private RegionWeightEntity regionWeightEntity;
    private AddressWeightEntity addressWeightEntity;

    public VersionWeightEntity getVersionWeightEntity() {
        return versionWeightEntity;
    }

    public void setVersionWeightEntity(VersionWeightEntity versionWeightEntity) {
        this.versionWeightEntity = versionWeightEntity;
    }

    public RegionWeightEntity getRegionWeightEntity() {
        return regionWeightEntity;
    }

    public void setRegionWeightEntity(RegionWeightEntity regionWeightEntity) {
        this.regionWeightEntity = regionWeightEntity;
    }

    public AddressWeightEntity getAddressWeightEntity() {
        return addressWeightEntity;
    }

    public void setAddressWeightEntity(AddressWeightEntity addressWeightEntity) {
        this.addressWeightEntity = addressWeightEntity;
    }
}
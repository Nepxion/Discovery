package com.nepxion.discovery.common.entity;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

public class StrategyConditionBlueGreenEntity extends StrategyConditionEntity {
    private static final long serialVersionUID = 7119457349396638259L;

    private String versionId;
    private String regionId;
    private String addressId;
    private String versionWeightId;
    private String regionWeightId;

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getVersionWeightId() {
        return versionWeightId;
    }

    public void setVersionWeightId(String versionWeightId) {
        this.versionWeightId = versionWeightId;
    }

    public String getRegionWeightId() {
        return regionWeightId;
    }

    public void setRegionWeightId(String regionWeightId) {
        this.regionWeightId = regionWeightId;
    }
}
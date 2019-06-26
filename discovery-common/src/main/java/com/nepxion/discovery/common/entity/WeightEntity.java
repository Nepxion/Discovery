package com.nepxion.discovery.common.entity;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

public class WeightEntity extends MapWeightEntity {
    private static final long serialVersionUID = 4242297554671632704L;

    private String consumerServiceName;
    private String providerServiceName;
    private WeightType type;

    public String getConsumerServiceName() {
        return consumerServiceName;
    }

    public void setConsumerServiceName(String consumerServiceName) {
        this.consumerServiceName = consumerServiceName;
    }

    public String getProviderServiceName() {
        return providerServiceName;
    }

    public void setProviderServiceName(String providerServiceName) {
        this.providerServiceName = providerServiceName;
    }

    public WeightType getType() {
        return type;
    }

    public void setType(WeightType type) {
        this.type = type;
    }
}
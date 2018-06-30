package com.nepxion.discovery.plugin.framework.entity;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

public class RegisterEntity extends FilterHolderEntity {
    private static final long serialVersionUID = -2097322826969006191L;

    private CountEntity countEntity;

    public RegisterEntity() {

    }

    public CountEntity getCountEntity() {
        return countEntity;
    }

    public void setCountEntity(CountEntity countEntity) {
        this.countEntity = countEntity;
    }
}
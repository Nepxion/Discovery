package com.nepxion.discovery.plugin.framework.event;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.Serializable;

import com.nepxion.discovery.common.entity.ParameterEntity;

public class ParameterChangedEvent implements Serializable {
    private static final long serialVersionUID = 7843872188960155327L;

    private ParameterEntity parameterEntity;

    public ParameterChangedEvent(ParameterEntity parameterEntity) {
        this.parameterEntity = parameterEntity;
    }

    public ParameterEntity getParameterEntity() {
        return parameterEntity;
    }
}
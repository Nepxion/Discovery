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
import java.util.Map;

public class AlarmEvent implements Serializable {
    private static final long serialVersionUID = 5966845230262521754L;

    private Map<String, String> contextMap;

    public AlarmEvent(Map<String, String> contextMap) {
        this.contextMap = contextMap;
    }

    public Map<String, String> getContextMap() {
        return contextMap;
    }
}
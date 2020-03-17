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

public class RuleFailureEvent implements Serializable {
    private static final long serialVersionUID = 954041724496099958L;

    private String rule;
    private Exception exception;

    public RuleFailureEvent(String rule, Exception exception) {
        this.rule = rule;
    }

    public String getRule() {
        return rule;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
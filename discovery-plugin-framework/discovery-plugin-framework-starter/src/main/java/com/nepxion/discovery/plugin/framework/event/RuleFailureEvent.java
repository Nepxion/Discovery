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

import com.nepxion.discovery.common.entity.RuleType;

public class RuleFailureEvent implements Serializable {
    private static final long serialVersionUID = 954041724496099958L;

    private RuleType ruleType;
    private String rule;
    private Exception exception;

    public RuleFailureEvent(RuleType ruleType, String rule, Exception exception) {
        this.ruleType = ruleType;
        this.rule = rule;
        this.exception = exception;
    }

    public RuleType getRuleType() {
        return ruleType;
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
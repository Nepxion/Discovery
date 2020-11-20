package com.nepxion.discovery.common.entity;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

public enum EscapeType {
    AND("&", "&amp;"),
    LESS_THAN("<", "&lt;"),
    DOUBLE_QUOTATION("\"", "&quot;");
    // GREATER_THAN(">", "&gt;");
    // SINGLE_QUOTATION("'", "&apos;");

    private String source;
    private String target;

    private EscapeType(String source, String target) {
        this.source = source;
        this.target = target;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return source;
    }

    public static String escape(String value, boolean escaped) {
        if (value == null) {
            return null;
        }

        EscapeType[] escapeTypes = EscapeType.values();
        for (int i = 0; i < escapeTypes.length; i++) {
            EscapeType escapeType = escapeTypes[i];
            value = value.replace(escaped ? escapeType.getSource() : escapeType.getTarget(), escaped ? escapeType.getTarget() : escapeType.getSource());
        }

        return value;
    }
}
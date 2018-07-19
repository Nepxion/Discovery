package com.nepxion.discovery.console.desktop.workspace.topology;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.awt.Point;

public class TopologyEntity {
    public static final String REGISTRY_LARGE_IMAGE = "registry_48.png";
    public static final String MQ_LARGE_IMAGE = "mq_48.png";
    public static final String CACHE_LARGE_IMAGE = "cache_48.png";
    public static final String LOGGER_LARGE_IMAGE = "logger_48.png";
    public static final String SERVICE_LARGE_IMAGE = "service_48.png";
    public static final String REFERENCE_LARGE_IMAGE = "reference_48.png";

    public static final String REGISTRY_SMALL_IMAGE = "registry_32.png";
    public static final String MQ_SMALL_IMAGE = "mq_32.png";
    public static final String CACHE_SMALL_IMAGE = "cache_32.png";
    public static final String LOGGER_SMALL_IMAGE = "logger_32.png";
    public static final String SERVICE_SMALL_IMAGE = "service_32.png";
    public static final String REFERENCE_SMALL_IMAGE = "reference_32.png";

    private TopologyEntityType type;
    private String image;
    private Point location;
    private boolean horizontalPile;

    public TopologyEntity(TopologyEntityType type, boolean largeStyle, Point location) {
        initialize(type, largeStyle);

        this.location = location;
    }

    public TopologyEntity(TopologyEntityType type, boolean largeStyle, boolean horizontalPile) {
        initialize(type, largeStyle);

        this.horizontalPile = horizontalPile;
    }

    private void initialize(TopologyEntityType type, boolean largeStyle) {
        this.type = type;
        switch (type) {
            case REGISTRY:
                image = largeStyle ? REGISTRY_LARGE_IMAGE : REGISTRY_SMALL_IMAGE;
                break;
            case MQ:
                image = largeStyle ? MQ_LARGE_IMAGE : MQ_SMALL_IMAGE;
                break;
            case CACHE:
                image = largeStyle ? CACHE_LARGE_IMAGE : CACHE_SMALL_IMAGE;
                break;
            case LOGGER:
                image = largeStyle ? LOGGER_LARGE_IMAGE : LOGGER_SMALL_IMAGE;
                break;
            case SERVICE:
                image = largeStyle ? SERVICE_LARGE_IMAGE : SERVICE_SMALL_IMAGE;
                break;
            case REFERENCE:
                image = largeStyle ? REFERENCE_LARGE_IMAGE : REFERENCE_SMALL_IMAGE;
                break;
        }
    }

    public TopologyEntityType getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    public Point getLocation() {
        return location;
    }

    public boolean isHorizontalPile() {
        return horizontalPile;
    }
}
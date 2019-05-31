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
    public static final String THEME_DIRECTORY = "theme_1/";

    public static final String SERVICE_GROUP_LARGE_IMAGE = "service_group_80.png";
    public static final String SERVICE_GROUP_MIDDLE_IMAGE = "service_group_64.png";
    public static final String SERVICE_GROUP_SMALL_IMAGE = "service_group_48.png";

    public static final String REFERENCE_GROUP_LARGE_IMAGE = "reference_group_80.png";
    public static final String REFERENCE_GROUP_MIDDLE_IMAGE = "reference_group_64.png";
    public static final String REFERENCE_GROUP_SMALL_IMAGE = "reference_group_48.png";

    public static final String GATEWAY_GROUP_LARGE_IMAGE = "gateway_group_80.png";
    public static final String GATEWAY_GROUP_MIDDLE_IMAGE = "gateway_group_64.png";
    public static final String GATEWAY_GROUP_SMALL_IMAGE = "gateway_group_48.png";

    public static final String SERVICE_LARGE_IMAGE = THEME_DIRECTORY + "service_64.png";
    public static final String SERVICE_MIDDLE_IMAGE = THEME_DIRECTORY + "service_48.png";
    public static final String SERVICE_SMALL_IMAGE = THEME_DIRECTORY + "service_32.png";

    public static final String REFERENCE_LARGE_IMAGE = THEME_DIRECTORY + "reference_64.png";
    public static final String REFERENCE_MIDDLE_IMAGE = THEME_DIRECTORY + "reference_48.png";
    public static final String REFERENCE_SMALL_IMAGE = THEME_DIRECTORY + "reference_32.png";

    public static final String GATEWAY_LARGE_IMAGE = THEME_DIRECTORY + "gateway_64.png";
    public static final String GATEWAY_MIDDLE_IMAGE = THEME_DIRECTORY + "gateway_48.png";
    public static final String GATEWAY_SMALL_IMAGE = THEME_DIRECTORY + "gateway_32.png";

    private TopologyEntityType entityType;
    private TopologyStyleType styleType;
    private String image;
    private Point location;
    private boolean horizontalPile;

    public TopologyEntity(TopologyEntityType entityType, TopologyStyleType styleType, Point location) {
        initialize(entityType, styleType);

        this.location = location;
    }

    public TopologyEntity(TopologyEntityType entityType, TopologyStyleType styleType, boolean horizontalPile) {
        initialize(entityType, styleType);

        this.horizontalPile = horizontalPile;
    }

    private void initialize(TopologyEntityType entityType, TopologyStyleType styleType) {
        this.entityType = entityType;
        this.styleType = styleType;
        switch (entityType) {
            case SERVICE_GROUP:
                switch (styleType) {
                    case LARGE:
                        image = SERVICE_GROUP_LARGE_IMAGE;
                        break;
                    case MIDDLE:
                        image = SERVICE_GROUP_MIDDLE_IMAGE;
                        break;
                    case SMALL:
                        image = SERVICE_GROUP_SMALL_IMAGE;
                        break;
                }
                break;
            case REFERENCE_GROUP:
                switch (styleType) {
                    case LARGE:
                        image = REFERENCE_GROUP_LARGE_IMAGE;
                        break;
                    case MIDDLE:
                        image = REFERENCE_GROUP_MIDDLE_IMAGE;
                        break;
                    case SMALL:
                        image = REFERENCE_GROUP_SMALL_IMAGE;
                        break;
                }
                break;
            case GATEWAY_GROUP:
                switch (styleType) {
                    case LARGE:
                        image = GATEWAY_GROUP_LARGE_IMAGE;
                        break;
                    case MIDDLE:
                        image = GATEWAY_GROUP_MIDDLE_IMAGE;
                        break;
                    case SMALL:
                        image = GATEWAY_GROUP_SMALL_IMAGE;
                        break;
                }
                break;
            case SERVICE:
                switch (styleType) {
                    case LARGE:
                        image = SERVICE_LARGE_IMAGE;
                        break;
                    case MIDDLE:
                        image = SERVICE_MIDDLE_IMAGE;
                        break;
                    case SMALL:
                        image = SERVICE_SMALL_IMAGE;
                        break;
                }
                break;
            case REFERENCE:
                switch (styleType) {
                    case LARGE:
                        image = REFERENCE_LARGE_IMAGE;
                        break;
                    case MIDDLE:
                        image = REFERENCE_MIDDLE_IMAGE;
                        break;
                    case SMALL:
                        image = REFERENCE_SMALL_IMAGE;
                        break;
                }
                break;
            case GATEWAY:
                switch (styleType) {
                    case LARGE:
                        image = GATEWAY_LARGE_IMAGE;
                        break;
                    case MIDDLE:
                        image = GATEWAY_MIDDLE_IMAGE;
                        break;
                    case SMALL:
                        image = GATEWAY_SMALL_IMAGE;
                        break;
                }
                break;
        }
    }

    public TopologyEntityType getEntityType() {
        return entityType;
    }

    public TopologyStyleType getStyleType() {
        return styleType;
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
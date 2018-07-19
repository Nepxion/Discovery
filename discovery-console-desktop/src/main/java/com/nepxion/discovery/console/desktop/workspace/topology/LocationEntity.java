package com.nepxion.discovery.console.desktop.workspace.topology;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

public class LocationEntity {
    private int startX;
    private int startY;
    private int horizontalGap;
    private int verticalGap;

    public LocationEntity(int startX, int startY, int horizontalGap, int verticalGap) {
        this.startX = startX;
        this.startY = startY;
        this.horizontalGap = horizontalGap;
        this.verticalGap = verticalGap;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getHorizontalGap() {
        return horizontalGap;
    }

    public void setHorizontalGap(int horizontalGap) {
        this.horizontalGap = horizontalGap;
    }

    public int getVerticalGap() {
        return verticalGap;
    }

    public void setVerticalGap(int verticalGap) {
        this.verticalGap = verticalGap;
    }
}
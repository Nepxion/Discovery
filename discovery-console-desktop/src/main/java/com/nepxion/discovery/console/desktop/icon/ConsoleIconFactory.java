package com.nepxion.discovery.console.desktop.icon;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import javax.swing.ImageIcon;

import com.nepxion.swing.icon.IconFactory;

public class ConsoleIconFactory extends IconFactory {
    public static final String ICON_FOLDER = "com/nepxion/discovery/console/desktop/icon/";

    public static ImageIcon getContextIcon(String iconName) {
        return getIcon(ICON_FOLDER + iconName);
    }
}
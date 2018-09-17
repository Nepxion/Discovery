package com.nepxion.discovery.console.desktop;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.nepxion.discovery.console.desktop.controller.ServiceController;
import com.nepxion.discovery.console.desktop.icon.ConsoleIconFactory;
import com.nepxion.discovery.console.desktop.locale.ConsoleLocale;
import com.nepxion.swing.frame.JBasicFrame;
import com.nepxion.swing.framework.reflection.JReflectionHierarchy;
import com.nepxion.swing.style.texture.shrink.JBlackHeaderTextureStyle;
import com.nepxion.swing.style.texture.shrink.JGreenOutlookTextureStyle;

public class ConsoleFrame extends JBasicFrame {
    private static final long serialVersionUID = 1L;

    public ConsoleFrame() {
        super(ConsoleLocale.getString("title") + " [" + ServiceController.getDiscoveryType() + " " + ConsoleLocale.getString("discovery_center") + "] [" + ServiceController.getConfigType() + " " + ConsoleLocale.getString("config_center") + "]", ConsoleIconFactory.getSwingIcon("ribbon/navigator_nepxion.png"), new Dimension(1280, 900));
    }

    public void launch() {
        ConsoleHierarchy deployHierarchy = new ConsoleHierarchy(new JBlackHeaderTextureStyle(), new JGreenOutlookTextureStyle());

        JReflectionHierarchy reflectionHierarchy = new JReflectionHierarchy(20, 20);
        reflectionHierarchy.setContentPane(deployHierarchy);

        getContentPane().add(reflectionHierarchy);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

            }
        });

        setExtendedState(ConsoleFrame.MAXIMIZED_BOTH);
        setVisible(true);
        toFront();
    }
}
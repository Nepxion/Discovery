package com.nepxion.discovery.console.desktop.ui;

/**
 * <p>Title: Nepxion Thunder</p>
 * <p>Description: Nepxion Thunder For Distribution</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.nepxion.discovery.console.desktop.context.UIContext;
import com.nepxion.swing.separator.JBasicSeparator;

public class UIFactory {
    public static JBasicSeparator createSeparator() {
        JBasicSeparator separator = new JBasicSeparator(JBasicSeparator.HORIZONTAL, JBasicSeparator.LOWERED_STYLE, -1);
        separator.setBrightColor(new Color(197, 196, 198));
        separator.setDarkColor(new Color(153, 152, 154));

        return separator;
    }

    public static Border createTitledBorder(String title) {
        return BorderFactory.createTitledBorder(null, title, TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, new Font(UIContext.getFontName(), Font.PLAIN, UIContext.getLargeFontSize()), new Color(64, 0, 0));
    }
}

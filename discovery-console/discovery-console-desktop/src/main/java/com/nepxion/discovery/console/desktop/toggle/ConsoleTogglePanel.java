package com.nepxion.discovery.console.desktop.toggle;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.awt.BorderLayout;
import java.awt.Component;

import com.nepxion.discovery.console.desktop.icon.ConsoleIconFactory;
import com.nepxion.discovery.console.desktop.locale.ConsoleLocale;
import com.nepxion.swing.element.IElementNode;
import com.nepxion.swing.locale.SwingLocale;
import com.nepxion.swing.toggle.JThreadTogglePanel;

public class ConsoleTogglePanel extends JThreadTogglePanel {
    private static final long serialVersionUID = 1L;

    private IElementNode listElementNode;

    private ConsoleToggleSpace toggleSpace;

    public ConsoleTogglePanel(IElementNode listElementNode) {
        super(SwingLocale.getString("initialize_and_wait"));

        this.listElementNode = listElementNode;

        setToggleBannerIcon(ConsoleIconFactory.getSwingIcon("banner/edit.png"));
        setInformationText(ConsoleLocale.getString("initialize_component"));
        setInformationIcon(ConsoleIconFactory.getSwingIcon("banner/query_128.png"));
        setErrorText(ConsoleLocale.getString("initialize_component_failure"));
        setErrorIcon(ConsoleIconFactory.getSwingIcon("banner/error_128.png"));
        setThreadPanelWidth(300);

        showInformation();

        setLayout(new BorderLayout());
    }

    public Component getContentPane() {
        return toggleSpace;
    }

    public boolean isLoadCache() {
        return toggleSpace != null;
    }

    protected void loadForeground(Object data) throws Exception {
        if (toggleSpace == null) {
            toggleSpace = new ConsoleToggleSpace(listElementNode);
            add(toggleSpace, BorderLayout.CENTER);
        }
    }

    protected Object loadBackground() throws Exception {
        return null;
    }
}
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

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.nepxion.discovery.console.desktop.workspace.ServiceTopology;
import com.nepxion.swing.element.IElementNode;

public class ConsoleToggleSpace extends JPanel {
    private static final long serialVersionUID = 1L;

    private IElementNode listElementNode;
    private JPanel blankPane = new JPanel();

    public ConsoleToggleSpace(IElementNode listElementNode) {
        this.listElementNode = listElementNode;

        setLayout(new BorderLayout());
        add(createContentPane(), BorderLayout.CENTER);
    }

    private JComponent createContentPane() {
        JComponent contentPane = null;

        String name = listElementNode.getName();
        if (name.equals(ConsoleToggleConstants.SERVICE_TOPOLOGY)) {
            contentPane = new ServiceTopology();
        } else {
            contentPane = blankPane;
        }

        return contentPane;
    }
}
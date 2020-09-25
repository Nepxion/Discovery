package com.nepxion.discovery.console.desktop.toggle;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.swing.element.IElementNode;
import com.nepxion.swing.list.toggle.AbstractToggleAdapter;
import com.nepxion.swing.list.toggle.JToggleList;
import com.nepxion.swing.toggle.ITogglePanel;

public class ConsoleToggleListener extends AbstractToggleAdapter {
    public ConsoleToggleListener(JToggleList list) {
        super(list);
    }

    public ITogglePanel getTogglePanel(IElementNode elementNode) {
        Object userObject = elementNode.getUserObject();
        if (userObject == null) {
            userObject = new ConsoleTogglePanel(elementNode);
            elementNode.setUserObject(userObject);
        }

        return (ITogglePanel) userObject;
    }
}
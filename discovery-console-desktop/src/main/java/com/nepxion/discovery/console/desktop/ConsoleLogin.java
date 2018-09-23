package com.nepxion.discovery.console.desktop;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.awt.Frame;
import java.util.Locale;

import com.nepxion.discovery.common.entity.UserEntity;
import com.nepxion.discovery.console.desktop.controller.ServiceController;
import com.nepxion.swing.dialog.JLoginDialog;

public class ConsoleLogin extends JLoginDialog {
    private static final long serialVersionUID = 1L;

    public ConsoleLogin() {
        super(null);
    }

    public ConsoleLogin(Frame parent) {
        super(parent);
    }

    @Override
    public boolean login(String userId, String password, Locale locale) throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        userEntity.setPassword(password);
        
        return ServiceController.authenticate(userEntity);
    }

    public void launch() {
        setVisible(true);
        toFront();
    }
}
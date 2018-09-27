package com.nepxion.discovery.console.desktop;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.util.Locale;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JPanel;

import org.apache.commons.lang3.StringUtils;

import com.nepxion.discovery.common.entity.UserEntity;
import com.nepxion.discovery.console.desktop.controller.ServiceController;
import com.nepxion.discovery.console.desktop.locale.ConsoleLocale;
import com.nepxion.swing.combobox.JBasicComboBox;
import com.nepxion.swing.dialog.JLoginDialog;
import com.nepxion.swing.font.FontContext;
import com.nepxion.swing.label.JBasicLabel;
import com.nepxion.swing.layout.table.TableLayout;

public class ConsoleLogin extends JLoginDialog {
    private static final long serialVersionUID = 1L;

    private JBasicLabel urlLabel;
    private JBasicComboBox urlComboBox;

    public ConsoleLogin() {
        super(null);
    }

    public ConsoleLogin(Frame parent) {
        super(parent);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void initEditorPanelLayout() {
        urlLabel = new JBasicLabel();
        urlLabel.setFont(new Font(FontContext.getFontName(), FONT_STYLE, FONT_SIZE));

        String url = null;
        try {
            url = ServiceController.getUrl();
        } catch (Exception e) {

        }

        Vector<String> urls = new Vector<String>();
        if (StringUtils.isNotEmpty(url)) {
            urls.add(url);
        }

        urlComboBox = new JBasicComboBox(urls);
        urlComboBox.setEditable(true);
        urlComboBox.setRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;

            @SuppressWarnings("rawtypes")
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                setToolTipText(value.toString());

                return this;
            }
        });

        double[][] size = {
                { 80, 230 },
                { TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED }
        };

        TableLayout tableLayout = new TableLayout(size);
        tableLayout.setVGap(10);

        editorPanel = new JPanel();
        editorPanel.setLayout(tableLayout);
        editorPanel.add(urlLabel, "0, 0");
        editorPanel.add(urlComboBox, "1, 0");
        editorPanel.add(accountLabel, "0, 1");
        editorPanel.add(accountTextField, "1, 1");
        editorPanel.add(passwordLabel, "0, 2");
        editorPanel.add(passwordField, "1, 2");
        editorPanel.add(localeLabel, "0, 3");
        editorPanel.add(localeComboBox, "1, 3");
    }

    @Override
    protected void initLocale(Locale locale) {
        super.initLocale(locale);

        urlLabel.setText(ConsoleLocale.getString("url", locale));
    }

    @Override
    public boolean login(String userId, String password, Locale locale) throws Exception {
        Object url = urlComboBox.getSelectedItem();
        if (url == null || StringUtils.isEmpty(url.toString().trim())) {
            throw new IllegalArgumentException("Console url can't be null or empty");
        }

        ServiceController.setUrl(url.toString().trim());

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
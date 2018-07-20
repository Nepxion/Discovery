package com.nepxion.discovery.console.desktop.workspace;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import twaver.Generator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JToolBar;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.nepxion.cots.twaver.element.TElementManager;
import com.nepxion.cots.twaver.element.TLink;
import com.nepxion.cots.twaver.element.TNode;
import com.nepxion.cots.twaver.graph.TGraphBackground;
import com.nepxion.cots.twaver.graph.TGraphControlBar;
import com.nepxion.cots.twaver.graph.TGraphManager;
import com.nepxion.cots.twaver.graph.TLayoutPanel;
import com.nepxion.cots.twaver.graph.TLayouterBar;
import com.nepxion.discovery.console.desktop.controller.ServiceController;
import com.nepxion.discovery.console.desktop.entity.InstanceEntity;
import com.nepxion.discovery.console.desktop.entity.RouterEntity;
import com.nepxion.discovery.console.desktop.icon.ConsoleIconFactory;
import com.nepxion.discovery.console.desktop.locale.ConsoleLocale;
import com.nepxion.discovery.console.desktop.workspace.topology.AbstractTopology;
import com.nepxion.discovery.console.desktop.workspace.topology.LocationEntity;
import com.nepxion.discovery.console.desktop.workspace.topology.TopologyEntity;
import com.nepxion.discovery.console.desktop.workspace.topology.TopologyEntityType;
import com.nepxion.swing.action.JSecurityAction;
import com.nepxion.swing.button.ButtonManager;
import com.nepxion.swing.button.JBasicButton;
import com.nepxion.swing.button.JBasicToggleButton;
import com.nepxion.swing.button.JClassicButton;
import com.nepxion.swing.combobox.JBasicComboBox;
import com.nepxion.swing.dialog.JExceptionDialog;
import com.nepxion.swing.handle.HandleManager;
import com.nepxion.swing.listener.DisplayAbilityListener;
import com.nepxion.swing.locale.SwingLocale;
import com.nepxion.swing.optionpane.JBasicOptionPane;
import com.nepxion.swing.textfield.JBasicTextField;

public class RouterTopology extends AbstractTopology {
    private static final long serialVersionUID = 1L;

    private LocationEntity nodeLocationEntity = new LocationEntity(100, 200, 200, 0);
    private TopologyEntity serviceNodeEntity = new TopologyEntity(TopologyEntityType.SERVICE, true, true);

    private TGraphBackground background;
    private JBasicTextField textField = new JBasicTextField();
    private JBasicComboBox comboBox = new JBasicComboBox();
    private ActionListener layoutActionListener;

    private InstanceEntity instance;

    public RouterTopology() {
        initializeToolBar();
        initializeTopology();
        initializeListener();
    }

    private void initializeToolBar() {
        comboBox.setPreferredSize(new Dimension(300, comboBox.getPreferredSize().height));
        textField.setPreferredSize(new Dimension(650, textField.getPreferredSize().height));

        JToolBar toolBar = getGraph().getToolbar();
        toolBar.addSeparator();
        toolBar.add(Box.createHorizontalStrut(5));
        toolBar.add(new JLabel(ConsoleLocale.getString("service_list")));
        toolBar.add(Box.createHorizontalStrut(5));
        toolBar.add(comboBox);
        toolBar.add(new JClassicButton(createAddServiceAction()));
        toolBar.add(new JClassicButton(createDeleteServiceAction()));
        toolBar.add(textField);
        toolBar.add(new JClassicButton(createExecuteRouterAction()));
        toolBar.add(new JClassicButton(createClearRouterAction()));

        ButtonManager.updateUI(toolBar);
    }

    private void initializeTopology() {
        background = graph.getGraphBackground();
        background.setTitle(ConsoleLocale.getString("title_service_gray_router"));
        graph.setElementStateOutlineColorGenerator(new Generator() {
            public Object generate(Object object) {
                return null;
            }
        });
    }

    private void initializeListener() {
        addHierarchyListener(new DisplayAbilityListener() {
            public void displayAbilityChanged(HierarchyEvent e) {
                // Ugly code
                TGraphControlBar graphControlBar = (TGraphControlBar) graph.getControlBarInternalFrame().getContent();
                JBasicToggleButton toggleButton = (JBasicToggleButton) graphControlBar.getViewToolBar().getViewOutlook().getComponent(10);
                toggleButton.setSelected(true);

                TGraphManager.layout(graph);

                TLayouterBar layouterBar = (TLayouterBar) graph.getLayoutInternalFrame().getContent();
                JScrollPane scrollPane = (JScrollPane) layouterBar.getTabAt(layouterBar.getSelectedTitle());
                JPanel panel = (JPanel) scrollPane.getViewport().getView();
                TLayoutPanel layoutPanel = (TLayoutPanel) panel.getComponent(0);

                JPanel childPanel1 = (JPanel) layoutPanel.getComponent(0);
                JBasicComboBox typeComboBox = (JBasicComboBox) childPanel1.getComponent(1);
                typeComboBox.setSelectedIndex(2);

                JPanel childPanel2 = (JPanel) layoutPanel.getComponent(1);
                JSlider xGapSlider = (JSlider) childPanel2.getComponent(13);
                xGapSlider.setValue(200);
                JSlider yGapSlider = (JSlider) childPanel2.getComponent(15);
                yGapSlider.setValue(100);

                JPanel childPanel3 = (JPanel) layoutPanel.getComponent(2);
                JBasicButton runButton = (JBasicButton) childPanel3.getComponent(1);
                layoutActionListener = runButton.getActionListeners()[0];

                graph.getLayoutInternalFrame().setLocation(3000, 3000);
                // graph.adjustComponentPosition(graph.getLayoutInternalFrame());

                RouterTopology.this.setPreferredSize(new Dimension(RouterTopology.this.getPreferredSize().width - 82, 900));

                removeHierarchyListener(this);
            }
        });
    }

    private void route(RouterEntity routerEntity) {
        dataBox.clear();

        int index = 0;

        TNode node = addNode(routerEntity, index);

        index++;

        route(routerEntity, node, index);
    }

    private void route(RouterEntity routerEntity, TNode node, int index) {
        List<RouterEntity> nexts = routerEntity.getNexts();
        if (CollectionUtils.isNotEmpty(nexts)) {
            for (RouterEntity next : nexts) {
                String nodeName = getNodeName(next);
                TNode nextNode = TElementManager.getNode(dataBox, nodeName);
                if (nextNode == null) {
                    nextNode = addNode(next, index);

                    index++;
                }
                addLink(node, nextNode);

                route(next, nextNode, index);
            }
        }

    }

    private String getNodeName(RouterEntity routerEntity) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(routerEntity.getServiceId()).append("\n");
        stringBuilder.append(routerEntity.getHost()).append(":").append(routerEntity.getPort());

        if (StringUtils.isNotEmpty(routerEntity.getVersion())) {
            stringBuilder.append("\n [V").append(routerEntity.getVersion()).append("]");
        }

        return ButtonManager.getHtmlText(stringBuilder.toString());
    }

    private TNode addNode(RouterEntity routerEntity, int index) {
        String nodeName = getNodeName(routerEntity);

        TNode node = createNode(nodeName, serviceNodeEntity, nodeLocationEntity, index);
        node.setUserObject(routerEntity);

        dataBox.addElement(node);

        return node;
    }

    private void addLink(TNode fromNode, TNode toNode) {
        TLink link = createLink(fromNode, toNode, true);
        link.putLinkToArrowColor(Color.yellow);

        dataBox.addElement(link);
    }

    @SuppressWarnings({ "unchecked" })
    public void setServices(Object[] services) {
        comboBox.setModel(new DefaultComboBoxModel<>(services));
    }

    public void setInstance(InstanceEntity instance) {
        if (this.instance != instance) {
            this.instance = instance;

            textField.setText("");
            dataBox.clear();
        }
    }

    private JSecurityAction createAddServiceAction() {
        JSecurityAction action = new JSecurityAction(ConsoleIconFactory.getSwingIcon("add.png"), ConsoleLocale.getString("add_service")) {
            private static final long serialVersionUID = 1L;

            public void execute(ActionEvent e) {
                String routerPath = textField.getText();
                String serviceId = comboBox.getSelectedItem().toString();
                if (StringUtils.isNotEmpty(routerPath)) {
                    routerPath = routerPath + ";" + serviceId;
                } else {
                    routerPath = serviceId;
                }
                textField.setText(routerPath);
            }
        };

        return action;
    }

    private JSecurityAction createDeleteServiceAction() {
        JSecurityAction action = new JSecurityAction(ConsoleIconFactory.getSwingIcon("delete.png"), ConsoleLocale.getString("delete_service")) {
            private static final long serialVersionUID = 1L;

            public void execute(ActionEvent e) {
                String routerPath = textField.getText();
                if (StringUtils.isEmpty(routerPath)) {
                    return;
                }

                if (routerPath.contains(";")) {
                    routerPath = routerPath.substring(0, routerPath.lastIndexOf(";"));
                } else {
                    routerPath = "";
                }

                textField.setText(routerPath);
            }
        };

        return action;
    }

    private JSecurityAction createExecuteRouterAction() {
        JSecurityAction action = new JSecurityAction(ConsoleLocale.getString("execute_router"), ConsoleIconFactory.getSwingIcon("netbean/action_16.png"), ConsoleLocale.getString("execute_router")) {
            private static final long serialVersionUID = 1L;

            public void execute(ActionEvent e) {
                String routerPath = textField.getText();
                if (StringUtils.isEmpty(routerPath)) {
                    JBasicOptionPane.showMessageDialog(HandleManager.getFrame(RouterTopology.this), ConsoleLocale.getString("router_path_invalid"), SwingLocale.getString("warning"), JBasicOptionPane.WARNING_MESSAGE);

                    return;
                }

                RouterEntity routerEntity = null;
                try {
                    routerEntity = ServiceController.routes(instance, routerPath);
                } catch (Exception ex) {
                    JExceptionDialog.traceException(HandleManager.getFrame(RouterTopology.this), ConsoleLocale.getString("query_data_failure"), ex);

                    return;
                }

                route(routerEntity);

                layoutActionListener.actionPerformed(null);
            }
        };

        return action;
    }

    private JSecurityAction createClearRouterAction() {
        JSecurityAction action = new JSecurityAction(ConsoleLocale.getString("clear_router"), ConsoleIconFactory.getSwingIcon("paint.png"), ConsoleLocale.getString("clear_router")) {
            private static final long serialVersionUID = 1L;

            public void execute(ActionEvent e) {
                textField.setText("");
                dataBox.clear();
            }
        };

        return action;
    }
}
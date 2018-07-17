package com.nepxion.discovery.console.desktop.workspace;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import twaver.AlarmSeverity;
import twaver.BlinkingRule;
import twaver.Element;
import twaver.Generator;
import twaver.TWaverConst;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.nepxion.cots.twaver.element.TElementManager;
import com.nepxion.cots.twaver.element.TGroup;
import com.nepxion.cots.twaver.element.TGroupType;
import com.nepxion.cots.twaver.element.TNode;
import com.nepxion.cots.twaver.graph.TGraphManager;
import com.nepxion.discovery.console.desktop.controller.ServiceController;
import com.nepxion.discovery.console.desktop.entity.InstanceEntity;
import com.nepxion.discovery.console.desktop.icon.ConsoleIconFactory;
import com.nepxion.discovery.console.desktop.locale.ConsoleLocale;
import com.nepxion.discovery.console.desktop.workspace.common.UIUtil;
import com.nepxion.discovery.console.desktop.workspace.topology.AbstractTopology;
import com.nepxion.discovery.console.desktop.workspace.topology.TopologyEntity;
import com.nepxion.discovery.console.desktop.workspace.topology.TopologyEntityType;
import com.nepxion.swing.action.JSecurityAction;
import com.nepxion.swing.button.ButtonManager;
import com.nepxion.swing.button.JClassicButton;
import com.nepxion.swing.dialog.JExceptionDialog;
import com.nepxion.swing.dialog.JOptionDialog;
import com.nepxion.swing.handle.HandleManager;
import com.nepxion.swing.icon.IconFactory;
import com.nepxion.swing.label.JBasicLabel;
import com.nepxion.swing.layout.filed.FiledLayout;
import com.nepxion.swing.layout.table.TableLayout;
import com.nepxion.swing.locale.SwingLocale;
import com.nepxion.swing.optionpane.JBasicOptionPane;
import com.nepxion.swing.textfield.number.JNumberTextField;

public class ServiceTopology extends AbstractTopology {
    private static final long serialVersionUID = 1L;

    private int groupStartX = 120;
    private int groupStartY = 200;
    private int groupHorizontalGap = 280;
    private int groupVerticalGap = 0;

    private int nodeStartX = 0;
    private int nodeStartY = 0;
    private int nodeHorizontalGap = 110;
    private int nodeVerticalGap = 80;

    private TopologyEntity serviceGroupEntity = new TopologyEntity(TopologyEntityType.SERVICE, true, true);
    private TopologyEntity serviceNodeEntity = new TopologyEntity(TopologyEntityType.SERVICE, false, false);

    private Map<String, Point> groupLocationMap = new HashMap<String, Point>();

    private LayoutDialog layoutDialog;

    private Map<String, List<InstanceEntity>> instanceMap;

    public ServiceTopology() {
        initializeToolBar();
        initializeTopology();
    }

    private void initializeToolBar() {
        JToolBar toolBar = getGraph().getToolbar();
        toolBar.addSeparator();
        toolBar.add(Box.createHorizontalStrut(5));
        toolBar.add(new JClassicButton(createShowTopologyAction()));
        toolBar.add(new JClassicButton(createExecuteGrayReleaseAction()));
        toolBar.add(new JClassicButton(createRefreshGrayStateAction()));
        toolBar.addSeparator();
        toolBar.add(new JClassicButton(createViewServiceInfoAction()));
        toolBar.add(new JClassicButton(createViewRouterInfoAction()));
        toolBar.addSeparator();
        toolBar.add(createConfigButton(true));

        ButtonManager.updateUI(toolBar);

        setGroupAutoExpand(true);
        setLinkAutoHide(true);
    }

    private void initializeTopology() {
        graph.setBlinkingRule(new BlinkingRule() {
            public boolean isBodyBlinking(Element element) {
                return element.getAlarmState().getHighestNativeAlarmSeverity() != null || element.getClientProperty(TWaverConst.PROPERTYNAME_RENDER_COLOR) != null;
            }

            public boolean isOutlineBlinking(Element element) {
                return element.getAlarmState().getPropagateSeverity() != null || element.getClientProperty(TWaverConst.PROPERTYNAME_STATE_OUTLINE_COLOR) != null;
            }
        });
        graph.setElementStateOutlineColorGenerator(new Generator() {
            public Object generate(Object object) {
                return null;
            }
        });
    }

    private void addServices(Map<String, List<InstanceEntity>> instanceMap) {
        for (Map.Entry<String, List<InstanceEntity>> entry : instanceMap.entrySet()) {
            addService(entry.getKey(), entry.getValue());
        }
    }

    private void addService(String serviceId, List<InstanceEntity> instances) {
        int count = groupLocationMap.size();
        String groupName = getGroupName(serviceId, instances.size());

        TGroup group = createGroup(groupName, serviceGroupEntity, count, groupStartX, groupStartY, groupHorizontalGap, groupVerticalGap);
        group.setGroupType(TGroupType.ELLIPSE_GROUP_TYPE.getType());
        group.setUserObject(serviceId);

        addInstances(group, serviceGroupEntity, serviceNodeEntity, serviceId, instances);
    }

    private void addInstances(TGroup group, TopologyEntity groupEntity, TopologyEntity nodeEntity, String serviceId, List<InstanceEntity> instances) {
        if (CollectionUtils.isNotEmpty(instances)) {
            for (int i = 0; i < instances.size(); i++) {
                InstanceEntity instance = instances.get(i);
                String nodeName = getNodeName(instance);

                TNode node = createNode(nodeName, nodeEntity, i, nodeStartX, nodeStartY, nodeHorizontalGap, nodeVerticalGap);
                node.setUserObject(instance);

                group.addChild(node);
            }
        }

        groupLocationMap.put(serviceId, group.getLocation());

        dataBox.addElement(group);
        TElementManager.addGroupChildren(dataBox, group);
    }

    @SuppressWarnings("unchecked")
    private void locateGroups() {
        List<TGroup> groups = TElementManager.getGroups(dataBox);
        for (TGroup group : groups) {
            String key = group.getUserObject().toString();
            if (groupLocationMap.containsKey(key)) {
                group.setLocation(groupLocationMap.get(key));
            }
        }
    }

    private String getGroupName(String serviceId, int count) {
        return ButtonManager.getHtmlText(serviceId + " [" + count + "]");
    }

    private void updateGroup(TGroup group) {
        String name = getGroupName(group.getUserObject().toString(), group.childrenSize());

        group.setName(name);
    }

    private String getNodeName(InstanceEntity instance) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(instance.getHost()).append(":").append(instance.getPort());
        if (StringUtils.isNotEmpty(instance.getVersion())) {
            stringBuilder.append("\n [V").append(instance.getVersion());
            if (StringUtils.isNotEmpty(instance.getDynamicVersion())) {
                stringBuilder.append(" -> V").append(instance.getDynamicVersion());
            }
            stringBuilder.append("]");
        }

        return ButtonManager.getHtmlText(stringBuilder.toString());
    }

    private void updateNode(TNode node, InstanceEntity instance) {
        String name = getNodeName(instance);
        node.setName(name);
        if (StringUtils.isNotEmpty(instance.getDynamicRule())) {
            node.getAlarmState().clear();
            node.getAlarmState().addAcknowledgedAlarm(AlarmSeverity.WARNING);
        } else if (StringUtils.isNotEmpty(instance.getDynamicVersion())) {
            node.getAlarmState().clear();
            node.getAlarmState().addAcknowledgedAlarm(AlarmSeverity.MINOR);
        } else {
            node.getAlarmState().clear();
        }
    }

    private void showTopology(boolean reloaded) {
        dataBox.clear();
        groupLocationMap.clear();

        if (reloaded) {
            Map<String, List<InstanceEntity>> instanceMap = null;
            try {
                instanceMap = ServiceController.getInstanceMap();
            } catch (Exception e) {
                JExceptionDialog.traceException(HandleManager.getFrame(this), "获取服务和实例列表失败", e);

                return;
            }

            this.instanceMap = instanceMap;
        }

        addServices(instanceMap);

        locateGroups();

        TGraphManager.setGroupExpand(graph, isGroupAutoExpand());
        TGraphManager.setLinkVisible(graph, !isLinkAutoHide());
    }

    @Override
    public void showLayout() {
        if (layoutDialog == null) {
            layoutDialog = new LayoutDialog();
        }

        layoutDialog.setToUI();
        layoutDialog.setVisible(true);
        boolean confirmed = layoutDialog.isConfirmed();
        if (confirmed && !dataBox.isEmpty()) {
            showTopology(false);
        }
    }

    private JSecurityAction createShowTopologyAction() {
        JSecurityAction action = new JSecurityAction(ConsoleLocale.getString("show_topology"), ConsoleIconFactory.getSwingIcon("component/ui_16.png"), ConsoleLocale.getString("show_topology")) {
            private static final long serialVersionUID = 1L;

            public void execute(ActionEvent e) {
                showTopology(true);
            }
        };

        return action;
    }

    private JSecurityAction createExecuteGrayReleaseAction() {
        JSecurityAction action = new JSecurityAction(ConsoleLocale.getString("execute_gray_release"), ConsoleIconFactory.getSwingIcon("netbean/action_16.png"), ConsoleLocale.getString("execute_gray_release")) {
            private static final long serialVersionUID = 1L;

            public void execute(ActionEvent e) {

            }
        };

        return action;
    }

    private JSecurityAction createRefreshGrayStateAction() {
        JSecurityAction action = new JSecurityAction(ConsoleLocale.getString("refresh_gray_state"), ConsoleIconFactory.getSwingIcon("netbean/rotate_16.png"), ConsoleLocale.getString("refresh_gray_state")) {
            private static final long serialVersionUID = 1L;

            public void execute(ActionEvent e) {
                TGroup group = TElementManager.getSelectedGroup(dataBox);
                if (group != null) {
                    @SuppressWarnings("unchecked")
                    List<TNode> nodes = group.getChildren();

                    Iterator<TNode> iterator = nodes.iterator();
                    while (iterator.hasNext()) {
                        TNode node = iterator.next();

                        InstanceEntity instance = (InstanceEntity) node.getUserObject();
                        try {
                            List<String> versions = ServiceController.getVersions(instance);
                            List<String> rules = ServiceController.getRules(instance);
                            instance.setVersion(versions.get(0));
                            instance.setDynamicVersion(versions.get(1));
                            instance.setRule(rules.get(0));
                            instance.setDynamicRule(rules.get(1));

                            updateNode(node, instance);
                        } catch (Exception ex) {
                            JExceptionDialog.traceException(HandleManager.getFrame(ServiceTopology.this), "查询数据失败，可能该实例已下线", ex);

                            iterator.remove();
                            dataBox.removeElement(node);
                        }
                    }

                    updateGroup(group);
                } else {
                    JBasicOptionPane.showMessageDialog(HandleManager.getFrame(ServiceTopology.this), "请选择一个服务集群", SwingLocale.getString("error"), JBasicOptionPane.ERROR_MESSAGE);
                }
            }
        };

        return action;
    }

    private JSecurityAction createViewServiceInfoAction() {
        JSecurityAction action = new JSecurityAction(ConsoleLocale.getString("view_service_info"), ConsoleIconFactory.getSwingIcon("netbean/stack_16.png"), ConsoleLocale.getString("view_service_info")) {
            private static final long serialVersionUID = 1L;

            public void execute(ActionEvent e) {

            }
        };

        return action;
    }

    private JSecurityAction createViewRouterInfoAction() {
        JSecurityAction action = new JSecurityAction(ConsoleLocale.getString("view_router_info"), ConsoleIconFactory.getSwingIcon("netbean/close_path_16.png"), ConsoleLocale.getString("view_router_info")) {
            private static final long serialVersionUID = 1L;

            public void execute(ActionEvent e) {

            }
        };

        return action;
    }

    private class LayoutDialog extends JOptionDialog {
        private static final long serialVersionUID = 1L;

        private JNumberTextField groupStartXTextField;
        private JNumberTextField groupStartYTextField;
        private JNumberTextField groupHorizontalGapTextField;
        private JNumberTextField groupVerticalGapTextField;

        private JNumberTextField nodeStartXTextField;
        private JNumberTextField nodeStartYTextField;
        private JNumberTextField nodeHorizontalGapTextField;
        private JNumberTextField nodeVerticalGapTextField;

        public LayoutDialog() {
            super(HandleManager.getFrame(ServiceTopology.this), SwingLocale.getString("layout"), new Dimension(500, 330), true, false, true);

            groupStartXTextField = new JNumberTextField(4, 0, 0, 10000);
            groupStartYTextField = new JNumberTextField(4, 0, 0, 10000);
            groupHorizontalGapTextField = new JNumberTextField(4, 0, 0, 10000);
            groupVerticalGapTextField = new JNumberTextField(4, 0, 0, 10000);

            nodeStartXTextField = new JNumberTextField(4, 0, 0, 10000);
            nodeStartYTextField = new JNumberTextField(4, 0, 0, 10000);
            nodeHorizontalGapTextField = new JNumberTextField(4, 0, 0, 10000);
            nodeVerticalGapTextField = new JNumberTextField(4, 0, 0, 10000);

            double[][] size = {
                    { 100, TableLayout.FILL, 100, TableLayout.FILL },
                    { TableLayout.PREFERRED, TableLayout.PREFERRED }
            };

            TableLayout tableLayout = new TableLayout(size);
            tableLayout.setHGap(5);
            tableLayout.setVGap(5);

            JPanel groupPanel = new JPanel();
            groupPanel.setLayout(tableLayout);
            groupPanel.setBorder(UIUtil.createTitledBorder(ConsoleLocale.getString("group_layout")));
            groupPanel.add(new JBasicLabel(ConsoleLocale.getString("start_x")), "0, 0");
            groupPanel.add(groupStartXTextField, "1, 0");
            groupPanel.add(new JBasicLabel(ConsoleLocale.getString("start_y")), "2, 0");
            groupPanel.add(groupStartYTextField, "3, 0");
            groupPanel.add(new JBasicLabel(ConsoleLocale.getString("horizontal_gap")), "0, 1");
            groupPanel.add(groupHorizontalGapTextField, "1, 1");
            groupPanel.add(new JBasicLabel(ConsoleLocale.getString("vertical_gap")), "2, 1");
            groupPanel.add(groupVerticalGapTextField, "3, 1");

            JPanel nodePanel = new JPanel();
            nodePanel.setLayout(tableLayout);
            nodePanel.setBorder(UIUtil.createTitledBorder(ConsoleLocale.getString("node_layout")));
            nodePanel.add(new JBasicLabel(ConsoleLocale.getString("start_x")), "0, 0");
            nodePanel.add(nodeStartXTextField, "1, 0");
            nodePanel.add(new JBasicLabel(ConsoleLocale.getString("start_y")), "2, 0");
            nodePanel.add(nodeStartYTextField, "3, 0");
            nodePanel.add(new JBasicLabel(ConsoleLocale.getString("horizontal_gap")), "0, 1");
            nodePanel.add(nodeHorizontalGapTextField, "1, 1");
            nodePanel.add(new JBasicLabel(ConsoleLocale.getString("vertical_gap")), "2, 1");
            nodePanel.add(nodeVerticalGapTextField, "3, 1");

            JPanel panel = new JPanel();
            panel.setLayout(new FiledLayout(FiledLayout.COLUMN, FiledLayout.FULL, 5));
            panel.add(groupPanel);
            panel.add(nodePanel);

            setOption(YES_NO_OPTION);
            setIcon(IconFactory.getSwingIcon("banner/navigator.png"));
            setContent(panel);
        }

        @Override
        public boolean confirm() {
            return setFromUI();
        }

        @Override
        public boolean cancel() {
            return true;
        }

        public void setToUI() {
            groupStartXTextField.setText(groupStartX + "");
            groupStartYTextField.setText(groupStartY + "");
            groupHorizontalGapTextField.setText(groupHorizontalGap + "");
            groupVerticalGapTextField.setText(groupVerticalGap + "");

            nodeStartXTextField.setText(nodeStartX + "");
            nodeStartYTextField.setText(nodeStartY + "");
            nodeHorizontalGapTextField.setText(nodeHorizontalGap + "");
            nodeVerticalGapTextField.setText(nodeVerticalGap + "");
        }

        public boolean setFromUI() {
            int groupStartX = 0;
            int groupStartY = 0;
            int groupHorizontalGap = 0;
            int groupVerticalGap = 0;
            int nodeStartX = 0;
            int nodeStartY = 0;
            int nodeHorizontalGap = 0;
            int nodeVerticalGap = 0;

            try {
                groupStartX = Integer.parseInt(groupStartXTextField.getText());
                groupStartY = Integer.parseInt(groupStartYTextField.getText());
                groupHorizontalGap = Integer.parseInt(groupHorizontalGapTextField.getText());
                groupVerticalGap = Integer.parseInt(groupVerticalGapTextField.getText());

                nodeStartX = Integer.parseInt(nodeStartXTextField.getText());
                nodeStartY = Integer.parseInt(nodeStartYTextField.getText());
                nodeHorizontalGap = Integer.parseInt(nodeHorizontalGapTextField.getText());
                nodeVerticalGap = Integer.parseInt(nodeVerticalGapTextField.getText());
            } catch (NumberFormatException e) {
                return false;
            }

            ServiceTopology.this.groupStartX = groupStartX;
            ServiceTopology.this.groupStartY = groupStartY;
            ServiceTopology.this.groupHorizontalGap = groupHorizontalGap;
            ServiceTopology.this.groupVerticalGap = groupVerticalGap;
            ServiceTopology.this.nodeStartX = nodeStartX;
            ServiceTopology.this.nodeStartY = nodeStartY;
            ServiceTopology.this.nodeHorizontalGap = nodeHorizontalGap;
            ServiceTopology.this.nodeVerticalGap = nodeVerticalGap;

            return true;
        }
    }
}
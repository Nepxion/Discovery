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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.nepxion.cots.twaver.element.TElement;
import com.nepxion.cots.twaver.element.TElementManager;
import com.nepxion.cots.twaver.element.TGroup;
import com.nepxion.cots.twaver.element.TGroupType;
import com.nepxion.cots.twaver.element.TNode;
import com.nepxion.cots.twaver.graph.TGraphBackground;
import com.nepxion.cots.twaver.graph.TGraphManager;
import com.nepxion.discovery.common.entity.InstanceEntityWrapper;
import com.nepxion.discovery.common.entity.ResultEntity;
import com.nepxion.discovery.console.desktop.constant.ConsoleConstant;
import com.nepxion.discovery.console.desktop.controller.ServiceController;
import com.nepxion.discovery.console.desktop.entity.Instance;
import com.nepxion.discovery.console.desktop.icon.ConsoleIconFactory;
import com.nepxion.discovery.console.desktop.locale.ConsoleLocale;
import com.nepxion.discovery.console.desktop.ui.UIFactory;
import com.nepxion.discovery.console.desktop.workspace.topology.AbstractTopology;
import com.nepxion.discovery.console.desktop.workspace.topology.LocationEntity;
import com.nepxion.discovery.console.desktop.workspace.topology.TopologyEntity;
import com.nepxion.discovery.console.desktop.workspace.topology.TopologyEntityType;
import com.nepxion.discovery.console.desktop.workspace.topology.TopologyStyleType;
import com.nepxion.swing.action.JSecurityAction;
import com.nepxion.swing.button.ButtonManager;
import com.nepxion.swing.button.JClassicButton;
import com.nepxion.swing.button.JClassicMenuButton;
import com.nepxion.swing.dialog.JExceptionDialog;
import com.nepxion.swing.dialog.JOptionDialog;
import com.nepxion.swing.element.ElementNode;
import com.nepxion.swing.framework.dockable.JDockable;
import com.nepxion.swing.framework.dockable.JDockableView;
import com.nepxion.swing.handle.HandleManager;
import com.nepxion.swing.icon.IconFactory;
import com.nepxion.swing.label.JBasicLabel;
import com.nepxion.swing.layout.filed.FiledLayout;
import com.nepxion.swing.layout.table.TableLayout;
import com.nepxion.swing.list.BasicListModel;
import com.nepxion.swing.list.JBasicList;
import com.nepxion.swing.locale.SwingLocale;
import com.nepxion.swing.menuitem.JBasicMenuItem;
import com.nepxion.swing.menuitem.JBasicRadioButtonMenuItem;
import com.nepxion.swing.optionpane.JBasicOptionPane;
import com.nepxion.swing.popupmenu.JBasicPopupMenu;
import com.nepxion.swing.query.JQueryHierarchy;
import com.nepxion.swing.scrollpane.JBasicScrollPane;
import com.nepxion.swing.selector.checkbox.JCheckBoxSelector;
import com.nepxion.swing.tabbedpane.JBasicTabbedPane;
import com.nepxion.swing.textarea.JBasicTextArea;
import com.nepxion.swing.textfield.JBasicTextField;
import com.nepxion.swing.textfield.number.JNumberTextField;

public class ServiceTopology extends AbstractTopology {
    private static final long serialVersionUID = 1L;

    private LocationEntity groupLocationEntity = new LocationEntity(120, 250, 280, 0);
    private LocationEntity nodeLocationEntity = new LocationEntity(0, 0, 120, 120);
    private TopologyEntity serviceGroupEntity = new TopologyEntity(TopologyEntityType.SERVICE_GROUP, TopologyStyleType.LARGE, true);
    private TopologyEntity notServiceGroupEntity = new TopologyEntity(TopologyEntityType.GATEWAY_GROUP, TopologyStyleType.LARGE, true);
    private TopologyEntity serviceNodeEntity = new TopologyEntity(TopologyEntityType.SERVICE, TopologyStyleType.MIDDLE, false);
    private TopologyEntity notServiceNodeEntity = new TopologyEntity(TopologyEntityType.GATEWAY, TopologyStyleType.MIDDLE, false);
    private Map<String, Point> groupLocationMap = new HashMap<String, Point>();

    private TGraphBackground background;
    private JBasicMenuItem executeGrayReleaseMenuItem;
    private JBasicMenuItem refreshGrayStateMenuItem;
    private JBasicMenuItem executeGrayRouterMenuItem;
    private JBasicMenuItem showMetadataActionMenuItem;
    private JBasicRadioButtonMenuItem pushAsyncModeRadioButtonMenuItem;
    private JBasicRadioButtonMenuItem pushSyncModeRadioButtonMenuItem;
    private JBasicRadioButtonMenuItem ruleToConfigCenterRadioButtonMenuItem;
    private JBasicRadioButtonMenuItem ruleToServiceRadioButtonMenuItem;
    private GlobalGrayPanel globalGrayPanel;
    private GrayPanel grayPanel;
    private JBasicTextArea resultTextArea;
    private JBasicTextArea metadataTextArea;
    private RouterTopology routerTopology;
    private LayoutDialog layoutDialog;

    private Map<String, List<Instance>> globalInstanceMap;

    public ServiceTopology() {
        initializeToolBar();
        initializeTopology();
    }

    @Override
    protected void initializePopupMenu() {
        super.initializePopupMenu();

        executeGrayReleaseMenuItem = new JBasicMenuItem(createExecuteGrayReleaseAction());
        refreshGrayStateMenuItem = new JBasicMenuItem(createRefreshGrayStateAction());
        executeGrayRouterMenuItem = new JBasicMenuItem(createExecuteGrayRouterAction());
        showMetadataActionMenuItem = new JBasicMenuItem(createShowMetadataAction());
        popupMenu.add(executeGrayReleaseMenuItem, 0);
        popupMenu.add(executeGrayRouterMenuItem, 1);
        popupMenu.add(refreshGrayStateMenuItem, 2);
        popupMenu.add(showMetadataActionMenuItem, 3);
    }

    @Override
    protected JBasicPopupMenu popupMenuGenerate() {
        super.popupMenuGenerate();

        TGroup group = TElementManager.getSelectedGroup(dataBox);

        TNode node = TElementManager.getSelectedNode(dataBox);
        executeGrayRouterMenuItem.setVisible(node != null && isPlugin(node));

        TElement element = TElementManager.getSelectedElement(dataBox);
        executeGrayReleaseMenuItem.setVisible(element != null && isPlugin(element));
        refreshGrayStateMenuItem.setVisible(element != null && isPlugin(element));
        showMetadataActionMenuItem.setVisible(node != null);

        if (group != null || node != null || element != null) {
            return popupMenu;
        }

        return null;
    }

    private void initializeToolBar() {
        pushAsyncModeRadioButtonMenuItem = new JBasicRadioButtonMenuItem(ConsoleLocale.getString("push_async_mode"), ConsoleLocale.getString("push_async_mode"), true);
        pushSyncModeRadioButtonMenuItem = new JBasicRadioButtonMenuItem(ConsoleLocale.getString("push_sync_mode"), ConsoleLocale.getString("push_sync_mode"));
        ButtonGroup pushModeButtonGroup = new ButtonGroup();
        pushModeButtonGroup.add(pushAsyncModeRadioButtonMenuItem);
        pushModeButtonGroup.add(pushSyncModeRadioButtonMenuItem);

        ruleToConfigCenterRadioButtonMenuItem = new JBasicRadioButtonMenuItem(ConsoleLocale.getString("rule_to_config_center"), ConsoleLocale.getString("rule_to_config_center"), true);
        ruleToServiceRadioButtonMenuItem = new JBasicRadioButtonMenuItem(ConsoleLocale.getString("rule_to_service"), ConsoleLocale.getString("rule_to_service"));
        ButtonGroup ruleToButtonGroup = new ButtonGroup();
        ruleToButtonGroup.add(ruleToConfigCenterRadioButtonMenuItem);
        ruleToButtonGroup.add(ruleToServiceRadioButtonMenuItem);

        JBasicPopupMenu pushControlPopupMenu = new JBasicPopupMenu();
        pushControlPopupMenu.add(pushAsyncModeRadioButtonMenuItem);
        pushControlPopupMenu.add(pushSyncModeRadioButtonMenuItem);
        pushControlPopupMenu.addSeparator();
        pushControlPopupMenu.add(ruleToConfigCenterRadioButtonMenuItem);
        pushControlPopupMenu.add(ruleToServiceRadioButtonMenuItem);

        JClassicMenuButton pushControllMenubutton = new JClassicMenuButton(ConsoleLocale.getString("push_control_mode"), ConsoleIconFactory.getSwingIcon("netbean/custom_node_16.png"), ConsoleLocale.getString("push_control_mode"));
        pushControllMenubutton.setPopupMenu(pushControlPopupMenu);

        JToolBar toolBar = getGraph().getToolbar();
        toolBar.addSeparator();
        toolBar.add(Box.createHorizontalStrut(5));
        toolBar.add(new JClassicButton(createShowTopologyAction()));
        toolBar.addSeparator();
        toolBar.add(new JClassicButton(createExecuteGrayReleaseAction()));
        toolBar.add(new JClassicButton(createExecuteGrayRouterAction()));
        toolBar.add(new JClassicButton(createRefreshGrayStateAction()));
        toolBar.add(new JClassicButton(createShowMetadataAction()));
        toolBar.add(new JClassicButton(createPushGlobalConfigAction()));
        toolBar.add(pushControllMenubutton);
        toolBar.addSeparator();
        toolBar.add(createConfigButton(true));

        ButtonManager.updateUI(toolBar);
    }

    private void initializeTopology() {
        background = graph.getGraphBackground();
        background.setTitle(ConsoleLocale.getString("title_service_cluster_gray_release"));
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

        setGroupAutoExpand(true);
        setLinkAutoHide(true);
    }

    private String getFilter(TElement element) {
        return element.getClientProperty(ConsoleConstant.FILTER).toString();
    }

    private void setFilter(TElement element, String filter) {
        element.putClientProperty(ConsoleConstant.FILTER, filter);
    }

    private String getPlugin(TElement element) {
        return element.getClientProperty(ConsoleConstant.PLUGIN).toString();
    }

    private void setPlugin(TElement element, String plugin) {
        element.putClientProperty(ConsoleConstant.PLUGIN, plugin);
    }

    private boolean isPlugin(TElement element) {
        String plugin = getPlugin(element);

        return StringUtils.isNotEmpty(plugin);
    }

    private void addServices() {
        for (Map.Entry<String, List<Instance>> entry : globalInstanceMap.entrySet()) {
            String serviceId = entry.getKey();
            List<Instance> instances = entry.getValue();
            addService(serviceId, instances);
        }
    }

    private void addService(String serviceId, List<Instance> instances) {
        String filter = getValidFilter(instances);
        String plugin = getValidPlugin(instances);

        int count = groupLocationMap.size();
        String groupName = getGroupName(serviceId, instances.size(), filter);

        TGroup group = createGroup(groupName, StringUtils.isNotEmpty(plugin) ? serviceGroupEntity : notServiceGroupEntity, groupLocationEntity, count);
        group.setGroupType(TGroupType.ELLIPSE_GROUP_TYPE.getType());
        group.setUserObject(serviceId);
        setFilter(group, filter);
        setPlugin(group, plugin);

        addInstances(group, serviceId, instances);
    }

    private void addInstances(TGroup group, String serviceId, List<Instance> instances) {
        for (int i = 0; i < instances.size(); i++) {
            Instance instance = instances.get(i);
            String filter = InstanceEntityWrapper.getGroup(instance);
            String plugin = InstanceEntityWrapper.getPlugin(instance);
            String nodeName = getNodeName(instance);

            TNode node = createNode(nodeName, StringUtils.isNotEmpty(plugin) ? serviceNodeEntity : notServiceNodeEntity, nodeLocationEntity, i);
            node.setUserObject(instance);
            setFilter(node, filter);
            setPlugin(node, plugin);

            group.addChild(node);
        }

        updateGroup(group);

        groupLocationMap.put(serviceId, group.getLocation());

        dataBox.addElement(group);
        TElementManager.addGroupChildren(dataBox, group);
    }

    private String getValidFilter(List<Instance> instances) {
        // 服务注册发现中心，必须有一个规范，即在同一个服务集群下，必须所有服务的metadata格式一致，例如一个服务配了group，另一个服务没有配group
        // 只取有值的那个
        for (Instance instance : instances) {
            String filter = InstanceEntityWrapper.getGroup(instance);
            if (StringUtils.isNotEmpty(filter)) {
                return filter;
            }
        }

        return StringUtils.EMPTY;
    }

    private String getValidPlugin(List<Instance> instances) {
        for (Instance instance : instances) {
            String plugin = InstanceEntityWrapper.getPlugin(instance);
            if (StringUtils.isNotEmpty(plugin)) {
                return plugin;
            }
        }

        return StringUtils.EMPTY;
    }

    private Object[] filterServices(TNode node, Map<String, List<Instance>> instanceMap) {
        Set<String> services = instanceMap.keySet();
        List<String> filterServices = new ArrayList<String>();
        for (String service : services) {
            TGroup group = getGroup(service);
            // node.getParent() != group 表示自己不能路由自己
            if (group != null && isPlugin(group) && node.getParent() != group) {
                filterServices.add(service);
            }
        }

        return filterServices.toArray();
    }

    @SuppressWarnings("unchecked")
    private TGroup getGroup(String serviceId) {
        List<TElement> elements = dataBox.getAllElements();
        for (TElement element : elements) {
            if (element instanceof TGroup) {
                if (StringUtils.equals(element.getUserObject().toString(), serviceId)) {
                    return (TGroup) element;
                }
            }
        }

        return null;
    }

    private String getGroupName(String serviceId, int count, String filter) {
        return ButtonManager.getHtmlText(serviceId + " [" + count + "]" + (StringUtils.isNotEmpty(filter) ? "\n" + filter : StringUtils.EMPTY));
    }

    @SuppressWarnings("unchecked")
    private void updateGroup(TGroup group) {
        String name = getGroupName(group.getUserObject().toString(), group.childrenSize(), getFilter(group));
        group.setName(name);

        boolean hasDynamicRule = false;
        boolean hasDynamicVersion = false;
        List<TNode> nodes = group.getChildren();
        Iterator<TNode> iterator = nodes.iterator();
        while (iterator.hasNext()) {
            TNode node = iterator.next();
            Instance instance = (Instance) node.getUserObject();
            if (StringUtils.isNotEmpty(instance.getDynamicRule())) {
                hasDynamicRule = true;
            }

            if (StringUtils.isNotEmpty(instance.getDynamicVersion())) {
                hasDynamicVersion = true;
            }
        }

        group.getAlarmState().clear();
        if (hasDynamicRule) {
            group.getAlarmState().addAcknowledgedAlarm(AlarmSeverity.MINOR);
        } else if (hasDynamicVersion) {
            group.getAlarmState().addAcknowledgedAlarm(AlarmSeverity.WARNING);
        }
    }

    private String getNodeName(Instance instance) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(instance.getHost()).append(":").append(instance.getPort());
        if (StringUtils.isNotEmpty(instance.getVersion())) {
            stringBuilder.append("\n[V").append(instance.getVersion());
            if (StringUtils.isNotEmpty(instance.getDynamicVersion())) {
                stringBuilder.append(" -> V").append(instance.getDynamicVersion());
            }
            stringBuilder.append("]");
            if (StringUtils.isNotEmpty(instance.getRegion())) {
                stringBuilder.append("\n [Region=").append(instance.getRegion()).append("]");
            }
        }

        return ButtonManager.getHtmlText(stringBuilder.toString());
    }

    private void updateNode(TNode node, Instance instance) {
        String name = getNodeName(instance);
        node.setName(name);

        boolean hasDynamicRule = StringUtils.isNotEmpty(instance.getDynamicRule());
        boolean hasDynamicVersion = StringUtils.isNotEmpty(instance.getDynamicVersion());

        node.getAlarmState().clear();
        if (hasDynamicRule) {
            node.getAlarmState().addAcknowledgedAlarm(AlarmSeverity.MINOR);
        } else if (hasDynamicVersion) {
            node.getAlarmState().addAcknowledgedAlarm(AlarmSeverity.WARNING);
        }
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

    private void showTopology() {
        dataBox.clear();
        groupLocationMap.clear();

        addServices();
        locateGroups();

        TGraphManager.setGroupExpand(graph, isGroupAutoExpand());
        TGraphManager.setLinkVisible(graph, !isLinkAutoHide());
    }

    private void updateGrayState(TNode node) {
        Instance instance = (Instance) node.getUserObject();
        List<String> versions = ServiceController.getVersions(instance);
        List<String> rules = ServiceController.getRules(instance);
        instance.setVersion(versions.get(0));
        instance.setDynamicVersion(versions.get(1));
        instance.setRule(rules.get(0));
        instance.setDynamicRule(rules.get(1));

        updateNode(node, instance);
    }

    private boolean refreshGrayState(TNode node) {
        boolean hasException = false;

        TGroup group = (TGroup) node.getParent();
        try {
            updateGrayState(node);
        } catch (Exception e) {
            JExceptionDialog.traceException(HandleManager.getFrame(this), ConsoleLocale.getString("query_data_failure"), e);

            group.removeChild(node);
            dataBox.removeElement(node);

            hasException = true;
        }

        updateGroup(group);

        return hasException;
    }

    @SuppressWarnings("unchecked")
    private boolean refreshGrayState(TGroup group) {
        boolean hasException = false;

        List<TNode> nodes = group.getChildren();
        Iterator<TNode> iterator = nodes.iterator();
        while (iterator.hasNext()) {
            TNode node = iterator.next();

            try {
                updateGrayState(node);
            } catch (Exception e) {
                JExceptionDialog.traceException(HandleManager.getFrame(this), ConsoleLocale.getString("query_data_failure"), e);

                iterator.remove();
                dataBox.removeElement(node);

                hasException = true;
            }
        }

        updateGroup(group);

        return hasException;
    }

    private void showResult(Object result) {
        if (resultTextArea == null) {
            resultTextArea = new JBasicTextArea();
            resultTextArea.setLineWrap(true);
            resultTextArea.setPreferredSize(new Dimension(800, 600));
        }
        resultTextArea.setText(result.toString());

        JBasicOptionPane.showOptionDialog(HandleManager.getFrame(this), new JBasicScrollPane(resultTextArea), ConsoleLocale.getString("execute_result"), JBasicOptionPane.DEFAULT_OPTION, JBasicOptionPane.PLAIN_MESSAGE, ConsoleIconFactory.getSwingIcon("banner/edit.png"), new Object[] { SwingLocale.getString("close") }, null, true);
    }

    private void showMetadata(Instance instance) {
        Map<String, String> metadata = instance.getMetadata();

        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : metadata.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            stringBuilder.append(key).append("=").append(value).append("\n");
        }

        if (metadataTextArea == null) {
            metadataTextArea = new JBasicTextArea();
            metadataTextArea.setLineWrap(true);
            metadataTextArea.setPreferredSize(new Dimension(800, 600));
        }
        String result = stringBuilder.toString();
        if (StringUtils.isNotEmpty(result)) {
            result = result.substring(0, result.lastIndexOf("\n"));
        }
        metadataTextArea.setText(result);

        JBasicOptionPane.showOptionDialog(HandleManager.getFrame(this), new JBasicScrollPane(metadataTextArea), ConsoleLocale.getString("show_metadata"), JBasicOptionPane.DEFAULT_OPTION, JBasicOptionPane.PLAIN_MESSAGE, ConsoleIconFactory.getSwingIcon("banner/property.png"), new Object[] { SwingLocale.getString("close") }, null, true);
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
            showTopology();
        }
    }

    private JSecurityAction createShowTopologyAction() {
        JSecurityAction action = new JSecurityAction(ConsoleLocale.getString("show_topology"), ConsoleIconFactory.getSwingIcon("component/ui_16.png"), ConsoleLocale.getString("show_topology")) {
            private static final long serialVersionUID = 1L;

            @SuppressWarnings("unchecked")
            public void execute(ActionEvent e) {
                List<String> groups = null;
                try {
                    groups = ServiceController.getGroups();
                } catch (Exception ex) {
                    JExceptionDialog.traceException(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("get_service_group_failure"), ex);

                    return;
                }

                List<ElementNode> filterElementNodes = new ArrayList<ElementNode>();
                for (String filter : groups) {
                    filterElementNodes.add(new ElementNode(filter, IconFactory.getSwingIcon("component/file_chooser_16.png"), filter));
                }

                JCheckBoxSelector checkBoxSelector = new JCheckBoxSelector(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("service_cluster_filter"), new Dimension(400, 350), filterElementNodes);
                checkBoxSelector.setVisible(true);
                checkBoxSelector.dispose();

                List<String> filters = new ArrayList<String>();
                if (checkBoxSelector.isConfirmed()) {
                    List<ElementNode> selectedFilterElementNodes = checkBoxSelector.getSelectedElementNodes();
                    for (ElementNode selectedFilterElementNode : selectedFilterElementNodes) {
                        filters.add(selectedFilterElementNode.getText());
                    }
                }

                Map<String, List<Instance>> instanceMap = null;
                try {
                    instanceMap = ServiceController.getInstanceMap(filters);
                } catch (Exception ex) {
                    JExceptionDialog.traceException(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("get_service_instances_failure"), ex);

                    return;
                }

                globalInstanceMap = instanceMap;

                String title = ConsoleLocale.getString("title_service_cluster_gray_release") + " " + (CollectionUtils.isNotEmpty(filters) ? filters : StringUtils.EMPTY);
                background.setTitle(title);

                showTopology();
            }
        };

        return action;
    }

    private JSecurityAction createExecuteGrayReleaseAction() {
        JSecurityAction action = new JSecurityAction(ConsoleLocale.getString("execute_gray_release"), ConsoleIconFactory.getSwingIcon("netbean/action_16.png"), ConsoleLocale.getString("execute_gray_release")) {
            private static final long serialVersionUID = 1L;

            public void execute(ActionEvent e) {
                TGroup group = TElementManager.getSelectedGroup(dataBox);
                TNode node = TElementManager.getSelectedNode(dataBox);
                if (group == null && node == null) {
                    JBasicOptionPane.showMessageDialog(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("select_a_group_or_node"), SwingLocale.getString("warning"), JBasicOptionPane.WARNING_MESSAGE);

                    return;
                }

                if (group != null && !isPlugin(group)) {
                    JBasicOptionPane.showMessageDialog(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("group_not_for_gray_release"), SwingLocale.getString("warning"), JBasicOptionPane.WARNING_MESSAGE);

                    return;
                }

                if (node != null && !isPlugin(node)) {
                    JBasicOptionPane.showMessageDialog(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("node_not_for_gray_release"), SwingLocale.getString("warning"), JBasicOptionPane.WARNING_MESSAGE);

                    return;
                }

                boolean hasException = false;
                if (group != null) {
                    hasException = refreshGrayState(group);
                } else if (node != null) {
                    hasException = refreshGrayState(node);
                }

                if (hasException) {
                    return;
                }

                if (grayPanel == null) {
                    grayPanel = new GrayPanel();
                    grayPanel.setPreferredSize(new Dimension(1300, 800));
                }

                String description = null;
                if (group != null) {
                    grayPanel.setGray(group);

                    description = group.getUserObject().toString();
                } else if (node != null) {
                    grayPanel.setGray(node);

                    Instance instance = (Instance) node.getUserObject();

                    description = instance.getServiceId() + " [" + instance.getHost() + ":" + instance.getPort() + "]";
                }

                JBasicOptionPane.showOptionDialog(HandleManager.getFrame(ServiceTopology.this), grayPanel, ConsoleLocale.getString("execute_gray_release") + " - " + description, JBasicOptionPane.DEFAULT_OPTION, JBasicOptionPane.PLAIN_MESSAGE, ConsoleIconFactory.getSwingIcon("banner/navigator.png"), new Object[] { SwingLocale.getString("close") }, null, true);
            }
        };

        return action;
    }

    private JSecurityAction createExecuteGrayRouterAction() {
        JSecurityAction action = new JSecurityAction(ConsoleLocale.getString("execute_gray_router"), ConsoleIconFactory.getSwingIcon("netbean/close_path_16.png"), ConsoleLocale.getString("execute_gray_router")) {
            private static final long serialVersionUID = 1L;

            public void execute(ActionEvent e) {
                TNode node = TElementManager.getSelectedNode(dataBox);
                if (node == null) {
                    JBasicOptionPane.showMessageDialog(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("select_a_node"), SwingLocale.getString("warning"), JBasicOptionPane.WARNING_MESSAGE);

                    return;
                }

                if (node != null && !isPlugin(node)) {
                    JBasicOptionPane.showMessageDialog(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("node_not_for_gray_router"), SwingLocale.getString("warning"), JBasicOptionPane.WARNING_MESSAGE);

                    return;
                }

                Instance instance = (Instance) node.getUserObject();

                if (routerTopology == null) {
                    routerTopology = new RouterTopology();
                    // routerTopology.setPreferredSize(new Dimension(1300, 800));
                }

                Object[] filterServices = filterServices(node, globalInstanceMap);
                routerTopology.setServices(filterServices);
                routerTopology.setInstance(instance);

                String description = instance.getServiceId() + " [" + instance.getHost() + ":" + instance.getPort() + "]";

                JBasicOptionPane.showOptionDialog(HandleManager.getFrame(ServiceTopology.this), routerTopology, ConsoleLocale.getString("execute_gray_router") + " - " + description, JBasicOptionPane.DEFAULT_OPTION, JBasicOptionPane.PLAIN_MESSAGE, ConsoleIconFactory.getSwingIcon("banner/navigator.png"), new Object[] { SwingLocale.getString("close") }, null, true);
            }
        };

        return action;
    }

    private JSecurityAction createRefreshGrayStateAction() {
        JSecurityAction action = new JSecurityAction(ConsoleLocale.getString("refresh_gray_state"), ConsoleIconFactory.getSwingIcon("netbean/rotate_16.png"), ConsoleLocale.getString("refresh_gray_state")) {
            private static final long serialVersionUID = 1L;

            public void execute(ActionEvent e) {
                TGroup group = TElementManager.getSelectedGroup(dataBox);
                TNode node = TElementManager.getSelectedNode(dataBox);
                if (group == null && node == null) {
                    JBasicOptionPane.showMessageDialog(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("select_a_group_or_node"), SwingLocale.getString("warning"), JBasicOptionPane.WARNING_MESSAGE);

                    return;
                }

                if (group != null && !isPlugin(group)) {
                    JBasicOptionPane.showMessageDialog(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("group_not_for_refresh_gray_state"), SwingLocale.getString("warning"), JBasicOptionPane.WARNING_MESSAGE);

                    return;
                }

                if (node != null && !isPlugin(node)) {
                    JBasicOptionPane.showMessageDialog(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("node_not_for_refresh_gray_state"), SwingLocale.getString("warning"), JBasicOptionPane.WARNING_MESSAGE);

                    return;
                }

                if (group != null) {
                    refreshGrayState(group);
                } else if (node != null) {
                    refreshGrayState(node);
                }
            }
        };

        return action;
    }

    private JSecurityAction createShowMetadataAction() {
        JSecurityAction action = new JSecurityAction(ConsoleLocale.getString("show_metadata"), ConsoleIconFactory.getSwingIcon("component/file_chooser_16.png"), ConsoleLocale.getString("show_metadata")) {
            private static final long serialVersionUID = 1L;

            public void execute(ActionEvent e) {
                TNode node = TElementManager.getSelectedNode(dataBox);
                if (node == null) {
                    JBasicOptionPane.showMessageDialog(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("select_a_node"), SwingLocale.getString("warning"), JBasicOptionPane.WARNING_MESSAGE);

                    return;
                }

                Object userObject = node.getUserObject();
                if (userObject == null || !(userObject instanceof Instance)) {
                    JBasicOptionPane.showMessageDialog(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("node_has_no_metadata"), SwingLocale.getString("warning"), JBasicOptionPane.WARNING_MESSAGE);

                    return;
                }

                Instance instance = (Instance) userObject;

                showMetadata(instance);
            }
        };

        return action;
    }

    private JSecurityAction createPushGlobalConfigAction() {
        JSecurityAction action = new JSecurityAction(ConsoleLocale.getString("push_global_config"), ConsoleIconFactory.getSwingIcon("netbean/ease_both_16.png"), ConsoleLocale.getString("push_global_config")) {
            private static final long serialVersionUID = 1L;

            public void execute(ActionEvent e) {
                if (!ruleToConfigCenterRadioButtonMenuItem.isSelected()) {
                    JBasicOptionPane.showMessageDialog(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("not_for_push_global_config"), SwingLocale.getString("warning"), JBasicOptionPane.WARNING_MESSAGE);

                    return;
                }

                List<String> groups = null;
                try {
                    groups = ServiceController.getGroups();
                } catch (Exception ex) {
                    JExceptionDialog.traceException(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("get_service_group_failure"), ex);

                    return;
                }

                Vector<Object> globalFilterVector = new Vector<Object>(groups);

                if (globalGrayPanel == null) {
                    globalGrayPanel = new GlobalGrayPanel();
                    globalGrayPanel.setPreferredSize(new Dimension(1300, 800));
                }

                globalGrayPanel.setFilters(globalFilterVector);

                JBasicOptionPane.showOptionDialog(HandleManager.getFrame(ServiceTopology.this), globalGrayPanel, ConsoleLocale.getString("push_global_config"), JBasicOptionPane.DEFAULT_OPTION, JBasicOptionPane.PLAIN_MESSAGE, ConsoleIconFactory.getSwingIcon("banner/navigator.png"), new Object[] { SwingLocale.getString("close") }, null, true);
            }
        };

        return action;
    }

    private class GlobalGrayPanel extends JQueryHierarchy {
        private static final long serialVersionUID = 1L;

        private JBasicList filterList;
        private JBasicTextArea ruleTextArea;
        private JClassicButton updateRuleButton;
        private JClassicButton clearRuleButton;

        public GlobalGrayPanel() {
            filterList = new JBasicList() {
                private static final long serialVersionUID = 1L;

                @Override
                public void mousePressed(MouseEvent e) {
                    int selectedRow = getSelectedIndex();
                    if (selectedRow < 0) {
                        return;
                    }

                    String filter = getListData().get(selectedRow).toString();
                    String config = ServiceController.remoteConfigView(filter, filter);
                    ruleTextArea.setText(config);
                }
            };
            filterList.setSelectionMode(JBasicList.SINGLE_SELECTION);

            JPanel filterPanel = new JPanel();
            filterPanel.setLayout(new BorderLayout());
            filterPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            filterPanel.add(new JBasicScrollPane(filterList), BorderLayout.CENTER);

            ruleTextArea = new JBasicTextArea();

            updateRuleButton = new JClassicButton(createUpdateRuleAction());
            updateRuleButton.setPreferredSize(new Dimension(updateRuleButton.getPreferredSize().width, 30));

            clearRuleButton = new JClassicButton(createClearRuleAction());
            updateRuleButton.setPreferredSize(new Dimension(clearRuleButton.getPreferredSize().width, 30));

            JPanel toolBar = new JPanel();
            toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.X_AXIS));
            toolBar.add(updateRuleButton);
            toolBar.add(clearRuleButton);
            ButtonManager.updateUI(toolBar);

            JPanel rulePanel = new JPanel();
            rulePanel.setLayout(new BorderLayout());
            rulePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            rulePanel.add(new JBasicScrollPane(ruleTextArea), BorderLayout.CENTER);
            rulePanel.add(toolBar, BorderLayout.SOUTH);

            JDockable dockable = (JDockable) getDockableContainer().getContentPane();

            JDockableView filterView = (JDockableView) dockable.getPaneAt(0);
            filterView.setTitle(ConsoleLocale.getString("global_group"));
            filterView.setIcon(ConsoleIconFactory.getSwingIcon("netbean/stack_16.png"));
            filterView.setToolTipText(ConsoleLocale.getString("global_group"));
            filterView.add(filterPanel);

            JDockableView ruleView = (JDockableView) dockable.getPaneAt(1);
            ruleView.setTitle(ConsoleLocale.getString("global_rule"));
            ruleView.setIcon(ConsoleIconFactory.getSwingIcon("netbean/custom_node_16.png"));
            ruleView.setToolTipText(ConsoleLocale.getString("global_rule"));
            ruleView.add(rulePanel);
        }

        @SuppressWarnings("unchecked")
        public void setFilters(Vector<Object> filters) {
            filterList.setModel(new BasicListModel(filters));
            ruleTextArea.setText(StringUtils.EMPTY);
        }

        private JSecurityAction createUpdateRuleAction() {
            JSecurityAction action = new JSecurityAction(ConsoleLocale.getString("button_update_rule"), ConsoleIconFactory.getSwingIcon("save.png"), ConsoleLocale.getString("button_update_rule")) {
                private static final long serialVersionUID = 1L;

                public void execute(ActionEvent e) {
                    int selectedRow = filterList.getSelectedIndex();
                    if (selectedRow < 0) {
                        JBasicOptionPane.showMessageDialog(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("select_a_global_group"), SwingLocale.getString("warning"), JBasicOptionPane.WARNING_MESSAGE);

                        return;
                    }

                    String rule = ruleTextArea.getText();
                    if (StringUtils.isEmpty(rule)) {
                        JBasicOptionPane.showMessageDialog(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("gray_rule_not_null"), SwingLocale.getString("warning"), JBasicOptionPane.WARNING_MESSAGE);

                        return;
                    }

                    String filter = filterList.getListData().get(selectedRow).toString();

                    String result = null;
                    try {
                        result = ServiceController.remoteConfigUpdate(filter, filter, rule);
                    } catch (Exception ex) {
                        JExceptionDialog.traceException(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("query_data_failure"), ex);

                        return;
                    }

                    showResult(result);
                }
            };

            return action;
        }

        private JSecurityAction createClearRuleAction() {
            JSecurityAction action = new JSecurityAction(ConsoleLocale.getString("button_clear_rule"), ConsoleIconFactory.getSwingIcon("paint.png"), ConsoleLocale.getString("button_clear_rule")) {
                private static final long serialVersionUID = 1L;

                public void execute(ActionEvent e) {
                    int selectedRow = filterList.getSelectedIndex();
                    if (selectedRow < 0) {
                        JBasicOptionPane.showMessageDialog(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("select_a_global_group"), SwingLocale.getString("warning"), JBasicOptionPane.WARNING_MESSAGE);

                        return;
                    }

                    String filter = filterList.getListData().get(selectedRow).toString();

                    String result = null;
                    try {
                        result = ServiceController.remoteConfigClear(filter, filter);
                    } catch (Exception ex) {
                        JExceptionDialog.traceException(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("query_data_failure"), ex);

                        return;
                    }

                    showResult(result);
                }
            };

            return action;
        }
    }

    private class GrayPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        private JBasicTextField dynamicVersionTextField;
        private JPanel dynamicVersionPanel;
        private JBasicTextField localVersionTextField;
        private JPanel localVersionPanel;
        private JLabel versionInfoLabel;
        private JBasicTabbedPane versionTabbedPane;
        private JClassicButton updateVersionButton;
        private JClassicButton clearVersionButton;

        private JBasicTextArea dynamicRuleTextArea;
        private JPanel dynamicRulePanel;
        private JBasicTextArea localRuleTextArea;
        private JPanel localRulePanel;
        private JLabel ruleInfoLabel;
        private JBasicTabbedPane ruleTabbedPane;
        private JClassicButton updateRuleButton;
        private JClassicButton clearRuleButton;

        private TGroup group;
        private TNode node;

        public GrayPanel() {
            setLayout(new BorderLayout());
            add(createVersionPanel(), BorderLayout.NORTH);
            add(createRulePanel(), BorderLayout.CENTER);
        }

        private JPanel createVersionPanel() {
            dynamicVersionTextField = new JBasicTextField();
            dynamicVersionPanel = new JPanel();
            dynamicVersionPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            dynamicVersionPanel.setLayout(new BorderLayout());
            dynamicVersionPanel.add(dynamicVersionTextField, BorderLayout.CENTER);

            localVersionTextField = new JBasicTextField();
            localVersionTextField.setEditable(false);
            localVersionPanel = new JPanel();
            localVersionPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            localVersionPanel.setLayout(new BorderLayout());
            localVersionPanel.add(localVersionTextField, BorderLayout.CENTER);

            versionTabbedPane = new JBasicTabbedPane();
            versionTabbedPane.setPreferredSize(new Dimension(versionTabbedPane.getPreferredSize().width, 75));
            versionTabbedPane.addTab(ConsoleLocale.getString("label_dynamic_version"), dynamicVersionPanel, ConsoleLocale.getString("label_dynamic_version"));
            versionTabbedPane.addTab(ConsoleLocale.getString("label_local_version"), localVersionPanel, ConsoleLocale.getString("label_local_version"));

            updateVersionButton = new JClassicButton(createUpdateVersionAction());
            updateVersionButton.setPreferredSize(new Dimension(updateVersionButton.getPreferredSize().width, 30));

            clearVersionButton = new JClassicButton(createClearVersionAction());
            clearVersionButton.setPreferredSize(new Dimension(clearVersionButton.getPreferredSize().width, 30));

            JPanel toolBar = new JPanel();
            toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.X_AXIS));
            toolBar.add(updateVersionButton);
            toolBar.add(clearVersionButton);
            ButtonManager.updateUI(toolBar);

            versionInfoLabel = new JLabel(ConsoleLocale.getString("description_gray_version"), IconFactory.getSwingIcon("question_message.png"), SwingConstants.LEADING);

            JPanel layoutPanel = new JPanel();
            layoutPanel.setLayout(new FiledLayout(FiledLayout.COLUMN, FiledLayout.FULL, 5));
            layoutPanel.add(versionInfoLabel);
            layoutPanel.add(toolBar);

            JPanel panel = new JPanel();
            panel.setBorder(UIFactory.createTitledBorder(ConsoleLocale.getString("title_gray_version_operation")));
            panel.setLayout(new BorderLayout());
            panel.add(versionTabbedPane, BorderLayout.CENTER);
            panel.add(layoutPanel, BorderLayout.SOUTH);

            return panel;
        }

        private JPanel createRulePanel() {
            dynamicRuleTextArea = new JBasicTextArea();
            dynamicRulePanel = new JPanel();
            dynamicRulePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            dynamicRulePanel.setLayout(new BorderLayout());
            dynamicRulePanel.add(new JBasicScrollPane(dynamicRuleTextArea), BorderLayout.CENTER);

            localRuleTextArea = new JBasicTextArea();
            localRuleTextArea.setEditable(false);
            localRulePanel = new JPanel();
            localRulePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            localRulePanel.setLayout(new BorderLayout());
            localRulePanel.add(new JBasicScrollPane(localRuleTextArea), BorderLayout.CENTER);

            ruleTabbedPane = new JBasicTabbedPane();
            ruleTabbedPane.addTab(ConsoleLocale.getString("label_dynamic_rule"), dynamicRulePanel, ConsoleLocale.getString("label_dynamic_rule"));
            ruleTabbedPane.addTab(ConsoleLocale.getString("label_local_rule"), localRulePanel, ConsoleLocale.getString("label_local_rule"));

            updateRuleButton = new JClassicButton(createUpdateRuleAction());
            updateRuleButton.setPreferredSize(new Dimension(updateRuleButton.getPreferredSize().width, 30));

            clearRuleButton = new JClassicButton(createClearRuleAction());
            updateRuleButton.setPreferredSize(new Dimension(clearRuleButton.getPreferredSize().width, 30));

            JPanel toolBar = new JPanel();
            toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.X_AXIS));
            toolBar.add(updateRuleButton);
            toolBar.add(clearRuleButton);
            ButtonManager.updateUI(toolBar);

            ruleInfoLabel = new JLabel(ConsoleLocale.getString("description_gray_rule_to_config_center"), IconFactory.getSwingIcon("question_message.png"), SwingConstants.LEADING);

            JPanel layoutPanel = new JPanel();
            layoutPanel.setLayout(new FiledLayout(FiledLayout.COLUMN, FiledLayout.FULL, 5));
            layoutPanel.add(ruleInfoLabel);
            layoutPanel.add(toolBar);

            JPanel panel = new JPanel();
            panel.setBorder(UIFactory.createTitledBorder(ConsoleLocale.getString("title_gray_rule_operation")));
            panel.setLayout(new BorderLayout());
            panel.add(ruleTabbedPane, BorderLayout.CENTER);
            panel.add(layoutPanel, BorderLayout.SOUTH);

            return panel;
        }

        @SuppressWarnings("unchecked")
        public void setGray(TGroup group) {
            this.group = group;
            this.node = null;

            boolean versionControlEnabled = ruleToConfigCenterRadioButtonMenuItem.isSelected();
            boolean ruleControlEnabled = ruleToConfigCenterRadioButtonMenuItem.isSelected();
            if (!versionControlEnabled && !ruleControlEnabled) {
                for (Iterator<TNode> iterator = group.children(); iterator.hasNext();) {
                    TNode node = iterator.next();
                    Instance instance = (Instance) node.getUserObject();

                    boolean versionEnabled = InstanceEntityWrapper.isDiscoveryControlEnabled(instance);
                    if (versionEnabled) {
                        versionControlEnabled = true;
                    }
                    boolean ruleEnabled = InstanceEntityWrapper.isDiscoveryControlEnabled(instance) && InstanceEntityWrapper.isConfigRestControlEnabled(instance);
                    if (ruleEnabled) {
                        ruleControlEnabled = true;
                    }
                }
            }

            if (versionTabbedPane.getTabCount() == 2) {
                versionTabbedPane.remove(localVersionPanel);
            }
            if (ruleTabbedPane.getTabCount() == 2) {
                ruleTabbedPane.remove(localRulePanel);
            }

            dynamicVersionTextField.setText(StringUtils.EMPTY);
            localVersionTextField.setText(StringUtils.EMPTY);
            updateVersionButton.setText(ConsoleLocale.getString("button_batch_update_version"));
            clearVersionButton.setText(ConsoleLocale.getString("button_batch_clear_version"));
            updateVersionButton.setEnabled(versionControlEnabled);
            clearVersionButton.setEnabled(versionControlEnabled);

            dynamicRuleTextArea.setText(StringUtils.EMPTY);
            localRuleTextArea.setText(StringUtils.EMPTY);
            updateRuleButton.setText(ConsoleLocale.getString("button_batch_update_rule"));
            clearRuleButton.setText(ConsoleLocale.getString("button_batch_clear_rule"));
            updateRuleButton.setEnabled(ruleControlEnabled);
            clearRuleButton.setEnabled(ruleControlEnabled);

            String ruleInfo = null;
            if (ruleToConfigCenterRadioButtonMenuItem.isSelected()) {
                String filter = getFilter(group);
                String serviceId = group.getUserObject().toString();
                String config = ServiceController.remoteConfigView(filter, serviceId);
                dynamicRuleTextArea.setText(config);
                ruleInfo = ConsoleLocale.getString("description_gray_rule_to_config_center");
            } else {
                ruleInfo = ConsoleLocale.getString("description_gray_rule_to_service");
            }

            ruleInfoLabel.setText(ruleInfo);
        }

        public void setGray(TNode node) {
            this.group = null;
            this.node = node;
            Instance instance = (Instance) node.getUserObject();

            boolean versionControlEnabled = InstanceEntityWrapper.isDiscoveryControlEnabled(instance);
            boolean ruleControlEnabled = InstanceEntityWrapper.isDiscoveryControlEnabled(instance) && InstanceEntityWrapper.isConfigRestControlEnabled(instance) && !ruleToConfigCenterRadioButtonMenuItem.isSelected();

            if (versionTabbedPane.getTabCount() == 1) {
                versionTabbedPane.addTab(ConsoleLocale.getString("label_local_version"), localVersionPanel, ConsoleLocale.getString("label_local_version"));
            }
            if (ruleTabbedPane.getTabCount() == 1) {
                ruleTabbedPane.addTab(ConsoleLocale.getString("label_local_rule"), localRulePanel, ConsoleLocale.getString("label_local_rule"));
            }

            dynamicVersionTextField.setText(instance.getDynamicVersion());
            localVersionTextField.setText(instance.getVersion());
            updateVersionButton.setText(ConsoleLocale.getString("button_update_version"));
            clearVersionButton.setText(ConsoleLocale.getString("button_clear_version"));
            updateVersionButton.setEnabled(versionControlEnabled);
            clearVersionButton.setEnabled(versionControlEnabled);

            dynamicRuleTextArea.setText(instance.getDynamicRule());
            localRuleTextArea.setText(instance.getRule());
            updateRuleButton.setText(ConsoleLocale.getString("button_update_rule"));
            clearRuleButton.setText(ConsoleLocale.getString("button_clear_rule"));
            updateRuleButton.setEnabled(ruleControlEnabled);
            clearRuleButton.setEnabled(ruleControlEnabled);

            String ruleInfo = null;
            if (ruleToConfigCenterRadioButtonMenuItem.isSelected()) {
                ruleInfo = ConsoleLocale.getString("description_gray_rule_to_config_center");
            } else {
                ruleInfo = ConsoleLocale.getString("description_gray_rule_to_service");
            }

            ruleInfoLabel.setText(ruleInfo);
        }

        private JSecurityAction createUpdateVersionAction() {
            JSecurityAction action = new JSecurityAction(ConsoleLocale.getString("button_update_version"), ConsoleIconFactory.getSwingIcon("save.png"), ConsoleLocale.getString("button_update_version")) {
                private static final long serialVersionUID = 1L;

                public void execute(ActionEvent e) {
                    String dynamicVersion = dynamicVersionTextField.getText();
                    if (StringUtils.isEmpty(dynamicVersion)) {
                        JBasicOptionPane.showMessageDialog(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("gray_version_not_null"), SwingLocale.getString("warning"), JBasicOptionPane.WARNING_MESSAGE);

                        return;
                    }

                    if (group != null) {
                        String serviceId = group.getUserObject().toString();
                        List<ResultEntity> results = null;
                        try {
                            results = ServiceController.versionUpdate(serviceId, dynamicVersion, pushAsyncModeRadioButtonMenuItem.isSelected());
                        } catch (Exception ex) {
                            JExceptionDialog.traceException(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("query_data_failure"), ex);

                            refreshGrayState(group);

                            return;
                        }

                        showResult(results);

                        refreshGrayState(group);
                    } else if (node != null) {
                        Instance instance = (Instance) node.getUserObject();

                        String result = null;
                        try {
                            result = ServiceController.versionUpdate(instance, dynamicVersion, pushAsyncModeRadioButtonMenuItem.isSelected());
                        } catch (Exception ex) {
                            JExceptionDialog.traceException(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("query_data_failure"), ex);

                            refreshGrayState(node);

                            return;
                        }

                        showResult(result);

                        refreshGrayState(node);
                    }
                }
            };

            return action;
        }

        private JSecurityAction createClearVersionAction() {
            JSecurityAction action = new JSecurityAction(ConsoleLocale.getString("button_clear_version"), ConsoleIconFactory.getSwingIcon("paint.png"), ConsoleLocale.getString("button_clear_version")) {
                private static final long serialVersionUID = 1L;

                public void execute(ActionEvent e) {
                    if (group != null) {
                        String serviceId = group.getUserObject().toString();
                        List<ResultEntity> results = null;
                        try {
                            results = ServiceController.versionClear(serviceId, pushAsyncModeRadioButtonMenuItem.isSelected());
                        } catch (Exception ex) {
                            JExceptionDialog.traceException(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("query_data_failure"), ex);

                            refreshGrayState(group);

                            return;
                        }

                        showResult(results);

                        refreshGrayState(group);
                    } else if (node != null) {
                        Instance instance = (Instance) node.getUserObject();

                        String result = null;
                        try {
                            result = ServiceController.versionClear(instance, pushAsyncModeRadioButtonMenuItem.isSelected());
                        } catch (Exception ex) {
                            JExceptionDialog.traceException(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("query_data_failure"), ex);

                            refreshGrayState(node);

                            return;
                        }

                        showResult(result);

                        refreshGrayState(node);
                    }
                }
            };

            return action;
        }

        private JSecurityAction createUpdateRuleAction() {
            JSecurityAction action = new JSecurityAction(ConsoleLocale.getString("button_update_rule"), ConsoleIconFactory.getSwingIcon("save.png"), ConsoleLocale.getString("button_update_rule")) {
                private static final long serialVersionUID = 1L;

                public void execute(ActionEvent e) {
                    String dynamicRule = dynamicRuleTextArea.getText();
                    if (StringUtils.isEmpty(dynamicRule)) {
                        JBasicOptionPane.showMessageDialog(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("gray_rule_not_null"), SwingLocale.getString("warning"), JBasicOptionPane.WARNING_MESSAGE);

                        return;
                    }

                    if (group != null) {
                        String serviceId = group.getUserObject().toString();

                        if (ruleToConfigCenterRadioButtonMenuItem.isSelected()) {
                            String filter = getFilter(group);
                            String result = null;
                            try {
                                result = ServiceController.remoteConfigUpdate(filter, serviceId, dynamicRule);
                            } catch (Exception ex) {
                                JExceptionDialog.traceException(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("query_data_failure"), ex);

                                refreshGrayState(group);

                                return;
                            }

                            showResult(result);
                        } else {
                            List<ResultEntity> results = null;
                            try {
                                results = ServiceController.configUpdate(serviceId, dynamicRule, pushAsyncModeRadioButtonMenuItem.isSelected());
                            } catch (Exception ex) {
                                JExceptionDialog.traceException(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("query_data_failure"), ex);

                                refreshGrayState(group);

                                return;
                            }

                            showResult(results);
                        }

                        refreshGrayState(group);
                    } else if (node != null) {
                        Instance instance = (Instance) node.getUserObject();

                        String result = null;
                        try {
                            result = ServiceController.configUpdate(instance, dynamicRule, pushAsyncModeRadioButtonMenuItem.isSelected());
                        } catch (Exception ex) {
                            JExceptionDialog.traceException(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("query_data_failure"), ex);

                            refreshGrayState(node);

                            return;
                        }

                        showResult(result);

                        refreshGrayState(node);
                    }
                }
            };

            return action;
        }

        private JSecurityAction createClearRuleAction() {
            JSecurityAction action = new JSecurityAction(ConsoleLocale.getString("button_clear_rule"), ConsoleIconFactory.getSwingIcon("paint.png"), ConsoleLocale.getString("button_clear_rule")) {
                private static final long serialVersionUID = 1L;

                public void execute(ActionEvent e) {
                    if (group != null) {
                        String serviceId = group.getUserObject().toString();
                        if (ruleToConfigCenterRadioButtonMenuItem.isSelected()) {
                            String filter = getFilter(group);
                            String result = null;
                            try {
                                result = ServiceController.remoteConfigClear(filter, serviceId);
                            } catch (Exception ex) {
                                JExceptionDialog.traceException(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("query_data_failure"), ex);

                                refreshGrayState(group);

                                return;
                            }

                            showResult(result);
                        } else {
                            List<ResultEntity> results = null;
                            try {
                                results = ServiceController.configClear(serviceId, pushAsyncModeRadioButtonMenuItem.isSelected());
                            } catch (Exception ex) {
                                JExceptionDialog.traceException(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("query_data_failure"), ex);

                                refreshGrayState(group);

                                return;
                            }

                            showResult(results);
                        }

                        refreshGrayState(group);
                    } else if (node != null) {
                        Instance instance = (Instance) node.getUserObject();

                        String result = null;
                        try {
                            result = ServiceController.configClear(instance, pushAsyncModeRadioButtonMenuItem.isSelected());
                        } catch (Exception ex) {
                            JExceptionDialog.traceException(HandleManager.getFrame(ServiceTopology.this), ConsoleLocale.getString("query_data_failure"), ex);

                            refreshGrayState(node);

                            return;
                        }

                        showResult(result);

                        refreshGrayState(node);
                    }
                }
            };

            return action;
        }
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
            groupPanel.setBorder(UIFactory.createTitledBorder(ConsoleLocale.getString("group_layout")));
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
            nodePanel.setBorder(UIFactory.createTitledBorder(ConsoleLocale.getString("node_layout")));
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
            groupStartXTextField.setText(groupLocationEntity.getStartX() + StringUtils.EMPTY);
            groupStartYTextField.setText(groupLocationEntity.getStartY() + StringUtils.EMPTY);
            groupHorizontalGapTextField.setText(groupLocationEntity.getHorizontalGap() + StringUtils.EMPTY);
            groupVerticalGapTextField.setText(groupLocationEntity.getVerticalGap() + StringUtils.EMPTY);

            nodeStartXTextField.setText(nodeLocationEntity.getStartX() + StringUtils.EMPTY);
            nodeStartYTextField.setText(nodeLocationEntity.getStartY() + StringUtils.EMPTY);
            nodeHorizontalGapTextField.setText(nodeLocationEntity.getHorizontalGap() + StringUtils.EMPTY);
            nodeVerticalGapTextField.setText(nodeLocationEntity.getVerticalGap() + StringUtils.EMPTY);
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

            groupLocationEntity.setStartX(groupStartX);
            groupLocationEntity.setStartY(groupStartY);
            groupLocationEntity.setHorizontalGap(groupHorizontalGap);
            groupLocationEntity.setVerticalGap(groupVerticalGap);

            nodeLocationEntity.setStartX(nodeStartX);
            nodeLocationEntity.setStartY(nodeStartY);
            nodeLocationEntity.setHorizontalGap(nodeHorizontalGap);
            nodeLocationEntity.setVerticalGap(nodeVerticalGap);

            return true;
        }
    }
}
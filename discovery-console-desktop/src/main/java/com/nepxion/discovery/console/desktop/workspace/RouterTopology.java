package com.nepxion.discovery.console.desktop.workspace;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import twaver.BlinkingRule;
import twaver.Element;
import twaver.Generator;
import twaver.TWaverConst;

import java.awt.Color;
import java.util.List;

import javax.swing.Box;
import javax.swing.JToolBar;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.nepxion.cots.twaver.element.TLink;
import com.nepxion.cots.twaver.element.TNode;
import com.nepxion.cots.twaver.graph.TGraphControlBar;
import com.nepxion.cots.twaver.graph.TGraphManager;
import com.nepxion.discovery.console.desktop.entity.RouterEntity;
import com.nepxion.discovery.console.desktop.workspace.topology.AbstractTopology;
import com.nepxion.discovery.console.desktop.workspace.topology.TopologyEntity;
import com.nepxion.discovery.console.desktop.workspace.topology.TopologyEntityType;
import com.nepxion.swing.button.ButtonManager;
import com.nepxion.swing.button.JBasicToggleButton;

public class RouterTopology extends AbstractTopology {
    private static final long serialVersionUID = 1L;

    private int nodeStartX = 300;
    private int nodeStartY = 150;
    private int nodeHorizontalGap = 200;
    private int nodeVerticalGap = 0;

    private TopologyEntity serviceNodeEntity = new TopologyEntity(TopologyEntityType.SERVICE, true, true);

    public RouterTopology() {
        initializeToolBar();
        initializeTopology();
    }

    private void initializeToolBar() {
        JToolBar toolBar = getGraph().getToolbar();
        toolBar.addSeparator();
        toolBar.add(Box.createHorizontalStrut(5));

        ButtonManager.updateUI(toolBar);
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

        TGraphControlBar graphControlBar = (TGraphControlBar) graph.getControlBarInternalFrame().getContent();
        JBasicToggleButton toggleButton = (JBasicToggleButton) graphControlBar.getViewToolBar().getViewOutlook().getComponent(10);
        toggleButton.setSelected(true);

        TGraphManager.layout(graph);
    }

    public void route(RouterEntity routerEntity) {
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
                TNode nextNode = addNode(next, index);
                addLink(node, nextNode);

                index++;

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

        TNode node = createNode(nodeName, serviceNodeEntity, index, nodeStartX, nodeStartY, nodeHorizontalGap, nodeVerticalGap);
        node.setUserObject(routerEntity);

        dataBox.addElement(node);

        return node;
    }

    private void addLink(TNode fromNode, TNode toNode) {
        TLink link = createLink(fromNode, toNode, true);
        link.putLinkToArrowColor(Color.yellow);

        dataBox.addElement(link);
    }
}
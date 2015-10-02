package com.mbzshajib.mining.processor.uncertain.model;

import com.mbzshajib.mining.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/21/2015
 * @time: 3:59 PM
 * ****************************************************************
 */


public class HeaderTableItem {
    private String itemId;
    private List<UNode> nodeList;

    public HeaderTableItem(String itemId) {
        this.itemId = itemId;
        nodeList = new ArrayList<UNode>();
    }


    public void updateHeaderData(UNode uNode) {
        nodeList.add(uNode);
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public List<UNode> getNodeList() {
        return nodeList;
    }

    public double getItemPrefixValue() {
        double result = 0;
        for (UNode node : nodeList) {
            node.getNodePrefixValue();
        }
        return result;
    }

    public double getItemProbabilityValue() {
        double result = 0;
        for (UNode node : nodeList) {
            node.getItemProbabilityValue();
        }
        return result;
    }

    public void setNodeList(List<UNode> nodeList) {
        this.nodeList = nodeList;
    }

    public String traverse() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constant.HASH)
                .append("ID ")
                .append(itemId)
                .append(Constant.TABBED_HASH)
                .append("Data { ").append(Constant.TAB);

        int index = 0;
        for (UNode node : nodeList) {
            stringBuilder.append(Constant.HASH)
                    .append(node.toString()).append(Constant.TAB);
            index++;
        }
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "HeaderTableItem{" +
                "itemId='" + itemId + '\'' +
                ", nodeCount=" + nodeList.size() +
                '}';
    }
}
